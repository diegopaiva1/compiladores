/**
 * @file   State.hpp
 * @author Diego Paiva
 * @date   30/03/2020
 *
 * @brief Data structure of the state of an automaton.
 */

#ifndef STATE_H_INCLUDED
#define STATE_H_INCLUDED

class State
{
public:
  int  id;
  bool isAccepted;

 /**
  * @brief Construct a new State.
  */
  State();

 /**
  * @brief Construct a new State.
  *
  * @param id         State's id.
  * @param isAccepted State's acceptance status.
  */
  State(int id, bool isAccepted);

 /**
  * @brief Default destructor.
  */
  ~State();

 /**
  * @brief Check if state is bad, i.e, a state without transitions to any other state.
  *
  * @return `true` if state is bad, `false` otherwise.
  */
  bool bad();
};

#endif // STATE_H_INCLUDED
