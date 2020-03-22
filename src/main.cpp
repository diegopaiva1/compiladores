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





  std::string resto = "";

  while (!buffer.eof()) {
    // Init
    std::stack<int> stack;
    std::string lexeme = resto;
    resto = "";
    int state = m1.getStartState();

    // Processing
    while (state != -1) {
      char c = buffer.get();
      lexeme += c;
      stack.push(state);
      state = m1.move(std::make_pair(state, c));
    }

    // Emiting
    while (!m1.hasAcceptState(state) && !stack.empty()) {
      state = stack.top();
      stack.pop();
      resto = lexeme.substr(lexeme.size() - 1, 1) + resto;
      lexeme = lexeme.substr(0, lexeme.size() - 1);
    }

    if (m1.hasAcceptState(state))
      tokens.push_back(Token(Name::IDENTIFIER, lexeme));
  }











  for (Token &t : tokens)
    printf("<%d, %s>\n", t.getName(), t.getValue().c_str());

  return 0;
}
