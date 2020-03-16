#include "FDA.hpp"

#include <iostream>

int main(int argc, char *argv[])
{
  std::string file = argv[1];
  std::string word = argv[2] ? argv[2] : "";

  FDA m1 = FDA(file);

  m1.match(word) ? printf("Match.\n") : printf("No match.\n");

  return 0;
}
