package lexicalAnalyzer;

import tokens.LextantToken;
import tokens.Token;


public enum Keyword implements Lextant {
	EXEC("exec"),
	TRUE("_true_"),
	FALSE("_false_"),
	CHAR("char"),
	BOOL("bool"),
	STRING("string"),
	FLOAT("float"),
	RATIONAL("rat"),
	NULL("void"),
	CONST("const"),
	INT("int"),
	STATIC("static"),
	VAR("var"),
	NEW("new"),
	CLONE("clone"),
	DEALLOC("dealloc"),
	ZIP("zip"),
	REDUCE("reduce"),
	LENGTH("length"),
	REVERSE("reverse"),
	MAP("map"),
	FOLD("fold"),
	FUNCTION("func"),
	WHILE("while"),
	CALL("call"),
	RETURN("return"),
	IF("if"),
	ELSE("else"),
	FOR("for"),
	INDEX("index"),
	ELEMENT("elem"),
	OF("of"),
	CONTINUE("continue"),
	BREAK("break"),
	PRINT("print"),
	TAB("_t_"),
	NEWLINE("_n_"),
	NULL_KEYWORD("");

	private String lexeme;
	private Token prototype;
	
	private Keyword(String lexeme) {
		this.lexeme = lexeme;
		this.prototype = LextantToken.make(null, lexeme, this);
	}
	public String getLexeme() {
		return lexeme;
	}
	public Token prototype() {
		return prototype;
	}
	
	public static Keyword forLexeme(String lexeme) {
		for(Keyword keyword: values()) {
			if(keyword.lexeme.equals(lexeme)) {
				return keyword;
			}
		}
		return NULL_KEYWORD;
	}
	public static boolean isAKeyword(String lexeme) {
		return forLexeme(lexeme) != NULL_KEYWORD;
	}
	
	public static Lextant[] getTypeLiteral() {
		Lextant[] literals = { BOOL, CHAR, STRING, INT, FLOAT, RATIONAL, NULL};
		return literals;
	}
	
	/*   the following hashtable lookup can replace the serial-search implementation of forLexeme() above. It is faster but less clear. 
	private static LexemeMap<Keyword> lexemeToKeyword = new LexemeMap<Keyword>(values(), NULL_KEYWORD);
	public static Keyword forLexeme(String lexeme) {
		return lexemeToKeyword.forLexeme(lexeme);
	}
	*/
}
