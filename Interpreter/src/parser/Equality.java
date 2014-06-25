package parser;


import interpreter.TypeError;

import java.util.HashMap;

import tokenizer.Token;
import tokenizer.Tokenizer;

//Equality -> Relation (EQ|NEQ Relation)?

public class Equality extends Expression{
	
	private Relation relation;
	private Relation relatio;
	private Token neqOrEq;
	
	public Equality(Tokenizer tokenizer){
		relation=new Relation(tokenizer);
		if(tokenizer.hasNext(Token.Type.EQ,Token.Type.NEQ)){
			neqOrEq=tokenizer.next(Token.Type.EQ,Token.Type.NEQ);
			relatio= new Relation(tokenizer);
			
		}
		
	}

	
	public String toString() {
		if(neqOrEq!=null){
			return relation.toString()+" "+neqOrEq.text+" "+relatio.toString();
		}else{
			return relation.toString();
		}
	}


	
	public Object evaluate(HashMap<String, Object> state) {
		
		Object value=relation.evaluate(state);
		
		//If there's only one operand, return its value.
		if(relatio==null){
			return value;
		}
		
		//Evaluate both operands; 
		
		else{
			
			Object value2=relatio.evaluate(state);
			
			//if the values have incompatible types, 
			//throw a type error on the second operand.
			if(compatible(value,value2)==false){
				throw new TypeError(relatio.firstToken());
			}
			
			//Apply the appropriate operation
			value=applyOperation(value,value2);  
		}
		
		//return the boolean result
		return value;
	}
	
	//Applys a given operation on two values
	public boolean applyOperation(Object value, Object value2){
		if(neqOrEq.type==Token.Type.EQ){
			return value.equals(value2);
		}
		else{
			return !(value.equals(value2));
		}
		
		
	}
	
	//Helper methods that check if two values are compatible
	private boolean compatible(Object value,Object value2){
		
		//If both are integers
		if(value instanceof Integer && value2 instanceof Integer){
			return true;	
		}
		
		//if either is a float
		else if(value instanceof Float && value2 instanceof Integer){
			return true;
		}
		else if(value2 instanceof Float && value instanceof Integer){
			return true;
		}
		
		//If both are float numbers
		else if(value instanceof Float && value2 instanceof Float){
			return true;
		}
		
		//If both are booleans
		else if(value instanceof Boolean){
			return (value instanceof Boolean && value2 instanceof Boolean);
		}
		else{
			return false;
		}
	}


	
	public Token firstToken() {
		
		return relation.firstToken();
	}

}
