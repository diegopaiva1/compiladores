package lang.compiler.visitors;

import lang.compiler.ast.Program;
import lang.compiler.parser.LangBaseVisitor;
import lang.compiler.parser.LangParser;

public class ProgramVisitor extends LangBaseVisitor<Program> {
  @Override
  public Program visitProgram(LangParser.ProgramContext ctx) {
    Program prog = new Program();
    BuildAstVisitor visitor = new BuildAstVisitor();

    for (int i = 0; i < ctx.getChildCount() - 1 /* -1 to exclude EOF */; i++)
      prog.addExpression(visitor.visit(ctx.getChild(i)));

    return prog;
  }
}
