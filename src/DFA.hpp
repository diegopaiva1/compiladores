/**
 * @file   DFA.hpp
 * @author Diego Paiva
 * @date   11/03/2020
 *
 * @brief Implementation of a deterministic finite automaton.
 */

#include "State.hpp"

#include <unordered_map>

#ifndef DFA_H_INCLUDED
#define DFA_H_INCLUDED

struct KeyHasher
{
  std::size_t operator()(const std::pair<State*, char>& k) const
  {
    return k.first->id ^ k.second;
  }
};

class DFA
{
protected:
 /**
  * @brief A hash map to retrieve a state by it's id.
  */
  std::unordered_map<int, State*> states;

 /**
  * @brief A hash map to efficiently query the DFA's transitions.
  */
  std::unordered_map<std::pair<State*, char>, State*, KeyHasher> transitionsMap;

public:
 /**
  * @brief Construct a new DFA.
  *
  * @param fileName The file containing the DFA data.
  */
  DFA(const std::string &fileName);

 /**
  * @brief Default destructor.
  */
  ~DFA();

 /**
  * @brief Check if a given word matches the language defined by the automaton.
  *
  * @param word The word.
  * @return     `true` if *word* matches the language defined by the automaton, `false` otherwise.
  */
  bool match(std::string word);

 /**
  * @brief Add a new transition.
  *
  * @param pair               The [`std::pair`](http://www.cplusplus.com/reference/utility/pair/)
  *                           containing the origin state and the character.
  * @param destination        The destination state.
  */
  void addTransition(std::pair<State*, char> pair, State* destination);

 /**
  * @brief Move from a state with a character.
  *
  * @param pair The [`std::pair`](http://www.cplusplus.com/reference/utility/pair/)
  *             containing the state and the character.
  * @return     The state resulting from the transition of the state with the character or a bad state
  *             if the transisition is not defined.
  */
  State* move(std::pair<State*, char> pair);

 /**
  * @brief Get the start state.
  *
  * @return The start state.
  */
  State* getStartState();

 /**
  * @brief Get a state by it's id.
  *
  * @param id State's id.
  * @return   The state.
  */
  State* getStateById(int id);
};

#endif // DFA_H_INCLUDED
