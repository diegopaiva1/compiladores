#include "State.hpp"

State::State()
{
  // Empty constructor
}

State::State(int id, bool isAccepted)
{
  this->id = id;
  this->isAccepted = isAccepted;
}

State::~State()
{
  // Empty destructor
}

bool State::bad()
{
  return id == -1;
}
