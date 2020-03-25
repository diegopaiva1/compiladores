/**
 * @file   Token.hpp
 * @author Diego Paiva
 * @date   18/03/2020
 *
 * Abstract data type (ADT) of a Token.
 */

#ifndef TOKEN_H_INCLUDED
#define TOKEN_H_INCLUDED

#include <string>
#include <unordered_map>

/**
 * @brief Enumeration of possible token types.
 */
enum class Type {IDENTIFIER = 0, LITERAL, SEPARATOR, OPERATOR, KEYWORD, COMMENT};

class Token
{
private:
  Type type;
  std::string value;

public:
 /**
  * @brief Construct a new Token.
  */
  Token();

 /**
  * @brief Construct a new Token.
  *
  * @param type  Type.
  * @param value Value.
  */
  Token(Type type, std::string value);

 /**
  * @brief Default destructor.
  */
  ~Token();

 /**
  * @brief Get the type of the Token.
  *
  * @return A [`std::string`](http://www.cplusplus.com/reference/string/string/) containing the type.
  */
  std::string getType();

 /**
  * @brief Get the value of the Token.
  *
  * @return A [`std::string`](http://www.cplusplus.com/reference/string/string/) containing the value.
  */
  std::string getValue();

 /**
  * @brief Set the token's type.
  *
  * @param type The type to be set to.
  */
  void setType(Type type);

 /**
  * @brief Set the token's value.
  *
  * @param value The value to be set to.
  */
  void setValue(std::string value);

 /**
  * @brief Check if token is an identifier.
  *
  * @return `true` if is an identifier, `false` otherwise.
  */
  bool isIdentifier();

 /**
  * @brief Check if token is a literal.
  *
  * @return `true` if is a literal, `false` otherwise.
  */
  bool isLiteral();

 /**
  * @brief Check if token is a separator.
  *
  * @return `true` if is a separator, `false` otherwise.
  */
  bool isSeparator();

 /**
  * @brief Check if token is an operator.
  *
  * @return `true` if is an operator, `false` otherwise.
  */
  bool isOperator();

 /**
  * @brief Check if token is a keyword.
  *
  * @return `true` if is a keyword, `false` otherwise.
  */
  bool isKeyword();

 /**
  * @brief Check if token is a comment.
  *
  * @return `true` if is a comment, `false` otherwise.
  */
  bool isComment();
};

#endif // TOKEN_H_INCLUDED
