package lang.compiler.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lang.compiler.ast.*;
import lang.compiler.ast.commands.*;
import lang.compiler.ast.literals.*;
import lang.compiler.ast.lvalues.*;
import lang.compiler.ast.miscellaneous.*;
import lang.compiler.ast.operators.binary.*;
import lang.compiler.ast.operators.binary.Module;
import lang.compiler.ast.operators.unary.*;
import lang.compiler.ast.types.*;

public class TypeCheckVisitor extends AstVisitor {
  private BoolType boolType;
  private CharType charType;
  private FloatType floatType;
  private IntType intType;
  private ErrorLogger logger;
  private Map<String, CustomType> customTypes;
  private Map<Function, LocalEnvironment> functionsEnv;
  private LocalEnvironment currentEnv;

  public TypeCheckVisitor(ErrorLogger logger) {
    this.boolType = new BoolType(0, 0);
    this.charType = new CharType(0, 0);
    this.floatType = new FloatType(0, 0);
    this.intType = new IntType(0, 0);
    this.logger = logger;
    this.customTypes = new HashMap<>();
    this.functionsEnv = new HashMap<>();
  }

  public Void visitProgram(Program program) {
    // Store all functions local environment
    for (Function f : program.getFunctionSet()) {
      LocalEnvironment localEnv = new LocalEnvironment(f);

      for (Parameter param : f.getParameters()) {
        localEnv.addParameterType(param.getType());
        localEnv.addVarType(param.getId().getName(), param.getType());
      }

      for (AbstractType returnType : f.getReturnTypes())
        localEnv.addReturnType(returnType);

      functionsEnv.put(f, localEnv);
    }

    for (Data d : program.getDataSet())
      d.accept(this);

    for (Function f : program.getFunctionSet())
      f.accept(this);

    return null;
  }

  public Object visitFunction(Function f) {
    currentEnv = functionsEnv.get(f);

    if (f.getId().getName().equals("main") && f.getParameters().size() > 0)
      logger.addGenericError(f,
        "\t" + f.getLine() + ":" + f.getColumn() + ": Function \"main\" cannot contain parameters"
      );

    for (AbstractCommand cmd : f.getCommands()) {
      Object result = cmd.accept(this);

      if (cmd instanceof Return) {
        List<AbstractType> returnTypes = (List<AbstractType>) result;

        if (returnTypes.size() == currentEnv.getReturnsTypes().size()) {
          for (int i = 0; i < returnTypes.size(); i++) {
            AbstractType actualType = returnTypes.get(i);
            AbstractType expectedType = currentEnv.getReturnsTypes().get(i);

            if (actualType != null && !actualType.match(expectedType)) {
              logger.addInvalidExpressionTypeError(
                currentEnv.getFunction(), ((Return) cmd).getExpressions().get(i), expectedType
              );
            }
          }
        }
        else {
          logger.addIncompatibleReturnSizesError(f, (Return) cmd);
        }
      }
    }

    return null;
  }

  public Object visitAddition(Addition add) {
    AbstractType leftExprType = (AbstractType) add.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) add.getRight().accept(this);

    if (leftExprType.match(intType) && (rightExprType.match(intType) || rightExprType.match(floatType)))
      return rightExprType;
    else if (leftExprType.match(floatType) && (rightExprType.match(intType) || rightExprType.match(floatType)))
      return leftExprType;

