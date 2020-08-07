package lexicalAnalyzer;

import tokens.LextantToken;
import tokens.Token;

public enum Punctuator implements Lextant {
	SPACE(";"),
	SEPARATOR(","),
	TERMINATOR("."),
	RETURNS("->"),
	ADD("+"),
	SUBTRACT("-"),
	MULTIPLY("*"),
	DIVISION("/"),
	OVER("//"),
	EXPRESS_OVER("///"),
	RATIONALIZE("////"),
	AND("&&"),
	OR("||"),
	NOT("!"),
	LESS_OR_EQUAL("<="),
	LESS("<"),
	EQUAL("=="),
	NOT_EQUAL("!="),
	GREATER(">"),
	GREATER_OR_EQUAL(">="),
	OPEN_BRACE("{"),
	CLOSE_BRACE("}"),
	OPEN_SMALL_BRACKET("("),
	CLOSE_SMALL_BRACKET(")"),
	OPEN_BRACKET("["),
	CLOSE_BRACKET("]"),
	PIPE("|"),
	HASH("#"),
	ASSIGN(":="),
	NULL_PUNCTUATOR("");

	private String lexeme;
	private Token prototype;
	
	private Punctuator(String lexeme) {
		this.lexeme = lexeme;
		this.prototype = LextantToken.make(null, lexeme, this);
	}
	public String getLexeme() {
		return lexeme;
	}
	public Token prototype() {
		return prototype;
	}
	
	//   the following hashtable lookup can replace the implementation of forLexeme above. It is faster but less clear. 
	private static LexemeMap<Punctuator> lexemeToPunctuator = new LexemeMap<Punctuator>(values(), NULL_PUNCTUATOR);
	public static Punctuator forLexeme(String lexeme) {
		   return lexemeToPunctuator.forLexeme(lexeme);
	}

	public static Lextant[] ReturnComparators() {
		Lextant[] comparators = {LESS_OR_EQUAL, LESS, EQUAL, NOT_EQUAL, GREATER, GREATER_OR_EQUAL};
		return comparators;
	}
	public static Lextant[] getMultiplicatives() {
		Lextant[] multiplicatives = { MULTIPLY, DIVISION, OVER, EXPRESS_OVER, RATIONALIZE };
		return multiplicatives;
	}
	
	public static Lextant[] getRationals() {
		Lextant[] rationals = { OVER, EXPRESS_OVER, RATIONALIZE };
		return rationals;
	}
	
}


