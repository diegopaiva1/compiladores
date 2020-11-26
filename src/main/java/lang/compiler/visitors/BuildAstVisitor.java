package lang.compiler.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;

import lang.compiler.ast.*;
import lang.compiler.ast.commands.*;
import lang.compiler.ast.literals.*;
import lang.compiler.ast.lvalues.*;
import lang.compiler.ast.miscellaneous.*;
import lang.compiler.ast.operators.binary.*;
import lang.compiler.ast.operators.binary.Module;
import lang.compiler.ast.operators.unary.*;
import lang.compiler.ast.types.*;
import lang.compiler.LangBaseVisitor;
import lang.compiler.LangParser;

public class BuildAstVisitor extends LangBaseVisitor<AbstractExpression> {
  @Override
  public AbstractExpression visitFunction(LangParser.FunctionContext ctx) {
    List<AbstractCommand> cmds = new ArrayList<>();

    for (LangParser.CmdContext cmdCtx : ctx.cmd()) {
      AbstractCommand cmd = (AbstractCommand) visit(cmdCtx);
      cmds.add(cmd);
    }

    List<AbstractType> returnTypes = new ArrayList<>();

    for (LangParser.TypeContext typeCtx : ctx.type()) {
      AbstractType type = (AbstractType) visit(typeCtx);
      returnTypes.add(type);
    }

    List<Parameter> params = new ArrayList<>();

    if (ctx.params() != null) {
      for (int i = 0 ; i < ctx.params().ID().size(); i++) {
        int paramIdLine = ctx.params().ID(i).getSymbol().getLine();
        int paramIdColumn = ctx.params().ID(i).getSymbol().getCharPositionInLine() + 1;
        Identifier paramId = new Identifier(paramIdLine, paramIdColumn, ctx.params().ID(i).getText());

        AbstractType paramType = (AbstractType) visit(ctx.params().type(i));

        params.add(new Parameter(paramIdLine, paramIdColumn, paramId, paramType));
      }
    }

    Identifier id = new Identifier(
      ctx.ID().getSymbol().getLine(),
      ctx.ID().getSymbol().getCharPositionInLine() + 1,
      ctx.ID().getText()
    );
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new Function(line, column, id, params, returnTypes, cmds);
  }

  @Override
  public AbstractExpression visitData(LangParser.DataContext ctx) {
    Map<String, AbstractType> properties = new HashMap<>();

    for (LangParser.DeclContext declCtx : ctx.decl()) {
      String propertyName = declCtx.ID().getText();
      AbstractType propertyType = (AbstractType) visit(declCtx.type());
      properties.put(propertyName, propertyType);
    }

    DataType type = new DataType(
      ctx.TYPE_NAME().getSymbol().getLine(),
      ctx.TYPE_NAME().getSymbol().getCharPositionInLine() + 1,
      ctx.TYPE_NAME().toString()
    );
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new Data(line, column, type, properties);
  }

  @Override
  public AbstractExpression visitPrint(LangParser.PrintContext ctx) {
    AbstractExpression expr = visit(ctx.exp());
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new Print(line, column, expr);
  }

  @Override
  public AbstractExpression visitRead(LangParser.ReadContext ctx) {
    AbstractLvalue lvalue = (AbstractLvalue) visit(ctx.lvalue());
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new Read(line, column, lvalue);
  }

  @Override
  public AbstractExpression visitReturn(LangParser.ReturnContext ctx) {
    List<AbstractExpression> exprs = new ArrayList<>();

    for (LangParser.ExpContext expCtx : ctx.exp())
      exprs.add(visit(expCtx));

    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new Return(line, column, exprs);
  }

  @Override
  public AbstractExpression visitIf(LangParser.IfContext ctx) {
    AbstractExpression expr = visit(ctx.exp());
    AbstractCommand scopeCmd = (AbstractCommand) visit(ctx.cmd());
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new If(line, column, expr, scopeCmd);
  }

  @Override
  public AbstractExpression visitIfElse(LangParser.IfElseContext ctx) {
    AbstractExpression expr = visit(ctx.exp());
    AbstractCommand ifScopeCmd = (AbstractCommand) visit(ctx.cmd(0));
    AbstractCommand elseScopeCmd = (AbstractCommand) visit(ctx.cmd(1));
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new IfElse(line, column, expr, ifScopeCmd, elseScopeCmd);
  }

  @Override
  public AbstractExpression visitIterate(LangParser.IterateContext ctx) {
    AbstractExpression expr = visit(ctx.exp());
    AbstractCommand cmd = (AbstractCommand) visit(ctx.cmd());
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new Iterate(line, column, expr, cmd);
  }

