/**
 * @file   Automaton.hpp
 * @author Diego Paiva
 * @date   11/03/2020
 *
 * @brief A implementable automaton. It contains the common features for DFAs, NDFAs, etc.
 */

#include <vector>
#include <string>

#ifndef AUTOMATON_H_INCLUDED
#define AUTOMATON_H_INCLUDED

struct KeyHasher
{
 std::size_t operator()(const std::pair<int, char>& k) const
 {
   return k.first ^ k.second;
 }
};

class Automaton
{
protected:
  int states;
  int transitions;
  int startState;
  std::vector<int> acceptStates;

public:
 /**
  * @brief Check if a given word matches the language defined by the automaton.
  *
  * @details Pure virtual method.
  *
  * @param word The word.
  * @return     `true` if *word* matches the language defined by the automaton, `false` otherwise.
  */
  virtual bool match(std::string word) = 0;

 /**
  * @brief Get the start state.
  *
  * @return ID of the start state.
  */
  int getStartState();
};

#endif // AUTOMATON_H_INCLUDED
