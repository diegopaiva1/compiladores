import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.AbstractMap;
import java.util.Stack;

public class LangScanner {
 /**
  * Buffer containing the program.
  */
  private PushbackInputStream buffer;

 /**
  * The DFA our scanner will work on to recognize the tokens.
  */
  private Dfa dfa = new Dfa();

 /**
  * Utility for stacking states during scanner proccesing.
  */
  private Stack<State> statesStack = new Stack<State>();

 /**
  * Utility for stacking characters during scanner proccesing.
  */
  private Stack<Character> charsStack = new Stack<Character>();

 /**
  * Default constructor.
  *
  * @param program Name of the file containing the program to be scanned.
  */
  public LangScanner(String program) {
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
    statesStack.clear();
    charsStack.clear();

    String lexeme = "";
    State state = dfa.getStartState();

    while (!state.isError()) {
      char c = nextChar();
      charsStack.push(c);
      statesStack.push(state);
      lexeme += c;
      state = dfa.move(new AbstractMap.SimpleEntry<State, Character>(state, c));
    }

    lexeme = rollback(charsStack.pop(), lexeme);
    state = statesStack.pop();

    while (!state.isAccepted() && !statesStack.empty()) {
      state = statesStack.pop();
      lexeme = rollback(charsStack.pop(), lexeme);
    }

    return state.isAccepted() ? new Token(state.tokenType, lexeme) : null;
  }

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

    // -1 is EOF
    if (c == -1)
      return '\0';

    return (char) c;
  }
}
