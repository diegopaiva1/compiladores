package lang.compiler.parser;

import java.io.IOException;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;
import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.Function;
import lang.compiler.ast.Program;
import lang.compiler.visitors.ProgramVisitor;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

public class LangParseAdaptor implements ParseAdaptor {
  @Override
  public Program parseFile(String path) {
    try {
      CharStream inputStream = CharStreams.fromFileName(path);
      LangLexer langLexer = new LangLexer(inputStream);
      CommonTokenStream tokenStream = new CommonTokenStream(langLexer);
      LangParser langParser = new LangParser(tokenStream);

      // Retrieve ANTLR parse tree from the start symbol 'prog'
      ParseTree cst = langParser.prog();

      if (langParser.getNumberOfSyntaxErrors() == 0) {
        // Convert parse tree into Program object
        Program prog = new ProgramVisitor().visit(cst);
        AbstractExpressionEvaluatorVisitor ev = new AbstractExpressionEvaluatorVisitor();
        boolean hasMainFunction = false;

        for (AbstractExpression expr : prog.getExpressions()) {
          if (expr instanceof Function) {
            Function f = (Function) expr;
            ev.addFunction(f);

            if (f.getId().getName().equals("main")) {
              hasMainFunction = true;
              expr.accept(ev);
              break;
            }
          }
        }

        if (!hasMainFunction)
          throw new RuntimeException("function main does not exist");

        return prog;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}
