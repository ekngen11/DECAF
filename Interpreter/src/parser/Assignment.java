package parser;

import interpreter.NameError;
import interpreter.TypeError;

import java.util.HashMap;

import tokenizer.Token;
import tokenizer.Tokenizer;

//Assignment -> IDENTIFIER ASSIGN Expression SEMICOLON
public class Assignment extends Statement{
	
	private Token identifier,assign,semicolon;
	private Expression expression;
	
	public Assignment(Tokenizer tokenizer){
		identifier = tokenizer.next(Token.Type.IDENTIFIER);
		assign=tokenizer.next(Token.Type.ASSIGN);
		expression= Expression.getExpression(tokenizer);
		semicolon=tokenizer.next(Token.Type.SEMICOLON);
	}

	
	public String toString() {
		
		return identifier.text+" "+assign.text+" "+expression.toString()+" "+semicolon.text;
		
	}


	public void execute(HashMap<String, Object> state) {
		
		//If no binding exists for the identifier name, 
		//throw a name error on the identifier.
		if(!(state.containsKey(identifier.text))){
			throw new NameError(identifier);
		}
		
		//Evaluate the expression.
		Object value= expression.evaluate(state);
		
		//If the expression value is the correct type, update the binding.
		//If it can be promoted to the correct type, promote it and update the binding.
		if(value instanceof Integer && state.get(identifier.text) instanceof Float ){
			state.put(identifier.text, ((Integer) value).floatValue());
		}
		else if( value instanceof Float && state.get(identifier.text) instanceof Float){
			state.put(identifier.text, value);
		}
		else if(value instanceof Integer && state.get(identifier.text) instanceof Integer){
			state.put(identifier.text,((Integer) value).intValue());
		}
		else if(value instanceof Boolean && state.get(identifier.text) instanceof Boolean){
			state.put(identifier.text, ((Boolean) value).booleanValue());
		}
		
		//Otherwise, throw a type error.
		else{
			
			throw new TypeError(identifier);
		}
		
		
	}


	
}
