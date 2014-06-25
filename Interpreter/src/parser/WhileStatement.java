package parser;

import interpreter.TypeError;

import java.util.HashMap;

import tokenizer.Token;
import tokenizer.Tokenizer;

//WhileStatement -> WHILE LPAREN Expression RPAREN Statement
public class WhileStatement extends Statement{

	private Token whyle,lParen,rParen;
	private Expression expression;
	private Statement statement;
	
	public WhileStatement(Tokenizer tokenizer){
		whyle= tokenizer.next(Token.Type.WHILE);
		lParen=tokenizer.next(Token.Type.LPAREN);
		expression= Expression.getExpression(tokenizer);
		rParen=tokenizer.next(Token.Type.RPAREN);
		statement= Statement.getStatement(tokenizer);
	}

	public String toString() {
		
		return whyle.text+" " +lParen.text+" "+expression.toString()+" "+rParen.text+"\n"+statement.toString();
		
	}

	
	public void execute(HashMap<String, Object> state) {
		//Evaluate the expression
		Object value= expression.evaluate(state);
		
		//if it's not a boolean value, throw a type error on it.
		if(!(value instanceof Boolean)){
			throw new TypeError(expression.firstToken());
		}
		
		//While the expression evaluates to true, 
		//execute the statement and re-evaluate the expression.
		while(((Boolean) value).booleanValue()==true){
			statement.execute(state);
			value=expression.evaluate(state);
		}
		
	}
	
	

	
	
}
