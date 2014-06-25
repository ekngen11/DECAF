package interpreter;

import tokenizer.Token;

public class TypeError extends RuntimeException{

	public TypeError(Token token){
		
		super("Type error: line" +token.line+ ", column"+token.column);
	}
	
	
}
