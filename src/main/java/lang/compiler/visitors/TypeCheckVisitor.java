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
  private Map<String, CustomType> customTypes;
  private Map<Function, LocalEnvironment> functionsEnv;
  private LocalEnvironment currentEnv;

  public TypeCheckVisitor() {
    boolType = new BoolType(0, 0);
    charType = new CharType(0, 0);
    floatType = new FloatType(0, 0);
    intType = new IntType(0, 0);
    errorsLog = new LinkedHashMap<>();
    List<String> dataErrors = new ArrayList<>();
    dataErrors.add("\u26A0");
    errorsLog.put("data", dataErrors);
    customTypes = new HashMap<>();
    functionsEnv = new HashMap<>();
  }

  public boolean hasErrors() {
    for (List<String> errors : errorsLog.values())
      // First line of errors is the header
      if (errors.size() > 1)
        return true;

    return false;
  }

  public void printErrors() {
    for (List<String> errors : errorsLog.values())
      if (errors.size() > 1)
        for (String error : errors)
          System.err.println(error);
  }

  public Boolean visitProgram(Program program) {
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

        functionsEnv.put(f, localEnv);

        // Add error log with default header for function
        List<String> log = new ArrayList<>();
        log.add("In function \"" + f.getId().getName() + "\":");
        errorsLog.put(f.getId().getName(), log);
      }
    }

    for (AbstractExpression expr : program.getExpressions())
      if (expr instanceof Data)
        expr.accept(this);

    for (AbstractExpression expr : program.getExpressions())
      if (expr instanceof Function)
        expr.accept(this);

    if (hasErrors()) {
      printErrors();
      return false;
    }

    return true;
  }

  public Object visitFunction(Function f) {
    currentEnv = functionsEnv.get(f);

    if (!f.getId().getName().equals("main") || f.getParameters().size() == 0) {
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
                  "\t" + cmd.getLine() + ":" + cmd.getColumn() + ": " +
                  "\"" + ((Return) cmd).getExpressions().get(i).toString() + "\" does not match expected type \""
                  + expectedType.toString() + "\""
                );
              }
            }
          }
          else {
            errorsLog.get(currentEnv.getName()).add(
              "\t" + cmd.getLine() + ":" + cmd.getColumn() + ": Command \"" + cmd.toString() + "\"" +
              " incompatible with the function definition \"" + f.toString() + "\""
            );
          }
        }
      }
    }
    else
      errorsLog.get(currentEnv.getName()).add("Function main can not contain parameters");

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
      "\" does not apply to types \"" + leftExprType.toString() + "\" and \"" + rightExprType.toString() + "\""
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
      ": Binary operator \"" + and.getSymbol() + "\" does not apply to types \"" +
      leftExprType.toString() + "\" and \"" + rightExprType.toString() + "\""
    );

    return null;
  }

  @Override
  public Object visitArrayAccess(ArrayAccess arrayAccess) {
    String name = arrayAccess.getLvalue().getIdentifier().getName();

    if (currentEnv.getVarsTypes().containsKey(name)) {
      if (arrayAccess.getExpression() instanceof IntLiteral) {
        if (arrayAccess.getLvalue().accept(this) instanceof ArrayType)
          return ((ArrayType) arrayAccess.getLvalue().accept(this)).getType();
        else
          errorsLog.get(currentEnv.getName()).add(
            "\t" + arrayAccess.getLvalue().getLine() + ":" + arrayAccess.getLvalue().getColumn() +
            ": \"" + arrayAccess.getLvalue().getIdentifier().getName() + "\" is not an Array"
          );
      }
      else {
        errorsLog.get(currentEnv.getName()).add(
          "\t" + arrayAccess.getExpression().getLine() + ":" + arrayAccess.getExpression().getColumn() +
          ": Can not determine access index of variable \"" + arrayAccess.getLvalue().getIdentifier().getName() + "\""
        );
      }
    }
    else {
      errorsLog.get(currentEnv.getName()).add(
        "\t" + arrayAccess.getLine() + ":" + arrayAccess.getColumn() + ": Undefined variable \"" + name + "\""
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

      if (indexType.match(intType)) {
        if (fCall.getIndex() instanceof IntLiteral) {
          IntLiteral index = (IntLiteral) fCall.getIndex();

          if (index.getValue() < localEnv.getReturnsTypes().size())
            return localEnv.getReturnsTypes().get(index.getValue());
          else
            errorsLog.get(currentEnv.getName()).add(
              "\t" + index.getLine() + ":" + index.getColumn() +
              ": Access index of function call must be in range [0," + (localEnv.getReturnsTypes().size() - 1) + "]\n" +
              "\tsrc: \"" + fCall.toString() + "\""
            );
        }
        else {
          errorsLog.get(currentEnv.getName()).add(
            "\t" + fCall.getIndex().getLine() + ":" + fCall.getIndex().getColumn() +
            ": Cannot determine access index of function call\n" +
            "\tsrc: \"" + fCall.toString() + "\""
          );
        }
      }
      else {
        errorsLog.get(currentEnv.getName()).add(
          "\t" + fCall.getIndex().getLine() + ":" + fCall.getIndex().getColumn() +
          ": Access index of function call cannot be of type \"" + indexType.toString() + "\"\n" +
          "\tsrc: \"" + fCall.toString() + "\""
        );
      }
    }
    else {
      errorsLog.get(currentEnv.getName()).add(
        "\t" + fCall.getLine() + ":" + fCall.getColumn() +
        ": Cannot resolve function call \"" + fCall.toString() + "\""
      );
    }

    return null;
  }

  @Override
  public Object visitAssignment(Assignment assignment) {
    Identifier lvalueIdentifier = assignment.getLvalue().getIdentifier();
    AbstractType actualType = (AbstractType) assignment.getExpression().accept(this);

    if (!currentEnv.getVarsTypes().containsKey(lvalueIdentifier.getName())) {
      if (assignment.getLvalue() instanceof ArrayAccess || assignment.getLvalue() instanceof DataIdentifierAccess)
        errorsLog.get(currentEnv.getName()).add(
          "\t" + assignment.getLvalue().getLine() + ":" + assignment.getLvalue().getColumn() +
          ": Undefined variable \"" + lvalueIdentifier.getName() + "\""
        );
      else if (actualType == null)
        errorsLog.get(currentEnv.getName()).add(
          "\t" + assignment.getLvalue().getLine() + ":" + assignment.getLvalue().getColumn() +
          ": \"" + lvalueIdentifier.getName() + "\" cannot be declared as value of type \"null\""
        );
      else
        currentEnv.getVarsTypes().put(lvalueIdentifier.getName(), actualType);
    }
    else {
      AbstractType expectedType = (AbstractType) assignment.getLvalue().accept(this);

      if (actualType != null && !actualType.match(expectedType))
        errorsLog.get(currentEnv.getName()).add(
          "\t" + assignment.getLine() + ":" + assignment.getColumn() +
          ": A value of type \"" + actualType.toString() + "\" cannot be assigned to an entity of type \"" + expectedType.toString() + "\""
        );
      else if (actualType == null && !(expectedType instanceof ArrayType || expectedType instanceof CustomType))
        // null can only be assigned to arrays and custom data types
        errorsLog.get(currentEnv.getName()).add(
          "\t" + assignment.getLine() + ":" + assignment.getColumn() +
          ": A value of type \"null\" cannot be assigned to an entity of type \"" + expectedType.toString() + "\""
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
    boolean hasInvalidType = false;
    if (!customTypes.containsKey(data.getType().toString())) {
      for(AbstractType type : data.getType().getAllTypes()) {
        if (!(type.match(intType) || type.match(floatType) ||
              type.match(boolType) || type.match(charType))){
          hasInvalidType = true;
          errorsLog.get("data").add(
            data.getLine() + ":" + data.getColumn() + ": Invalid type \"" + type.toString() +
            "\" found on custom data \"" + data.getType().toString() + "\"");
        }
      }
      if(!hasInvalidType)
        customTypes.put(data.getType().toString(), data.getType());
    }
    else
      errorsLog.get("data").add(
        data.getLine() + ":" + data.getColumn() + ": Redefinition of data \"" + data.getType().toString() + "\""
      );

    return null;
  }

  @Override
  public Object visitDataIdentifierAccess(DataIdentifierAccess dataIdentifierAccess) {
    String name = dataIdentifierAccess.getLvalue().getIdentifier().getName();

    if (currentEnv.getVarsTypes().containsKey(name)) {
      CustomType customType = (CustomType) currentEnv.getVarsTypes().get(name);

      if (customType.hasVar(dataIdentifierAccess.getId().getName()))
        return customType.getVarType(dataIdentifierAccess.getId().getName());
      else
        errorsLog.get(currentEnv.getName()).add(
          "\t" + dataIdentifierAccess.getLine() + ":" + dataIdentifierAccess.getColumn() +
          ": Variable \"" + name + "\" does not have a property named \"" + dataIdentifierAccess.getId().getName() + "\""
        );
    }
    else
      errorsLog.get(currentEnv.getName()).add(
        "\t" + dataIdentifierAccess.getLvalue().getLine() + ":" + dataIdentifierAccess.getLvalue().getColumn() +
        ": Undefined variable \"" + name + "\""
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

    errorsLog.get(currentEnv.getName()).add(
      "\t" + div.getLine() + ":" + div.getColumn() +
      ": Binary operator \"" + div.getSymbol() + "\" does not apply to types \"" +
      leftExprType.toString() + "\" and \"" + rightExprType.toString() + "\""
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
      ": Binary operator \"" + eq.getSymbol() + "\" does not apply to types \"" +
      leftExprType.toString() + "\" and \"" + rightExprType.toString() + "\""
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
      errorsLog.get(currentEnv.getName()).add(
        "\t" + id.getLine() + ":" + id.getColumn() +
        ": Undefined variable \"" + id.getName() + "\""
      );
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
        ": \"" + ifCmd.getName() + "\" expression cannot be of type \"" + exprType.toString() + "\""
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
        ": \"" + ifElseCmd.getName() + "\" expression cannot be of type \"" + exprType.toString() + "\""
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
        ": \"" + iterateCmd.getName() + "\" expression cannot be of type \"" + exprType.toString() + "\""
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
      ": Binary operator \"" + lt.getSymbol() + "\" does not apply to types \"" +
      leftExprType.toString() + "\" and \"" + rightExprType.toString() + "\""
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
      ": Binary operator \"" + mod.getSymbol() + "\" does not apply to types \"" +
      leftExprType.toString() + "\" and \"" + rightExprType.toString() + "\""
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
      ": Binary operator \"" + mult.getSymbol() + "\" does not apply to types \"" +
      leftExprType.toString() + "\" and \"" + rightExprType.toString() + "\""
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
      ": Unary operator \"" + neg.getSymbol() + "\" does not apply to type \"" + exprType.toString() + "\""
    );

    return null;
  }

  @Override
  public Object visitNew(New newCmd) {
    if (newCmd.getExpression() instanceof IntLiteral || newCmd.getExpression() == null) {
      if (newCmd.getType() instanceof CustomType) {
        CustomType customType = customTypes.get(newCmd.getType().toString());

        if (customType != null)
          return customType;
        else
          errorsLog.get(currentEnv.getName()).add(
            "\t" + newCmd.getLine() + ":" + newCmd.getColumn() +
            ": data \"" + newCmd.getType().toString() + "\" was not defined"
          );
      }
      else {
        return newCmd.getType();
      }
    }
    else {
      errorsLog.get(currentEnv.getName()).add(
        "\t" + newCmd.getLine() + ":" + newCmd.getColumn() +
        ": Can not determine size of array declaration"
      );
    }

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
      ": Unary operator \"" + not.getSymbol() + "\" does not apply to type \"" + exprType.toString() + "\""
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
      ": Binary operator \"" + neq.getSymbol() + "\" does not apply to types \"" +
      leftExprType.toString() + "\" and \"" + rightExprType.toString() + "\""
    );

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
    Identifier lvalueIdentifier = readCmd.getLvalue().getIdentifier();
    AbstractType actualType = intType;

    if (!currentEnv.getVarsTypes().containsKey(lvalueIdentifier.getName())) {
      if (readCmd.getLvalue() instanceof ArrayAccess)
        errorsLog.get(currentEnv.getName()).add(
          "\t" + readCmd.getLvalue().getLine() + ":" + readCmd.getLvalue().getColumn() +
          ": Undefined variable \"" + lvalueIdentifier.getName() + "\""
        );
      else
        currentEnv.getVarsTypes().put(lvalueIdentifier.getName(), actualType);
    }
    else {
      AbstractType expectedType = (AbstractType) readCmd.getLvalue().accept(this);

      if (actualType != null && !actualType.match(expectedType))
        errorsLog.get(currentEnv.getName()).add(
          "\t" + readCmd.getLine() + ":" + readCmd.getColumn() +
          ": A value of type \"" + actualType.toString() + "\" cannot be assigned to an entity of type \"" + expectedType.toString() + "\""
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

      if (fCall.getLvalues().size() == localEnv.getReturnsTypes().size()) {
        for (int i = 0; i < fCall.getLvalues().size(); i++) {
          AbstractLvalue lvalue = fCall.getLvalues().get(i);
          AbstractType expectedType = localEnv.getReturnsTypes().get(i);

          if (!currentEnv.getVarsTypes().containsKey(lvalue.getIdentifier().getName())) {
            if (lvalue instanceof ArrayAccess)
              errorsLog.get(currentEnv.getName()).add(
                "\t" + lvalue.getLine() + ":" + lvalue.getColumn() +
                ": Undefined variable \"" + lvalue.getIdentifier().getName() + "\""
              );
            else
              currentEnv.getVarsTypes().put(lvalue.getIdentifier().getName(), expectedType);
          }
          else {
            AbstractType actualType = (AbstractType) lvalue.accept(this);

            if (!actualType.match(expectedType)) {
              errorsLog.get(currentEnv.getName()).add(
                "\t" + fCall.getLine() + ":" + fCall.getColumn() +
                ": A value of type \"" + expectedType.toString() + "\" cannot be assigned to an entity of type \"" + actualType.toString() + "\""
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
    else {
      errorsLog.get(currentEnv.getName()).add(
        "\t" + fCall.getLine() + ":" + fCall.getColumn() +
        ": Cannot resolve function call \"" + fCall.toString() + "\""
      );
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

    errorsLog.get(currentEnv.getName()).add(
      "\t" + sub.getLine() + ":" + sub.getColumn() +
      ": Binary operator \"" + sub.getSymbol() + "\" does not apply to types \"" +
      leftExprType.toString() + "\" and \"" + rightExprType.toString() + "\""
    );

    return null;
  }
}
