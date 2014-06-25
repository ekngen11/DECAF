package parser;

import java.util.HashMap;

import tokenizer.Token;
import tokenizer.Tokenizer;

//Literal -> INT_LITERAL | FLOAT_LITERAL | TRUE | FALSE
public class Literal extends Operand{

	private Token literal;
	
	public Literal(Tokenizer tokenizer){
		
		literal=tokenizer.next(Token.Type.INT_LITERAL,Token.Type.FLOAT_LITERAL,Token.Type.TRUE,Token.Type.FALSE);
	
	
	}

	
	public String toString() {
		return literal.text;
	}



	public Object evaluate(HashMap<String, Object> state) {
		
		//Create and return an object to represent the value of the literal text.
		if(literal.type==Token.Type.INT_LITERAL){
			return new Integer(literal.text);
		}
		else if(literal.type==Token.Type.FLOAT_LITERAL){
			return new Float(literal.text);
		}
		else{
			return new Boolean(literal.text);
		}
	}


	
	public Token firstToken() {
		return literal;
	}
}
