package lexicalAnalyzer;


import logging.PikaLogger;
import inputHandler.InputHandler;
import inputHandler.LocatedChar;
import inputHandler.LocatedCharStream;
import inputHandler.PushbackCharStream;
import inputHandler.TextLocation;
import tokens.*;

import static lexicalAnalyzer.PunctuatorScanningAids.*;

public class LexicalAnalyzer extends ScannerImp implements Scanner {
	public static LexicalAnalyzer make(String filename) {
		InputHandler handler = InputHandler.fromFilename(filename);
		PushbackCharStream charStream = PushbackCharStream.make(handler);
		return new LexicalAnalyzer(charStream);
	}

	public LexicalAnalyzer(PushbackCharStream input) {
		super(input);
	}

	
	//////////////////////////////////////////////////////////////////////////////
	// Token-finding main dispatch


	//just a helper

	private boolean isValidString(char x){
		if(x == '.') return false;
		if(x == ',') return false;
		return !(x == ';');
	}
	@Override
	protected Token findNextToken() {
		LocatedChar ch = nextNonWhitespaceChar();


		if (ch.getCharacter() == '#'){
			ch = input.next();
			while (ch.getCharacter() != '\n' && ch.getCharacter() != '#'){
				ch = input.next();
			}
			//ch = input.next();
			/*if(ch.getCharacter() == '\n')*/
			return findNextToken();
			//input.pushback(ch);
			//return findNextToken();
		}


		if(ch.getCharacter() == '[' || ch.getCharacter() == ']'){
			ch = nextNonWhitespaceChar();
		}

		//if(ch.getCharacter() == '(' || ch.getCharacter() == ')') ch = nextNonWhitespaceChar();
		if(ch.isDigit() || ch.getCharacter() == '+' || ch.getCharacter() == '-') {
			return scanNumber(ch);
		}
		else if(ch.isAllowedToBeIdentifier()) {
			return scanIdentifier(ch);
		}
		else if(isPunctuatorStart(ch)) {
			//for chars
			if(ch.getCharacter() == '^'){
				LocatedChar next = input.next();
				LocatedChar next__ = input.peek();
				if(next__.getCharacter() != '^') {
					lexicalError(ch);
					return findNextToken();
				}
				input.next();
				StringBuffer x = new StringBuffer();
				x.append(next.getCharacter());
				return CharacterToken.make(next.getLocation(), x.toString());
				//input.pushback(ch);
			}

			//for strings
			if(ch.getCharacter() == '"'){
				LocatedChar next = input.next();
				LocatedChar next_ = input.peek();
				StringBuffer x = new StringBuffer();
				while (next.getCharacter() != '"' && next.getCharacter() != '\n'){
					x.append(next.getCharacter());
					next = input.next();
					next_ = input.peek();
				}
				if(next_.getCharacter() == ' '){
					next_ = nextNonWhitespaceChar();
				}
				//input.pushback(next);

				if(isValidString(next_.getCharacter())) {
					lexicalError(next);
				}
				return StringToken.make(next.getLocation(), x.toString());
			}

			return PunctuatorScanner.scan(ch, input);
		}
		else if(isEndOfInput(ch)) {
			return NullToken.make(ch.getLocation());
		}
		else {
			lexicalError(ch);
			return findNextToken();
		}
	}


