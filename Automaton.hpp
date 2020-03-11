/**
 * @file   Automaton.hpp
 * @author Diego Paiva
 * @date   11/03/2020
 *
 * <Class description here>
 */

#include <vector>
#include <string>

#ifndef AUTOMATON_H_INCLUDED
#define AUTOMATON_H_INCLUDED

// TODO: Explanation of intent
struct Key
{
  int  state;
  char character;

  Key(int state, char character)
  {
    this->state     = state;
    this->character = character;
  }

  bool operator==(const Key &other) const
  {
    return (state == other.state && character == other.character);
  }
};

struct KeyHasher
{
  std::size_t operator()(const Key& k) const
  {
    return ((std::hash<int>()(k.state) ^ (std::hash<char>()(k.character) << 1)) >> 1);
  }
};

class Automaton
{
protected:
  std::vector<int>  states;
  std::vector<char> alphabet;
  std::vector<int>  acceptStates;
  int startState;

public:
  Automaton(std::vector<int> states, std::vector<char> alphabet, std::vector<int> acceptStates, int startState)
  {
    this->states       = states;
    this->alphabet     = alphabet;
    this->acceptStates = acceptStates;
    this->startState   = startState;
  }

  ~Automaton() { }

  virtual bool match(std::string word) = 0;
};

#endif // AUTOMATON_H_INCLUDED
