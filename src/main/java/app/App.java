/*
 * @author Diego Paiva (201565516C)
 * @author Thaynara Ferreira (201565254C)
 */

package app;

import java.io.IOException;

public class App {
  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Invalid number of arguments");
      System.exit(1);
    }

    try {
      LangScanner langScanner = new LangScanner(args[0]);

      for (Token token; (token = langScanner.nextToken()) != null; )
        System.out.println("<" + token.getLexeme() + ", " + token.getType() + ">");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
