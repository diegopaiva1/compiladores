import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Scanner;

public class Dfa {
 /**
  * HashMap to retrieve a state by its id.
  */
  protected HashMap<Integer, State> states = new HashMap<Integer, State>();

 /**
  * HashMap to efficiently query the DFA's transitions.
  */
  protected HashMap<AbstractMap.SimpleEntry<State, Character>, State> transitionsMap =
        new HashMap<AbstractMap.SimpleEntry<State, Character>, State>();

 /**
  * Construct a new Dfa.
  */
  public Dfa() {
    File file = new File("dfa.txt");

    try {
      Scanner fileReader = new Scanner(file);

      // 1st block (header)
      int statesNum = Integer.parseInt(fileReader.next());
      int transitionsNum = Integer.parseInt(fileReader.next());
      int acceptStatesNum = Integer.parseInt(fileReader.next());

      for (int i = 0; i < statesNum; i++) {
        // States are initialized as not accepted (token type is NONE)
        states.put(i, new State(i, Token.Type.NONE));
      }

      states.put(-1, new State(-1, Token.Type.NONE)); // Add an error state

      // 2nd block (transitions)
      for (int i = 0; i < transitionsNum; i++) {
        int originId = Integer.parseInt(fileReader.next());
        String word = fileReader.next();
        char c = word.length() == 1 ? word.charAt(0) : (char) Integer.parseInt(word);
        int destinationId = Integer.parseInt(fileReader.next());

        transitionsMap.put(new AbstractMap.SimpleEntry<State, Character>(states.get(originId), c), states.get(destinationId));
      }

      // 3rd block (accept states)
      for (int i = 0; i < acceptStatesNum; i++) {
        int id = Integer.parseInt(fileReader.next());
        String typeString = fileReader.next();

        try {
          states.get(id).tokenType = Token.Type.valueOf(typeString);
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
        }
      }

      fileReader.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

 /**
  * Get the start State.
  *
  * @return Start State.
  */
  public State getStartState() {
    // Always assume start state is the one with id equal to 0
    return states.get(0);
  }

 /**
  * Move from a state with a character.
  *
  * @param pair The AbstractMap.SimpleEntry<State, Character> containing the state and the character.
  * @return     The state resulting from the transition of the state with the character or a bad state
  *             if the transisition is not defined.
  */
  public State move(AbstractMap.SimpleEntry<State, Character> pair)
  {
    if (transitionsMap.containsKey(pair))
      return transitionsMap.get(pair);

    // Error state
    return states.get(-1);
  }
}
