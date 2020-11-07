package lang.compiler.parser;

import java.io.IOException;

import lang.compiler.ExpressionEvaluator;
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

      ParseTree cst = langParser.prog(); // Retrieve ANTLR parse tree from the start symbol 'prog'
      Program prog = new ProgramVisitor().visit(cst); // Convert parse tree into Program object
      ExpressionEvaluator ev = new ExpressionEvaluator(prog.getExpressions());

      for (String eval : ev.getEvaluationResults())
        System.out.println(eval);

      if (langParser.getNumberOfSyntaxErrors() == 0)
        return prog;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}
