package lang.compiler.visitors;

import java.util.ArrayList;
import java.util.List;

import lang.compiler.ast.*;
import lang.compiler.ast.commands.*;
import lang.compiler.ast.operators.binary.*;
import lang.compiler.ast.operators.binary.Module;
import lang.compiler.ast.operators.unary.*;
import lang.compiler.parser.LangBaseVisitor;
import lang.compiler.parser.LangParser;

public class BuildAstVisitor extends LangBaseVisitor<AbstractExpression> {
  @Override
  public AbstractExpression visitFunction(LangParser.FunctionContext ctx) {
    String id = ctx.ID().getText();
    List<AbstractCommand> cmds = new ArrayList<>();
    List<Parameter> params = new ArrayList<>();

    for (LangParser.CmdContext cmdCtx : ctx.cmd()) {
      AbstractCommand cmd = (AbstractCommand) visit(cmdCtx);
      cmds.add(cmd);
    }

    if (ctx.params() != null) {
      /**
       *          ----------
       *          | PARAMS |
       *          ----------
       *         /    |     \
       *        ID    ::   type
       *        0     1     2
       */
      String paramId = ctx.params().getChild(0).getText();
      String paramType = ctx.params().getChild(2).getText();
      params.add(new Parameter(paramId, paramType));

      /**
       *      ----------------------------------------------
       *      |                   PARAMS                   |
       *      ----------------------------------------------
       *     /    |     |     |      |     |     |     |    \
       *    ID    ::  type    ,     ID     ::   type   ,    ...
       *    0     1     2     3      4      5    6     7     n
       */
      for (int i = 4; i < ctx.params().getChildCount(); i += 4) {
        paramId = ctx.params().getChild(i).getText();
        paramType = ctx.params().getChild(i + 2).getText();
        params.add(new Parameter(paramId, paramType));
      }
    }

    return new Function(id, params, cmds);
  }

  @Override
  public AbstractExpression visitInt(LangParser.IntContext ctx) {
    String valueText = ctx.INT().getText();
    Integer value = Integer.parseInt(valueText);
    return new Int(value);
  }

  @Override
  public AbstractExpression visitData(LangParser.DataContext ctx) {
    String typeName = ctx.TYPE_NAME().getText();
    List<Declaration> decls = new ArrayList<>();

    for (LangParser.DeclContext declCtx : ctx.decl())
      decls.add((Declaration) visit(declCtx));

    return new Data(typeName, decls);
  }

  @Override
  public AbstractExpression visitDeclaration(LangParser.DeclarationContext ctx) {
    String id = ctx.ID().getText();
    String type = ctx.type().getText();
    return new Declaration(id, type);
  }

  @Override
  public AbstractExpression visitPrint(LangParser.PrintContext ctx) {
    AbstractExpression expr = visit(ctx.exp());
    return new Print(expr);
  }

  @Override
  public AbstractExpression visitRead(LangParser.ReadContext ctx) {
    AbstractExpression expr = visit(ctx.lvalue());
    return new Read(expr);
  }

  @Override
  public AbstractExpression visitReturn(LangParser.ReturnContext ctx) {
    /**         --------------------
     *          |      RETURN      |
     *          --------------------
     *         /    |     |    |    \
     *        exp   ,    exp   ,    ...
     *        0     1     2    3     n
     */
    List<AbstractExpression> exprs = new ArrayList<>();

    for (int i = 0; i < ctx.getChildCount(); i += 2) {
      AbstractExpression expr = visit(ctx.getChild(i));
      exprs.add(expr);
    }

    return new Return(exprs);
  }

  @Override
  public AbstractExpression visitIf(LangParser.IfContext ctx) {
    AbstractExpression expr = visit(ctx.exp());
    AbstractCommand cmd = (AbstractCommand) visit(ctx.cmd());
    return new If(expr, cmd);
  }

  @Override
  public AbstractExpression visitIfElse(LangParser.IfElseContext ctx) {
    AbstractExpression expr = visit(ctx.exp());
    AbstractCommand ifCmd = (AbstractCommand) visit(ctx.cmd(0));
    AbstractCommand elseCmd = (AbstractCommand) visit(ctx.cmd(1));
    return new IfElse(expr, ifCmd, elseCmd);
  }

  @Override
  public AbstractExpression visitIterate(LangParser.IterateContext ctx) {
    AbstractExpression expr = visit(ctx.exp());
    AbstractCommand cmd = (AbstractCommand) visit(ctx.cmd());
    return new Iterate(expr, cmd);
  }

  @Override
  public AbstractExpression visitAnd(LangParser.AndContext ctx) {
    AbstractExpression left = visit(ctx.exp(0));
    AbstractExpression right = visit(ctx.exp(1));
    return new And(left, right);
  }

  @Override
  public AbstractExpression visitLessThan(LangParser.LessThanContext ctx) {
    AbstractExpression left = visit(ctx.aexp(0));
    AbstractExpression right = visit(ctx.aexp(1));
    return new LessThan(left, right);
  }

  @Override
  public AbstractExpression visitEqual(LangParser.EqualContext ctx) {
    AbstractExpression left = visit(ctx.rexp());
    AbstractExpression right = visit(ctx.aexp());
    return new Equal(left, right);
  }

  @Override
  public AbstractExpression visitNotEqual(LangParser.NotEqualContext ctx) {
    AbstractExpression left = visit(ctx.rexp());
    AbstractExpression right = visit(ctx.aexp());
    return new NotEqual(left, right);
  }

  @Override
  public AbstractExpression visitAddition(LangParser.AdditionContext ctx) {
    AbstractExpression left = visit(ctx.aexp());
    AbstractExpression right = visit(ctx.mexp());
    return new Addition(left, right);
  }

  @Override
  public AbstractExpression visitSubtraction(LangParser.SubtractionContext ctx) {
    AbstractExpression left = visit(ctx.aexp());
    AbstractExpression right = visit(ctx.mexp());
    return new Subtraction(left, right);
  }

  @Override
  public AbstractExpression visitMultiplication(LangParser.MultiplicationContext ctx) {
    AbstractExpression left = visit(ctx.mexp());
    AbstractExpression right = visit(ctx.sexp());
    return new Multiplication(left, right);
  }

  @Override
  public AbstractExpression visitDivision(LangParser.DivisionContext ctx) {
    AbstractExpression left = visit(ctx.mexp());
    AbstractExpression right = visit(ctx.sexp());
    return new Division(left, right);
  }

  @Override
  public AbstractExpression visitModule(LangParser.ModuleContext ctx) {
    AbstractExpression left = visit(ctx.mexp());
    AbstractExpression right = visit(ctx.sexp());
    return new Module(left, right);
  }

  @Override
  public AbstractExpression visitNot(LangParser.NotContext ctx) {
    AbstractExpression expr = visit(ctx.sexp());
    return new Not(expr);
  }

  @Override
  public AbstractExpression visitNegate(LangParser.NegateContext ctx) {
    AbstractExpression expr = visit(ctx.sexp());
    return new Negate(expr);
  }
}
