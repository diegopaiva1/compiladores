package lang.compiler.visitors;

import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.Program;
import lang.compiler.ast.miscellaneous.Data;
import lang.compiler.ast.miscellaneous.Function;
import lang.compiler.LangBaseVisitor;
import lang.compiler.LangParser;

public class ProgramVisitor extends LangBaseVisitor<Program> {
  private String fileName;

  public ProgramVisitor(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public Program visitProgram(LangParser.ProgramContext ctx) {
    Program prog = new Program(fileName);
    BuildAstVisitor visitor = new BuildAstVisitor();

    for (int i = 0; i < ctx.getChildCount() - 1 /* -1 to exclude EOF */; i++) {
      AbstractExpression expr = visitor.visit(ctx.getChild(i));

      // Program is a collection of functions and data types
      if (expr instanceof Function)
        prog.addFunction((Function) expr);
      else if (expr instanceof Data)
        prog.addData((Data) expr);
    }

    return prog;
  }
}
