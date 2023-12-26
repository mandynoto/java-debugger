package interpreter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import interpreter.bytecode.ByteCode;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Constructs new instances of bytecodes from a file.
 *
 * @author mandynoto
 */
public class ByteCodeLoader
{

	private final BufferedReader bufferedReader;
	private Program program;
	// The container for each byte code argument.
	ArrayList<String> byteCodeArgs;

	/**
	 * Takes in a new specified program file to initialize scanner filled with
	 * code.
	 *
	 * @param programFile
	 * @throws IOException
	 */
	public ByteCodeLoader(String programFile) throws IOException
	{
		// Read the file ...
		FileReader fileReader = new FileReader(programFile);
		// ... and pass it into buffered reader.
		this.bufferedReader = new BufferedReader(fileReader);
	}

	/**
	 * Returns a program with parsed source code.
	 *
	 * @return a program with parsed source code.
	 */
	public Program loadCodes()
	{
		String line;
		program = new Program();

		try
		{
			while ((line = bufferedReader.readLine()) != null)
			{
				try
				{
					StringTokenizer str = new StringTokenizer(line);
					String token = str.nextToken();

					String[] code = line.split("\\s", 2);

					String codeClass = "interpreter.bytecode." + getCodeClass(code[0]);

					ByteCode byteCode = (ByteCode) (Class.forName(codeClass).newInstance());

					// Create an arrayList for this this byte code and its argument/s.
					byteCodeArgs = new ArrayList<>();
					while (str.hasMoreTokens())
					{
						byteCodeArgs.add(str.nextToken());
					}
					
					// Initialize the byte code arguments via dynamic binding.
					byteCode.init(byteCodeArgs);
					program.add(byteCode);
				} catch (Exception e)
				{
					continue;
				}

			}

			program.resolveAddress(program);
		} catch (IOException e)
		{
			e.printStackTrace(System.out);
		}

		return program;
	}

	/**
	 * Returns the class name of a specified bytecode.
	 *
	 * Note: These other bytecodes can be for debugging.
	 *
	 * @param	code the specified bytecode.
	 * @return	the class name of a specified bytecode.
	 */
	protected String getCodeClass(String code)
	{
		return CodeTable.get(code);
	}
}
