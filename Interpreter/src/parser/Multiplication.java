package parser;

import interpreter.DivisionError;
import interpreter.TypeError;

import java.util.ArrayList;
import java.util.HashMap;

import tokenizer.Token;
import tokenizer.Tokenizer;

//Multiplication -> Negation (MULT|DIV|MOD Negation)*
public class Multiplication extends Expression{
	
	private Negation negation;
	private ArrayList<Token>operators=new ArrayList<Token> ();
	private ArrayList<Negation> negations=new ArrayList<Negation>();
	
	public Multiplication(Tokenizer tokenizer){
		negation=new Negation(tokenizer);
		
		while (tokenizer.hasNext(Token.Type.MULT,Token.Type.DIV,Token.Type.MOD)){		
			operators.add(tokenizer.next(Token.Type.MULT,Token.Type.DIV,Token.Type.MOD));
			negations.add(new Negation(tokenizer));	
		}

	}

	
	public String toString() {
		
		String s=negation.toString();
		for(int i=0;i<operators.size();i++){
			s+=operators.get(i).text+" "+negations.get(i).toString();
		}
		return s;
	}


	
	public Object evaluate(HashMap<String, Object> state) {
		
		//Evaluate the left operand
		Object value= negation.evaluate(state);
		
		//If there's only one operand, return its value.
		if(operators.size()==0){
			return value;
		}
		
		//if it's not a number value, throw a type error on it.
		else if(!(value instanceof Number)){
			throw new TypeError(negation.firstToken());
		}
		else{
			Object value2;
			
			//For each other operand
			for(int i=0;i<operators.size();i++){
				value2=negations.get(i).evaluate(state);
				
				//if it's not a number value, throw a type error on it.
				if(!(value2 instanceof Number)){
					throw new TypeError(negations.get(i).firstToken());
				}
				value=applyOperation((Number) value,(Number) value2, operators.get(i),negations.get(i));
			}
			
		}
		
		//Return the final value after all the operands have been processed
		
		return value;
	}
	
	//Helper method that applys operation to the values.
	private Number applyOperation(Number x, Number y,Token token,Negation n){
		if(token.type==Token.Type.MULT){
			
			//Operations should produce integer values when both operands are integers
			if(x instanceof Integer && y instanceof Integer){
				return Integer.valueOf(x.intValue()*y.intValue());
			}
			
			//otherwise they should produce float values.
			else{
				return Float.valueOf(x.floatValue()*y.floatValue());
			}
		}
		else if(token.type==Token.Type.DIV){
			
			//Operations should produce integer values when both operands are integers
			if(x instanceof Integer && y instanceof Integer){
				
				//If at any point a MOD is performed with a right operand of zero, 
				//throw a division error on the right operand.
				if(y.intValue()==0){
					throw new DivisionError(n.firstToken());
				}
				return Integer.valueOf(x.intValue()/y.intValue());
			}
			
			
			//otherwise they should produce float values.
			else{
				
				//If at any point a MOD is performed with a right operand of zero,
				//throw a division error on the right operand.
				if(y.floatValue()==0){
					throw new DivisionError(n.firstToken());
				}
				return Float.valueOf(x.floatValue()/y.floatValue());
			}
		}
		
		else{
			
			//Operations should produce integer values when both operands are integers
			if(x instanceof Integer && y instanceof Integer){
				
				//If at any point a DIV is performed with a right operand of zero,
				//throw a division error on the right operand.
				if(y.intValue()==0){
					throw new DivisionError(n.firstToken());
				}
				return Integer.valueOf(x.intValue()%y.intValue());
			}
			
			//otherwise they should produce float values.
			else{
				
				//If at any point a DIV is performed with a right operand of zero, 
				//throw a division error on the right operand.
				if(y.floatValue()==0){
					throw new DivisionError(n.firstToken());
				}
				return Float.valueOf(x.floatValue()%y.floatValue());
			}
		}
	}


	
	public Token firstToken() {
		return negation.firstToken();
	}

}
