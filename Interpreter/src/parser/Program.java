package parser;

import java.util.ArrayList;
import java.util.HashMap;

import tokenizer.Token;
import tokenizer.Tokenizer;

public class Program extends Symbol {
	
	private ArrayList<Declaration> declarations = new ArrayList<Declaration>();
	private ArrayList<Statement> statements = new ArrayList<Statement>();
	
	// Program -> Declaration* Statement*
	public Program(Tokenizer tokenizer) {
		
		while (tokenizer.hasNext(Token.Type.INT, Token.Type.FLOAT, Token.Type.BOOLEAN)) {
			Declaration declaration = new Declaration(tokenizer);
			declarations.add(declaration);
		}
		
		while (tokenizer.hasNext()) {
			Statement statement = Statement.getStatement(tokenizer);
			statements.add(statement);
		}
	}

	public String toString() {
		String program = "";
		
		for (Declaration declaration: declarations)
			
			program += declaration.toString();
		
		for (Statement statement: statements)
			program += statement.toString();
		
		return program;
	}
	
	
	public HashMap<String,Object> run(){
		HashMap<String,Object> state= new HashMap<String,Object>();
		
		for (Declaration declaration: declarations){
			declaration.declare(state);
			
		}
		
		for (Statement statement: statements){
			//System.out.println(state);
			statement.execute(state);
		}
		
		return state;
	}
}
