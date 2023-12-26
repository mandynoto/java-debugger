/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

/**
 * POP n: Pop top n levels of runtime stack
 *
 * @author mandynoto
 */
public class PopCode extends ByteCode
{
	// The top N levels of runtime stack.

	protected int runtimeStackTopNLevels;

	/**
	 * Assigns the first argument of the specified array list to
	 * runtimeStackTopNLevels.
	 *
	 * @param byteCodeArgs the specified array list.
	 */
	@Override
	public void init(ArrayList<String> byteCodeArgs)
	{
		runtimeStackTopNLevels = Integer.parseInt(byteCodeArgs.get(0));
	}

	/**
	 * POP n: Pop top n levels of runtime stack
	 *
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		vm.popTopNLevelsFromStack(runtimeStackTopNLevels);
	}

	/**
	 * Returns the string representation of initializing this code.
	 */
	@Override
	public String toString()
	{
		return "POP";
	}
}
