package lang.compiler.visitors;

import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.Program;
import lang.compiler.ast.ScopeTable;
import lang.compiler.ast.miscellaneous.Data;
import lang.compiler.ast.miscellaneous.Function;
import lang.compiler.LangBaseVisitor;
import lang.compiler.LangParser;

public class ProgramVisitor extends LangBaseVisitor<Program> {
  @Override
  public Program visitProgram(LangParser.ProgramContext ctx) {
    Program prog = new Program();
    BuildAstVisitor visitor = new BuildAstVisitor();

    for (int i = 0; i < ctx.getChildCount() - 1 /* -1 to exclude EOF */; i++) {
      AbstractExpression expr = visitor.visit(ctx.getChild(i));

      // Program is a collection of functions and data types
      if (expr instanceof Function) {
        Function f = (Function) expr;
        prog.addFunction(f);
        prog.getEnv().put(f, new ScopeTable());
      }
      else if (expr instanceof Data) {
        prog.addData((Data) expr);
      }
    }

    return prog;
  }
}
