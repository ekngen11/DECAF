package parser;

import java.util.HashMap;

import tokenizer.Token;
import tokenizer.Tokenizer;

public abstract class Expression extends Symbol{

	//Expression-> Disjunction
	
	public static Expression getExpression(Tokenizer tokenizer){
		return new Disjunction(tokenizer);
	}
	
	public abstract Object evaluate(HashMap<String,Object> state);
	public abstract Token firstToken();
}
