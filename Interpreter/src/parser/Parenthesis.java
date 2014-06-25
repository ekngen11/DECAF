package parser;

import java.util.HashMap;

import tokenizer.Token;
import tokenizer.Tokenizer;

//Parenthesis -> LPAREN Expression RPAREN
public class Parenthesis extends Operand{

	private Token lParen;
	private Expression expression;
	private Token rParen;
	
	public Parenthesis(Tokenizer tokenizer){
		lParen=tokenizer.next(Token.Type.LPAREN);
		expression=Expression.getExpression(tokenizer);
		rParen=tokenizer.next(Token.Type.RPAREN);
	}

	public String toString() {
		return lParen.text+" "+expression.toString()+" "+rParen.text;
	}

	
	public Object evaluate(HashMap<String, Object> state) {
		
		//Evaluate the expression and return its value.
		return expression.evaluate(state);
		
	}

	
	public Token firstToken() {
		
		return lParen;
	}
}
