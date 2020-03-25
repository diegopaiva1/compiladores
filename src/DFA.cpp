#include "DFA.hpp"

#include <algorithm>
#include <fstream>

DFA::~DFA()
{
  // Empty destructor
}

DFA::DFA(const std::string &fileName)
{
  std::ifstream file(fileName);

  if (!file.is_open()) {
    printf("Failed to read file\n");
    exit(1);
  }

  // 1st block (header)
  file >> states;
  file >> transitions;
  file >> startState;

  // 2nd block (transitions)
  for (int i = 0; i < transitions; i++) {
    int  origin;
    int  destination;
    char character;

    file >> origin;
    file >> character;
    file >> destination;

    addTransition(std::make_pair(origin, character), destination);
  }

  // 3rd block (accept states)
  for (int temp; file >> temp; )
    acceptStates.push_back(temp);
}

void DFA::addTransition(std::pair<int, char> pair, int destinationState)
{
  transitionsMap[pair] = destinationState;
}

bool DFA::match(std::string word)
{
  int currentState = startState;

  for (char &c : word)
    currentState = move(std::make_pair(currentState, c));

  if (hasAcceptState(currentState))
    return true;

  return false;
}

int DFA::move(std::pair<int, char> pair)
{
  if (transitionsMap.find(pair) != transitionsMap.end())
    return transitionsMap[pair];

  return -1;
}

bool DFA::hasAcceptState(int state)
{
  if (std::find(acceptStates.begin(), acceptStates.end(), state) != acceptStates.end())
    return true;

  return false;
}
