/**
 * @file   FDA.cpp
 * @author Diego Paiva
 * @date   11/03/2020
 *
 * <Class description here>
 */

#include "FDA.hpp"

#include <algorithm>

void FDA::addTransition(int originState, char character, int destinationState)
{
  transitionsMap[Key(originState, character)] = destinationState;
}

int FDA::move(int state, char character)
{
  if (transitionsMap.find(Key(state, character)) != transitionsMap.end())
    return transitionsMap[Key(state, character)];
  else
    return -1;
}

bool FDA::match(std::string word)
{
  int currentState = startState;

  for (char &c : word)
    currentState = move(currentState, c);

  if (std::find(acceptStates.begin(), acceptStates.end(), currentState) != acceptStates.end())
    return true;

  return false;
}
