grammar Lang;

prog : data* func* ;
data : 'data' TYPE_NAME '{' decl* '}' ;
decl : ID '::' type ';' ;
func : ID '(' params? ')' (':' type (',' type)*)? '{' cmd* '}' ;
params : ID '::' type (',' ID '::' type)* ; 
type : btype type1 ;
type1 : ('[' ']' type1)? ;
btype : 'Int'
      | 'Char'
      | 'Bool'
      | 'Float'
      | TYPE_NAME 
      ;
cmd : '{' cmd* '}'
    | 'if' '(' exp ')' cmd cmd1
    | 'iterate' '(' exp ')' cmd
    | 'read' lvalue ';'
    | 'print' exp ';'
    | 'return' exp (',' exp)* ';'
    | lvalue '=' exp ';'
    | ID '(' exps? ')' ('<' lvalue (',' lvalue)* '>')? ';' 
    ;
cmd1 : ('else' cmd)? ;
exp : rexp exp1 ;
exp1 : ('&&' exp exp1)? ;
rexp : aexp rexp1 rexp3 ;
rexp1 : ('<' aexp)? ;
rexp2 : '==' aexp
      | '!=' aexp
      ;
rexp3 : (rexp2 rexp3)? ;
aexp : mexp aexp2 ;
aexp1 : '+' mexp
      | '-' mexp
      ;
aexp2 : (aexp1 aexp2)? ;
mexp : sexp mexp2 ;
mexp1 : '*' sexp
      | '/' sexp
      | '%' sexp
      ;
mexp2 : (mexp1 mexp2)? ;
sexp : '!' sexp
     | '-' sexp
     | 'true'
     | 'false'
     | 'null'
     | INT
     | FLOAT
     | CHAR
     | pexp
     ;
pexp : lvalue
     | '(' exp ')'
     | 'new' btype ('[' sexp ']')?
     | ID '(' exps? ')' '[' exp ']'
     ;
lvalue : ID lvalue2 ;
lvalue1 : '[' exp ']'
        | '.' ID
        ;
lvalue2 : (lvalue1 lvalue2)? ;
exps : exp (',' exp)* ;

fragment
LOWER_LETTER : [a-z] ;

fragment
UPPER_LETTER : [A-Z] ;

fragment
ANY_LETTER : (LOWER_LETTER|UPPER_LETTER) ;

fragment
DIGIT : [0-9] ;

fragment
SPECIAL_CHAR : ('\\n'|'\\t'|'\\b'|'\\r'|'\\\\'|'\\\'') ;

fragment
ASCII_CHAR : ([\u0000-\u0026]|[\u0028-\u005B]|[\u005D-\u007F]) ;

ID : LOWER_LETTER+(ANY_LETTER|DIGIT|'_')* ;
TYPE_NAME : UPPER_LETTER+(ANY_LETTER|DIGIT|'_')* ;
INT : DIGIT+ ;
FLOAT : DIGIT* '.' DIGIT+ ;
CHAR : '\'' (ASCII_CHAR|SPECIAL_CHAR) '\'' ;

WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines
LINE_COMMENT : '--' ~[\r\n]* '\r'? '\n' -> skip ;
BLOCK_COMMENT : '{-' .*? '-}' -> skip ;