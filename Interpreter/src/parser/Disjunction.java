package parser;

import interpreter.TypeError;

import java.util.ArrayList;
import java.util.HashMap;

import tokenizer.Token;
import tokenizer.Tokenizer;

public class Disjunction extends Expression {

	private Conjunction conjunction;
	private ArrayList<Token> operators = new ArrayList<Token>();
	private ArrayList<Conjunction> conjunctions = new ArrayList<Conjunction>();

	// Disjunction -> Conjunction (OR Conjunction)*
	public Disjunction(Tokenizer tokenizer) {
		conjunction = new Conjunction(tokenizer);
		while (tokenizer.hasNext(Token.Type.OR)) {
			operators.add(tokenizer.next(Token.Type.OR));
			conjunctions.add(new Conjunction(tokenizer));
		}
	}

	public String toString() {
		String s = conjunction.toString();
		for (int i=0; i<operators.size(); i++)
			s+= operators.get(i).text+" "+conjunctions.get(i).toString();
		return s;
	}


	public Object evaluate(HashMap<String, Object> state) {
		//Evaluate the left operand
		Object value = conjunction.evaluate(state);
		
		//If there's only one operand, return its value
		if(operators.size()==0){
			
			return value;
		}

		Boolean bool=null;
		
		//if it's not a boolean value, throw a type error on it
		if(!(value instanceof Boolean)){
			throw new TypeError(conjunction.firstToken());
		}
		
		else{
			bool=((Boolean)value).booleanValue();
			
			//For each other operand,
			for(int i=0;i<operators.size();i++){
				Object values=conjunctions.get(i).evaluate(state);
				
				// if it's not a boolean value, throw a type error on it
				if(!(values instanceof Boolean)){
					throw new TypeError(conjunctions.get(i).firstToken());
				}

				boolean bool1= ((Boolean) values).booleanValue();
				
				//If at any point you encounter an operand that evaluates to true, 
				//perform logical short-circuiting
				if(bool1==true){
					bool=bool1;
					break;
				}
				
				//otherwise apply the boolean OR operation.
				bool= (bool || bool1);


			}
		}
		
		//Otherwise, return the final value after 
		return bool;
	}



	public Token firstToken() {

		return conjunction.firstToken();
	}
}