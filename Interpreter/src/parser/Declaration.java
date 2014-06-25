package parser;

import interpreter.NameError;

import java.util.ArrayList;
import java.util.HashMap;

import tokenizer.Token;
import tokenizer.Tokenizer;

//Declaration -> (INT|FLOAT|BOOLEAN) IDENTIFIER (COMMA IDENTIFIER)* SEMICOLON

public class Declaration extends Symbol{

	private Token firstToken;
	private Token identifier;
	private ArrayList<Token> identifiers= new ArrayList<Token>();
	private ArrayList<Token> operators= new ArrayList<Token>();
	private Token semicolon;

	public Declaration (Tokenizer tokenizer){

		
		firstToken=tokenizer.next(Token.Type.INT,Token.Type.FLOAT,Token.Type.BOOLEAN);
		

		identifier=tokenizer.next(Token.Type.IDENTIFIER);

		while(tokenizer.hasNext(Token.Type.COMMA)){
			operators.add(tokenizer.next(Token.Type.COMMA));
			identifiers.add(tokenizer.next(Token.Type.IDENTIFIER));
		}

		semicolon= tokenizer.next(Token.Type.SEMICOLON);


	}
	
	

	public void declare(HashMap<String,Object> state){

		declare(identifier, state);
		for (Token id: identifiers)
			declare(id,state);
	}

	private void declare(Token id,HashMap<String,Object> state){
		String name=id.text;
		if (state.containsKey(name)){
			throw new NameError(id);
		}
		if(firstToken.type==Token.Type.INT)
			state.put(name,Integer.valueOf(0));
		else if(firstToken.type==Token.Type.FLOAT)
			state.put(name,Float.valueOf(0));
		else
			state.put(name,Boolean.valueOf(false));
	}
	


	public String toString() {
		String s= firstToken.text +" "+identifier.text;

		for(int i=0;i<identifiers.size();i++){
			s+=operators.get(i).text+" "+identifiers.get(i).text;
		}

		s+=semicolon.text;
		return s+"\n";
	}

}
