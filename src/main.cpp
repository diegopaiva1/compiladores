#include "DFA.hpp"
#include "Token.hpp"
#include "Lexer.hpp"

int main(int argc, char *argv[])
{
  int expectedArgs = 1;

  if (expectedArgs != argc - 1) {
    printf("Error: %d argument(s) expected; %d provided\n", expectedArgs, argc - 1);
    exit(1);
  }

  const std::string programFileName = argv[1];

  Lexer lexer = Lexer();

  std::vector<Token> tokens = lexer.getTokens(programFileName);

  printf("Printing all tokens:\n\n");
  for (Token &t : tokens)
    printf("%20s is %s\n", t.getValue().c_str(), t.getType().c_str());

  return 0;
}
