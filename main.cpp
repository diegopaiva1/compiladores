#include "FDA.hpp"

#include <iostream>

int main(int argc, char *argv[])
{
  std::string word = argv[1];

  FDA fda = FDA({0, 1}, {'a'}, {1}, 0);

  fda.addTransition(0, 'a', 1);
  fda.addTransition(1, 'a', 0);

  fda.match(word) ? printf("Match.\n") : printf("No match.\n");

  return 0;
}
