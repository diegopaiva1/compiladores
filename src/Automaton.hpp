/**
 * @file   Automaton.hpp
 * @author Diego Paiva
 * @date   11/03/2020
 *
 * <Class description here>
 */

#include <vector>
#include <string>
#include <utility>

#ifndef AUTOMATON_H_INCLUDED
#define AUTOMATON_H_INCLUDED

typedef std::pair<int, char> Key;

struct KeyHasher : public std::unary_function<Key, std::size_t>
{
 std::size_t operator()(const Key& k) const
 {
   return std::get<0>(k) ^ std::get<1>(k);
 }
};

class Automaton
{
protected:
  std::vector<int>  acceptStates;
  int startState;

public:
  Automaton(std::vector<int> acceptStates, int startState)
  {
    this->acceptStates = acceptStates;
    this->startState   = startState;
  }

  ~Automaton() { }

  virtual bool match(std::string word) = 0;
};

#endif // AUTOMATON_H_INCLUDED
