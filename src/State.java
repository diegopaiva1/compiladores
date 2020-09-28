public class State {
 /**
  * @brief id.
  */
  public int id;

 /**
  * @brief Type of token returned by the state.
  */
  public Token.Type type;

 /**
  * @brief Construct a new State.
  *
  * @param id   id.
  * @param type type.
  */
  public State(int id, Token.Type type) {
    this.id = id;
    this.type = type;
  }

 /**
  * @brief Check if state is error, i.e, a state without transitions to any other state.
  *
  * @return `true` if error state.
  */
  public boolean isError() {
    return id == -1;
  }

 /**
  * @brief Check if state is accepted.
  *
  * @return `true` if accepted.
  */
  public boolean isAccepted() {
    return type != Token.Type.NONE;
  }
}
