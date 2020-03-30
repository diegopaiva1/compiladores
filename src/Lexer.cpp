#include "Lexer.hpp"

#include <stack>
#include <fstream>
#include <iostream>

Lexer::Lexer()
{
  dfa = new DFA("../tokens_pattern.txt");

  // Here we map each accept state id to it's corresponding token type
  acceptStateMap[1] = Type::LITERAL;
  acceptStateMap[2] = Type::IDENTIFIER;
  acceptStateMap[3] = Type::SEPARATOR;
  acceptStateMap[4] = Type::OPERATOR;
  acceptStateMap[5] = Type::OPERATOR;
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
  int lineCount = 1;

  while (buffer.peek() != EOF) {
    // If we read white space or line break, we consume the character and keep moving
    if (buffer.peek() == ' ' || buffer.peek() == '\n') {
      if (buffer.peek() == '\n')
        lineCount++;

      buffer.get();
      beginning++;
      continue;
    }

    // Init
    int length = 0;
    std::stack<State*> s;
    State* state = dfa->getStartState();

    // Processing
    while (!state->bad()) {
      char c = buffer.get();
      s.push(state);
      state = dfa->move(std::make_pair(state, c));
      length++;
    }

    // Emiting
    while (!state->isAccepted && !s.empty()) {
      state = s.top();
      s.pop();
      buffer.unget();
      length--;
    }

    if (state->isAccepted) {
      buffer.seekg(beginning);
      char lexeme[length + 1];
      buffer.read(lexeme, length);
      lexeme[length] = '\0';
      Type name = acceptStateMap[state->id];
      tokens.push_back(Token(name, lexeme));
      beginning += length;
    }
    else {
      printf("Error: undefined token in line %d\n", lineCount);
      exit(1);
    }
  }

  return tokens;
}
