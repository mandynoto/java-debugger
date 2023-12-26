/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

/**
 * LOAD n <id>; push the value in the slot which is offset n from the start of
 * the frame onto the top of the stack; <id> is used as a comment, it’s the
 * variable name from which the data is loaded
 *
 * @author mandynoto
 */
public class LoadCode extends ByteCode
{
	// The offset number from the start of the frame.

	private int offsetNumber;
	// The variable name from which the data is loaded.
	private String varNameFromLoadedData;

	/**
	 * Assigns the first argument and second of the specified array list to
	 * offsetNumber and varNameFromLoadedData, respectively.
	 *
	 * @param byteCodeArgs the specified array list.
	 */
	@Override
	public void init(ArrayList<String> byteCodeArgs)
	{
		offsetNumber = Integer.parseInt(byteCodeArgs.get(0));
		varNameFromLoadedData = byteCodeArgs.get(1);
	}

	/**
	 * LOAD n <id>; push the value in the slot which is offset n from the start
	 * of the frame onto the top of the stack; <id> is used as a comment, it’s
	 * the variable name from which the data is loaded
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		vm.load(offsetNumber);
	}

	/**
	 * Returns the string representation of initializing this code.
	 */
	@Override
	public String toString()
	{
		String loadCodeInitializationStringRepresentation = "LOAD " + offsetNumber + " ";
		if (varNameFromLoadedData != null)
		{
			loadCodeInitializationStringRepresentation += varNameFromLoadedData
					+ "\t"
					+ "<load "
					+ varNameFromLoadedData
					+ ">";
		}

		return loadCodeInitializationStringRepresentation;
	}
}