  @Override
  public AbstractExpression visitAnd(LangParser.AndContext ctx) {
    AbstractExpression left = visit(ctx.exp(0));
    AbstractExpression right = visit(ctx.exp(1));
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new And(line, column, left, right);
  }

  @Override
  public AbstractExpression visitLessThan(LangParser.LessThanContext ctx) {
    AbstractExpression left = visit(ctx.aexp(0));
    AbstractExpression right = visit(ctx.aexp(1));
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new LessThan(line, column, left, right);
  }

  @Override
  public AbstractExpression visitEqual(LangParser.EqualContext ctx) {
    AbstractExpression left = visit(ctx.rexp());
    AbstractExpression right = visit(ctx.aexp());
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new Equal(line, column, left, right);
  }

  @Override
  public AbstractExpression visitNotEqual(LangParser.NotEqualContext ctx) {
    AbstractExpression left = visit(ctx.rexp());
    AbstractExpression right = visit(ctx.aexp());
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new NotEqual(line, column, left, right);
  }

  @Override
  public AbstractExpression visitAddition(LangParser.AdditionContext ctx) {
    AbstractExpression left = visit(ctx.aexp());
    AbstractExpression right = visit(ctx.mexp());
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new Addition(line, column, left, right);
  }

  @Override
  public AbstractExpression visitSubtraction(LangParser.SubtractionContext ctx) {
    AbstractExpression left = visit(ctx.aexp());
    AbstractExpression right = visit(ctx.mexp());
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new Subtraction(line, column, left, right);
  }

  @Override
  public AbstractExpression visitMultiplication(LangParser.MultiplicationContext ctx) {
    AbstractExpression left = visit(ctx.mexp());
    AbstractExpression right = visit(ctx.sexp());
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new Multiplication(line, column, left, right);
  }

  @Override
  public AbstractExpression visitDivision(LangParser.DivisionContext ctx) {
    AbstractExpression left = visit(ctx.mexp());
    AbstractExpression right = visit(ctx.sexp());
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new Division(line, column, left, right);
  }

  @Override
  public AbstractExpression visitModule(LangParser.ModuleContext ctx) {
    AbstractExpression left = visit(ctx.mexp());
    AbstractExpression right = visit(ctx.sexp());
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new Module(line, column, left, right);
  }

  @Override
  public AbstractExpression visitNot(LangParser.NotContext ctx) {
    AbstractExpression expr = visit(ctx.sexp());
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new Not(line, column, expr);
  }

  @Override
  public AbstractExpression visitNegate(LangParser.NegateContext ctx) {
    AbstractExpression expr = visit(ctx.sexp());
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new Negate(line, column, expr);
  }

  @Override
  public AbstractExpression visitTrue(LangParser.TrueContext ctx) {
    Boolean value = Boolean.parseBoolean(ctx.getText());
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new BoolLiteral(line, column, value);
  }

  @Override
  public AbstractExpression visitFalse(LangParser.FalseContext ctx) {
    Boolean value = Boolean.parseBoolean(ctx.getText());
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new BoolLiteral(line, column, value);
  }

  @Override
  public AbstractExpression visitChar(LangParser.CharContext ctx) {
    String valueText = ctx.CHAR().getText();
    valueText = valueText.substring(1, valueText.length() - 1); // Pick string inside simple quotes ''
    Character value = StringEscapeUtils.unescapeJava(valueText).charAt(0); // If special char, unscape it
    int line = ctx.CHAR().getSymbol().getLine();
    int column = ctx.CHAR().getSymbol().getCharPositionInLine() + 1;
    return new CharLiteral(line, column, value);
  }

  @Override
  public AbstractExpression visitFloat(LangParser.FloatContext ctx) {
    String valueText = ctx.FLOAT().getText();
    Float value = Float.parseFloat(valueText);
    int line = ctx.FLOAT().getSymbol().getLine();
    int column = ctx.FLOAT().getSymbol().getCharPositionInLine() + 1;
    return new FloatLiteral(line, column, value);
  }

  @Override
  public AbstractExpression visitInt(LangParser.IntContext ctx) {
    String valueText = ctx.INT().getText();
    Integer value = Integer.parseInt(valueText);
    int line = ctx.INT().getSymbol().getLine();
    int column = ctx.INT().getSymbol().getCharPositionInLine() + 1;
    return new IntLiteral(line, column, value);
  }

  @Override
  public AbstractExpression visitNull(LangParser.NullContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new NullLiteral(line, column, null);
  }

