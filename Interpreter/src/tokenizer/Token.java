// Represent a token in the decaf language.

package tokenizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public  class Token {

	public final String text;
	public final Type type;
	public final int line,column;
	
	public Token(Type type, String text, int line,int column) {
		this.type = type;
		this.text = text;
		this.line=line;
		this.column=column;
	}
	
	public String toString() {
		return type+" ["+text+"] "+line+","+column;
	}
	
	public enum Type {
		
		COMMENT("//.*"), 
		WHITESPACE("\\s+"), 
		SEMICOLON(";"),
		EQ("=="),
		ASSIGN("="),
		COMMA(","),
		LBRACE("\\{"),
		RBRACE("\\}"),
		LPAREN("\\("),
		RPAREN("\\)"),
		IF("if\\b"),
		WHILE("while\\b"),
		PRINT("print\\b"),
		ELSE ("else\\b"),
		INT("int\\b"),
		FLOAT("float\\b"),
		BOOLEAN ("boolean\\b"),
		TRUE("true\\b"),
		FALSE("false\\b"),
		FLOAT_LITERAL("[0-9]+\\.[0-9]+"), 
		INT_LITERAL("\\d+"),
		PLUS("\\+"),
		MINUS("-"),
		MULT("\\*"),
		DIV("/"),
		MOD("%"),
		NEQ("!="),
		LEQ("<="),
		GEQ(">="),
		LT("<"),
		GT(">"),
		AND("&&"),
		OR("\\|\\|"),
		NOT("!"),
		IDENTIFIER("[a-z]\\w*");
		
		private String regExp;
		
		Type(String regExp) {
			this.regExp = regExp;
		}
		
		// If the front of the code string matches this type's pattern,
		// return the matching substring. Otherwise, return null.
		public String getMatch(String code) {
			
			Pattern pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(code);
			
			if (matcher.lookingAt())
				return matcher.group();
			else
				return null;
		}
	}
}




