/**
 * @file   FDA.hpp
 * @author Diego Paiva
 * @date   11/03/2020
 *
 * <Class description here>
 */

#include "Automaton.hpp"

#include <unordered_map>

#ifndef FDA_H_INCLUDED
#define FDA_H_INCLUDED

class FDA : public Automaton
{
protected:
  std::unordered_map<Key, int, KeyHasher> transitionsMap;

  int move(int state, char character);

public:
  FDA(std::vector<int> states, std::vector<char> alphabet, std::vector<int> acceptStates, int startState) :
  Automaton(states, alphabet, acceptStates, startState) { }

  ~FDA() { }

  void addTransition(int originState, char character, int destinationState);

  bool match(std::string word);
};

#endif // FDA_H_INCLUDED
