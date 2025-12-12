grammar ExpressionGrammar;

@header {
    package com.navadeep.CustomGrammerBackend.expression.generated;
}

program: statement* returnStmt EOF; // main rule

statement: assignment ';' ;

assignment: ID '=' expr ;

returnStmt: 'return' expr ';' ;

expr: expr op=('*' | '/') expr  # MulDiv
    | expr op=('+' | '-') expr  # AddSub
    | NUMBER                    # Number
    | ID                        # Variable
    | '(' expr ')'              # Parens
    ;


ID: [a-zA-Z]+ ;
NUMBER: [0-9]+ ('.' [0-9]+)? ;
WS: [ \t\r\n]+ -> skip ;