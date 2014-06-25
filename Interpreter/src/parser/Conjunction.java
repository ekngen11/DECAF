package parser;

import interpreter.TypeError;

import java.util.ArrayList;
import java.util.HashMap;

import tokenizer.Token;
import tokenizer.Tokenizer;

//Conjunction -> Equality (AND Equality)*

public class Conjunction extends Expression{

	private Equality equality;
	private ArrayList<Token> operators= new ArrayList<Token>();
	private ArrayList<Equality> equalitys= new ArrayList<Equality>();

	public Conjunction(Tokenizer tokenizer){
		equality= new Equality(tokenizer);

		while(tokenizer.hasNext(Token.Type.AND)){
			operators.add(tokenizer.next(Token.Type.AND));
			equalitys.add(new Equality(tokenizer));
		}


	}

	public String toString() {
		String s= equality.toString();
		for(int i=0;i<operators.size();i++){
			s+=operators.get(i).text+" "+ equalitys.get(i).toString();
		}
		return s;
	}


	public Object evaluate(HashMap<String, Object> state) {
		
		//Evaluate the left operand
		Object value= equality.evaluate(state);
		
		//If there's only one operand, return its value.
		if(operators.size()==0){
			return value;
		}
		Boolean bool=null;
		
		//if it's not a boolean value, throw a type error on it.
		if(!(value instanceof Boolean)){
			throw new TypeError(equality.firstToken());
		}
		else{
			 bool= ((Boolean)value).booleanValue();
			
			//For each other operand,
			for(int i=0;i<operators.size();i++){
				Object values=equalitys.get(i).evaluate(state);
				
				//if it's not a boolean value, throw a type error on it
				if(!(values instanceof Boolean)){
					throw new TypeError(equalitys.get(i).firstToken());
				}
				boolean bool1= ((Boolean) values).booleanValue();
				
				/*If at any point you encounter an operand that evaluates to false,
				 *  perform logical short-circuiting: 
				 * return false without evaluating the rest of the expressions
				 */
				if(bool1==false){
					bool=bool1;
					break;
				}
				
				//otherwise apply the boolean AND operation.
				bool=bool && bool1;
			}

		}
		
		//return the final value after all the operands have been processed
		return bool;

	}

	
	public Token firstToken() {
		
		return equality.firstToken();
	}

}
