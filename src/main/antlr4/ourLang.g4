grammar ourLang;

SAVE
    : 'SAVE' | 'save'
    ;

LETTERS
    : ('a' .. 'z' | 'A' .. 'Z') +
    ;

NUMBER
    : ('0' .. '9')+
    ;

WS
    : [ \r\n\t] + -> channel (HIDDEN)
    ;