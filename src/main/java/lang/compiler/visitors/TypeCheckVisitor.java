package lang.compiler.visitors;

import java.util.ArrayList;
import java.util.List;

import lang.compiler.ast.commands.*;
import lang.compiler.ast.literals.*;
import lang.compiler.ast.lvalues.ArrayAccess;
import lang.compiler.ast.lvalues.DataIdentifierAccess;
import lang.compiler.ast.lvalues.Identifier;
import lang.compiler.ast.miscellaneous.*;
import lang.compiler.ast.operators.binary.*;
import lang.compiler.ast.operators.binary.Module;
import lang.compiler.ast.operators.unary.Negate;
import lang.compiler.ast.operators.unary.Not;
import lang.compiler.ast.types.*;
import lang.compiler.visitors.AstVisitor;

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
    for (AbstractCommand cmd : f.getCommands()) {
      AbstractType cmdType = (AbstractType) cmd.accept(this);
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

    errorsLog.add("Operator " + add.getSymbol() + " does not apply to types " + leftExprType.toString() + " and "
        + rightExprType.toString());

    return null;
  }

  public Object visitPrint(Print printCmd) {
    return printCmd.getExpression().accept(this);
  }

  public Object visitBoolLiteral(BoolLiteral c) {
    return boolType;
  }

  public Object visitCharLiteral(CharLiteral c) {
    return charType;
  }

  public Object visitIntLiteral(IntLiteral i) {
    return intType;
  }

  public Object visitFloatLiteral(FloatLiteral i) {
    return floatType;
  }

  @Override
  public Object visitAnd(And and) {
    // TODO Auto-generated method stub
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
    // TODO Auto-generated method stub
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
    // TODO Auto-generated method stub
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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitEqual(Equal eq) {
    // TODO Auto-generated method stub
    return null;
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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitIfElse(IfElse ifElseCmd) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitIntType(IntType intType) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitIterate(Iterate iterateCmd) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitLessThan(LessThan lt) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitModule(Module mod) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitMultiplication(Multiplication mult) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitNegate(Negate neg) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitNew(New newCmd) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitNot(Not not) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object visitNotEqual(NotEqual neq) {
    // TODO Auto-generated method stub
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
    // TODO Auto-generated method stub
    return null;
  }
}
