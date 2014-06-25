package interpreter;

import tokenizer.Token;

public class NameError extends RuntimeException{

	public NameError(Token token){
		super("Name error: line" +token.line+ ", column"+token.column);
	}
	
	
}
