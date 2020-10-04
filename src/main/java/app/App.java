package app;

import java.io.IOException;

public class App {
  public static void main(String[] args) {
    try {
      LangScanner langScanner = new LangScanner(args[0]);

      for (Token token; (token = langScanner.nextToken()) != null; )
        System.out.println("<" + token.getLexeme() + ", " + token.getType() + ">");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
