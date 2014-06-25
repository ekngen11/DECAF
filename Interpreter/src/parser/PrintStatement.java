package parser;


import java.util.HashMap;

import tokenizer.Token;
import tokenizer.Tokenizer;


//PrintStatement -> PRINT Expression SEMICOLON
public class PrintStatement extends Statement{
	
	private Token print,semicolon;
	private Expression expression;
	
	public PrintStatement(Tokenizer tokenizer){
		print=tokenizer.next(Token.Type.PRINT);
		expression= Expression.getExpression(tokenizer);
		semicolon=tokenizer.next(Token.Type.SEMICOLON);
	}
	
	public String toString(){
		return print.text+" "+expression.toString()+" "+semicolon.text+"\n";
	}

	
	public void execute(HashMap<String, Object> state) {
		//Evaluate the expression.
		Object value=expression.evaluate(state);
		
		//Print it to the console.
		System.out.println(value);
		
	}
}
