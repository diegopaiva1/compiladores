/**
 * @file   Automaton.hpp
 * @author Diego Paiva
 * @date   11/03/2020
 *
 * A implementable automaton. It contains the common features for FDAs, NFDAs, etc.
 */

#include <vector>
#include <string>
#include <utility>

#ifndef AUTOMATON_H_INCLUDED
#define AUTOMATON_H_INCLUDED

struct KeyHasher
{
 std::size_t operator()(const std::pair<int, char>& k) const
 {
   return std::get<0>(k) ^ std::get<1>(k);
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
  * @return     True if 'word' matches the language defined by the automaton, false otherwise.
  */
  virtual bool match(std::string word) = 0;
};

#endif // AUTOMATON_H_INCLUDED
