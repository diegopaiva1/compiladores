public class Token {
 /**
  * Enumeration of possible tokens types.
  */
  public enum Type {NONE, IDENTIFIER, TYPE_NAME, INT, FLOAT, CHAR, SEPARATOR, OPERATOR}

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
