package interpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import parser.Program;
import tokenizer.SyntaxError;
import tokenizer.Tokenizer;

//Run Decaf Programs
public class Interpreter {

	public static void main(String[] args) {
		
		
		
		try {
			String fileName= args[0];
			
			File file= new File(fileName);
			Scanner scanner= new Scanner(file);
			Tokenizer tokenizer = new Tokenizer(scanner);
			Program program= new Program(tokenizer);
			program.run();
			
		} catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Missing argument: fileName");
			System.exit(0);
		}
		
		catch (FileNotFoundException e) {
			System.out.println("Error: file "+args[0]+" not found.");
			System.exit(0);
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
	}
}
