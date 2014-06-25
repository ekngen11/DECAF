package tokenizer;

import java.util.ArrayList;
import java.util.Scanner;

public class Tokenizer {

	public final ArrayList<Token> tokenList;
	public int lineNumber=0;
	public int column;


	//Make list of tokens from all lines of the scanner
	//Throw a syntax error if there are any invalid tokens

	public Tokenizer (Scanner scanner){
		tokenList= new ArrayList<Token>();
		Token tok = null;


		//While there is a next line to read
		//Read the next line 
		while(scanner.hasNextLine()){

			String line= scanner.nextLine();
			lineNumber++;
			column=1;
			while (line.length()!=0){
				String text = null; 

				for (Token.Type typed : Token.Type.values()) {
					text = typed.getMatch(line);   //Compare the first token of the line to the tokens available

					if (text != null) {       //If the token for the first character is found, add it to arraylist
						tok=new Token(typed, text,lineNumber,column);
						if (!(tok.type.equals(Token.Type.COMMENT)) && !(tok.type.equals(Token.Type.WHITESPACE))){
							tokenList.add(tok);
						}
						column+=text.length();
						line= line.substring(text.length());   //Get rid of the found token from the beginning of the line.
						break;  							//Break out of the FOR loop

					}
				}
				if(text==null){  //If loop ends without finding parner token, throw a syntax error.
					throw new SyntaxError(lineNumber,column);
				}
			}
		}

	}

	//Does the list contain at least one token?

	public boolean hasNext(){
		return (tokenList.size()>0);

	}

	//Is the first token one of this types?
	public boolean hasNext(Token.Type ... types){
		if(tokenList.isEmpty()){
			return false;
		}
		for(Token.Type type: types){
			if(type.equals(tokenList.get(0).type)){
				return true;
			}

		}
		return  false;

	}

	//Consume and return the next token in the list.
	//Throw a syntax error if there are no tokens left.-- should point to the end of the code

	public Token next(){
		Token type=null;
		if(tokenList.size()>0){
			type= tokenList.get(0);
			tokenList.remove(0);
			return type;
		}
		throw new SyntaxError(lineNumber,column);

	}

	//Consume and return the next token in the list.
	//Throw a syntax error if there are no tokens left.
	public Token next(Token.Type ... types){

		if(tokenList.isEmpty()){
			throw new SyntaxError(tokenList.get(0));
		}
		Token tok = null;
		for(Token.Type type: types){
			if (type.equals(tokenList.get(0).type)){
				tok= tokenList.get(0);
				tokenList.remove(0);
				return tok;
			}

		}
		throw new SyntaxError(tokenList.get(0));
	}

}
