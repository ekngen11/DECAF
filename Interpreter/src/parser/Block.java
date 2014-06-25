package parser;

import java.util.ArrayList;
import java.util.HashMap;

import tokenizer.Token;
import tokenizer.Tokenizer;

//Block -> LBRACE Statement* RBRACE
public class Block extends Statement{
	
	private Token lBrace,rBrace;
	ArrayList<Statement> statements= new ArrayList<Statement>();
	
	public Block(Tokenizer tokenizer){
		
		lBrace=tokenizer.next(Token.Type.LBRACE);
		while(!tokenizer.hasNext(Token.Type.RBRACE)){
			statements.add(Statement.getStatement(tokenizer));
		}
		
		rBrace=tokenizer.next(Token.Type.RBRACE);
	}

	
	public String toString() {
		String s="";
		s+=lBrace.text+"\n";
		for(int i=0;i<statements.size();i++){
			s+=statements.get(i).toString();
		}
		return s+"\n"+rBrace.text;
	}


	
	public void execute(HashMap<String, Object> state) {
		
		//Execute each statement in the block
		for(int i=0;i<statements.size();i++){
			statements.get(i).execute(state);
		}
	}
	
	

}
