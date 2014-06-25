package parser;

import tokenizer.Token;
import tokenizer.Tokenizer;

//Operand -> Literal | Identifier | Parenthesis

public abstract class Operand extends Expression{
	
	public static Operand getOperand(Tokenizer tokenizer){
		
		if(tokenizer.hasNext(Token.Type.IDENTIFIER)){
			return new Identifier(tokenizer);

		}
		else if(tokenizer.hasNext(Token.Type.INT_LITERAL,Token.Type.FLOAT_LITERAL,Token.Type.TRUE,Token.Type.FALSE)){
			
			return new Literal(tokenizer);
		}
		else{
			return new Parenthesis(tokenizer);
		}
		
	}
	

}
