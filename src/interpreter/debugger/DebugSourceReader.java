/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.debugger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to manage the source program input stream; each read request will return
 * the next usable character; it maintains the source column position of the
 * character.
 *
 * @author mandynoto
 *
 */
public class DebugSourceReader
{

	/**
	 * Singleton design pattern for managing construction and to therefore save
	 * space.
	 */
	private DebugSourceReader()
	{
		// Intentionally left empty.
	}

	/**
	 * Returns the parsed source code from the specified source file.
	 *
	 * @param codeFile the specified code file.
	 * @return the parsed source code from the specified code file.
	 *
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<DebugSourceLineMapEntry> load(String codeFile) throws FileNotFoundException, IOException
	{
		try ( BufferedReader programFile = new BufferedReader(new FileReader(codeFile)))
		{
			List<DebugSourceLineMapEntry> sourceCode = new ArrayList<>();

			while (programFile.ready())
			{
				sourceCode.add(new DebugSourceLineMapEntry(programFile.readLine(), false));
			}

			return sourceCode;
		}
	}

}
