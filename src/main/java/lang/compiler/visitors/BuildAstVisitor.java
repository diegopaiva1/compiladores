package lang.compiler.visitors;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

import lang.compiler.ast.*;
import lang.compiler.ast.commands.*;
import lang.compiler.ast.literals.Bool;
import lang.compiler.ast.literals.Float;
import lang.compiler.ast.literals.Int;
import lang.compiler.ast.literals.Null;
import lang.compiler.ast.lvalues.AbstractLvalue;
import lang.compiler.ast.lvalues.ArrayAccess;
import lang.compiler.ast.lvalues.DataIdentifierAccess;
import lang.compiler.ast.lvalues.Identifier;
import lang.compiler.ast.operators.binary.*;
import lang.compiler.ast.operators.binary.Module;
import lang.compiler.ast.operators.unary.*;
import lang.compiler.parser.LangBaseVisitor;
import lang.compiler.parser.LangParser;
import lang.compiler.parser.LangParser.ArrayAccessContext;
import lang.compiler.parser.LangParser.CharContext;
import lang.compiler.parser.LangParser.CmdContext;
import lang.compiler.parser.LangParser.CmdScopeContext;
import lang.compiler.parser.LangParser.DataIdentifierAccessContext;
import lang.compiler.parser.LangParser.FalseContext;
import lang.compiler.parser.LangParser.FloatContext;
import lang.compiler.parser.LangParser.IdentifierContext;
import lang.compiler.parser.LangParser.NullContext;
import lang.compiler.parser.LangParser.TrueContext;

public class BuildAstVisitor extends LangBaseVisitor<AbstractExpression> {
  @Override
  public AbstractExpression visitFunction(LangParser.FunctionContext ctx) {
    String name = ctx.ID().getText();
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
      params.add(new Parameter(new Identifier(paramId), paramType));

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
        params.add(new Parameter(new Identifier(paramId), paramType));
      }
    }

    return new Function(new Identifier(name), params, cmds);
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
    return new Declaration(new Identifier(id), type);
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

  @Override
  public AbstractExpression visitTrue(TrueContext ctx) {
    Boolean value = Boolean.parseBoolean(ctx.getText());
    return new Bool(value);
  }

  @Override
  public AbstractExpression visitFalse(FalseContext ctx) {
    Boolean value = Boolean.parseBoolean(ctx.getText());
    return new Bool(value);
  }

  @Override
  public AbstractExpression visitChar(CharContext ctx) {
    String valueText = ctx.CHAR().getText();
    Character value = null;
    //TODO: Errado
    if (valueText.contains("\\"))
      value = '\\' + 'n';
    else
      value = StringUtils.substringBetween(valueText, "'", "'").charAt(0);

    System.out.println(value);
    return null;
  }

  @Override
  public AbstractExpression visitFloat(FloatContext ctx) {
    String valueText = ctx.FLOAT().getText();
    java.lang.Float value = java.lang.Float.parseFloat(valueText);
    return new Float(value);
  }

  @Override
  public AbstractExpression visitInt(LangParser.IntContext ctx) {
    String valueText = ctx.INT().getText();
    Integer value = Integer.parseInt(valueText);
    return new Int(value);
  }

  @Override
  public AbstractExpression visitNull(NullContext ctx) {
    return new Null(null);
  }

  @Override
  public AbstractExpression visitCmdScope(CmdScopeContext ctx) {
    List<AbstractCommand> cmds = new ArrayList<>();

    for (CmdContext cmdCtx : ctx.cmd()) {
      cmds.add((AbstractCommand) visit(cmdCtx));
    }

    return new CmdScope(cmds);
  }

  @Override
  public AbstractExpression visitArrayAccess(ArrayAccessContext ctx) {
    AbstractLvalue lvalue = (AbstractLvalue) visit(ctx.lvalue());
    AbstractExpression expr = visit(ctx.exp());
    return new ArrayAccess(lvalue, expr);
  }

  @Override
  public AbstractExpression visitDataIdentifierAccess(DataIdentifierAccessContext ctx) {
    AbstractLvalue lvalue = (AbstractLvalue) visit(ctx.lvalue());
    String id = ctx.ID().getText();
    return new DataIdentifierAccess(lvalue, new Identifier(id));
  }
}
