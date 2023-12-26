/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter;

import java.util.HashMap;

/**
 * Holds and initializes the Hashtable that ByteCodeLoader uses to create
 * instances of the bytecode classes.
 *
 * @author mandynoto
 */
public class CodeTable extends Object
{
	// The key-value pair of a class name and its file absent of an extension.
	// From Source Reader page 133

	private static HashMap<String, String> codeTableHashMap;

	/**
	 * Initializes this CodeTable's hash map.
	 */
	public static void init()
	{
		codeTableHashMap = new HashMap<>();
		codeTableHashMap.put("ARGS", "ArgsCode");
		codeTableHashMap.put("BOP", "BopCode");
		codeTableHashMap.put("CALL", "CallCode");
		codeTableHashMap.put("Dump", "DumpCode");
		codeTableHashMap.put("FALSEBRANCH", "FalseBranchCode");
		codeTableHashMap.put("GOTO", "GotoCode");
		codeTableHashMap.put("HALT", "HaltCode");
		codeTableHashMap.put("LABEL", "LabelCode");
		codeTableHashMap.put("LIT", "LitCode");
		codeTableHashMap.put("LOAD", "LoadCode");
		codeTableHashMap.put("POP", "PopCode");
		codeTableHashMap.put("READ", "ReadCode");
		codeTableHashMap.put("RETURN", "ReturnCode");
		codeTableHashMap.put("STORE", "StoreCode");
		codeTableHashMap.put("WRITE", "WriteCode");
		codeTableHashMap.put("DUMP", "DumpCode");
		codeTableHashMap.put("FORMAL", "FormalCode");
		codeTableHashMap.put("FUNCTION", "FunctionCode");
		codeTableHashMap.put("LINE", "LineCode");
	}

	/**
	 * Returns the class name to which the specied code is mapped.
	 *
	 * @param	code the code whose associated clalss name is to be returned.
	 * @return the class name to which the specified code is mapped.
	 */
	public static String get(String code)
	{
		return codeTableHashMap.get(code);
	}
}
