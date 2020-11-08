package lang.compiler.visitors;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.text.StringEscapeUtils;

import lang.compiler.ast.*;
import lang.compiler.ast.commands.*;
import lang.compiler.ast.literals.*;
import lang.compiler.ast.literals.Float;
import lang.compiler.ast.lvalues.*;
import lang.compiler.ast.operators.binary.*;
import lang.compiler.ast.operators.binary.Module;
import lang.compiler.ast.operators.unary.*;
import lang.compiler.ast.types.*;
import lang.compiler.parser.LangBaseVisitor;
import lang.compiler.parser.LangParser;
import lang.compiler.parser.LangParser.AssignmentContext;

public class BuildAstVisitor extends LangBaseVisitor<AbstractExpression> {
  @Override
  public AbstractExpression visitFunction(LangParser.FunctionContext ctx) {
    Identifier id = new Identifier(ctx.ID().getText());
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
      /**
       *          ----------
       *          | PARAMS |
       *          ----------
       *         /    |     \
       *        ID    ::   type
       *        0     1     2
       */
      AbstractType paramType = (AbstractType) visit(ctx.params().getChild(2));
      Identifier paramId = new Identifier(ctx.params().getChild(0).getText());
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
        paramId = new Identifier(ctx.params().getChild(i).getText());
        paramType = (AbstractType) visit(ctx.params().getChild(i + 2));
        params.add(new Parameter(paramId, paramType));
      }
    }

    return new Function(id, params, returnTypes, cmds);
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
    Identifier id = new Identifier(ctx.ID().getText());
    AbstractType type = (AbstractType) visit(ctx.type());
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

  @Override
  public AbstractExpression visitTrue(LangParser.TrueContext ctx) {
    Boolean value = Boolean.parseBoolean(ctx.getText());
    return new Bool(value);
  }

  @Override
  public AbstractExpression visitFalse(LangParser.FalseContext ctx) {
    Boolean value = Boolean.parseBoolean(ctx.getText());
    return new Bool(value);
  }

  @Override
  public AbstractExpression visitChar(LangParser.CharContext ctx) {
    String valueText = ctx.CHAR().getText();

    // Pick string inside simple quotes ''
    valueText = valueText.substring(1, valueText.length() - 1);

    // In case it is a special character we must unscape it
    Character value = StringEscapeUtils.unescapeJava(valueText).charAt(0);

    return new Char(value);
  }

  @Override
  public AbstractExpression visitFloat(LangParser.FloatContext ctx) {
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
  public AbstractExpression visitNull(LangParser.NullContext ctx) {
    return new Null(null);
  }

  @Override
  public AbstractExpression visitAssignableFunctionCall(LangParser.AssignableFunctionCallContext ctx) {
    Identifier id = new Identifier(ctx.ID().getText());
    List<AbstractExpression> args = new ArrayList<>();
    AbstractExpression index = visit(ctx.exp());

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

    return new AssignableFunctionCall(id, args, index);
  }

  @Override
  public AbstractExpression visitBasicType(LangParser.BasicTypeContext ctx) {
    return new BasicType(ctx.getText());
  }

  @Override
  public AbstractExpression visitArray(LangParser.ArrayContext ctx) {
    AbstractType type = (AbstractType) visit(ctx.type());
    return new Array(type);
  }

  @Override
  public AbstractExpression visitInstantiation(LangParser.InstantiationContext ctx) {
    AbstractType type = (AbstractType) visit(ctx.type());
    AbstractExpression expr = visit(ctx.exp());
    return new Instantiation(type, expr);
  }

  @Override
  public AbstractExpression visitBalancedParenthesesExpression(LangParser.BalancedParenthesesExpressionContext ctx) {
    AbstractExpression expr = visit(ctx.exp());
    return new BalancedParenthesesExpression(expr);
  }

  public AbstractExpression visitCmdScope(LangParser.CmdScopeContext ctx) {
    List<AbstractCommand> cmds = new ArrayList<>();

    for (LangParser.CmdContext cmdCtx : ctx.cmd()) {
      cmds.add((AbstractCommand) visit(cmdCtx));
    }

    return new CmdScope(cmds);
  }

  @Override
  public AbstractExpression visitArrayAccess(LangParser.ArrayAccessContext ctx) {
    AbstractLvalue lvalue = (AbstractLvalue) visit(ctx.lvalue());
    AbstractExpression expr = visit(ctx.exp());
    return new ArrayAccess(lvalue, expr);
  }

  @Override
  public AbstractExpression visitIdentifier(LangParser.IdentifierContext ctx) {
    return new Identifier(ctx.ID().getText());
  }

  @Override
  public AbstractExpression visitDataIdentifierAccess(LangParser.DataIdentifierAccessContext ctx) {
    AbstractLvalue lvalue = (AbstractLvalue) visit(ctx.lvalue());
    Identifier id = new Identifier(ctx.ID().getText());
    return new DataIdentifierAccess(lvalue, id);
  }

  @Override
  public AbstractExpression visitStaticFunctionCall(LangParser.StaticFunctionCallContext ctx) {
    Identifier id = new Identifier(ctx.ID().getText());
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

      System.out.println(ctx.exps().getChildCount());
      for (int i = 0; i < ctx.exps().getChildCount(); i += 2) {
        AbstractExpression arg = visit(ctx.exps().getChild(i));
        args.add(arg);
      }
    }

    return new StaticFunctionCall(id, args, lvalues);
  }

  @Override
  public AbstractExpression visitAssignment(AssignmentContext ctx) {
    AbstractLvalue lvalue = (AbstractLvalue) visit(ctx.lvalue());
    AbstractExpression expr = visit(ctx.exp());
    return new Assignment(lvalue, expr);
  }
}
