package parser;

import interpreter.TypeError;

import java.util.HashMap;

import tokenizer.Token;
import tokenizer.Tokenizer;

//Relation -> Addition (LT|LEQ|GT|GEQ Addition)?
public class Relation extends Expression{
	
	private Addition addition;
	private Addition additio;
	private Token tok;
	
	
	public Relation(Tokenizer tokenizer){
		
		addition= new Addition(tokenizer);
		
		if(tokenizer.hasNext(Token.Type.LT,Token.Type.LEQ,Token.Type.GT,Token.Type.GEQ)){
			tok=tokenizer.next(Token.Type.LT,Token.Type.LEQ,Token.Type.GT,Token.Type.GEQ);
			additio=new Addition(tokenizer);
		}
		
	}

	
	public String toString() {
	
		if(tok!=null){
			return addition.toString()+" "+tok.text+" "+additio.toString();
			
		}
		else{
			return addition.toString();
		}
		
	}

	
	public Token firstToken() {
		
		return addition.firstToken();
	}



	public Object evaluate(HashMap<String, Object> state) {
		//Evaluate the left operand
		Object value= addition.evaluate(state);
		
		//If there's only one operand,return its value
		if(tok==null){
			return value;
		}
		//if it's not a number value, throw a type error on it.
		else if(!(value instanceof Number)){
			throw new TypeError(addition.firstToken());
		}
		
		else {
			//Evaluate the right operand
			Object value2= additio.evaluate(state);
			
			//if it's not a number value, throw a type error on it.
			if(!(value2 instanceof Number)){
				throw new TypeError(additio.firstToken());
			}
			
			//Apply the appropriate operation
			value=applyOperation(((Number) value).intValue(),((Number) value2).intValue());
			
		}
		
		//return the boolean result.
		return value;
	}
	
	//Helper method that applys operation to the values.
	private boolean applyOperation(Number num1,Number num2){
		if(tok.type==Token.Type.LT){
			return (num1.intValue()<num2.intValue());
		}
		else if(tok.type==Token.Type.LEQ){
			return (num1.intValue()<=num2.intValue());
		}
		else if(tok.type==Token.Type.GT){
			return (num1.intValue()>num2.intValue());
		}
		else{
			return (num1.intValue()>=num2.intValue());
		}
	}
	

}
