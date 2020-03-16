/**
 * @file   FDA.hpp
 * @author Diego Paiva
 * @date   11/03/2020
 *
 * Implementation of a finite deterministic automaton.
 */

#include "Automaton.hpp"

#include <unordered_map>

#ifndef FDA_H_INCLUDED
#define FDA_H_INCLUDED

class FDA : public Automaton
{
protected:
  std::unordered_map<std::pair<int, char>, int, KeyHasher> transitionsMap;

 /**
  * @brief Move from a state with a character.
  *
  * @param pair The std::pair containing the state and the character.
  * @return     The state resulting from the transition the state with the character.
  */
  int move(std::pair<int, char> pair);

public:
 /**
  * @brief Construct a new FDA.
  *
  * @param fileName The file containing the FDA data.
  */
  FDA(std::string fileName);

 /**
  * @brief Default destructor.
  */
  ~FDA();

 /**
  * @copydoc Automaton::match()
  */
  bool match(std::string word);

 /**
  * @brief Add a new transition.
  *
  * @param pair             The std::pair containing the origin state and the character.
  * @param destinationState The destination state.
  */
  void addTransition(std::pair<int, char> pair, int destinationState);
};

#endif // FDA_H_INCLUDED