  @Override
  public AbstractExpression visitAssignableFunctionCall(LangParser.AssignableFunctionCallContext ctx) {
    List<AbstractExpression> args = new ArrayList<>();

    if (ctx.exps() != null)
      for (LangParser.ExpContext expCtx : ctx.exps().exp())
        args.add(visit(expCtx));

    Identifier id = new Identifier(
      ctx.ID().getSymbol().getLine(),
      ctx.ID().getSymbol().getCharPositionInLine() + 1,
      ctx.ID().getText()
    );
    AbstractExpression index = visit(ctx.exp());
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new AssignableFunctionCall(line, column, id, args, index);
  }

  @Override
  public AbstractExpression visitInstantiation(LangParser.InstantiationContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractType type;
    AbstractExpression expr = null;

    if (ctx.exp() != null) {
      type = new ArrayType(line, column, (AbstractType) visit(ctx.type()));
      expr = visit(ctx.exp());
    }
    else
      type = (AbstractType) visit(ctx.type());

    return new New(line, column, type, expr);
  }

  @Override
  public AbstractExpression visitBalancedParenthesesExpression(LangParser.BalancedParenthesesExpressionContext ctx) {
    AbstractExpression expr = visit(ctx.exp());
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new BalancedParenthesesExpression(line, column, expr);
  }

  @Override
  public AbstractExpression visitCommandScope(LangParser.CommandScopeContext ctx) {
    List<AbstractCommand> cmds = new ArrayList<>();

    for (LangParser.CmdContext cmdCtx : ctx.cmd())
      cmds.add((AbstractCommand) visit(cmdCtx));

    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new CommandScope(line, column, cmds);
  }

  @Override
  public AbstractExpression visitArrayAccess(LangParser.ArrayAccessContext ctx) {
    AbstractLvalue lvalue = (AbstractLvalue) visit(ctx.lvalue());
    AbstractExpression expr = visit(ctx.exp());
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new ArrayAccess(line, column, lvalue, expr);
  }

  @Override
  public AbstractExpression visitIdentifier(LangParser.IdentifierContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new Identifier(line, column, ctx.ID().getText());
  }

  @Override
  public AbstractExpression visitDataIdentifierAccess(LangParser.DataIdentifierAccessContext ctx) {
    AbstractLvalue lvalue = (AbstractLvalue) visit(ctx.lvalue());
    Identifier id = new Identifier(
      ctx.ID().getSymbol().getLine(),
      ctx.ID().getSymbol().getCharPositionInLine() + 1,
      ctx.ID().getText()
    );
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new DataIdentifierAccess(line, column, lvalue, id);
  }

  @Override
  public AbstractExpression visitStaticFunctionCall(LangParser.StaticFunctionCallContext ctx) {
    List<AbstractLvalue> lvalues = new ArrayList<>();

    for (LangParser.LvalueContext lvalueCtx : ctx.lvalue()) {
      AbstractLvalue lvalue = (AbstractLvalue) visit(lvalueCtx);
      lvalues.add(lvalue);
    }

    List<AbstractExpression> args = new ArrayList<>();

    if (ctx.exps() != null)
      for (LangParser.ExpContext expCtx : ctx.exps().exp())
        args.add(visit(expCtx));

    Identifier id = new Identifier(
      ctx.ID().getSymbol().getLine(),
      ctx.ID().getSymbol().getCharPositionInLine() + 1,
      ctx.ID().getText()
    );
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new StaticFunctionCall(line, column, id, args, lvalues);
  }

  @Override
  public AbstractExpression visitAssignment(LangParser.AssignmentContext ctx) {
    AbstractLvalue lvalue = (AbstractLvalue) visit(ctx.lvalue());
    AbstractExpression expr = visit(ctx.exp());
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new Assignment(line, column, lvalue, expr);
  }

  @Override
  public AbstractExpression visitBoolType(LangParser.BoolTypeContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new BoolType(line, column);
  }

  @Override
  public AbstractExpression visitCharType(LangParser.CharTypeContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new CharType(line, column);
  }

  @Override
  public AbstractExpression visitFloatType(LangParser.FloatTypeContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new FloatType(line, column);
  }

  @Override
  public AbstractExpression visitIntType(LangParser.IntTypeContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new IntType(line, column);
  }

  @Override
  public AbstractExpression visitArrayType(LangParser.ArrayTypeContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new ArrayType(line, column, (AbstractType) visit(ctx.type()));
  }

  @Override
  public AbstractExpression visitDataType(LangParser.DataTypeContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new DataType(line, column, ctx.TYPE_NAME().getText());
  }
}
