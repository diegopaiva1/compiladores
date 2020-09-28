import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.AbstractMap;
import java.util.Stack;

public class LangScanner {
 /**
  * @brief Buffer containing the program.
  */
  private PushbackInputStream buffer;

 /**
  * @brief The DFA our scanner will work on to recognize the tokens.
  */
  private Dfa dfa = new Dfa();

 /**
  * @brief Utility for stacking states during scanner proccesing.
  */
  private Stack<State> statesStack = new Stack<State>();

 /**
  * @brief Utility for stacking characters during scanner proccesing.
  */
  private Stack<Character> charsStack = new Stack<Character>();

 /**
  * @brief Default constructor.
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
  * @brief Get next token.
  */
  public Token nextToken() throws IOException
  {
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

    lexeme = lexeme.substring(0, lexeme.length() - 1);
    state = statesStack.pop();

    while (!state.isAccepted() && !statesStack.empty()) {
      state = statesStack.pop();
      buffer.unread(charsStack.pop());
      lexeme = lexeme.substring(0, lexeme.length() - 1);
    }

    if (state.isAccepted())
      return new Token(state.type, lexeme);

    return null;
  }

 /**
  * @brief Get next char.
  */
  private char nextChar() throws IOException {
    int c = buffer.read();

    // -1 is EOF
    if (c == -1)
      return '\0';

    return (char) c;
  }
}
