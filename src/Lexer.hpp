/**
 * @author Diego Paiva
 * @date   24/03/2020
 *
 * @brief A hand-crafted module that performs lexical analysis for compilers.
 */

#ifndef Lexer_H_INCLUDED
#define Lexer_H_INCLUDED

#include "DFA.hpp"
#include "Token.hpp"

class Lexer
{
private:
  DFA *dfa;
  std::unordered_map<int, Type> acceptStateMap;

public:
 /**
  * @brief Construct a new Lexer.
  */
  Lexer();

 /**
  * @brief Default destructor.
  */
  ~Lexer();

 /**
  * @brief Get all tokens (sequentially) from the program file passed as argument.
  *
  * @param programFileName  The file containing the desired program.
  * @return                 A [`std::vector`](http://www.cplusplus.com/reference/vector/vector/)
  *                         containing all `Token` objects.
  */
  std::vector<Token> getTokens(const std::string &programFileName);
};

#endif // Lexer_H_INCLUDED
