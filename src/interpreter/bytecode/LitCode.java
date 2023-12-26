/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

/**
 * Loads literal values which can optionally be initialized.
 *
 * @author mandynoto
 */
public class LitCode extends ByteCode
{

	protected int intArg;
	protected String stringArg;

	/**
	 * Assigns the first argument and second of the specified array list to
	 * intArg and stringArg, respectively and appropriately.
	 *
	 * @param byteCodeArgs the specified array list.
	 */
	@Override
	public void init(ArrayList<String> byteCodeArgs)
	{
		intArg = Integer.parseInt(byteCodeArgs.get(0));

		if (byteCodeArgs.size() > 1)
		{
			stringArg = byteCodeArgs.get(1);
		}
	}

	/**
	 * Loads literal values which can optionally be initialized.
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		Integer i = intArg;
		vm.lit(intArg);
	}

	/**
	 * Returns the string representation of initializing this code.
	 */
	@Override
	public String toString()
	{
		String litCodeInitializationStringRepresentation = "LIT " + intArg + " ";

		// Add on the optional string argument.
		if (stringArg != null)
		{
			litCodeInitializationStringRepresentation += "\t" + "int " + stringArg;
		}

		return litCodeInitializationStringRepresentation;
	}
}
