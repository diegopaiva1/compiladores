package lang.compiler.visitors;

import lang.compiler.ast.Program;
import lang.compiler.ast.commands.*;
import lang.compiler.ast.literals.*;
import lang.compiler.ast.lvalues.*;
import lang.compiler.ast.miscellaneous.*;
import lang.compiler.ast.operators.binary.*;
import lang.compiler.ast.operators.binary.Module;
import lang.compiler.ast.operators.unary.*;
import lang.compiler.ast.types.*;

public abstract class AstVisitor {
  public abstract Object visitProgram(Program program);

  public abstract Object visitBoolLiteral(BoolLiteral b);

  public abstract Object visitCharLiteral(CharLiteral c);

  public abstract Object visitFloatLiteral(FloatLiteral f);

  public abstract Object visitIntLiteral(IntLiteral i);

  public abstract Object visitNullLiteral(NullLiteral n);

  public abstract Object visitAddition(Addition add);

  public abstract Object visitAnd(And and);

  public abstract Object visitDivision(Division div);

  public abstract Object visitEqual(Equal eq);

  public abstract Object visitLessThan(LessThan lt);

  public abstract Object visitModule(Module mod);

  public abstract Object visitMultiplication(Multiplication mult);

  public abstract Object visitNotEqual(NotEqual neq);

  public abstract Object visitSubtraction(Subtraction sub);

  public abstract Object visitNot(Not not);

  public abstract Object visitNegate(Negate neg);

  public abstract Object visitFunction(Function f);

  public abstract Object visitReturn(Return returnCmd);

  public abstract Object visitAssignableFunctionCall(AssignableFunctionCall fCall);

  public abstract Object visitStaticFunctionCall(StaticFunctionCall fCall);

  public abstract Object visitCommandScope(CommandScope cmdScope);

  public abstract Object visitPrint(Print printCmd);

  public abstract Object visitIf(If ifCmd);

  public abstract Object visitIfElse(IfElse ifElseCmd);

  public abstract Object visitNew(New newCmd);

  public abstract Object visitIterate(Iterate iterateCmd);

  public abstract Object visitIdentifier(Identifier id);

  public abstract Object visitArrayAccess(ArrayAccess arrayAccess);

  public abstract Object visitDataIdentifierAccess(DataIdentifierAccess dataIdentifierAccess);

  public abstract Object visitAssignment(Assignment assignment);

  public abstract Object visitRead(Read readCmd);

  public abstract Object visitBalancedParentheses(BalancedParenthesesExpression balanced);

  public abstract Object visitArrayType(ArrayType arrayType);

  public abstract Object visitBoolType(BoolType boolType);

  public abstract Object visitCharType(CharType charType);

  public abstract Object visitFloatType(FloatType floatType);

  public abstract Object visitIntType(IntType intType);

  public abstract Object visitCustomType(CustomType customType);

  public abstract Object visitData(Data data);

  public abstract Object visitDeclaration(Declaration decl);

  public abstract Object visitParameter(Parameter param);
}
