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
  private List<String> errorsLog;
  private Map<String, LocalEnvironment> functionsEnv;
  private LocalEnvironment currentEnv;

  public TypeCheckVisitor() {
    this.boolType = new BoolType();
    this.charType = new CharType();
    this.floatType = new FloatType();
    this.intType = new IntType();
    this.errorsLog = new ArrayList<>();
    this.functionsEnv = new HashMap<>();
  }

  public void printErrors() {
    for (String error : errorsLog)
      System.err.println(error);
  }

  public Object visitProgram(Program program) {
    // Store all functions local environment
    for (AbstractExpression expr : program.getExpressions()) {
      if (expr instanceof Function) {
        Function f = (Function) expr;
        LocalEnvironment localEnv = new LocalEnvironment();

        for (Parameter param : f.getParameters()) {
          localEnv.addParameterType(param.getType());
          localEnv.addVarType(param.getId().getName(), param.getType());
        }

        for (AbstractType returnType : f.getReturnTypes())
          localEnv.addReturnType(returnType);

        functionsEnv.put(f.getId().getName(), localEnv);
      }
    }

    for (AbstractExpression expr : program.getExpressions())
      if (expr instanceof Function)
        expr.accept(this);

    printErrors();

    return null;
  }

  public Object visitFunction(Function f) {
    currentEnv = functionsEnv.get(f.getId().getName());

    for (AbstractCommand cmd : f.getCommands()) {
      Object result = cmd.accept(this);

      if (cmd instanceof Return) {
        List<AbstractType> returnTypes = (List<AbstractType>) result;

        if (returnTypes.size() == currentEnv.getReturnsTypes().size()) {
          for (int i = 0; i < returnTypes.size(); i++) {
            AbstractType actualType = returnTypes.get(i);
            AbstractType expectedType = currentEnv.getReturnsTypes().get(i);

            if (!actualType.match(expectedType)) {
              errorsLog.add("Return type (" + (i + 1) + ") \"" + actualType.toString() +
                            "\" does not match expected type \"" + expectedType.toString() + "\"");
            }
          }
        }
        else {
          errorsLog.add("Number of returns of function \"" + f.getId().getName() +
                        "\" incompatible with the function definition");
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

    errorsLog.add("Binary operator \"" + add.getSymbol() + "\" does not apply to types " +
                  leftExprType.toString() + " and " + rightExprType.toString());
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

    errorsLog.add("Binary operator \"" + and.getSymbol() + "\" does not apply to types " +
                   leftExprType.toString() + " and " + rightExprType.toString());
    return null;
  }

  @Override
  public Object visitArrayAccess(ArrayAccess arrayAccess) {
    if (arrayAccess.getExpression() instanceof IntLiteral) {
      ArrayType array = (ArrayType) arrayAccess.getLvalue().accept(this);
      return array.getType();
    }
    else
      errorsLog.add("Can not determine access index of array");

    return null;
  }

  @Override
  public Object visitArrayType(ArrayType arrayType) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitAssignableFunctionCall(AssignableFunctionCall fCall) {
    LocalEnvironment localEnv = functionsEnv.get(fCall.getId().getName());

    if (localEnv != null) {
      if (fCall.getArgs().size() == localEnv.getParamsTypes().size()) {
        for (int i = 0; i < localEnv.getParamsTypes().size(); i++) {
          AbstractType actualType = (AbstractType) fCall.getArgs().get(i).accept(this);
          AbstractType expectedType = localEnv.getParamsTypes().get(i);

          if (!actualType.match(expectedType))
            errorsLog.add("Param type (" + (i + 1) + ") " + actualType.toString() +
                          " does not match expected type " + expectedType.toString() + "");
        }

        AbstractType indexType = (AbstractType) fCall.getIndex().accept(this);

        if (indexType.match(intType)) {
          if (fCall.getIndex() instanceof IntLiteral) {
            int index = ((IntLiteral) fCall.getIndex()).getValue();

            if (index < localEnv.getReturnsTypes().size())
              return localEnv.getReturnsTypes().get(index);
            else
              errorsLog.add("Access index of function call \"" + fCall.getId().getName() +
              "\" must be in range [0," + (localEnv.getReturnsTypes().size() - 1) + "]");
          }
          else
            errorsLog.add("Can not determine access index of function call \"" + fCall.getId().getName() + "\"");
        }
        else
          errorsLog.add("Access index of function call \"" + fCall.getId().getName() +
                        "\" can not be of type " + indexType.toString());
      }
      else
        errorsLog.add("Number of arguments of function call \"" + fCall.getId().getName() +
                      "\" incompatible with the function definition");
    }
    else
      errorsLog.add("No function named \"" + fCall.getId().getName() + "\"");

    return null;
  }

  @Override
  public Object visitAssignment(Assignment assignment) {
    // TODO: Funcionar pra todos lvalues
    Identifier id = (Identifier) assignment.getLvalue();
    AbstractType actualType = (AbstractType) assignment.getExpression().accept(this);

    if (!currentEnv.getVarsTypes().containsKey(id.getName())) {
      currentEnv.getVarsTypes().put(id.getName(), actualType);
    }
    else {
      AbstractType expectedType = (AbstractType) assignment.getLvalue().accept(this);

      if (!actualType.match(expectedType))
        errorsLog.add("Can not assign value of type " + actualType.toString() +
                      " to variable of type " + expectedType.toString() + "");
    }

    return null;
  }

  @Override
  public Object visitBalancedParentheses(BalancedParenthesesExpression balanced) {
    return balanced.getExpression().accept(this);
  }

  @Override
  public Object visitBoolType(BoolType boolType) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitCharType(CharType charType) {
    // TODO Auto-generated method stub
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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitData(Data data) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitDataIdentifierAccess(DataIdentifierAccess dataIdentifierAccess) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitDeclaration(Declaration decl) {
    // TODO Auto-generated method stub
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

    errorsLog.add("Binary operator \"" + div.getSymbol() + "\" does not apply to types " +
                   leftExprType.toString() + " and " + rightExprType.toString());
    return null;
  }

  @Override
  public Object visitEqual(Equal eq) {
    AbstractType leftExprType = (AbstractType) eq.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) eq.getRight().accept(this);

    // Can only compare expressions of same type
    if (leftExprType.match(rightExprType))
      return leftExprType;

    errorsLog.add("Binary operator \"" + eq.getSymbol() + "\" does not apply to types " +
                    leftExprType.toString() + " and " + rightExprType.toString());
    return null;
  }

  @Override
  public Object visitFloatType(FloatType floatType) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitIdentifier(Identifier id) {
    if (!currentEnv.getVarsTypes().containsKey(id.getName()))
      errorsLog.add("Undefined variable \"" + id.getName() + "\"");
    else
      return currentEnv.getVarsTypes().get(id.getName());

    return null;
  }

  @Override
  public Object visitIf(If ifCmd) {
    AbstractType exprType = (AbstractType) ifCmd.getExpression().accept(this);

    if (exprType.match(boolType))
      ifCmd.getScopeCommand().accept(this);
    else
      errorsLog.add("Command \"" + ifCmd.getName() + "\" does not apply to type " + exprType.toString());

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
      errorsLog.add("Command \"" + ifElseCmd.getName() + "\" does not apply to type " + exprType.toString());
    }

    return null;
  }

  @Override
  public Object visitIntType(IntType intType) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitIterate(Iterate iterateCmd) {
    AbstractType exprType = (AbstractType) iterateCmd.getExpression().accept(this);

    if (exprType.match(intType))
      return iterateCmd.getCommand().accept(this);
    else
      errorsLog.add("Command \"" + iterateCmd.getName() + "\" does not apply to type " + exprType.toString());

    return null;
  }

  @Override
  public Object visitLessThan(LessThan lt) {
    AbstractType leftExprType = (AbstractType) lt.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) lt.getRight().accept(this);

    if ((leftExprType.match(intType) || leftExprType.match(charType)) &&
        (rightExprType.match(intType) || rightExprType.match(charType)))
      return boolType;

    errorsLog.add("Binary operator \"" + lt.getSymbol() + "\" does not apply to types " +
                   leftExprType.toString() + " and " + rightExprType.toString());
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

    errorsLog.add("Binary operator \"" + mod.getSymbol() + "\" does not apply to types " +
                   leftExprType.toString() + " and " + rightExprType.toString());
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

    errorsLog.add("Binary operator \"" + mult.getSymbol() + "\" does not apply to types " +
                  leftExprType.toString() + " and " + rightExprType.toString());
    return null;
  }

  @Override
  public Object visitNegate(Negate neg) {
    AbstractType exprType = (AbstractType) neg.getExpression().accept(this);

    // Negating booleans or chars makes no sense
    if (exprType.match(boolType) || exprType.match(charType)) {
      errorsLog.add("Unary operator \"" + neg.getSymbol() + "\" does not apply to type " + exprType.toString());
      return null;
    }

    return exprType;
  }

  @Override
  public Object visitNew(New newCmd) {
    if (newCmd.getExpression() instanceof IntLiteral || newCmd.getExpression() == null)
      return newCmd.getType();
    else
      errorsLog.add("Can not determine size of array declaration");

    return null;
  }

  @Override
  public Object visitNot(Not not) {
    AbstractType exprType = (AbstractType) not.getExpression().accept(this);

    // 'Not' operator can only be applied to boolean value
    if (!exprType.match(boolType)) {
      errorsLog.add("Unary operator \"" + not.getSymbol() + "\" does not apply to type " + exprType.toString());
      return null;
    }

    return exprType;
  }

  @Override
  public Object visitNotEqual(NotEqual neq) {
    AbstractType leftExprType = (AbstractType) neq.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) neq.getRight().accept(this);

    // Can only compare expressions of same type
    if (leftExprType.match(rightExprType))
      return leftExprType;

    errorsLog.add("Binary operator \"" + neq.getSymbol() + "\" does not apply to types " +
                   leftExprType.toString() + " and " + rightExprType.toString());
    return null;
  }

  @Override
  public Object visitNullLiteral(NullLiteral n) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitParameter(Parameter param) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitRead(Read readCmd) {
    // TODO Auto-generated method stub
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
    // TODO Auto-generated method stub
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

    errorsLog.add("Binary operator \"" + sub.getSymbol() + "\" does not apply to types " +
                   leftExprType.toString() + " and " + rightExprType.toString());
    return null;
  }
}
