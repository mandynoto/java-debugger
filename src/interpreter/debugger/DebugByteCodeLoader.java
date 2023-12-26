/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.debugger;

import interpreter.ByteCodeLoader;
import interpreter.CodeTable;
import java.io.IOException;

/**
 * Enables byte code loader to identify debugging byte codes.
 *
 * @author mandynoto
 */
public class DebugByteCodeLoader extends ByteCodeLoader
{

	/**
	 * Creates a byte code loader for recognizing debugging codes based on how
	 * this program was compiled via the specified program path , i.e, with a d
	 * flag.
	 *
	 * @param programPath the specified program path.
	 * 
	 * @throws IOException
	 */
	public DebugByteCodeLoader(String programPath) throws IOException
	{
		super(programPath);
	}

	/**
	 * Returns the debugger byte code mapping based on the specified code name.
	 *
	 * @param code the specified code name
	 * @return the debugger byte code based on the specified code name.
	 */
	@Override
	protected String getCodeClass(String code)
	{
		if (code.matches("FORMAL|FUNCTION|LINE|LIT|POP|RETURN"))
		{
			return "debuggerByteCodes." + CodeTable.get(code);
		} else
		{
			return CodeTable.get(code);
		}
	}

}
