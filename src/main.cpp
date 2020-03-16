#include "FDA.hpp"

#include <iostream>

int main(int argc, char *argv[])
{
  std::string word = argv[1];

  FDA m1 = FDA({1}, 0);

  m1.addTransition(0, 'a', 1);
  m1.addTransition(1, 'a', 0);

  m1.match(word) ? printf("Match.\n") : printf("No match.\n");

  return 0;
}
