/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package interpreter;

import interpreter.debugger.DebugByteCodeLoader;
import interpreter.debugger.DebugVM;
import interpreter.debugger.DebugSourceReader;
import interpreter.debugger.DebugSourceLineMapEntry;
import interpreter.debugger.ui.DebugConsoleUI;
import java.io.IOException;
import java.util.List;

/**
 * <pre>
 *
 *
 *
 * Interpreter class runs the interpreter:
 *
 * 1. Perform all initializations
 *
 * 2. Load the bytecodes from file
 *
 * 3. Run the virtual machine
 *
 *
 *
 * </pre>
 */
/**
 *
 * @author mandynoto
 */
public class Interpreter
{

	// True if there the debugging flag '-d' is set.
	private Boolean isDebugging = false;
	ByteCodeLoader bcl;
	// The list of code lines mapped to whether they have a break point or not.
	private List<DebugSourceLineMapEntry> sourceCode;

	/**
	 * Constructor for printing out byte codes of the specified code file.
	 *
	 * @param codeFile the specified code file.
	 */
	public Interpreter(String codeFile)
	{
		try
		{
			CodeTable.init();
			bcl = new ByteCodeLoader(codeFile);
		} catch (IOException e)
		{
			System.out.println("**** " + e);
		}
	}

	/**
	 * Constructor for debugging the specified code file when the specified
	 * debugging flag is set.
	 *
	 * Note: Assumes that the code file has a .x and a .x.cod created.
	 *
	 * @param baseSourceFileName the specified code file.
	 * @param isDebugging the specified debugging flag.
	 */
	public Interpreter(String baseSourceFileName, Boolean isDebugging)
	{
		this.isDebugging = isDebugging;

		try
		{
			String sourceFile;
			String codeFile = "";

			CodeTable.init();
			if (isDebugging)
			{
				// Get the source and code files.
				sourceFile = baseSourceFileName + ".x";
				codeFile = baseSourceFileName + ".x.cod";

				bcl = new DebugByteCodeLoader(codeFile);
				sourceCode = DebugSourceReader.load(sourceFile);
				System.out.printf("****Debugging %s****\n\n", sourceFile);
				return;
			}

			bcl = new ByteCodeLoader(codeFile);
		} catch (IOException e)
		{
			System.out.println("*** Could not find the proper files " + e);
			System.out.printf("Make sure there is a '%s.x' and a '%s.x.cod' file in your directory\n", baseSourceFileName, baseSourceFileName);
		}
	}

	void run()
	{
		Program program = bcl.loadCodes();
		VirtualMachine vm;

		// Run to print byte codes if we're not debugging, otherwise ...
		if (!isDebugging)
		{
			vm = new VirtualMachine(program);
			vm.executeProgram();
			return;
		}

		// ... activate debugging functionalities.
		vm = new DebugVM(program, sourceCode);
		DebugConsoleUI.showPrompt((DebugVM) vm);
	}

	/**
	 * Constructs a new interpreter based on the specified command line
	 * argument.
	 *
	 * @param args the specified command line arguments.
	 */
	public static void main(String[] args)
	{
		if (args.length == 0)
		{
			System.out.println("***Incorrect usage, try: java interpreter.Interpreter <file>");

			System.exit(1);
		}

		Interpreter interpreter;

		// Decide which overloaded constructor to use: debug or normal and ...
		if (args[0].equals("-d"))
		{
			// Go in debugging mode.
			interpreter = new Interpreter(args[1], true);
		} else
		{
			// Just view byte codes.
			interpreter = new Interpreter(args[0]);
		}

		// ... then run this interpreter.
		interpreter.run();
	}

}
