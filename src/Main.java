import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    try {
      LangScanner scanner = new LangScanner("program.txt");

      for (Token token; (token = scanner.nextToken()) != null; )
        System.out.println("<" + token.getLexeme() + ", " + token.getType() + ">");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
