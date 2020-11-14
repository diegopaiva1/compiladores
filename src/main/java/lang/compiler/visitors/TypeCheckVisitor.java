package lang.compiler.visitors;

import java.util.ArrayList;
import java.util.List;

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

  public TypeCheckVisitor() {
    this.boolType = new BoolType();
    this.charType = new CharType();
    this.floatType = new FloatType();
    this.intType = new IntType();
    this.errorsLog = new ArrayList<>();
  }

  public void printErrors() {
    for (String error : errorsLog)
      System.err.println(error);
  }

  public Object visitFunction(Function f) {
    for (AbstractCommand cmd : f.getCommands())
      cmd.accept(this);

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
    printCmd.getExpression().accept(this);
    return null;
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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitArrayType(ArrayType arrayType) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitAssignableFunctionCall(AssignableFunctionCall fCall) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitAssignment(Assignment assignment) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitBalancedParentheses(BalancedParenthesesExpression balanced) {
    balanced.getExpression().accept(this);
    return null;
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

    // Bool can only compare to another bool
    if ((leftExprType.match(boolType) && !rightExprType.match(boolType)) ||
        (!leftExprType.match(boolType) && rightExprType.match(boolType))) {
      errorsLog.add("Binary operator \"" + eq.getSymbol() + "\" does not apply to types " +
                     leftExprType.toString() + " and " + rightExprType.toString());
      return null;
    }

    return leftExprType;
  }

  @Override
  public Object visitFloatType(FloatType floatType) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitIdentifier(Identifier id) {
    // TODO Auto-generated method stub
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
    // TODO Auto-generated method stub
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

    // Bool can only compare to another bool
    if ((leftExprType.match(boolType) && !rightExprType.match(boolType)) ||
        (!leftExprType.match(boolType) && rightExprType.match(boolType))) {
      errorsLog.add("Binary operator \"" + neq.getSymbol() + "\" does not apply to types " +
                     leftExprType.toString() + " and " + rightExprType.toString());
      return null;
    }

    return leftExprType;
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
    // TODO Auto-generated method stub
    return null;
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
