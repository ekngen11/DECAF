package parser;

import interpreter.TypeError;

import java.util.HashMap;

import tokenizer.Token;
import tokenizer.Tokenizer;

//IfStatement -> IF LPAREN Expression RPAREN Statement (ELSE Statement)?
public class IfStatement extends Statement{
	
	private Token ifState,lParen,rParen,elseState;
	private Expression expression;
	private Statement statement1,statement2;
	
	
	public IfStatement(Tokenizer tokenizer){
		ifState=tokenizer.next(Token.Type.IF);
		lParen=tokenizer.next(Token.Type.LPAREN);
		expression=Expression.getExpression(tokenizer);
		rParen=tokenizer.next(Token.Type.RPAREN);
		statement1=Statement.getStatement(tokenizer);
		
		
		if(tokenizer.hasNext(Token.Type.ELSE)){
			elseState=tokenizer.next(Token.Type.ELSE);
			statement2=Statement.getStatement(tokenizer);
		}
		
	}



	public String toString() {
		if(elseState==null){
			return ifState.text+" "+lParen.text+" "+
					expression.toString()+" "+rParen.text+"\n"+statement1.toString();
		}
		else{
			return ifState.text+" "+lParen.text+" "+
				expression.toString()+" "+rParen.text
				+" "+statement1.toString()+
				" "+elseState.text+"\n"+statement2.toString();
		}
	}



	
	public void execute(HashMap<String, Object> state) {
		
		//Evaluate the expression;
		Object value=expression.evaluate(state);
		
		//if it's not a boolean value, throw a type error on it.
		if(!(value instanceof Boolean)){
			throw new TypeError(expression.firstToken());
		}
		
		//If the expression evaluates to true, execute the first statement.
		if(((Boolean) value).booleanValue()==true){
			statement1.execute(state);
		}
		
		//Otherwise, if there is a second statement, execute that.
		else{
			if(statement2!=null){
				statement2.execute(state);
			}
		}
		
	}

}
