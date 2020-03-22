/**
 * @file   Token.hpp
 * @author Diego Paiva
 * @date   18/03/20
 *
 * <Class description here>
 */

#ifndef TOKEN_H_INCLUDED
#define TOKEN_H_INCLUDED

#include <string>

enum class Name {IDENTIFIER, KEYWORD, SEPARATOR, OPERATOR, LITERAL, COMMENT};

class Token
{
private:
  Name name;
  std::string value;

public:
  Token(Name name, std::string value);

  ~Token();

  Name getName();

  std::string getValue();
};

#endif // TOKEN_H_INCLUDED