	private LocatedChar nextNonWhitespaceChar() {
		LocatedChar ch = input.next();
		while(ch.isWhitespace()) {
			ch = input.next();
		}
		return ch;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////
	// Integer and number lexical analysis

	private Token scanNumber(LocatedChar firstChar) {

		StringBuffer buffer = new StringBuffer();
		LocatedChar next = input.peek();

		//case of not being a number and being a punctuation of some kind
		if(firstChar.isNumericSign() && !next.isDigit() & next.getCharacter() != '.'){
			return PunctuatorScanner.scan(firstChar, input); //sketchyyyyyyyyy
		}

		buffer.append(firstChar.getCharacter());
		if(firstChar.isDigit() || firstChar.isNumericSign()){
			appendSubsequentDigits(buffer);
			next = input.next();
			//means we have a sign, so return buffer
			if(next.getCharacter() == ' '){
				input.pushback(next);
				return IntegerToken.make(firstChar.getLocation(), buffer.toString());
			}
			LocatedChar secondNext = input.peek();

			if(next.getCharacter() == '.'){ // means int, and we added an extra '.'
				//to check if there is something after the decimal
				if(secondNext.getCharacter() == '.'){
					lexicalError(secondNext);
				}
				if(!secondNext.isDigit()){
					input.pushback(next);
					return IntegerToken.make(firstChar.getLocation(), buffer.toString());
				}

			}
			buffer.append(next.getCharacter());
		}
		if(buffer.toString().endsWith("E")){
			appendSubsequentDigits(buffer);
			return FloatingToken.make(firstChar.getLocation(), buffer.toString());
		}
		appendSubsequentDigits(buffer);
		next = input.next();
		LocatedChar sec = input.peek();
		if(next.getCharacter() == 'E'){
			buffer.append(next.getCharacter());
			if(sec.isNumericSign()){
				next = input.next();
				buffer.append(next.getCharacter());
			}
			appendSubsequentDigits(buffer);
		}
		//next = input.next();
		if(next.getCharacter() == '.') {
			input.pushback(next);
		}
		return FloatingToken.make(firstChar.getLocation(), buffer.toString());
	}
	private void appendSubsequentDigits(StringBuffer buffer) {
		LocatedChar c = input.next();
		while (c.isDigit()){
			buffer.append(c.getCharacter());
			c = input.next();
		}
		input.pushback(c);
	}

	//////////////////////////////////////////////////////////////////////////////
	// Identifier and keyword lexical analysis	

	private Token scanIdentifier(LocatedChar firstChar) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(firstChar.getCharacter());
		appendSubsequentLetters(buffer);

		String lexeme = buffer.toString();
		if(Keyword.isAKeyword(lexeme)) {
			return LextantToken.make(firstChar.getLocation(), lexeme, Keyword.forLexeme(lexeme));
		}
		else {
			return IdentifierToken.make(firstChar.getLocation(), lexeme);
		}
	}
	private void appendSubsequentLetters(StringBuffer buffer) {
		LocatedChar c = input.next();
		int length = 0;
		while(allowedLetters(c)) {
			length++;
			buffer.append(c.getCharacter());
			c = input.next();
		}
		if(length >= 32) lexicalError(c);
		input.pushback(c);
	}
	private boolean allowedLetters(LocatedChar c){
		char c_ = c.getCharacter();
		switch(c_){
			case '_': return true;
			case '$': return true;
		}
		if((int)c_ >=48 && (int)c_ <=57) return true; //case of a number
		if((int)c_ >= 65 && (int)c_ <= 90) return true; //case of upperCase
		if((int)c_ >= 97 && (int)c_ <= 122) return true; //case of lowercase

		return false;
	}


	
	//////////////////////////////////////////////////////////////////////////////
	// Punctuator lexical analysis	
	// old method left in to show a simple scanning method.
	// current method is the algorithm object PunctuatorScanner.java

	@SuppressWarnings("unused")
	private Token oldScanPunctuator(LocatedChar ch) {
		TextLocation location = ch.getLocation();
		
		switch(ch.getCharacter()) {
		case '*':
			return LextantToken.make(location, "*", Punctuator.MULTIPLY);
		case '-':
			return LextantToken.make(location, "-", Punctuator.SUBTRACT);
		case '+':
			return LextantToken.make(location, "+", Punctuator.ADD);
		case '>':
			return LextantToken.make(location, ">", Punctuator.GREATER);
		case ':':
			if(ch.getCharacter()=='=') {
				return LextantToken.make(location, ":=", Punctuator.ASSIGN);
			}
			else {
				throw new IllegalArgumentException("found : not followed by = in scanOperator");
			}
		case ',':
			return LextantToken.make(location, ",", Punctuator.SEPARATOR);
		case ';':
			return LextantToken.make(location, ";", Punctuator.TERMINATOR);
		default:
			throw new IllegalArgumentException("bad LocatedChar " + ch + "in scanOperator");
		}
	}

	

	//////////////////////////////////////////////////////////////////////////////
	// Character-classification routines specific to Pika scanning.	

	private boolean isPunctuatorStart(LocatedChar lc) {
		char c = lc.getCharacter();
		return isPunctuatorStartingCharacter(c);
	}

	private boolean isEndOfInput(LocatedChar lc) {
		return lc == LocatedCharStream.FLAG_END_OF_INPUT;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////
	// Error-reporting	

	private void lexicalError(LocatedChar ch) {
		if(ch.getCharacter() == 'E') return;
		PikaLogger log = PikaLogger.getLogger("compiler.lexicalAnalyzer");
		log.severe("Lexical error: invalid character " + ch);
	}

	
}
