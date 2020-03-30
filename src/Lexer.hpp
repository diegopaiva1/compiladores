/**
 * @author Diego Paiva
 * @date   24/03/2020
 *
 * @brief A hand-crafted lexical analyzer for compilers.
 */

#ifndef Lexer_H_INCLUDED
#define Lexer_H_INCLUDED

#include "DFA.hpp"
#include "Token.hpp"

#include <vector>

class Lexer
{
protected:
 /**
  * @brief The DFA our lexer will work on to recognize the tokens.
  */
  DFA *dfa;

 /**
  * @brief A hash map to retrieve the type of a Token based on the accept state.
  */
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
