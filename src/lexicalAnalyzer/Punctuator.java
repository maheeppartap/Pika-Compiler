package lexicalAnalyzer;

import tokens.LextantToken;
import tokens.Token;

public enum Punctuator implements Lextant {
	SPACE(";"),
	SEPARATOR(","),
	TERMINATOR("."),
	
	ADD("+"),
	SUBTRACT("-"),
	MULTIPLY("*"),
	DIVISION("/"),
	
	OVER("//"),
	EO("///"),
	RATIONALIZE("////"),
	
	AND("&&"),
	OR("||"),
	
	NOT("!"),
	
	LESSTHANEQUAL("<="),
	LESS("<"),
	EQUAL("=="),
	NOT_EQUAL("!="),
	GREATER(">"),
	GREATERTHANEQUAL(">="),
	
	OPEN_BRACE("{"),
	CLOSE_BRACE("}"),
	OPEN_PARENTHESIS("("),
	CLOSE_PARENTHESIS(")"),
	OPEN_BRACKET("["),
	CLOSE_BRACKET("]"),
	
	CAST_MID("|"),
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
	
	public static Lextant[] getMultiplicatives() {
		Lextant[] multiplicatives = { MULTIPLY, DIVISION, OVER, EO, RATIONALIZE };
		return multiplicatives;
	}
	
	public static Lextant[] getRationals() {
		Lextant[] rationals = { OVER, EO, RATIONALIZE };
		return rationals;
	}
	
	public static Lextant[] getComparators() {
		Lextant[] comparators = {LESSTHANEQUAL, LESS, EQUAL, NOT_EQUAL, GREATER, GREATERTHANEQUAL};
		return comparators;
	}
	
//	public static Punctuator forLexeme(String lexeme) {
//	for(Punctuator punctuator: values()) {
//		if(punctuator.lexeme.equals(lexeme)) {
//			return punctuator;
//		}
//	}
//	return NULL_PUNCTUATOR;
//}
	
}


