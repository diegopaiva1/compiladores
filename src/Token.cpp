#include "Token.hpp"

Token::Token()
{
  // Empty constructor
}

Token::Token(Type type, std::string value)
{
  this->type  = type;
  this->value = value;
}

Token::~Token()
{
  // Empty destructor
}

std::string Token::getType()
{
  std::string type;

  if (isIdentifier())
    type = "Identifier";
  else if (isLiteral())
    type = "Literal";
  else if (isSeparator())
    type = "Separator";
  else if (isOperator())
    type = "Operator";
  else if (isKeyword())
    type = "Keyword";
  else if (isComment())
    type = "Comment";

  return type;
}

std::string Token::getValue()
{
  return value;
}

void Token::setType(Type type)
{
  this->type = type;
}

void Token::setValue(std::string value)
{
  this->value = value;
}

bool Token::isIdentifier()
{
  return type == Type::IDENTIFIER;
}

bool Token::isLiteral()
{
  return type == Type::LITERAL;
}

bool Token::isSeparator()
{
  return type == Type::SEPARATOR;
}

bool Token::isOperator()
{
  return type == Type::OPERATOR;
}

bool Token::isKeyword()
{
  return type == Type::KEYWORD;
}

bool Token::isComment()
{
  return type == Type::COMMENT;
}
