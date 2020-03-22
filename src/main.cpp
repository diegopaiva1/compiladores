#include "DFA.hpp"
#include "Token.hpp"

#include <iostream>
#include <stack>
#include <fstream>

int main(int argc, char *argv[])
{
  std::string automatonFileName = argv[1];
  std::string programFileName   = argv[2];

  std::ifstream buffer(programFileName);

  if (!buffer.is_open()) {
    printf("Failed to read file\n");
    exit(1);
  }

  DFA m1 = DFA(automatonFileName);

  std::vector<Token> tokens;
  std::string lexeme = "";
  std::stack<int> stack;
  stack.push(-1);
  int state = m1.getStartState();

  while (state != -1) {
    char c = buffer.get();
    stack.push(state);
    state = m1.move(std::make_pair(state, c));
  }

  while (!m1.hasAcceptState(state)) {
    state = stack.top();
    stack.pop();
  }

  if (m1.hasAcceptState(state)) {
    tokens.push_back(Token(Name::KEYWORD, lexeme));
  }
  else {
    std::cout << "Invalid token\n" << '\n';
  }

  for (Token &t : tokens)
    printf("<%d, %s>\n", t.getName(), t.getValue().c_str());

  return 0;
}
