package lexicalAnalyzer;

import tokens.LextantToken;
import tokens.Token;


public enum Punctuator implements Lextant {
	ADD("+"),
	SUBTRACT("-"),
	MULTIPLY("*"),
	GREATER(">"),
	GREATEREQUAL(">="),
	LESSTHAN("<"),
	LESSTHANEQUAL("<="),
	EQUALTO("=="),
	NOTEQUALTO("!="),
	DIVIDE("/"),
	ASSIGN(":="),
	SEPARATOR(","),
	SPACE(";"),
	TERMINATOR("."), 
	OPEN_BRACE("{"),
	CLOSE_BRACE("}"),
	CAST_BEGIN("["),
	CAST_END("]"),
	CAST_MID("|"),
	NULL_PUNCTUATOR(""),
	DECIMAL_POINT("."),
	CIRCUMFLEX("^"),
	COMMENT("#");	//added this for PIKA 1

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
	
	
	public static Punctuator forLexeme(String lexeme) {
		for(Punctuator punctuator: values()) {
			if(punctuator.lexeme.equals(lexeme)) {
				return punctuator;
			}
		}
		return NULL_PUNCTUATOR;
	}
	
/*
	//   the following hashtable lookup can replace the implementation of forLexeme above. It is faster but less clear. 
	private static LexemeMap<Punctuator> lexemeToPunctuator = new LexemeMap<Punctuator>(values(), NULL_PUNCTUATOR);
	public static Punctuator forLexeme(String lexeme) {
		   return lexemeToPunctuator.forLexeme(lexeme);
	}
*/
	
}


