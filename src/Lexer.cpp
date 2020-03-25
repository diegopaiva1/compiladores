#include "Lexer.hpp"

#include <stack>
#include <fstream>
#include <iostream>

Lexer::Lexer()
{
  dfa = new DFA("../tokens_pattern.txt");

  // Map each accept state to it's corresponding token type
  acceptStateMap[1] = Type::LITERAL;
  acceptStateMap[2] = Type::IDENTIFIER;
  acceptStateMap[3] = Type::SEPARATOR;
}

Lexer::~Lexer()
{
  delete dfa;
}

std::vector<Token> Lexer::getTokens(const std::string &programFileName)
{
  std::ifstream buffer(programFileName);

  if (!buffer.is_open()) {
    printf("Failed to read file\n");
    exit(1);
  }

  std::vector<Token> tokens;
  int beginning = 0;

  while (buffer.peek() != EOF) {
    // If we read blank or line break, we consume the character and keep moving
    if (buffer.peek() == ' ' || buffer.peek() == '\n') {
      buffer.get();
      beginning++;
      continue;
    }

    // Init
    int length = 0;
    std::stack<int> s;
    int state = dfa->getStartState();

    // Processing
    while (state != -1) {
      char c = buffer.get();
      s.push(state);
      state = dfa->move(std::make_pair(state, c));
      length++;
    }

    // Emiting
    while (!dfa->hasAcceptState(state) && !s.empty()) {
      state = s.top();
      s.pop();
      buffer.unget();
      length--;
    }

    if (dfa->hasAcceptState(state)) {
      buffer.seekg(beginning);
      char lexeme[length];
      buffer.read(lexeme, length);
      Type name = acceptStateMap[state];
      tokens.push_back(Token(name, lexeme));
      beginning += length;
    }
    else {
      printf("Error: undefined token\n");
      exit(1);
    }
  }

  return tokens;
}
