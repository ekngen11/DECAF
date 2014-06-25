package parser;

import java.util.Scanner;

import tokenizer.SyntaxError;
import tokenizer.Tokenizer;

public class Test {
	
	private String code;
	
	public Test(String code) {
		this.code = code;
	}
	
	public void run() {
		System.out.println("["+code+"]");
		
		try {
			Tokenizer tokenizer = new Tokenizer(new Scanner(code));
			Program program = new Program(tokenizer);
			System.out.println(program);
			
		}
		
		catch (SyntaxError e) {
			System.out.println(e.getMessage());
		}
		
	}

	public static void main(String[] args) {
		
		new Test("int x; print x;").run();
		new Test("while (x < 3) {x = x + 1;}").run();
		new Test("if(x<10){x=x*2;print x;}").run();
		new Test("while (true){ if(true) { x=x+1;print x;} else{ print 10;}}").run();;
		new Test("int x,y,z;").run();
		
		//Testing for errors
		new Test("int x=0;").run();
		new Test("float x=2.0;").run();
		new Test("boolean bool= true;").run();
		new Test("while (true {print x;}").run();
		new Test("if(true){print }").run();
		new Test("2+2").run();
		
	}
}
