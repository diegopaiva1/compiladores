public class State {
 /**
  * id.
  */
  public int id;

 /**
  * Type of token returned by the state.
  */
  public Token.Type tokenType;

 /**
  * Is skip state.
  */
  public boolean isSkip;

 /**
  * Construct a new State.
  *
  * @param id        id.
  * @param tokenType Type of token returned by the state.
  * @param isSkip    Is skip state.
  */
  public State(int id, Token.Type tokenType, boolean isSkip) {
    this.id = id;
    this.tokenType = tokenType;
    this.isSkip = isSkip;
  }

 /**
  * Check if is error state.
  *
  * @return true if error state.
  */
  public boolean isError() {
    return id == -1;
  }

 /**
  * Check if state is accepted.
  *
  * @return true if accepted.
  */
  public boolean isAccepted() {
    return tokenType != Token.Type.NONE;
  }
}
