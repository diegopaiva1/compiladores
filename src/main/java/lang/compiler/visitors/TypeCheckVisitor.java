package lang.compiler.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
  private Map<String, List<String>> errorsLog;
  private Map<String, LocalEnvironment> functionsEnv;
  private LocalEnvironment currentEnv;

  public TypeCheckVisitor() {
    this.boolType = new BoolType(0, 0);
    this.charType = new CharType(0, 0);
    this.floatType = new FloatType(0, 0);
    this.intType = new IntType(0, 0);
    this.errorsLog = new LinkedHashMap<>();
    this.functionsEnv = new HashMap<>();
  }

  public void printErrors() {
    for (List<String> errors : errorsLog.values())
      // First line of errors is the header
      if (errors.size() > 1)
        for (String error : errors)
          System.err.println(error);
  }

  public Object visitProgram(Program program) {
    // Store all functions local environment
    for (AbstractExpression expr : program.getExpressions()) {
      if (expr instanceof Function) {
        Function f = (Function) expr;
        LocalEnvironment localEnv = new LocalEnvironment(f.getId().getName());

        for (Parameter param : f.getParameters()) {
          localEnv.addParameterType(param.getType());
          localEnv.addVarType(param.getId().getName(), param.getType());
        }

        for (AbstractType returnType : f.getReturnTypes())
          localEnv.addReturnType(returnType);

        functionsEnv.put(f.getId().getName(), localEnv);

        // Add error log with default header for function
        List<String> log = new ArrayList<>();
        log.add("In function \"" + f.getId().getName() + "\":");
        errorsLog.put(f.getId().getName(), log);
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

            if (actualType != null && !actualType.match(expectedType)) {
              errorsLog.get(currentEnv.getName()).add(
                "\t" + actualType.getLine() + ":" + actualType.getColumn() + ": Return type (" + (i + 1) + ") \"" +
                actualType.toString() + "\" does not match expected type \"" + expectedType.toString() + "\""
              );
            }
          }
        }
        else {
          errorsLog.get(currentEnv.getName()).add(
            "\t" + f.getLine() + ":" + f.getColumn() + ": Number of returns of function \"" +
            f.getId().getName() + "\" incompatible with the function definition"
          );
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

    errorsLog.get(currentEnv.getName()).add(
      "\t" + add.getLine() + ":" + add.getColumn() +  ": Binary operator \"" + add.getSymbol() +
      "\" does not apply to types " + leftExprType.toString() + " and " + rightExprType.toString()
    );

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

    errorsLog.get(currentEnv.getName()).add(
      "\t" + and.getLine() + ":" + and.getColumn() +
      ": Binary operator \"" + and.getSymbol() + "\" does not apply to types " +
      leftExprType.toString() + " and " + rightExprType.toString()
    );

    return null;
  }

  @Override
  public Object visitArrayAccess(ArrayAccess arrayAccess) {
    String name = arrayAccess.getLvalue().getIdentifier().getName();

    if (currentEnv.getVarsTypes().containsKey(name)) {
      if (arrayAccess.getExpression() instanceof IntLiteral) {
        ArrayType array = (ArrayType) arrayAccess.getLvalue().accept(this);
        return array.getType();
      }
      else
        errorsLog.get(currentEnv.getName()).add(
          "\t" + arrayAccess.getLine() + ":" + arrayAccess.getColumn() +
          ": Can not determine access index of array"
        );
    }
    else {
      errorsLog.get(currentEnv.getName()).add(
        "\t" + arrayAccess.getLine() + ":" + arrayAccess.getColumn() +
        ": Array \"" + name + "\" does not exist"
      );
    }

    return null;
  }

  @Override
  public Object visitArrayType(ArrayType arrayType) {
    return null;
  }

  @Override
  public Object visitAssignableFunctionCall(AssignableFunctionCall fCall) {
    LocalEnvironment localEnv = functionsEnv.get(fCall.getId().getName());

    if (localEnv != null) {
      if (fCall.getArgs().size() == localEnv.getParamsTypes().size()) {
        for (int i = 0; i < localEnv.getParamsTypes().size(); i++) {
          AbstractExpression arg = fCall.getArgs().get(i);
          AbstractType expectedType = localEnv.getParamsTypes().get(i);
          AbstractType actualType = (AbstractType) arg.accept(this);

          if (actualType != null && !actualType.match(expectedType))
            errorsLog.get(currentEnv.getName()).add(
              "\t" + arg.getLine() + ":" + arg.getColumn() +
              ": Parameter type (" + (i + 1) + ") " + actualType.toString() +
              " does not match expected type " + expectedType.toString()
            );
        }

        AbstractType indexType = (AbstractType) fCall.getIndex().accept(this);

        if (indexType.match(intType)) {
          if (fCall.getIndex() instanceof IntLiteral) {
            IntLiteral index = (IntLiteral) fCall.getIndex();

            if (index.getValue() < localEnv.getReturnsTypes().size())
              return localEnv.getReturnsTypes().get(index.getValue());
            else
              errorsLog.get(currentEnv.getName()).add(
                "\t" + index.getLine() + ":" + index.getColumn() +
                ": Access index of function call \"" + fCall.getId().getName() +
                "\" must be in range [0," + (localEnv.getReturnsTypes().size() - 1) + "]"
              );
          }
          else {
            errorsLog.get(currentEnv.getName()).add(
              "\t" + fCall.getIndex().getLine() + ":" + fCall.getIndex().getColumn() +
              ": Can not determine access index of function call \"" + fCall.getId().getName() + "\""
            );
          }
        }
        else {
          errorsLog.get(currentEnv.getName()).add(
            "\t" + fCall.getIndex().getLine() + ":" + fCall.getIndex().getColumn() +
            ": Access index of function call \"" + fCall.getId().getName() +
            "\" can not be of type " + indexType.toString()
          );
        }
      }
      else {
        errorsLog.get(currentEnv.getName()).add(
          "\t" + fCall.getLine() + ":" + fCall.getColumn() +
          ": Number of arguments of function call \"" + fCall.getId().getName() +
          "\" incompatible with the function definition"
        );
      }
    }
    else {
      errorsLog.get(currentEnv.getName()).add(
        "\t" + fCall.getLine() + ":" + fCall.getColumn() +
        ": No function named \"" + fCall.getId().getName() + "\""
      );
    }

    return null;
  }

  @Override
  public Object visitAssignment(Assignment assignment) {
    Identifier lvalueIdentifier = assignment.getLvalue().getIdentifier();
    AbstractType actualType = (AbstractType) assignment.getExpression().accept(this);

    if (!currentEnv.getVarsTypes().containsKey(lvalueIdentifier.getName())) {
      if (assignment.getLvalue() instanceof ArrayAccess)
        errorsLog.get(currentEnv.getName()).add("Array \"" + lvalueIdentifier.getName() + "\" does not exist");
      else
        currentEnv.getVarsTypes().put(lvalueIdentifier.getName(), actualType);
    }
    else {
      AbstractType expectedType = (AbstractType) assignment.getLvalue().accept(this);

      if (actualType != null && !actualType.match(expectedType))
        errorsLog.get(currentEnv.getName()).add(
          "\t" + assignment.getLine() + ":" + assignment.getColumn() +
          ": Can not assign value of type " + actualType.toString() +
          " to variable of type " + expectedType.toString()
        );
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

    errorsLog.get(currentEnv.getName()).add(
      "\t" + div.getLine() + ":" + div.getColumn() +
      ": Binary operator \"" + div.getSymbol() + "\" does not apply to types " +
      leftExprType.toString() + " and " + rightExprType.toString()
    );

    return null;
  }

  @Override
  public Object visitEqual(Equal eq) {
    AbstractType leftExprType = (AbstractType) eq.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) eq.getRight().accept(this);

    // Can only compare expressions of same type
    if (leftExprType.match(rightExprType))
      return leftExprType;

    errorsLog.get(currentEnv.getName()).add(
      "\t" + eq.getLine() + ":" + eq.getColumn() +
      ": Binary operator \"" + eq.getSymbol() + "\" does not apply to types " +
      leftExprType.toString() + " and " + rightExprType.toString()
    );

    return null;
  }

  @Override
  public Object visitFloatType(FloatType floatType) {
    return null;
  }

  @Override
  public Object visitIdentifier(Identifier id) {
    if (!currentEnv.getVarsTypes().containsKey(id.getName()))
      errorsLog.get(currentEnv.getName()).add("Undefined variable \"" + id.getName() + "\"");
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
      errorsLog.get(currentEnv.getName()).add(
        "\t" + ifCmd.getExpression().getLine() + ":" + ifCmd.getExpression().getColumn() +
        ": Command \"" + ifCmd.getName() + "\" does not apply to type " + exprType.toString()
      );

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
      errorsLog.get(currentEnv.getName()).add(
        "\t" + ifElseCmd.getExpression().getLine() + ":" + ifElseCmd.getExpression().getColumn() +
        ": Command \"" + ifElseCmd.getName() + "\" does not apply to type " + exprType.toString()
      );
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
      return iterateCmd.getCommand().accept(this);
    else
      errorsLog.get(currentEnv.getName()).add(
        "\t" + iterateCmd.getExpression().getLine() + ":" + iterateCmd.getExpression().getColumn() +
        ": Command \"" + iterateCmd.getName() + "\" does not apply to type " + exprType.toString()
      );

    return null;
  }

  @Override
  public Object visitLessThan(LessThan lt) {
    AbstractType leftExprType = (AbstractType) lt.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) lt.getRight().accept(this);

    if ((leftExprType.match(intType) || leftExprType.match(charType)) &&
        (rightExprType.match(intType) || rightExprType.match(charType)))
      return boolType;

    errorsLog.get(currentEnv.getName()).add(
      "\t" + lt.getLine() + ":" + lt.getColumn() +
      ": Binary operator \"" + lt.getSymbol() + "\" does not apply to types " +
      leftExprType.toString() + " and " + rightExprType.toString()
    );

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

    errorsLog.get(currentEnv.getName()).add(
      "\t" + mod.getLine() + ":" + mod.getColumn() +
      ": Binary operator \"" + mod.getSymbol() + "\" does not apply to types " +
      leftExprType.toString() + " and " + rightExprType.toString()
    );

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

    errorsLog.get(currentEnv.getName()).add(
      "\t" + mult.getLine() + ":" + mult.getColumn() +
      ": Binary operator \"" + mult.getSymbol() + "\" does not apply to types " +
      leftExprType.toString() + " and " + rightExprType.toString()
    );

    return null;
  }

  @Override
  public Object visitNegate(Negate neg) {
    AbstractType exprType = (AbstractType) neg.getExpression().accept(this);

    // Negating booleans or chars makes no sense
    if (!exprType.match(boolType) && !exprType.match(charType))
      return exprType;

    errorsLog.get(currentEnv.getName()).add(
      "\t" + neg.getLine() + ":" + neg.getColumn() +
      ": Unary operator \"" + neg.getSymbol() + "\" does not apply to type " + exprType.toString()
    );

    return null;
  }

  @Override
  public Object visitNew(New newCmd) {
    if (newCmd.getExpression() instanceof IntLiteral || newCmd.getExpression() == null)
      return newCmd.getType();
    else
      errorsLog.get(currentEnv.getName()).add(
        "\t" + newCmd.getLine() + ":" + newCmd.getColumn() +
        ": Can not determine size of array declaration"
      );

    return null;
  }

  @Override
  public Object visitNot(Not not) {
    AbstractType exprType = (AbstractType) not.getExpression().accept(this);

    // 'Not' operator can only be applied to boolean value
    if (exprType.match(boolType))
      return exprType;

    errorsLog.get(currentEnv.getName()).add(
      "\t" + not.getLine() + ":" + not.getColumn() +
      ": Unary operator \"" + not.getSymbol() + "\" does not apply to type " + exprType.toString()
    );

    return null;
  }

  @Override
  public Object visitNotEqual(NotEqual neq) {
    AbstractType leftExprType = (AbstractType) neq.getLeft().accept(this);
    AbstractType rightExprType = (AbstractType) neq.getRight().accept(this);

    // Can only compare expressions of same type
    if (leftExprType.match(rightExprType))
      return leftExprType;

    errorsLog.get(currentEnv.getName()).add(
      "\t" + neq.getLine() + ":" + neq.getColumn() +
      ": Binary operator \"" + neq.getSymbol() + "\" does not apply to types " +
      leftExprType.toString() + " and " + rightExprType.toString()
    );

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
    Identifier lvalueIdentifier = readCmd.getLvalue().getIdentifier();
    AbstractType actualType = intType;

    if (!currentEnv.getVarsTypes().containsKey(lvalueIdentifier.getName())) {
      if (readCmd.getLvalue() instanceof ArrayAccess)
        errorsLog.get(currentEnv.getName()).add("Array \"" + lvalueIdentifier.getName() + "\" does not exist");
      else
        currentEnv.getVarsTypes().put(lvalueIdentifier.getName(), actualType);
    }
    else {
      AbstractType expectedType = (AbstractType) readCmd.getLvalue().accept(this);

      if (actualType != null && !actualType.match(expectedType))
        errorsLog.get(currentEnv.getName()).add(
          "\t" + readCmd.getLvalue().getLine() + ":" + readCmd.getLvalue().getColumn() +
          ": Can not assign value of type " + actualType.toString() +
          " to variable of type " + expectedType.toString()
        );
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
    LocalEnvironment localEnv = functionsEnv.get(fCall.getId().getName());

    if (localEnv != null) {
      if (fCall.getArgs().size() == localEnv.getParamsTypes().size()) {
        for (int i = 0; i < localEnv.getParamsTypes().size(); i++) {
          AbstractExpression arg = fCall.getArgs().get(i);
          AbstractType actualType = (AbstractType) arg.accept(this);
          AbstractType expectedType = localEnv.getParamsTypes().get(i);

          if (actualType != null && !actualType.match(expectedType))
            errorsLog.get(currentEnv.getName()).add(
              "\t" + arg.getLine() + ":" + arg.getColumn() +
              ": Param type (" + (i + 1) + ") " + actualType.toString() +
              " does not match expected type " + expectedType.toString()
            );
        }

        if (fCall.getLvalues().size() == localEnv.getReturnsTypes().size()) {
          for (int i = 0; i < fCall.getLvalues().size(); i++) {
            AbstractLvalue lvalue = fCall.getLvalues().get(i);
            AbstractType expectedType = localEnv.getReturnsTypes().get(i);

            if (!currentEnv.getVarsTypes().containsKey(lvalue.getIdentifier().getName())) {
              if (lvalue instanceof ArrayAccess)
                errorsLog.get(currentEnv.getName()).add(
                  "\t" + lvalue.getLine() + ":" + lvalue.getColumn() +
                  ": Array \"" + lvalue.getIdentifier().getName() + "\" does not exist"
                );
              else
                currentEnv.getVarsTypes().put(lvalue.getIdentifier().getName(), expectedType);
            }
            else {
              AbstractType actualType = (AbstractType) lvalue.accept(this);

              if (!actualType.match(expectedType)) {
                errorsLog.get(currentEnv.getName()).add(
                  "\t" + fCall.getLine() + ":" + fCall.getColumn() +
                  ": Can not assign value of type " + expectedType.toString() +
                  " to variable of type " + actualType.toString()
                );
              }
            }
          }
        }
        else {
          errorsLog.get(currentEnv.getName()).add(
            "\t" + fCall.getLine() + ":" + fCall.getColumn() +
            ": Number of lvalues of function call \"" + fCall.getId().getName() +
            "\" incompatible with the function definition"
          );
        }
      }
      else
        errorsLog.get(currentEnv.getName()).add(
          "\t" + fCall.getLine() + ":" + fCall.getColumn() +
          ": Number of arguments of function call \"" + fCall.getId().getName() +
          "\" incompatible with the function definition"
        );
    }
    else
      errorsLog.get(currentEnv.getName()).add(
        "\t" + fCall.getLine() + ":" + fCall.getColumn() +
        ": No function named \"" + fCall.getId().getName() + "\""
      );

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

    errorsLog.get(currentEnv.getName()).add(
      "\t" + sub.getLine() + ":" + sub.getColumn() +
      ": Binary operator \"" + sub.getSymbol() + "\" does not apply to types " +
      leftExprType.toString() + " and " + rightExprType.toString()
    );

    return null;
  }
}
