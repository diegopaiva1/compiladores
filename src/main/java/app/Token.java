/*
 * @author Diego Paiva (201565516C)
 * @author Thaynara Ferreira (201565254C)
 */

package app;

public class Token {
 /**
  * Enumeration of possible tokens types.
  */
  public enum Type {
    NONE,
    IDENTIFIER,
    INT, FLOAT, CHAR, BOOL, NULL, // Literal values
    TYPE_NAME, TYPE_INT, TYPE_FLOAT, TYPE_BOOL, TYPE_CHAR, // Name of user defined and primitive types
    IF, ELSE, ITERATE, READ, PRINT, RETURN, DATA, // Commands
    SEPARATOR,
    OPERATOR
  }

 /**
  * Token's type.
  */
  private Type type;

 /**
  * Lexeme value.
  */
  private String lexeme;

 /**
  * Construct a new Token.
  *
  * @param type   Token's type.
  * @param lexeme Lexeme value.
  */
  public Token(Type type, String lexeme) {
    this.type = type;
    this.lexeme = lexeme;
  }

 /**
  * Get type.
  *
  * @return Type.
  */
  public Type getType() {
    return type;
  }

 /**
  * Get lexeme.
  *
  * @return Lexeme.
  */
  public String getLexeme() {
    return lexeme;
  }
}
