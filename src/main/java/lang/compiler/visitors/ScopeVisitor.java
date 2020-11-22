package lang.compiler.visitors;

import lang.compiler.ast.*;
import lang.compiler.ast.commands.*;
import lang.compiler.ast.literals.*;
import lang.compiler.ast.lvalues.*;
import lang.compiler.ast.miscellaneous.*;
import lang.compiler.ast.operators.binary.*;
import lang.compiler.ast.operators.binary.Module;
import lang.compiler.ast.operators.unary.*;
import lang.compiler.ast.types.*;

public class ScopeVisitor extends AstVisitor {
  private ScopeTable table;
  private ErrorLogger logger;
  private Function currentFunction;

  public ScopeVisitor(ErrorLogger logger) {
    this.logger = logger;
    this.table = new ScopeTable();
  }

  @Override
  public Void visitAddition(Addition add) {
    add.getLeft().accept(this);
    add.getRight().accept(this);
    return null;
  }

  @Override
  public Void visitAnd(And and) {
    and.getLeft().accept(this);
    and.getRight().accept(this);
    return null;
  }

  @Override
  public Object visitArrayAccess(ArrayAccess arrayAccess) {
    return arrayAccess.getLvalue().getIdentifier().accept(this);
  }

  @Override
  public Void visitArrayType(ArrayType arrayType) {
    return null;
  }

  @Override
  public Void visitAssignableFunctionCall(AssignableFunctionCall fCall) {
    for (AbstractExpression expr : fCall.getArgs())
      expr.accept(this);

    return null;
  }

  @Override
  public Void visitAssignment(Assignment assignment) {
    String key = assignment.getLvalue().getIdentifier().getName();
    Symbol symbol = table.search(key);

    if (symbol == null)
      table.put(key, new Symbol());

    assignment.getExpression().accept(this);
    return null;
  }

  @Override
  public Object visitBalancedParentheses(BalancedParenthesesExpression balanced) {
    return balanced.getExpression().accept(this);
  }

  @Override
  public Void visitBoolLiteral(BoolLiteral b) {
    return null;
  }

  @Override
  public Void visitBoolType(BoolType boolType) {
    return null;
  }

  @Override
  public Void visitCharLiteral(CharLiteral c) {
    return null;
  }

  @Override
  public Void visitCharType(CharType charType) {
    return null;
  }

  @Override
  public Void visitCommandScope(CommandScope cmdScope) {
    table.push(new SymbolTable());

    for (AbstractCommand cmd : cmdScope.getCommmands())
      cmd.accept(this);

    table.pop();
    return null;
  }

  @Override
  public Void visitDataType(DataType dataType) {
    return null;
  }

  @Override
  public Void visitData(Data data) {
    return null;
  }

  @Override
  public Void visitDataIdentifierAccess(DataIdentifierAccess dataIdentifierAccess) {
    dataIdentifierAccess.getIdentifier().accept(this);
    return null;
  }

  @Override
  public Void visitDeclaration(Declaration decl) {
    return null;
  }

  @Override
  public Void visitDivision(Division div) {
    div.getLeft().accept(this);
    div.getRight().accept(this);
    return null;
  }

  @Override
  public Void visitEqual(Equal eq) {
    eq.getLeft().accept(this);
    eq.getRight().accept(this);
    return null;
  }

  @Override
  public Void visitFloatLiteral(FloatLiteral f) {
    return null;
  }

  @Override
  public Void visitFloatType(FloatType floatType) {
    return null;
  }

  @Override
  public Void visitFunction(Function f) {
    currentFunction = f;
    table.push(new SymbolTable());

    for (Parameter param : f.getParameters())
      table.put(param.getId().getName(), new Symbol());

    for (AbstractCommand cmd : f.getCommands())
      cmd.accept(this);

    table.pop();
    return null;
  }

  @Override
  public Void visitIdentifier(Identifier id) {
    Symbol symbol = table.search(id.getName());

    if (symbol == null)
      logger.addUndefinedLvalueError(currentFunction, id);

    return null;
  }

  @Override
  public Void visitIf(If ifCmd) {
    ifCmd.getExpression().accept(this);
    ifCmd.getScopeCommand().accept(this);
    return null;
  }

  @Override
  public Void visitIfElse(IfElse ifElseCmd) {
    ifElseCmd.getExpression().accept(this);
    ifElseCmd.getIfScopeCommand().accept(this);
    ifElseCmd.getElseScopeCommand().accept(this);
    return null;
  }

  @Override
  public Void visitIntLiteral(IntLiteral i) {
    return null;
  }

  @Override
  public Void visitIntType(IntType intType) {
    return null;
  }

  @Override
  public Void visitIterate(Iterate iterateCmd) {
    iterateCmd.getExpression().accept(this);
    iterateCmd.getCommand().accept(this);
    return null;
  }

  @Override
  public Void visitLessThan(LessThan lt) {
    lt.getLeft().accept(this);
    lt.getRight().accept(this);
    return null;
  }

  @Override
  public Void visitModule(Module mod) {
    mod.getLeft().accept(this);
    mod.getRight().accept(this);
    return null;
  }

  @Override
  public Void visitMultiplication(Multiplication mult) {
    mult.getLeft().accept(this);
    mult.getRight().accept(this);
    return null;
  }

  @Override
  public Void visitNegate(Negate neg) {
    neg.getExpression().accept(this);
    return null;
  }

  @Override
  public Void visitNew(New newCmd) {
    if (newCmd.getExpression() != null)
      newCmd.getExpression().accept(this);

    return null;
  }

  @Override
  public Void visitNot(Not not) {
    not.getExpression().accept(this);
    return null;
  }

  @Override
  public Void visitNotEqual(NotEqual neq) {
    neq.getLeft().accept(this);
    neq.getRight().accept(this);
    return null;
  }

  @Override
  public Void visitNullLiteral(NullLiteral n) {
    return null;
  }

  @Override
  public Void visitParameter(Parameter param) {
    return null;
  }

  @Override
  public Void visitPrint(Print printCmd) {
    printCmd.getExpression().accept(this);
    return null;
  }

  @Override
  public Void visitProgram(Program program) {
    int mainFunctionDefinitions = 0;

    for (Function f : program.getFunctionSet()) {
      f.accept(this);

      if (f.getId().getName().equals("main")) {
        mainFunctionDefinitions++;

        if (mainFunctionDefinitions > 1) {
          System.err.println("Error: multiple definitions of \"main\" function");
          System.exit(1);
        }
      }
    }

    if (mainFunctionDefinitions == 0) {
      System.err.println("Error: \"main\" function not provided");
      System.exit(1);
    }

    return null;
  }

  @Override
  public Void visitRead(Read readCmd) {
    String key = readCmd.getLvalue().getIdentifier().getName();

    if (table.search(key) == null)
      table.put(key, new Symbol());

    readCmd.getLvalue().getIdentifier().accept(this);
    return null;
  }

  @Override
  public Void visitReturn(Return returnCmd) {
    for (AbstractExpression expr : returnCmd.getExpressions())
      expr.accept(this);

    return null;
  }

  @Override
  public Void visitStaticFunctionCall(StaticFunctionCall fCall) {
    for (AbstractExpression expr : fCall.getArgs())
      expr.accept(this);

    for (AbstractLvalue lvalue : fCall.getLvalues()) {
      String key = lvalue.getIdentifier().getName();

      if (table.search(key) == null)
        table.put(key, new Symbol());
    }

    return null;
  }

  @Override
  public Void visitSubtraction(Subtraction sub) {
    sub.getLeft().accept(this);
    sub.getRight().accept(this);
    return null;
  }
}
