package applications;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import asmCodeGenerator.codeStorage.ASMCodeFragment;
import tokens.Tokens;

public class PikaStandaloneOptimizer extends PikaApplication {
	/** Optimizes an ASM file generated by a Pika compiler.
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		checkArguments(args, className());
		
		Tokens.setPrintLevel(Tokens.Level.FULL);
		optimize(args[0]);
	}
	
	/** analyzes a file specified by filename.
	 * @param filename the name of the file to be analyzed.
	 * @throws FileNotFoundException 
	 */
	public static void optimize(String filename) throws FileNotFoundException {
		ASMCodeFragment fragment = ASMCodeFragment.readFrom(filename);

		writeCodeIfNoErrors(filename, fragment);
	}

	private static void writeCodeIfNoErrors(String filename, ASMCodeFragment fragment)
			throws FileNotFoundException {
		String outfile = filename + "o";		// foo.asm -> foo.asmo
		//String outfile = filename;		// foo.asm -> foo.asmo
		
		if(thereAreErrors()) {
			stopProcessing(outfile);
			System.exit(1);
		} 
		else {
			printCodeToFile(outfile, fragment);
		}
	}

	// stopProcessing -- inform user and clean up.
	private static void stopProcessing(String outfile) {
		informUserNoCodeGenerated();
		removeOldASMOFile(outfile);
	}
	private static void informUserNoCodeGenerated() {
		System.err.println("optimizer has errors.  no executable created.");
	}
	private static void removeOldASMOFile(String filename) {
		File file = new File(filename);
		if(file.exists()) {
			file.delete();
		}
	}
	
	private static void printCodeToFile(String filename, ASMCodeFragment code)
			throws FileNotFoundException {
		File file = new File(filename);
		PrintStream out = new PrintStream(file);
		out.print(code);
		out.close();
	}

	private static boolean thereAreErrors() {
		return logging.PikaLogger.hasErrors();
	}
}
