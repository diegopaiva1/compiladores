/**
 * @file   Token.cpp
 * @author Diego Paiva
 * @date   18/03/2020
 *
 * <Class description here>
 */

#include "Token.hpp"

Token::Token(Name name, std::string value)
{
  this->name  = name;
  this->value = value;
}

Token::~Token()
{
  // Empty destructor
}

Name Token::getName()
{
  return name;
}

std::string Token::getValue()
{
  return value;
}
