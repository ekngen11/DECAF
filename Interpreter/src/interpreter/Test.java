package interpreter;

import java.util.HashMap;
import java.util.Scanner;

import parser.Program;

import tokenizer.SyntaxError;
import tokenizer.Tokenizer;

public class Test {

	private String code;
	
	public Test(String code){
		this.code=code;
	}
	
	public void run(){
		try {
			
			
			
			Scanner scanner= new Scanner(code);
			Tokenizer tokenizer = new Tokenizer(scanner);
			Program program= new Program(tokenizer);
			
			HashMap<String,Object> state=program.run();
			
			System.out.println("Final state: "+state);
			
		} 
		catch(SyntaxError e){
			System.out.println(e.getMessage());
			
		}catch(NameError e){
			System.out.println(e.getMessage());
			
		}
		catch(TypeError e){
			System.out.println(e.getMessage());
			
		}
		catch(DivisionError e){
			System.out.println(e.getMessage());
			
		}
		
		System.out.println();
	}
	public static void main(String[] args) {
		
		
		new Test("int x; x=x+1;print x;").run();  //prints 1 ---then --- Final state : [x=1]
		new Test("int x;while (x < 3) {x = x + 1; print x;}").run();
		new Test("float x; while(x<6){ if(x==3) { x=x+1;print x;} else{ x=x+2; print x;} }").run();
		new Test("int x; print x;").run();
		new Test("float y;while (y<10){ if(y<5) { y=y+1;print y;} else{ print 10;y=y+1;}}").run();;
		new Test("int x,y,z; x=20;y=30;z=50; x=x+y; y=y+z;z=z+x;").run();
		new Test("int x;if(x!=1){x=x+2.0;print x;}").run();
		new Test("int x;if(x==1){x=x+2.0;print x;}").run();
		
		//Testing for errors
		new Test("int x=0;").run();
		new Test("float x=2.0;").run();
		new Test("boolean bool= true;").run();
		new Test("while (true {print x;}").run();
		new Test("if(true){print }").run();
		new Test("2+2").run();
		
	}

}
