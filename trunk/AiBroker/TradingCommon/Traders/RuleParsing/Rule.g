grammar Rule;

options {
	language=CSharp2;
	output=AST;
	ASTLabelType=CommonTree;
}

tokens {
	LPAREN='(' ;
	RPAREN=')' ;	
}

@parser::namespace { TradingCommon.Traders.RuleParsing }

@lexer::namespace { TradingCommon.Traders.RuleParsing }

sexpr
	: list EOF
	;
	
item
	: atom 
	| list
	;
	
list
	: LPAREN item* RPAREN
	;
	
atom
	: SYMBOL | NUMBER
	;
	
	
WHITESPACE
	: (' ' | '\n' | '\t' | '\r' | '\u000C')+ 
		{ $channel = HIDDEN; }
	;
	
NUMBER
	: ('+' | '-')? (DIGIT)+ ('.' (DIGIT)+)?
	;
	
SYMBOL
	: SYMBOL_START (SYMBOL_START | DIGIT)*
	;

fragment
SYMBOL_START 
	: ('a'..'z') | ('A'..'Z') | ('_')
	;
	
fragment
DIGIT
	: ('0'..'9')
	;
