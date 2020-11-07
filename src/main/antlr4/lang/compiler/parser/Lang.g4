grammar Lang;

/* Parser rules */
prog : data* func* EOF # Program
     ;
data : 'data' TYPE_NAME '{' decl* '}'
     ;
decl : ID '::' type ';' # Declaration
     ;
func : ID '(' params? ')' (':' type (',' type)*)? '{' cmd* '}' # Function
     ;
params : ID '::' type (',' ID '::' type)* # Parameters
       ;
type : type '[' ']'
     | btype
     ;
btype : 'Int'
      | 'Char'
      | 'Bool'
      | 'Float'
      | TYPE_NAME
      ;
cmd : '{' cmd* '}'                                         # CmdScope
    | 'if' '(' exp ')' cmd                                 # If
    | 'if' '(' exp ')' cmd 'else' cmd                      # IfElse
    | 'iterate' '(' exp ')' cmd                            # Iterate
    | 'read' lvalue ';'                                    # Read
    | 'print' exp ';'                                      # Print
    | 'return' exp (',' exp)* ';'                          # Return
    | lvalue '=' exp ';'                                   # Assignment
    | ID '(' exps? ')' ('<' lvalue (',' lvalue)* '>')? ';' # SeiLa1
    ;
exp : exp '&&' exp # And
    | rexp         # SeiLa2
    ;
rexp : aexp '<' aexp   # LessThan
     | rexp '==' aexp  # Equal
     | rexp '!=' aexp  # NotEqual
     | aexp            # SeiLa3
     ;
aexp : aexp '+' mexp # Addition
     | aexp '-' mexp # Subtraction
     | mexp          # SeiLa4
     ;
mexp : mexp '*' sexp  # Multiplication
     | mexp '/' sexp  # Division
     | mexp '%' sexp  # Module
     | sexp           # SymbolicExpression
     ;
sexp : '!' sexp  # Not
     | '-' sexp  # Negate
     | 'true'    # True
     | 'false'   # False
     | 'null'    # Null
     | INT       # Int
     | FLOAT     # Float
     | CHAR      # Char
     | pexp      # PrimaryExpression
     ;
pexp : lvalue
     | '(' exp ')'
     | 'new' type ('[' exp ']')?
     | ID '(' exps? ')' '[' exp ']'
     ;
lvalue : ID
       | lvalue '[' exp ']'
       | lvalue '.' ID
       ;
exps : exp (',' exp)*
     ;

/* Tokens fragments */
fragment LOWER_LETTER : [a-z];
fragment UPPER_LETTER : [A-Z];
fragment ANY_LETTER : (LOWER_LETTER|UPPER_LETTER);
fragment DIGIT : [0-9];
fragment SPECIAL_CHAR : ('\\n'|'\\t'|'\\b'|'\\r'|'\\\\'|'\\\'');
fragment ASCII_CHAR : ([\u0000-\u0026]|[\u0028-\u005B]|[\u005D-\u007F]);

/* Tokens definitions */
ID : LOWER_LETTER+(ANY_LETTER|DIGIT|'_')*;
TYPE_NAME : UPPER_LETTER+(ANY_LETTER|DIGIT|'_')*;
INT : DIGIT+;
FLOAT : DIGIT* '.' DIGIT+;
CHAR : '\'' (ASCII_CHAR|SPECIAL_CHAR) '\'';
WS : [ \t\r\n]+ -> skip;
LINE_COMMENT : '--' ~[\r\n]* '\r'? '\n' -> skip;
BLOCK_COMMENT : '{-' .*? '-}' -> skip;
