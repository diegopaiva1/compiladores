package lang.compiler.visitors;

import java.util.ArrayList;
import java.util.List;

import lang.compiler.ast.Data;
import lang.compiler.ast.Declaration;
import lang.compiler.ast.Function;
import lang.compiler.ast.Int;
import lang.compiler.ast.Parameter;
import lang.compiler.ast.commands.AbstractCommand;
import lang.compiler.ast.commands.Print;
import lang.compiler.ast.commands.Read;
import lang.compiler.ast.commands.Return;
import lang.compiler.ast.AbstractExpression;
import lang.compiler.parser.LangBaseVisitor;
import lang.compiler.parser.LangParser;

public class BuildAstVisitor extends LangBaseVisitor<AbstractExpression> {
  @Override
  public AbstractExpression visitFunction(LangParser.FunctionContext ctx) {
    String id = ctx.ID().getText();
    List<AbstractCommand> commands = new ArrayList<>();
    List<Parameter> parameters = new ArrayList<>();

    for (LangParser.CmdContext cmdCtx : ctx.cmd()) {
      AbstractCommand cmd = (AbstractCommand) visit(cmdCtx);
      commands.add(cmd);
    }

    if (ctx.params() != null) {
      /**         ----------
       *          | PARAMS |
       *          ----------
       *         /    |     \
       *        ID    ::   type
       *        0     1     2
       */
      String paramId = ctx.params().getChild(0).getText();
      String paramType = ctx.params().getChild(2).getText();
      parameters.add(new Parameter(paramId, paramType));

      /**     ----------------------------------------------
       *      |                   PARAMS                   |
       *      ----------------------------------------------
       *     /    |     |     |      |     |     |     |    \
       *    ID    ::  type    ,     ID     ::   type   ,    ...
       *    0     1     2     3      4      5    6     7     n
       */
      for (int i = 4; i < ctx.params().getChildCount(); i += 4) {
        paramId = ctx.params().getChild(i).getText();
        paramType = ctx.params().getChild(i + 2).getText();
        parameters.add(new Parameter(paramId, paramType));
      }
    }

    return new Function(id, parameters, commands);
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
    List<Declaration> declarations = new ArrayList<>();

    for (LangParser.DeclContext declCtx : ctx.decl())
      declarations.add((Declaration) visit(declCtx));

    return new Data(typeName, declarations);
  }

  @Override
  public AbstractExpression visitDeclaration(LangParser.DeclarationContext ctx) {
    String id = ctx.ID().getText();
    String type = ctx.type().getText();
    return new Declaration(id, type);
  }

  @Override
  public AbstractExpression visitPrint(LangParser.PrintContext ctx) {
    AbstractExpression stmt = visit(ctx.exp());
    return new Print(stmt);
  }

  @Override
  public AbstractExpression visitRead(LangParser.ReadContext ctx) {
    AbstractExpression stmt = visit(ctx.lvalue());
    return new Read(stmt);
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
    List<AbstractExpression> stmts = new ArrayList<>();

    for (int i = 0; i < ctx.getChildCount(); i += 2) {
      AbstractExpression stmt = visit(ctx.getChild(i));
      stmts.add(stmt);
    }

    return new Return(stmts);
  }
}
