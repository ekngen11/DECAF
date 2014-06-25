package parser;

import java.util.HashMap;

import tokenizer.Token;
import tokenizer.Tokenizer;

public abstract class Statement extends Symbol {

	//Statement -> whileStament | ifStatement | PrintStatement
	
	public static Statement getStatement(Tokenizer tokenizer){
		
		if(tokenizer.hasNext(Token.Type.WHILE)){
			return new WhileStatement (tokenizer);
		}
		
		else if(tokenizer.hasNext(Token.Type.IF)){
			return new IfStatement (tokenizer);
		}
		
		else if(tokenizer.hasNext(Token.Type.PRINT)){
			return new PrintStatement (tokenizer);
		}
		
		else if(tokenizer.hasNext(Token.Type.IDENTIFIER)){
			return new Assignment (tokenizer);
		}
		else
			return new Block(tokenizer);
		
		
	}
	public abstract void execute(HashMap<String,Object> state);

}
