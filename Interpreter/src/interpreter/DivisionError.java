package interpreter;

import tokenizer.Token;

public class DivisionError extends RuntimeException{

	public DivisionError(Token token){
		super("Division error: line" +token.line+ ", column"+token.column);
	}
	
	
}
