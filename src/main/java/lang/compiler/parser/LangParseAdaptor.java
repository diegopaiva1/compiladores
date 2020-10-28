package lang.compiler.parser;

import lang.compiler.ast.ProgNode;
import lang.compiler.ast.SuperNode;
import java.io.IOException;
import org.antlr.v4.runtime.*;

public class LangParseAdaptor implements ParseAdaptor {
  @Override
  public SuperNode parseFile(String path) {
    try {
      // Create a ANTLR CharStream from a file
      CharStream stream = CharStreams.fromFileName(path);

      // Create a lexer that feeds off of stream
      LangLexer langLexer = new LangLexer(stream);

      // Create a buffer of tokens pulled from the lexer
      CommonTokenStream tokens = new CommonTokenStream(langLexer);

      // Create a parser that feeds of the tokens buffer
      LangParser langParser = new LangParser(tokens);

      // Run root
      langParser.prog();

      if (langParser.getNumberOfSyntaxErrors() == 0)
        return new ProgNode();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}
