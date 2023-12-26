/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

/**
 * Sets up a new frame.
 *
 * @author mandynoto
 */
public class ArgsCode extends ByteCode
{
	// The number of arguments.

	private int argCount;

	/**
	 * Assigns the first argument of the specified array list to argCount to
	 * create a new frame.
	 *
	 * @param byteCodeArgs the specified array list.
	 */
	@Override
	public void init(ArrayList<String> byteCodeArgs)
	{
		argCount = Integer.parseInt(byteCodeArgs.get(0));
	}

	/**
	 * Creates a new frame with a value assigned, argCount, down from the
	 * current frame.
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		vm.newFrameAt(argCount);
	}

	/**
	 * Returns the string representation of initializing this code.
	 */
	@Override
	public String toString()
	{
		return "ARGS " + argCount;
	}
}
