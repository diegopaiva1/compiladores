/*
 * @author Diego Paiva (201565516C)
 * @author Thaynara Ferreira (201565254C)
 */

package app;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.junit.Test;

/**
 * Unit test for LangScanner App.
 */
public class LangScannerTest
{
  /**
   * Test tokens for input program.
   */
  @Test
  public void shouldGetAllTokens() throws IOException
  {
    LangScanner langScanner = new LangScanner("test_prog.txt");

    Token next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "main");
    assertEquals(next.getType(), Token.Type.IDENTIFIER);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "(");
    assertEquals(next.getType(), Token.Type.SEPARATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), ")");
    assertEquals(next.getType(), Token.Type.SEPARATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "{");
    assertEquals(next.getType(), Token.Type.SEPARATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "print");
    assertEquals(next.getType(), Token.Type.PRINT);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "fat");
    assertEquals(next.getType(), Token.Type.IDENTIFIER);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "(");
    assertEquals(next.getType(), Token.Type.SEPARATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "10");
    assertEquals(next.getType(), Token.Type.INT);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), ")");
    assertEquals(next.getType(), Token.Type.SEPARATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), ";");
    assertEquals(next.getType(), Token.Type.SEPARATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "}");
    assertEquals(next.getType(), Token.Type.SEPARATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "fat");
    assertEquals(next.getType(), Token.Type.IDENTIFIER);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "(");
    assertEquals(next.getType(), Token.Type.SEPARATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "num");
    assertEquals(next.getType(), Token.Type.IDENTIFIER);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "::");
    assertEquals(next.getType(), Token.Type.SEPARATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "Int");
    assertEquals(next.getType(), Token.Type.TYPE_INT);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), ")");
    assertEquals(next.getType(), Token.Type.SEPARATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), ":");
    assertEquals(next.getType(), Token.Type.SEPARATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "Int");
    assertEquals(next.getType(), Token.Type.TYPE_INT);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "{");
    assertEquals(next.getType(), Token.Type.SEPARATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "if");
    assertEquals(next.getType(), Token.Type.IF);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "(");
    assertEquals(next.getType(), Token.Type.SEPARATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "num");
    assertEquals(next.getType(), Token.Type.IDENTIFIER);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "<");
    assertEquals(next.getType(), Token.Type.OPERATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "1");
    assertEquals(next.getType(), Token.Type.INT);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), ")");
    assertEquals(next.getType(), Token.Type.SEPARATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "return");
    assertEquals(next.getType(), Token.Type.RETURN);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "1");
    assertEquals(next.getType(), Token.Type.INT);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), ";");
    assertEquals(next.getType(), Token.Type.SEPARATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "else");
    assertEquals(next.getType(), Token.Type.ELSE);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "return");
    assertEquals(next.getType(), Token.Type.RETURN);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "num");
    assertEquals(next.getType(), Token.Type.IDENTIFIER);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "*");
    assertEquals(next.getType(), Token.Type.OPERATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "fat");
    assertEquals(next.getType(), Token.Type.IDENTIFIER);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "(");
    assertEquals(next.getType(), Token.Type.SEPARATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "num");
    assertEquals(next.getType(), Token.Type.IDENTIFIER);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "-");
    assertEquals(next.getType(), Token.Type.OPERATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "1");
    assertEquals(next.getType(), Token.Type.INT);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), ")");
    assertEquals(next.getType(), Token.Type.SEPARATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), ";");
    assertEquals(next.getType(), Token.Type.SEPARATOR);

    next = langScanner.nextToken();
    assertEquals(next.getLexeme(), "}");
    assertEquals(next.getType(), Token.Type.SEPARATOR);
  }
}
