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
import lang.compiler.parser.LangBaseVisitor;
import lang.compiler.parser.LangParser;

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
    CustomType type = (CustomType) visit(ctx.TYPE_NAME());
    List<Declaration> decls = new ArrayList<>();

    for (LangParser.DeclContext declCtx : ctx.decl())
      decls.add((Declaration) visit(declCtx));

    return new Data(type, decls);
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
    AbstractLvalue lvalue = (AbstractLvalue) visit(ctx.lvalue());
    return new Read(lvalue);
  }

  @Override
  public AbstractExpression visitReturn(LangParser.ReturnContext ctx) {
    List<AbstractExpression> exprs = new ArrayList<>();

    for (LangParser.ExpContext expCtx : ctx.exp()) {
      AbstractExpression expr = visit(expCtx);
      exprs.add(expr);
    }

    return new Return(exprs);
  }

  @Override
  public AbstractExpression visitIf(LangParser.IfContext ctx) {
    AbstractExpression expr = visit(ctx.exp());
    AbstractCommand scopeCmd = (AbstractCommand) visit(ctx.cmd());
    return new If(expr, scopeCmd);
  }

  @Override
  public AbstractExpression visitIfElse(LangParser.IfElseContext ctx) {
    AbstractExpression expr = visit(ctx.exp());
    AbstractCommand ifScopeCmd = (AbstractCommand) visit(ctx.cmd(0));
    AbstractCommand elseScopeCmd = (AbstractCommand) visit(ctx.cmd(1));
    return new IfElse(expr, ifScopeCmd, elseScopeCmd);
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
    return new BoolLiteral(value);
  }

  @Override
  public AbstractExpression visitFalse(LangParser.FalseContext ctx) {
    Boolean value = Boolean.parseBoolean(ctx.getText());
    return new BoolLiteral(value);
  }

  @Override
  public AbstractExpression visitChar(LangParser.CharContext ctx) {
    String valueText = ctx.CHAR().getText();
    valueText = valueText.substring(1, valueText.length() - 1); // Pick string inside simple quotes ''
    Character value = StringEscapeUtils.unescapeJava(valueText).charAt(0); // If special char, unscape it
    return new CharLiteral(value);
  }

  @Override
  public AbstractExpression visitFloat(LangParser.FloatContext ctx) {
    String valueText = ctx.FLOAT().getText();
    java.lang.Float value = java.lang.Float.parseFloat(valueText);
    return new lang.compiler.ast.literals.FloatLiteral(value);
  }

  @Override
  public AbstractExpression visitInt(LangParser.IntContext ctx) {
    String valueText = ctx.INT().getText();
    Integer value = Integer.parseInt(valueText);
    return new IntLiteral(value);
  }

  @Override
  public AbstractExpression visitNull(LangParser.NullContext ctx) {
    return new NullLiteral(null);
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
  public AbstractExpression visitInstantiation(LangParser.InstantiationContext ctx) {
    AbstractType type = (AbstractType) visit(ctx.type());
    AbstractExpression expr = ctx.exp() != null ? visit(ctx.exp()) : null;
    return new New(type, expr);
  }

  @Override
  public AbstractExpression visitBalancedParenthesesExpression(LangParser.BalancedParenthesesExpressionContext ctx) {
    AbstractExpression expr = visit(ctx.exp());
    return new BalancedParenthesesExpression(expr);
  }

  @Override
  public AbstractExpression visitCommandScope(LangParser.CommandScopeContext ctx) {
    List<AbstractCommand> cmds = new ArrayList<>();

    for (LangParser.CmdContext cmdCtx : ctx.cmd())
      cmds.add((AbstractCommand) visit(cmdCtx));

    return new CommandScope(cmds);
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

      for (int i = 0; i < ctx.exps().getChildCount(); i += 2) {
        AbstractExpression arg = visit(ctx.exps().getChild(i));
        args.add(arg);
      }
    }

    return new StaticFunctionCall(id, args, lvalues);
  }

  @Override
  public AbstractExpression visitAssignment(LangParser.AssignmentContext ctx) {
    AbstractLvalue lvalue = (AbstractLvalue) visit(ctx.lvalue());
    AbstractExpression expr = visit(ctx.exp());
    return new Assignment(lvalue, expr);
  }

  @Override
  public AbstractExpression visitBoolType(LangParser.BoolTypeContext ctx) {
    return new BoolType();
  }

  @Override
  public AbstractExpression visitCharType(LangParser.CharTypeContext ctx) {
    return new CharType();
  }

  @Override
  public AbstractExpression visitFloatType(LangParser.FloatTypeContext ctx) {
    return new FloatType();
  }

  @Override
  public AbstractExpression visitIntType(LangParser.IntTypeContext ctx) {
    return new IntType();
  }

  @Override
  public AbstractExpression visitArrayType(LangParser.ArrayTypeContext ctx) {
    return new ArrayType((AbstractType) visit(ctx.type()));
  }

  @Override
  public AbstractExpression visitCustomType(LangParser.CustomTypeContext ctx) {
    return new CustomType(ctx.TYPE_NAME().getText());
  }
}
