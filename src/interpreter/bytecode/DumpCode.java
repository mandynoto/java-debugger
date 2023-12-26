/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.ArrayList;

/**
 * Used to set dumping mode on or off for debugging information.
 *
 * @author mandynoto
 */
public class DumpCode extends ByteCode
{

	String arg;

	/**
	 * Assigns the first argument of the specified array list to arg.
	 *
	 * @byteCodeArgs the specified array list.
	 */
	public void init(ArrayList<String> byteCodeArgs)
	{
		arg = byteCodeArgs.get(0);
	}

	/**
	 * Sets the dumping mode in the specified virtual machine to either on or
	 * off.
	 *
	 * @param vm the specified virtual machine.
	 */
	public void execute(VirtualMachine vm)
	{
		if (arg.equals("ON"))
		{
			vm.setDumpMode(true);
		}
		if (arg.equals("OFF"))
		{
			vm.setDumpMode(false);
		}
	}

	/**
	 * Returns the string representation of initializing this code.
	 */
	@Override
	public String toString()
	{
		return String.format("DUMP %s", arg);
	}
}
