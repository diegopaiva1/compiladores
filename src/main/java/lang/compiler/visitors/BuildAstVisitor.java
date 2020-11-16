package lang.compiler.visitors;

import java.util.ArrayList;
import java.util.List;

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
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    Identifier id = new Identifier(
      ctx.ID().getSymbol().getLine(), ctx.ID().getSymbol().getCharPositionInLine() + 1, ctx.ID().getText()
    );
    List<AbstractCommand> cmds = new ArrayList<>();
    List<Parameter> params = new ArrayList<>();
    List<AbstractType> returnTypes = new ArrayList<>();

    for (LangParser.CmdContext cmdCtx : ctx.cmd()) {
      AbstractCommand cmd = (AbstractCommand) visit(cmdCtx);
      cmds.add(cmd);
    }

    for (LangParser.TypeContext typeCtx : ctx.type()) {
      AbstractType type = (AbstractType) visit(typeCtx);
      returnTypes.add(type);
    }

    if (ctx.params() != null) {
      for (int i = 0 ; i < ctx.params().ID().size(); i++) {
        int paramIdLine = ctx.params().ID(i).getSymbol().getLine();
        int paramIdColumn = ctx.params().ID(i).getSymbol().getCharPositionInLine() + 1;
        Identifier paramId = new Identifier(paramIdLine, paramIdColumn, ctx.params().ID(i).getText());

        AbstractType paramType = (AbstractType) visit(ctx.params().type(i));

        params.add(new Parameter(paramIdLine, paramIdColumn, paramId, paramType));
      }
    }

    return new Function(line, column, id, params, returnTypes, cmds);
  }

  @Override
  public AbstractExpression visitData(LangParser.DataContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    List<Declaration> decls = new ArrayList<>();
    CustomType type = new CustomType(line, column, ctx.TYPE_NAME().getText());

    for (LangParser.DeclContext declCtx : ctx.decl()){
      Declaration decl = (Declaration) visit(declCtx);
      type.addVarType(decl.getId().getName(), decl.getType());
    }

    return new Data(line, column, type);
  }

  @Override
  public AbstractExpression visitDeclaration(LangParser.DeclarationContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    Identifier id = new Identifier(
      ctx.ID().getSymbol().getLine(), ctx.ID().getSymbol().getCharPositionInLine() + 1,ctx.ID().getText()
    );
    AbstractType type = (AbstractType) visit(ctx.type());
    return new Declaration(line, column, id, type);
  }

  @Override
  public AbstractExpression visitPrint(LangParser.PrintContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractExpression expr = visit(ctx.exp());
    return new Print(line, column, expr);
  }

  @Override
  public AbstractExpression visitRead(LangParser.ReadContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractLvalue lvalue = (AbstractLvalue) visit(ctx.lvalue());
    return new Read(line, column, lvalue);
  }

  @Override
  public AbstractExpression visitReturn(LangParser.ReturnContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    List<AbstractExpression> exprs = new ArrayList<>();

    for (LangParser.ExpContext expCtx : ctx.exp()) {
      AbstractExpression expr = visit(expCtx);
      exprs.add(expr);
    }

    return new Return(line, column, exprs);
  }

  @Override
  public AbstractExpression visitIf(LangParser.IfContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractExpression expr = visit(ctx.exp());
    AbstractCommand scopeCmd = (AbstractCommand) visit(ctx.cmd());
    return new If(line, column, expr, scopeCmd);
  }

  @Override
  public AbstractExpression visitIfElse(LangParser.IfElseContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractExpression expr = visit(ctx.exp());
    AbstractCommand ifScopeCmd = (AbstractCommand) visit(ctx.cmd(0));
    AbstractCommand elseScopeCmd = (AbstractCommand) visit(ctx.cmd(1));
    return new IfElse(line, column, expr, ifScopeCmd, elseScopeCmd);
  }

  @Override
  public AbstractExpression visitIterate(LangParser.IterateContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractExpression expr = visit(ctx.exp());
    AbstractCommand cmd = (AbstractCommand) visit(ctx.cmd());
    return new Iterate(line, column, expr, cmd);
  }

  @Override
  public AbstractExpression visitAnd(LangParser.AndContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractExpression left = visit(ctx.exp(0));
    AbstractExpression right = visit(ctx.exp(1));
    return new And(line, column, left, right);
  }

  @Override
  public AbstractExpression visitLessThan(LangParser.LessThanContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractExpression left = visit(ctx.aexp(0));
    AbstractExpression right = visit(ctx.aexp(1));
    return new LessThan(line, column, left, right);
  }

  @Override
  public AbstractExpression visitEqual(LangParser.EqualContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractExpression left = visit(ctx.rexp());
    AbstractExpression right = visit(ctx.aexp());
    return new Equal(line, column, left, right);
  }

  @Override
  public AbstractExpression visitNotEqual(LangParser.NotEqualContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractExpression left = visit(ctx.rexp());
    AbstractExpression right = visit(ctx.aexp());
    return new NotEqual(line, column, left, right);
  }

  @Override
  public AbstractExpression visitAddition(LangParser.AdditionContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractExpression left = visit(ctx.aexp());
    AbstractExpression right = visit(ctx.mexp());
    return new Addition(line, column, left, right);
  }

  @Override
  public AbstractExpression visitSubtraction(LangParser.SubtractionContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractExpression left = visit(ctx.aexp());
    AbstractExpression right = visit(ctx.mexp());
    return new Subtraction(line, column, left, right);
  }

  @Override
  public AbstractExpression visitMultiplication(LangParser.MultiplicationContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractExpression left = visit(ctx.mexp());
    AbstractExpression right = visit(ctx.sexp());
    return new Multiplication(line, column, left, right);
  }

  @Override
  public AbstractExpression visitDivision(LangParser.DivisionContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractExpression left = visit(ctx.mexp());
    AbstractExpression right = visit(ctx.sexp());
    return new Division(line, column, left, right);
  }

  @Override
  public AbstractExpression visitModule(LangParser.ModuleContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractExpression left = visit(ctx.mexp());
    AbstractExpression right = visit(ctx.sexp());
    return new Module(line, column, left, right);
  }

  @Override
  public AbstractExpression visitNot(LangParser.NotContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractExpression expr = visit(ctx.sexp());
    return new Not(line, column, expr);
  }

  @Override
  public AbstractExpression visitNegate(LangParser.NegateContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractExpression expr = visit(ctx.sexp());
    return new Negate(line, column, expr);
  }

  @Override
  public AbstractExpression visitTrue(LangParser.TrueContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    Boolean value = Boolean.parseBoolean(ctx.getText());
    return new BoolLiteral(line, column, value);
  }

  @Override
  public AbstractExpression visitFalse(LangParser.FalseContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    Boolean value = Boolean.parseBoolean(ctx.getText());
    return new BoolLiteral(line, column, value);
  }

  @Override
  public AbstractExpression visitChar(LangParser.CharContext ctx) {
    int line = ctx.CHAR().getSymbol().getLine();
    int column = ctx.CHAR().getSymbol().getCharPositionInLine() + 1;
    String valueText = ctx.CHAR().getText();
    valueText = valueText.substring(1, valueText.length() - 1); // Pick string inside simple quotes ''
    Character value = StringEscapeUtils.unescapeJava(valueText).charAt(0); // If special char, unscape it
    return new CharLiteral(line, column, value);
  }

  @Override
  public AbstractExpression visitFloat(LangParser.FloatContext ctx) {
    int line = ctx.FLOAT().getSymbol().getLine();
    int column = ctx.FLOAT().getSymbol().getCharPositionInLine() + 1;
    String valueText = ctx.FLOAT().getText();
    Float value = Float.parseFloat(valueText);
    return new FloatLiteral(line, column, value);
  }

  @Override
  public AbstractExpression visitInt(LangParser.IntContext ctx) {
    int line = ctx.INT().getSymbol().getLine();
    int column = ctx.INT().getSymbol().getCharPositionInLine() + 1;
    String valueText = ctx.INT().getText();
    Integer value = Integer.parseInt(valueText);
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
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    Identifier id = new Identifier(
      ctx.ID().getSymbol().getLine(), ctx.ID().getSymbol().getCharPositionInLine() + 1, ctx.ID().getText()
    );
    List<AbstractExpression> args = new ArrayList<>();
    AbstractExpression index = visit(ctx.exp());

    if (ctx.exps() != null)
      for (LangParser.ExpContext expCtx : ctx.exps().exp())
        args.add(visit(expCtx));

    return new AssignableFunctionCall(line, column, id, args, index);
  }

  @Override
  public AbstractExpression visitInstantiation(LangParser.InstantiationContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractType type = (AbstractType) visit(ctx.type());
    AbstractExpression expr = ctx.exp() != null ? visit(ctx.exp()) : null;
    return new New(line, column, type, expr);
  }

  @Override
  public AbstractExpression visitBalancedParenthesesExpression(LangParser.BalancedParenthesesExpressionContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractExpression expr = visit(ctx.exp());
    return new BalancedParenthesesExpression(line, column, expr);
  }

  @Override
  public AbstractExpression visitCommandScope(LangParser.CommandScopeContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    List<AbstractCommand> cmds = new ArrayList<>();

    for (LangParser.CmdContext cmdCtx : ctx.cmd())
      cmds.add((AbstractCommand) visit(cmdCtx));

    return new CommandScope(line, column, cmds);
  }

  @Override
  public AbstractExpression visitArrayAccess(LangParser.ArrayAccessContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractLvalue lvalue = (AbstractLvalue) visit(ctx.lvalue());
    AbstractExpression expr = visit(ctx.exp());
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
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractLvalue lvalue = (AbstractLvalue) visit(ctx.lvalue());
    // TODO: FIX LINE COL.
    Identifier id = new Identifier(0, 0, ctx.ID().getText());
    return new DataIdentifierAccess(line, column, lvalue, id);
  }

  @Override
  public AbstractExpression visitStaticFunctionCall(LangParser.StaticFunctionCallContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    Identifier id = new Identifier(
      ctx.ID().getSymbol().getLine(), ctx.ID().getSymbol().getCharPositionInLine() + 1, ctx.ID().getText()
    );
    List<AbstractLvalue> lvalues = new ArrayList<>();
    List<AbstractExpression> args = new ArrayList<>();

    for (LangParser.LvalueContext lvalueCtx : ctx.lvalue()) {
      AbstractLvalue lvalue = (AbstractLvalue) visit(lvalueCtx);
      lvalues.add(lvalue);
    }

    if (ctx.exps() != null) {
      /**
       *          --------------------
       *          |       EXPS       |
       *          --------------------
       *         /    |     |    |    \
       *        exp   ,    exp   ,    ...
       *        0     1     2    3     n
       */

      for (int i = 0; i < ctx.exps().getChildCount(); i += 2) {
        AbstractExpression arg = visit(ctx.exps().getChild(i));
        args.add(arg);
      }
    }

    return new StaticFunctionCall(line, column, id, args, lvalues);
  }

  @Override
  public AbstractExpression visitAssignment(LangParser.AssignmentContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    AbstractLvalue lvalue = (AbstractLvalue) visit(ctx.lvalue());
    AbstractExpression expr = visit(ctx.exp());
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
  public AbstractExpression visitCustomType(LangParser.CustomTypeContext ctx) {
    int line = ctx.getStart().getLine();
    int column = ctx.getStart().getCharPositionInLine() + 1;
    return new CustomType(line, column, ctx.TYPE_NAME().getText());
  }
}
