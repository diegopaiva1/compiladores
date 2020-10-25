package lang.parser;

import java.io.IOException;

import org.antlr.v4.runtime.*;

public class LangApp {
  public static void main(String[] args)  throws IOException {
    // Create a ANTLR CharStream from a file
    CharStream stream = CharStreams.fromFileName(args[0]);

    // Create a lexer that feeds off of stream
    LangLexer langLexer = new LangLexer(stream);

    // Create a buffer of tokens pulled from the lexer
    CommonTokenStream tokens = new CommonTokenStream(langLexer);

    // Create a parser that feeds of the tokens buffer
    LangParser langParser = new LangParser(tokens);

    System.out.println(langParser.prog().toStringTree(langParser));
  }
}