    logger.addBinaryOperatorError(currentEnv.getFunction(), add, leftExprType, rightExprType);
    return null;
  }

  public Object visitPrint(Print printCmd) {
    return printCmd.getExpression().accept(this);
  }

  public Object visitBoolLiteral(BoolLiteral b) {
    return boolType;
  }

  public Object visitCharLiteral(CharLiteral c) {
    return charType;
  }

  public Object visitIntLiteral(IntLiteral i) {
    return intType;
  }

  public Object visitFloatLiteral(FloatLiteral f) {
    return floatType;
  }

  @Override
  public Object visitAnd(And and) {
    AbstractType leftExprType = (AbstractType) and.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) and.getRight().accept(this);

    if (leftExprType.match(boolType) && rightExprType.match(boolType))
      return leftExprType;

    logger.addBinaryOperatorError(currentEnv.getFunction(), and, leftExprType, rightExprType);
    return null;
  }

  @Override
  public Object visitArrayAccess(ArrayAccess arrayAccess) {
    if (arrayAccess.getExpression() instanceof IntLiteral) {
      AbstractType type = (AbstractType) arrayAccess.getLvalue().accept(this);

      if (type instanceof ArrayType)
        return ((ArrayType) type).getType();
      else
        logger.addUndefinedLvalueError(currentEnv.getFunction(), arrayAccess);
    }
    else {
      logger.addUndefinedIndexError(currentEnv.getFunction(), arrayAccess.getExpression());
    }

    return null;
  }

  @Override
  public Object visitArrayType(ArrayType arrayType) {
    return null;
  }

  @Override
  public Object visitAssignableFunctionCall(AssignableFunctionCall fCall) {
    Function callee = null;

    for (Function f : functionsEnv.keySet()) {
      if (f.getId().getName().equals(fCall.getId().getName())) {
        if (fCall.getArgs().size() == f.getParameters().size()) {
          boolean match = true;

          // Check parameters compatibility
          for (int i = 0; i < f.getParameters().size(); i++) {
            AbstractType argType = (AbstractType) fCall.getArgs().get(i).accept(this);
            AbstractType parameterType = (AbstractType) functionsEnv.get(f).getParamsTypes().get(i);

            // Incompatible types
            if (argType != null && !argType.match(parameterType))
              match = false;
          }

          if (match)
            callee = f;
        }
      }
    }

    if (callee != null) {
      LocalEnvironment localEnv = functionsEnv.get(callee);
      AbstractType indexType = (AbstractType) fCall.getIndex().accept(this);

      if (indexType.match(intType) && fCall.getIndex() instanceof IntLiteral) {
        IntLiteral index = (IntLiteral) fCall.getIndex();

        if (index.getValue() < localEnv.getReturnsTypes().size())
          return localEnv.getReturnsTypes().get(index.getValue());
        else
          logger.addIndexOutOfRangeError(currentEnv.getFunction(), fCall.getIndex());
      }
      else {
        logger.addUndefinedIndexError(currentEnv.getFunction(), fCall.getIndex());
      }
    }
    else {
      logger.addInvalidFunctionCallError(currentEnv.getFunction(), fCall);
    }

    return null;
  }

  @Override
  public Object visitAssignment(Assignment assignment) {
    AbstractLvalue lvalue = assignment.getLvalue();
    AbstractType assignedType = (AbstractType) assignment.getExpression().accept(this);

    if (!currentEnv.getVarsTypes().containsKey(lvalue.getIdentifier().getName())) {
      if (lvalue instanceof ArrayAccess || lvalue instanceof DataIdentifierAccess)
        logger.addUndefinedLvalueError(currentEnv.getFunction(), lvalue.getIdentifier());
      else if (assignedType == null)
        logger.addInvalidAssignmentError(
          currentEnv.getFunction(), assignment, assignedType, (AbstractType) lvalue.accept(this)
        );
      else
        currentEnv.getVarsTypes().put(lvalue.getIdentifier().getName(), assignedType);
    }
    else {
      AbstractType expectedType = (AbstractType) assignment.getLvalue().accept(this);

      if ((assignedType != null && !assignedType.match(expectedType)) ||
          (assignedType == null && !(expectedType instanceof ArrayType || expectedType instanceof CustomType)))
        logger.addInvalidAssignmentError(currentEnv.getFunction(), assignment, assignedType, expectedType);
    }

    return null;
  }

  @Override
  public Object visitBalancedParentheses(BalancedParenthesesExpression balanced) {
    return balanced.getExpression().accept(this);
  }

  @Override
  public Object visitBoolType(BoolType boolType) {
    return null;
  }

  @Override
  public Object visitCharType(CharType charType) {
    return null;
  }

  @Override
  public Object visitCommandScope(CommandScope cmdScope) {
    for (AbstractCommand cmd : cmdScope.getCommmands())
      cmd.accept(this);

    return null;
  }

  @Override
  public Object visitCustomType(CustomType customType) {
    return null;
  }

  @Override
  public Object visitData(Data data) {
    boolean hasInvalidType = false;
    if (!customTypes.containsKey(data.getType().toString())) {
      for(AbstractType type : data.getType().getAllTypes()) {
        if (!(type.match(intType) || type.match(floatType) ||
              type.match(boolType) || type.match(charType))){
          hasInvalidType = true;
          logger.addGenericError(data.getType(),
            data.getLine() + ":" + data.getColumn() + ": Invalid type \"" + type.toString() +
            "\" found on custom data \"" + data.getType().toString() + "\"");
        }
      }
      if(!hasInvalidType)
        customTypes.put(data.getType().toString(), data.getType());
    }
    else
      logger.addGenericError(data.getType(),
        data.getLine() + ":" + data.getColumn() + ": Redefinition of data \"" + data.getType().toString() + "\""
      );

    return null;
  }

  @Override
  public Object visitDataIdentifierAccess(DataIdentifierAccess dataIdentifierAccess) {
    String name = dataIdentifierAccess.getLvalue().getIdentifier().getName();
    CustomType customType = (CustomType) currentEnv.getVarsTypes().get(name);

    if (customType.hasVar(dataIdentifierAccess.getId().getName()))
      return customType.getVarType(dataIdentifierAccess.getId().getName());
    else
      logger.addGenericError(currentEnv.getFunction(),
        "\t" + dataIdentifierAccess.getLine() + ":" + dataIdentifierAccess.getColumn() +
        ": Variable \"" + name + "\" does not have a property named \"" + dataIdentifierAccess.getId().getName() + "\""
      );

    return null;
  }

  @Override
  public Object visitDeclaration(Declaration decl) {
    return null;
  }

  @Override
  public Object visitDivision(Division div) {
    AbstractType leftExprType = (AbstractType) div.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) div.getRight().accept(this);

    if (leftExprType.match(intType) && (rightExprType.match(intType) || rightExprType.match(floatType)))
      return rightExprType;
    else if (leftExprType.match(floatType) && (rightExprType.match(intType) || rightExprType.match(floatType)))
      return leftExprType;

    logger.addBinaryOperatorError(currentEnv.getFunction(), div, leftExprType, rightExprType);
    return null;
  }

  @Override
  public Object visitEqual(Equal eq) {
    AbstractType leftExprType = (AbstractType) eq.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) eq.getRight().accept(this);

    // Can only compare expressions of same type
    if (leftExprType.match(rightExprType))
      return leftExprType;

    logger.addBinaryOperatorError(currentEnv.getFunction(), eq, leftExprType, rightExprType);
    return null;
  }

  @Override
  public Object visitFloatType(FloatType floatType) {
    return null;
  }

  @Override
  public Object visitIdentifier(Identifier id) {
    return currentEnv.getVarsTypes().get(id.getName());
  }

  @Override
  public Object visitIf(If ifCmd) {
    AbstractType exprType = (AbstractType) ifCmd.getExpression().accept(this);

    if (exprType.match(boolType))
      ifCmd.getScopeCommand().accept(this);
    else
      logger.addInvalidExpressionTypeError(currentEnv.getFunction(), ifCmd.getExpression(), boolType);

    return null;
  }

  @Override
  public Object visitIfElse(IfElse ifElseCmd) {
    AbstractType exprType = (AbstractType) ifElseCmd.getExpression().accept(this);

    if (exprType.match(boolType)) {
      ifElseCmd.getIfScopeCommand().accept(this);
      ifElseCmd.getElseScopeCommand().accept(this);
    }
    else {
      logger.addInvalidExpressionTypeError(currentEnv.getFunction(), ifElseCmd.getExpression(), boolType);
    }

    return null;
  }

  @Override
  public Object visitIntType(IntType intType) {
    return null;
  }

  @Override
  public Object visitIterate(Iterate iterateCmd) {
    AbstractType exprType = (AbstractType) iterateCmd.getExpression().accept(this);

    if (exprType.match(intType))
      iterateCmd.getCommand().accept(this);
    else
      logger.addInvalidExpressionTypeError(currentEnv.getFunction(), iterateCmd.getExpression(), intType);

    return null;
  }

  @Override
  public Object visitLessThan(LessThan lt) {
    AbstractType leftExprType = (AbstractType) lt.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) lt.getRight().accept(this);

    if ((leftExprType.match(intType) || leftExprType.match(charType)) &&
        (rightExprType.match(intType) || rightExprType.match(charType)))
      return boolType;

    logger.addBinaryOperatorError(currentEnv.getFunction(), lt, leftExprType, rightExprType);
    return null;
  }

  @Override
  public Object visitModule(Module mod) {
    AbstractType leftExprType = (AbstractType) mod.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) mod.getRight().accept(this);

    if (leftExprType.match(intType) && (rightExprType.match(intType) || rightExprType.match(floatType)))
      return rightExprType;
    else if (leftExprType.match(floatType) && (rightExprType.match(intType) || rightExprType.match(floatType)))
      return leftExprType;

    logger.addBinaryOperatorError(currentEnv.getFunction(), mod, leftExprType, rightExprType);
    return null;
  }

  @Override
  public Object visitMultiplication(Multiplication mult) {
    AbstractType leftExprType = (AbstractType) mult.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) mult.getRight().accept(this);

    if (leftExprType.match(intType) && (rightExprType.match(intType) || rightExprType.match(floatType)))
      return rightExprType;
    else if (leftExprType.match(floatType) && (rightExprType.match(intType) || rightExprType.match(floatType)))
      return leftExprType;

    logger.addBinaryOperatorError(currentEnv.getFunction(), mult, leftExprType, rightExprType);
    return null;
  }

  @Override
  public Object visitNegate(Negate neg) {
    AbstractType exprType = (AbstractType) neg.getExpression().accept(this);

    // Negating booleans or chars makes no sense
    if (!exprType.match(boolType) && !exprType.match(charType))
      return exprType;

    logger.addUnaryOperatorError(currentEnv.getFunction(), neg, exprType);
    return null;
  }

  @Override
  public Object visitNew(New newCmd) {
    if (newCmd.getExpression() == null || newCmd.getExpression() instanceof IntLiteral) {
      if (newCmd.getType() instanceof CustomType) {
        CustomType customType = customTypes.get(newCmd.getType().toString());

        if (customType != null)
          return customType;
        else
          logger.addUndefinedDataError(customType);
      }
      else {
        return newCmd.getType();
      }
    }
    else {
      logger.addUndefinedArraySizeDeclarationError(currentEnv.getFunction(), newCmd.getExpression());
    }

    return null;
  }

  @Override
  public Object visitNot(Not not) {
    AbstractType exprType = (AbstractType) not.getExpression().accept(this);

    // 'Not' operator can only be applied to boolean value
    if (exprType.match(boolType))
      return exprType;

    logger.addUnaryOperatorError(currentEnv.getFunction(), not, exprType);
    return null;
  }

  @Override
  public Object visitNotEqual(NotEqual neq) {
    AbstractType leftExprType = (AbstractType) neq.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) neq.getRight().accept(this);

    // Can only compare expressions of same type
    if (leftExprType.match(rightExprType))
      return leftExprType;

    logger.addBinaryOperatorError(currentEnv.getFunction(), neq, leftExprType, rightExprType);
    return null;
  }

  @Override
  public Object visitNullLiteral(NullLiteral n) {
    return null;
  }

  @Override
  public Object visitParameter(Parameter param) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitRead(Read readCmd) {
    AbstractLvalue lvalue = readCmd.getLvalue();
    AbstractType assignedType = intType;

    if (!currentEnv.getVarsTypes().containsKey(lvalue.getIdentifier().getName())) {
      if (readCmd.getLvalue() instanceof ArrayAccess)
        logger.addUndefinedLvalueError(currentEnv.getFunction(), lvalue.getIdentifier());
      else
        currentEnv.getVarsTypes().put(lvalue.getIdentifier().getName(), assignedType);
    }
    else {
      AbstractType lvalueType = (AbstractType) lvalue.accept(this);

      if (assignedType != null && !assignedType.match(lvalueType))
        logger.addInvalidAssignmentError(currentEnv.getFunction(), readCmd, assignedType, lvalueType);
    }

    return null;
  }

  @Override
  public Object visitReturn(Return returnCmd) {
    List<AbstractType> returnTypes = new ArrayList<>();

    for (AbstractExpression expr : returnCmd.getExpressions())
      returnTypes.add((AbstractType) expr.accept(this));

    return returnTypes;
  }

  @Override
  public Object visitStaticFunctionCall(StaticFunctionCall fCall) {
    Function callee = null;

    for (Function f : functionsEnv.keySet()) {
      if (f.getId().getName().equals(fCall.getId().getName())) {
        if (fCall.getArgs().size() == f.getParameters().size()) {
          boolean match = true;

          // Check parameters compatibility
          for (int i = 0; i < f.getParameters().size(); i++) {
            AbstractType argType = (AbstractType) fCall.getArgs().get(i).accept(this);
            AbstractType parameterType = functionsEnv.get(f).getParamsTypes().get(i);

            // Incompatible types
            if (argType != null && !argType.match(parameterType))
              match = false;
          }

          if (match)
            callee = f;
        }
      }
    }

    if (callee != null) {
      LocalEnvironment localEnv = functionsEnv.get(callee);

      for (int i = 0; i < fCall.getLvalues().size() && i < localEnv.getReturnsTypes().size(); i++) {
        AbstractLvalue lvalue = fCall.getLvalues().get(i);
        AbstractType expectedType = localEnv.getReturnsTypes().get(i);

        if (!currentEnv.getVarsTypes().containsKey(lvalue.getIdentifier().getName())) {
          if (lvalue instanceof ArrayAccess)
            logger.addUndefinedLvalueError(currentEnv.getFunction(), lvalue.getIdentifier());
          else
            currentEnv.getVarsTypes().put(lvalue.getIdentifier().getName(), expectedType);
        }
        else {
          AbstractType actualType = (AbstractType) lvalue.accept(this);

          if (!actualType.match(expectedType))
            logger.addInvalidAssignmentError(currentEnv.getFunction(), fCall, expectedType, actualType);
        }
      }
    }
    else {
      logger.addInvalidFunctionCallError(currentEnv.getFunction(), fCall);
    }

    return null;
  }

  @Override
  public Object visitSubtraction(Subtraction sub) {
    AbstractType leftExprType = (AbstractType) sub.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) sub.getRight().accept(this);

    if (leftExprType.match(intType) && (rightExprType.match(intType) || rightExprType.match(floatType)))
      return rightExprType;
    else if (leftExprType.match(floatType) && (rightExprType.match(intType) || rightExprType.match(floatType)))
      return leftExprType;

    logger.addBinaryOperatorError(currentEnv.getFunction(), sub, leftExprType, rightExprType);
    return null;
  }
}
