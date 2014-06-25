package tokenizer;

public class SyntaxError extends RuntimeException{

	public SyntaxError(int line, int column){
		super("Syntax error: line" +line+ ", column"+column);
	}
	
	public SyntaxError(Token token){
		this(token.line,token.column);
	}

}
