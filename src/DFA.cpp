#include "DFA.hpp"

#include <algorithm>
#include <fstream>

DFA::DFA(const std::string &fileName)
{
  std::ifstream file(fileName);

  if (!file.is_open()) {
    printf("Failed to read file\n");
    exit(1);
  }

  // 1st block (header)
  int statesAmount;
  int transitionsAmount;

  file >> statesAmount;
  file >> transitionsAmount;

  // States are initialized as not isAccepted
  for (int i = 0; i < statesAmount; i++)
    states[i] = new State(i, false);

  // Add a bad state
  states[-1] = new State(-1, false);

  // 2nd block (transitions)
  for (int i = 0; i < transitionsAmount; i++) {
    int  origin;
    char character;
    int  destination;

    file >> origin;
    file >> character;
    file >> destination;

    addTransition(std::make_pair(getStateById(origin), character), getStateById(destination));
  }

  // 3rd block (accept states)
  for (int id; file >> id; )
    states[id]->isAccepted = true;
}

DFA::~DFA()
{
  // Empty destructor
}

void DFA::addTransition(std::pair<State*, char> pair, State* destination)
{
  transitionsMap[pair] = destination;
}

bool DFA::match(std::string word)
{
  State *currentState = getStartState();

  for (char &c : word)
    currentState = move(std::make_pair(currentState, c));

  if (currentState->isAccepted)
    return true;

  return false;
}

State* DFA::move(std::pair<State*, char> pair)
{
  if (transitionsMap.find(pair) != transitionsMap.end())
    return transitionsMap[pair];

  // Bad state
  return states.at(-1);
}

State* DFA::getStartState()
{
  // We always assume start state is the one with id equal to 0
  return states.at(0);
}

State* DFA::getStateById(int id)
{
  return states.at(id);
}
