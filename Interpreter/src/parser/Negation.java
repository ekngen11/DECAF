package parser;

import interpreter.TypeError;

import java.util.HashMap;

import tokenizer.Token;
import tokenizer.Tokenizer;

//Negation -> (MINUS|NOT)? Operand
public class Negation extends Expression{
	
	private Token preOperand;
	private Operand operand;
	
	public Negation(Tokenizer tokenizer){
		
		if(tokenizer.hasNext(Token.Type.MINUS,Token.Type.NOT)){
			preOperand=tokenizer.next(Token.Type.MINUS,Token.Type.NOT);
		}
		
		operand= Operand.getOperand(tokenizer);
		
	}
	
	
	
	public String toString() {
		if(preOperand!=null)
			return preOperand.text+" "+operand.toString();
		else
			return operand.toString();
	}

	
	public Token firstToken(){ 
		return (preOperand!=null) ? preOperand : operand.firstToken();
	}

	public Object evaluate(HashMap<String, Object> state) {
		
		//Evaluate the operand
		Object value=operand.evaluate(state);
		
		if (preOperand!=null){
			
			//If there's an operator, apply it to the operand
			//, as long as it has the appropriate type; 
			if (preOperand.type==Token.Type.MINUS && value instanceof Number){
				return minus((Number) value);
			}
			else if(preOperand.type==Token.Type.NOT && value instanceof Boolean){
				return not((Boolean) value);
			}
			
			// otherwise throw a type error on the operand.
			else 
				throw new TypeError(operand.firstToken());
		}
		
		//Return the final value
		return value;
		
	}
	
	//The MINUS operator should only be applied to number values
	private Number minus(Number x){
		
		if(x instanceof Integer)
			return Integer.valueOf(-x.intValue());
		else
			return Float.valueOf(-x.floatValue());
		
	}
	
	//The NOT operator should only be applied to boolean values.
	private Boolean not (Boolean x){
		return Boolean.valueOf(!x.booleanValue());
	}

}
