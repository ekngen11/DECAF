package parser;

import interpreter.NameError;

import java.util.HashMap;

import tokenizer.Token;
import tokenizer.Tokenizer;

//Identifier -> IDENTIFIER
public class Identifier extends Operand{
	
	private Token identifier;
	
	public Identifier(Tokenizer tokenizer){
		identifier=tokenizer.next(Token.Type.IDENTIFIER); 
	}
	
	public String toString() {
		
		return identifier.text;
	}

	
	public Object evaluate(HashMap<String, Object> state) {
		
		//If no binding exists for the identifier name, 
		//throw a name error on the identifier.
		if(!(state.containsKey(identifier.text))){
			throw new NameError(identifier);
		}
		
		//Otherwise, return the value that is currently bound to the identifier name.
		return state.get(identifier.text);
	}

	
	public Token firstToken() {
		
		return identifier;
	}

}
