package parser;

import interpreter.TypeError;

import java.util.ArrayList;
import java.util.HashMap;

import tokenizer.Token;
import tokenizer.Tokenizer;

//Addition -> Multiplication (PLUS|MINUS Multiplication)*

public class Addition extends Expression{

	private Multiplication multiplication;
	private ArrayList<Token>operators=new ArrayList<Token> ();
	private ArrayList<Multiplication> multiplications=new ArrayList<Multiplication>();

	public Addition(Tokenizer tokenizer){
		multiplication= new Multiplication(tokenizer);

		while(tokenizer.hasNext(Token.Type.PLUS,Token.Type.MINUS)){

			operators.add(tokenizer.next(Token.Type.PLUS,Token.Type.MINUS));
			multiplications.add(new Multiplication(tokenizer));

		}


	}

	public String toString() {
		String s=multiplication.toString();
		for(int i=0;i<operators.size();i++){
			s+=operators.get(i).text+" "+multiplications.get(i).toString();
		}
		return s;
	}

	
	public Object evaluate(HashMap<String, Object> state) {
		
		//Evaluate the left operand
		Object value= multiplication.evaluate(state);
		
		//If there's only one operand, return its value
		if(multiplications.size()==0){
			return value;
		}
		// if it's not a number value, throw a type error on it.
		else if(!(value instanceof Number)){
			throw new TypeError(multiplication.firstToken());
		}
		
		else{
			Object value2;
			
			//For each other operand
			for(int i=0;i<operators.size();i++){
				value2=multiplications.get(i).evaluate(state);
				
				// if it's not a number value, throw a type error on it.
				if(!(value2 instanceof Number)){
					throw new TypeError(multiplications.get(i).firstToken());
				}
				
				// otherwise apply the appropriate operation.
				value=applyOperation((Number) value,(Number)value2,operators.get(i));
			}
		}
		
		//Return the final value after all the operands have been processed
		return value;
	}
	
	//Helper method that applys operation to the values.
	private Number applyOperation(Number x,Number y,Token token){
		
		//Operations should produce integer values when both operands are integers
		if(x instanceof Integer && y instanceof Integer){
			if(token.type==Token.Type.MINUS){
				return Integer.valueOf(x.intValue()-y.intValue());
			}
			else if (token.type==Token.Type.PLUS){
				return Integer.valueOf(x.intValue()+y.intValue());
			}
		}
		
		// otherwise they should produce float values.
		else{
			if(token.type==Token.Type.MINUS)
				return Float.valueOf(x.floatValue()-y.floatValue());
			
			else if (token.type==Token.Type.PLUS)
				return Float.valueOf(x.floatValue()+y.floatValue());
			
		}
		return null;
	}

	
	public Token firstToken() {
		
		return multiplication.firstToken();
	}


}
