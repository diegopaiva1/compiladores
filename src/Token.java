public class Token {
 /**
  * @brief Enumeration of possible tokens types.
  */
  public enum Type {NONE, EOF, MULT, SEMI, EQ, VAR, PLUS, INT}

 /**
  * @brief Token's type.
  */
  private Type type;

 /**
  * @brief Lexeme value.
  */
  private String lexeme;

 /**
  * @brief Construct a new Token.
  *
  * @param type   Token's type.
  * @param lexeme Lexeme value.
  */
  public Token(Type type, String lexeme) {
    this.type = type;
    this.lexeme = lexeme;
  }

 /**
  * @brief Get type.
  *
  * @return Type.
  */
  public Type getType() {
    return type;
  }

 /**
  * @brief Get lexeme.
  *
  * @return Lexeme.
  */
  public String getLexeme() {
    return lexeme;
  }
}
