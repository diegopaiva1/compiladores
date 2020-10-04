import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Stack;

public class LangScanner {
  int line;

  int column;

 /**
  * Buffer containing the program.
  */
  private PushbackInputStream buffer;

 /**
  * The DFA our scanner will work on to recognize the tokens.
  */
  private Dfa dfa = new Dfa();

 /**
  * Hash Map containing Lang's reserved keywords.
  */
  private HashMap<String, Token.Type> langKeywords = new HashMap<String, Token.Type>();

 /**
  * Default constructor.
  *
  * @param program Name of the file containing the program to be scanned.
  */
  public LangScanner(String program) {
    line = 1;
    column = 1;

    langKeywords.put("true",    Token.Type.BOOL);
    langKeywords.put("false",   Token.Type.BOOL);
    langKeywords.put("null",    Token.Type.NULL);
    langKeywords.put("if",      Token.Type.IF);
    langKeywords.put("else",    Token.Type.ELSE);
    langKeywords.put("iterate", Token.Type.ITERATE);
    langKeywords.put("read",    Token.Type.READ);
    langKeywords.put("print",   Token.Type.PRINT);
    langKeywords.put("return",  Token.Type.RETURN);
    langKeywords.put("data",    Token.Type.DATA);
    langKeywords.put("Int",     Token.Type.TYPE_INT);
    langKeywords.put("Float",   Token.Type.TYPE_FLOAT);
    langKeywords.put("Bool",    Token.Type.TYPE_BOOL);
    langKeywords.put("Char",    Token.Type.TYPE_CHAR);

    try {
      buffer = new PushbackInputStream(new FileInputStream(program));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

 /**
  * Get next token.
  *
  * @return The next Token in current buffer.
  */
  public Token nextToken() throws IOException {
    Stack<State> statesStack = new Stack<State>();        // States being processed in DFA simulation
    Stack<Character> charsStack = new Stack<Character>(); // Characters being processed in DFA simulation

    String lexeme;
    State state;

    do {
      statesStack.clear();
      state = dfa.getStartState();
      lexeme = "";

      while (!state.isError()) {
        char c = nextChar();
        charsStack.push(c);
        statesStack.push(state);
        lexeme += c;
        state = dfa.move(new AbstractMap.SimpleEntry<State, Character>(state, c));
      }

      lexeme = rollback(charsStack.pop(), lexeme);
    } while (statesStack.peek().isSkip);

    state = statesStack.pop();

    while (!state.isAccepted() && !statesStack.empty()) {
      state = statesStack.pop();
      lexeme = rollback(charsStack.pop(), lexeme);
    }

    if (state.isAccepted()) {
      Token.Type type = langKeywords.containsKey(lexeme) ? langKeywords.get(lexeme) : state.tokenType;
      return new Token(type, lexeme);
    }

    return null;
  }

 /**
  * Put a byte back in the buffer and truncate lexeme.
  *
  * @param b      Byte.
  * @param lexeme Lexeme.
  *
  * @return Updated lexeme.
  */
  private String rollback(int b, String lexeme) throws IOException {
    buffer.unread(b);
    lexeme = lexeme.substring(0, lexeme.length() - 1);
    return lexeme;
  }

 /**
  * Get next char.
  *
  * @return The next char in current buffer.
  */
  private char nextChar() throws IOException {
    int c = buffer.read();
    return c == -1 ? '\0' : (char) c;
  }
}
