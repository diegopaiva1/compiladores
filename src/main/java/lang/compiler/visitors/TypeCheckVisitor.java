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
  private Program program;
  private BoolType boolType;
  private CharType charType;
  private FloatType floatType;
  private IntType intType;
  private Map<String, Data> dataTypes;
  private Function currentFunction;

  public TypeCheckVisitor() {
    this.boolType = new BoolType(0, 0);
    this.charType = new CharType(0, 0);
    this.floatType = new FloatType(0, 0);
    this.intType = new IntType(0, 0);
    this.dataTypes = new HashMap<>();
  }

  public Void visitProgram(Program program) {
    this.program = program;

    for (Data d : program.getDataSet())
      d.accept(this);

    for (Function f : program.getFunctionSet())
      f.accept(this);

    return null;
  }

  public Object visitFunction(Function f) {
    currentFunction = f;
    program.getEnv().get(f).push(new SymbolTable());

    if (f.getId().getName().equals("main") && f.getParameters().size() > 0)
      program.getLogger().addGenericError(f,
        "\t" + f.getLine() + ":" + f.getColumn() + ": Function \"main\" cannot contain parameters"
      );

    for (Parameter param : f.getParameters())
      program.getEnv().get(f).put(param.getId().getName(), new Symbol(param.getType()));

    boolean hasReturnCmd = false;

    for (AbstractCommand cmd : f.getCommands()) {
      Object result = cmd.accept(this);

      if (cmd instanceof Return) {
        hasReturnCmd = true;
        List<AbstractType> returnTypes = (List<AbstractType>) result;

        if (returnTypes.size() == f.getReturnTypes().size()) {
          for (int i = 0; i < returnTypes.size(); i++) {
            AbstractType actualType = returnTypes.get(i);
            AbstractType expectedType = f.getReturnTypes().get(i);

            if (actualType != null && !actualType.match(expectedType)) {
              program.getLogger().addInvalidExpressionTypeError(
                f, ((Return) cmd).getExpressions().get(i), expectedType
              );
            }
          }
        }
        else {
          program.getLogger().addIncompatibleReturnSizesError(f, (Return) cmd);
        }
      }
    }

    if (f.getReturnTypes().size() > 0 && !hasReturnCmd)
      program.getLogger().addNoReturnCommandError(f);

    program.getEnv().get(f).pop();
    return null;
  }

  public Object visitAddition(Addition add) {
    AbstractType leftExprType = (AbstractType) add.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) add.getRight().accept(this);

    if (leftExprType.match(intType) && (rightExprType.match(intType) || rightExprType.match(floatType)))
      return rightExprType;
    else if (leftExprType.match(floatType) && (rightExprType.match(intType) || rightExprType.match(floatType)))
      return leftExprType;

    program.getLogger().addBinaryOperatorError(currentFunction, add, leftExprType, rightExprType);
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

    program.getLogger().addBinaryOperatorError(currentFunction, and, leftExprType, rightExprType);
    return null;
  }

  @Override
  public Object visitArrayAccess(ArrayAccess arrayAccess) {
    if (arrayAccess.getExpression() instanceof IntLiteral) {
      AbstractType type = (AbstractType) arrayAccess.getLvalue().accept(this);

      if (type instanceof ArrayType)
        return ((ArrayType) type).getType();
      else
        program.getLogger().addUndefinedLvalueError(currentFunction, arrayAccess);
    }
    else {
      program.getLogger().addUndefinedIndexError(currentFunction, arrayAccess.getExpression());
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

    for (Function f : program.getEnv().keySet()) {
      if (f.getId().getName().equals(fCall.getId().getName())) {
        if (fCall.getArgs().size() == f.getParameters().size()) {
          boolean match = true;

          // Check parameters compatibility
          for (int i = 0; i < f.getParameters().size(); i++) {
            AbstractType argType = (AbstractType) fCall.getArgs().get(i).accept(this);
            AbstractType parameterType = (AbstractType) f.getParameters().get(i).getType();

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
      AbstractType indexType = (AbstractType) fCall.getIndex().accept(this);

      if (indexType.match(intType) && fCall.getIndex() instanceof IntLiteral) {
        IntLiteral index = (IntLiteral) fCall.getIndex();

        if (index.getValue() < callee.getParameters().size())
          return callee.getReturnTypes().get(index.getValue());
        else
          program.getLogger().addIndexOutOfRangeError(currentFunction, fCall.getIndex());
      }
      else {
        program.getLogger().addUndefinedIndexError(currentFunction, fCall.getIndex());
      }
    }
    else {
      program.getLogger().addInvalidFunctionCallError(currentFunction, fCall);
    }

    return null;
  }

  @Override
  public Object visitAssignment(Assignment assignment) {
    AbstractLvalue lvalue = assignment.getLvalue();
    AbstractType assignedType = (AbstractType) assignment.getExpression().accept(this);
    Symbol symbol = program.getEnv().get(currentFunction).search(lvalue.getIdentifier().getName());

    if (symbol == null) {
      if (lvalue instanceof ArrayAccess || lvalue instanceof DataIdentifierAccess)
        program.getLogger().addUndefinedLvalueError(currentFunction, lvalue.getIdentifier());
      else if (assignedType == null)
        program.getLogger().addInvalidAssignmentError(
          currentFunction, assignment, assignedType, (AbstractType) lvalue.accept(this)
        );
      else
        program.getEnv().get(currentFunction).put(lvalue.getIdentifier().getName(), new Symbol(assignedType));
    }
    else {
      AbstractType expectedType = (AbstractType) assignment.getLvalue().accept(this);

      if ((assignedType != null && !assignedType.match(expectedType)) ||
          (assignedType == null && !(expectedType instanceof ArrayType || expectedType instanceof DataType)))
        program.getLogger().addInvalidAssignmentError(currentFunction, assignment, assignedType, expectedType);
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
    program.getEnv().get(currentFunction).push(new SymbolTable());

    for (AbstractCommand cmd : cmdScope.getCommmands())
      cmd.accept(this);

    program.getEnv().get(currentFunction).pop();
    return null;
  }

  @Override
  public Object visitDataType(DataType dataType) {
    return null;
  }

  @Override
  public Object visitData(Data data) {
    if (!dataTypes.containsKey(data.getType().toString())) {
      dataTypes.put(data.getType().toString(), data);

      for (Map.Entry<String, AbstractType> properties : data.getProperties().entrySet()) {
        String name = properties.getKey();
        AbstractType type = properties.getValue();

       /* Parser guarantees that properties have one of the primitive types or a user defined type.
        * Thus we only need to check if in case it's a user defined type it has already been declared.
        */
        if (type instanceof DataType && !dataTypes.containsKey(type.toString()))
          program.getLogger().addInvalidPropertyTypeError(data, name, type);
      }
    }
    else {
      program.getLogger().addDataRedefinitionError(data);
    }

    return null;
  }

  @Override
  public Object visitDataIdentifierAccess(DataIdentifierAccess dataIdentifierAccess) {
    AbstractLvalue lvalue = dataIdentifierAccess.getLvalue();
    Symbol symbol = program.getEnv().get(currentFunction).search(lvalue.getIdentifier().getName());

    if (symbol != null) {
      AbstractType type = symbol.getType();

      while (type instanceof ArrayType)
        type = (ArrayType) symbol.getType();

      Data data = dataTypes.get(type.toString());
      String propertyName = dataIdentifierAccess.getId().getName();

      if (data.getProperties().containsKey(propertyName))
        return data.getProperties().get(propertyName);
      else
        program.getLogger().addGenericError(currentFunction,
          "\t" + dataIdentifierAccess.getLine() + ":" + dataIdentifierAccess.getColumn() +
          ": Variable \"" + lvalue.getIdentifier().getName() + "\" does not have a property named \"" + propertyName + "\""
        );
    }
    else {
      program.getLogger().addUndefinedLvalueError(currentFunction, lvalue);
    }

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

    program.getLogger().addBinaryOperatorError(currentFunction, div, leftExprType, rightExprType);
    return null;
  }

  @Override
  public Object visitEqual(Equal eq) {
    AbstractType leftExprType = (AbstractType) eq.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) eq.getRight().accept(this);

    // Can only compare expressions of same type
    if (leftExprType.match(rightExprType))
      return boolType;

    program.getLogger().addBinaryOperatorError(currentFunction, eq, leftExprType, rightExprType);
    return null;
  }

  @Override
  public Object visitFloatType(FloatType floatType) {
    return null;
  }

  @Override
  public Object visitIdentifier(Identifier id) {
    Symbol symbol = program.getEnv().get(currentFunction).search(id.getName());

    if (symbol != null)
      return symbol.getType();

    program.getLogger().addUndefinedLvalueError(currentFunction, id);
    return null;
  }

  @Override
  public Object visitIf(If ifCmd) {
    AbstractType exprType = (AbstractType) ifCmd.getExpression().accept(this);

    if (exprType.match(boolType))
      ifCmd.getScopeCommand().accept(this);
    else
      program.getLogger().addInvalidExpressionTypeError(currentFunction, ifCmd.getExpression(), boolType);

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
      program.getLogger().addInvalidExpressionTypeError(currentFunction, ifElseCmd.getExpression(), boolType);
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

    if (exprType != null && exprType.match(intType))
      iterateCmd.getCommand().accept(this);
    else
      program.getLogger().addInvalidExpressionTypeError(currentFunction, iterateCmd.getExpression(), intType);

    return null;
  }

  @Override
  public Object visitLessThan(LessThan lt) {
    AbstractType leftExprType = (AbstractType) lt.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) lt.getRight().accept(this);

    if ((leftExprType.match(intType) || leftExprType.match(charType)) &&
        (rightExprType.match(intType) || rightExprType.match(charType)))
      return boolType;

    program.getLogger().addBinaryOperatorError(currentFunction, lt, leftExprType, rightExprType);
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

    program.getLogger().addBinaryOperatorError(currentFunction, mod, leftExprType, rightExprType);
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

    program.getLogger().addBinaryOperatorError(currentFunction, mult, leftExprType, rightExprType);
    return null;
  }

  @Override
  public Object visitNegate(Negate neg) {
    AbstractType exprType = (AbstractType) neg.getExpression().accept(this);

    // Negating booleans or chars makes no sense
    if (!exprType.match(boolType) && !exprType.match(charType))
      return exprType;

    program.getLogger().addUnaryOperatorError(currentFunction, neg, exprType);
    return null;
  }

  @Override
  public Object visitNew(New newCmd) {
    if (newCmd.getExpression() == null || newCmd.getExpression() instanceof IntLiteral) {
      if (newCmd.getType() instanceof DataType) {
        Data data = dataTypes.get(newCmd.getType().toString());

        if (data != null)
          return data.getType();
        else
          program.getLogger().addGenericError(currentFunction,
            "\t" + newCmd.getLine() + ":" + newCmd.getColumn() +
            ": Undefined data \"" + newCmd.getType().toString() + "\""
          );
      }
      else {
        return newCmd.getType();
      }
    }
    else {
      program.getLogger().addUndefinedArraySizeDeclarationError(currentFunction, newCmd.getExpression());
    }

    return null;
  }

  @Override
  public Object visitNot(Not not) {
    AbstractType exprType = (AbstractType) not.getExpression().accept(this);

    // 'Not' operator can only be applied to boolean value
    if (exprType.match(boolType))
      return exprType;

    program.getLogger().addUnaryOperatorError(currentFunction, not, exprType);
    return null;
  }

  @Override
  public Object visitNotEqual(NotEqual neq) {
    AbstractType leftExprType = (AbstractType) neq.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) neq.getRight().accept(this);

    // Can only compare expressions of same type
    if (leftExprType.match(rightExprType))
      return boolType;

    program.getLogger().addBinaryOperatorError(currentFunction, neq, leftExprType, rightExprType);
    return null;
  }

  @Override
  public Object visitNullLiteral(NullLiteral n) {
    return null;
  }

  @Override
  public Object visitParameter(Parameter param) {
    return null;
  }

  @Override
  public Object visitRead(Read readCmd) {
    AbstractLvalue lvalue = readCmd.getLvalue();
    AbstractType assignedType = intType;
    Symbol symbol = program.getEnv().get(currentFunction).search(lvalue.getIdentifier().getName());

    if (symbol == null) {
      if (readCmd.getLvalue() instanceof ArrayAccess)
        program.getLogger().addUndefinedLvalueError(currentFunction, lvalue.getIdentifier());
      else
        program.getEnv().get(currentFunction).put(lvalue.getIdentifier().getName(), new Symbol(assignedType));
    }
    else {
      AbstractType lvalueType = symbol.getType();

      if (assignedType != null && !assignedType.match(lvalueType))
        program.getLogger().addInvalidAssignmentError(currentFunction, readCmd, assignedType, lvalueType);
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

    for (Function f : program.getEnv().keySet()) {
      if (f.getId().getName().equals(fCall.getId().getName())) {
        if (fCall.getArgs().size() == f.getParameters().size()) {
          boolean match = true;

          // Check parameters compatibility
          for (int i = 0; i < f.getParameters().size(); i++) {
            AbstractType argType = (AbstractType) fCall.getArgs().get(i).accept(this);
            AbstractType parameterType = f.getParameters().get(i).getType();

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
      for (int i = 0; i < fCall.getLvalues().size() && i < callee.getReturnTypes().size(); i++) {
        AbstractLvalue lvalue = fCall.getLvalues().get(i);
        AbstractType expectedType = callee.getReturnTypes().get(i);
        Symbol symbol = program.getEnv().get(currentFunction).search(lvalue.getIdentifier().getName());

        if (symbol == null) {
          if (lvalue instanceof ArrayAccess)
            program.getLogger().addUndefinedLvalueError(currentFunction, lvalue.getIdentifier());
          else
            program.getEnv().get(currentFunction).put(lvalue.getIdentifier().getName(), new Symbol(expectedType));
        }
        else {
          AbstractType actualType = symbol.getType();

          if (!actualType.match(expectedType))
            program.getLogger().addInvalidAssignmentError(currentFunction, fCall, expectedType, actualType);
        }
      }
    }
    else {
      program.getLogger().addInvalidFunctionCallError(currentFunction, fCall);
    }

    return null;
  }

  @Override
  public Object visitSubtraction(Subtraction sub) {
    AbstractType leftExprType = (AbstractType) sub.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) sub.getRight().accept(this);

    if (leftExprType.match(intType) && (rightExprType.match(intType) || rightExprType.match(floatType))) {
      return rightExprType;
    }
    else if (leftExprType.match(floatType) && (rightExprType.match(intType) || rightExprType.match(floatType)))
      return leftExprType;

    program.getLogger().addBinaryOperatorError(currentFunction, sub, leftExprType, rightExprType);
    return null;
  }
}
