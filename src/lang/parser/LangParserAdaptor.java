package lang.parser;

import lang.ast.SuperNode;
import java.io.IOException;
import org.antlr.v4.runtime.*;

public class LangParserAdaptor implements ParseAdaptor {

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

      System.out.println(langParser.prog().toStringTree(langParser));
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}
