grammar ExpressionGrammar;

@header {
    package com.navadeep.CustomGrammerBackend.expression.generated;
}

program
    : statement* returnStmt EOF
    ;

statement
    : assignment ';'
    | ifStatement
    | whileStatement
    | forStatement
    | block
    | expr ';'
    ;

assignment
    : ID '=' expr
    ;

block
    : '{' statement* '}'
    ;

ifStatement
    : 'if' '(' expr ')' statement ('else' statement)?
    ;

whileStatement
    : 'while' '(' expr ')' statement
    ;

forStatement
    : 'for' '(' assignment? ';' expr? ';' assignment? ')' statement
    ;

// return statement
returnStmt
    : 'return' expr ';'
    ;

expr
    : expr op=('*' | '/') expr       # MulDiv
    | expr op=('+' | '-') expr       # AddSub
    | expr op=('<' | '<=' | '>' | '>=' ) expr  # Comparison
    | expr op=('==' | '!=') expr     # Equality
    | expr op='&&' expr              # AndExpr
    | expr op='||' expr              # OrExpr
    | NUMBER                         # Number
    | ID                             # Variable
    | '(' expr ')'                   # Parens
    ;


// LEXER RULES
ID      : [a-zA-Z_][a-zA-Z0-9_]* ;
NUMBER  : [0-9]+ ('.' [0-9]+)? ;
WS      : [ \t\r\n]+ -> skip ;
