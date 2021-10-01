grammar ourLang;

@header {
    package me.retran.skijaexample.javafxskija;
}

LOAD
    : 'LOAD' | 'load'
    ;

SAVE
    : 'SAVE' | 'save'
    ;

LETTERS
    : ('a' .. 'z' | 'A' .. 'Z')+
    ;

NUMBER
    : ('0' .. '9')+
    ;

EOS
    : ';'
    ;

WS
    : [ \r\n\t] + -> channel (HIDDEN)
    ;

loadStatement
    : LOAD LETTERS EOS
    ;

saveStatement
    : SAVE LETTERS EOS
    ;

statements
    : ( loadStatement | saveStatement )*
    ;