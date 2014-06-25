package tokenizer;

import java.util.*;

public class test {
	
	private String code;
	
	public test(String code){
		this.code=code;
		
	}
	
	public void run(){
		
		System.out.println("["+code+"]");
		Token tok = null;
		try{
			
			Tokenizer tokenizer= new Tokenizer(new Scanner (code));
			
			while(tokenizer.hasNext()){
				tok=tokenizer.next();
				System.out.println(tok);
			}
			//tokenizer.next();
			
			
			
		}catch(SyntaxError e){
			System.out.println(e.getMessage());
		}
	}
	
	
	public static void main(String[] args) {
		
		//Test for hasNext() and next() methods
		
		new test("; // ==").run();
		new test("blah").run();
		new test ("#Error").run();
		new test("x=x+1").run();
		new test("if(x==4){} else {x=x+2}").run();
		new test("while(!true)").run();
		new test("print x").run();
		new test("boolean x=false").run();
		new test("2.3*23.45 / 45.34-263").run();
		new test("2*4/4-45").run();
		new test("24.34%34").run();
		new test("while(myVar!=2)").run();
		new test("if(example>=2)").run();
		new test("if(example<=2)").run();
		new test("if(example>=2) && (x==true)").run();
		new test("if(example<=2) || (x==true)").run();
		
		
		/*
		 * Test for the hasNext(Token.Type ... types) and
		 *  next(Token.Type ... types) methods 
		 */
		Tokenizer tokenizer1= new Tokenizer(new Scanner ("; == || belbleh"));
		while(tokenizer1.hasNext(Token.Type.ASSIGN,Token.Type.IDENTIFIER,
				Token.Type.EQ,Token.Type.OR,Token.Type.SEMICOLON,Token.Type.FLOAT,Token.Type.WHITESPACE)){
			System.out.println("~~~~~~~~~~~~"+tokenizer1.next(Token.Type.ASSIGN,Token.Type.IDENTIFIER,
					Token.Type.EQ,Token.Type.OR,Token.Type.SEMICOLON,Token.Type.FLOAT,Token.Type.WHITESPACE));
		}
		
	}
}
