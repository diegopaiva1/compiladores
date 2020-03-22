/**
 * @file   DFA.hpp
 * @author Diego Paiva
 * @date   11/03/2020
 *
 * Implementation of a deterministic finite automaton.
 */

#include "Automaton.hpp"

#include <unordered_map>

#ifndef DFA_H_INCLUDED
#define DFA_H_INCLUDED

class DFA : public Automaton
{
protected:
  std::unordered_map<std::pair<int, char>, int, KeyHasher> transitionsMap;

public:
 /**
  * @brief Construct a new DFA.
  *
  * @param fileName The file containing the DFA data.
  */
  DFA(std::string fileName);

 /**
  * @brief Default destructor.
  */
  ~DFA();

 /**
  * @copydoc Automaton::match()
  */
  bool match(std::string word);

 /**
  * @brief Add a new transition.
  *
  * @param pair             The std::pair containing the origin state and the character.
  * @param destinationState The destination state.
  */
  void addTransition(std::pair<int, char> pair, int destinationState);

 /**
  * @brief Move from a state with a character.
  *
  * @param pair The std::pair containing the state and the character.
  * @return     The state resulting from the transition of the state with the character or -1 if
  *             the transisition is not defined.
  */
  int move(std::pair<int, char> pair);

/**
  * @brief Checks whether a state belongs to the accept states set or not.
  *
  * @param state The state.
  * @return      True if 'state' belongs to the accept states set, false otherwise.
  */
  bool hasAcceptState(int state);
};

#endif // DFA_H_INCLUDED
