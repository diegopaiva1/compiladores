# Analisador Léxico

O código-fonte na pasta `src/` contém a implementação do analisador léxico para reconhecimentos de tokens.

## Utilização

```cpp
#include "Lexer.hpp"

int main(int argc, char *argv[])
{
  int expectedArgs = 1;

  // Arg error handling 
  if (expectedArgs != argc - 1) {
    printf("Error: %d argument(s) expected; %d provided\n", expectedArgs, argc - 1);
    exit(1);
  }

  // Read the program passed as arg
  const std::string programFileName = argv[1];

  // Initialize new lexer
  Lexer lexer = Lexer();

  // Use getTokens() to retrieve all tokens from the program
  std::vector<Token> tokens = lexer.getTokens(programFileName);

  // Display all tokens
  printf("Printing all tokens:\n\n");
  for (Token &t : tokens)
    printf("%20s is %s\n", t.getValue().c_str(), t.getType().c_str());

  return 0;
}
```

## Compilando o código-fonte

### [CMake](https://cmake.org/)

```
git clone git@github.com:diegopaiva1/compiladores.git

cd compiladores/

mkdir build && cd build

cmake ..
```

### g++

```
git clone git@github.com:diegopaiva1/compiladores.git

cd compiladores/

mkdir bin && cd bin

g++ -std=c++1z -O3 ../src/*.cpp -o lex
```

## Execução e exemplo

Para reconhecer os tokens de um programa, e.g, `program.txt`, digite  `./lex program.txt`.

Por exemplo, para o programa de exemplo:

```
int main ()
{
    int c = 3 * 4;
    printf(c);
}
```

O output vai ser:

```
                 int is Identifier
                main is Identifier
                   ( is Separator
                   ) is Separator
                   { is Separator
                 int is Identifier
                   c is Identifier
                   = is Operator
                   3 is Literal
                   * is Operator
                   4 is Literal
                   ; is Separator
              printf is Identifier
                   ( is Separator
                   c is Identifier
                   ) is Separator
                   ; is Separator
                   } is Separator
```
