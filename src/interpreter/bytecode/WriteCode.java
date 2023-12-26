/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

/**
 * WRITE ; Write the value on top of the stack to output; leave the value on top
 * of the stack
 *
 * @author mandynoto
 */
public class WriteCode extends ByteCode
{

	/**
	 * Intentionally does nothing.
	 */
	@Override
	public void init(ArrayList<String> byteCodeArgs)
	{
		// Intentionally does nothing.
	}

	/**
	 * WRITE ; Write the value on top of the stack to output; leave the value on
	 * top of the stack
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		int topOfStack = vm.peekRunStack();
		System.out.println(topOfStack);
	}

	/**
	 * Returns the string representation of initializing this code.
	 */
	@Override
	public String toString()
	{
		return "WRITE";
	}
}
