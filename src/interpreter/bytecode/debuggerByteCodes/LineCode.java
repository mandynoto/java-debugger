/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode.debuggerByteCodes;

import java.util.ArrayList;

import interpreter.VirtualMachine;
import interpreter.bytecode.ByteCode;
import interpreter.debugger.DebugVM;

/**
 * Indicates a new line in the source program.
 *
 * @author mandynoto
 */
public class LineCode extends ByteCode
{

	// The new line in the source program.
	private int srcCodeLineNumber;

	/**
	 * Initializes this via the specified arguments.
	 *
	 * @param byteCodeArgs the specified arguments.
	 */
	@Override
	public void init(ArrayList<String> byteCodeArgs)
	{
		srcCodeLineNumber = Integer.parseInt(byteCodeArgs.get(0));
	}

	/**
	 * Executes this via the virtual machine.
	 *
	 * @param vm the specified virtual machine.
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		execute((DebugVM) vm);
	}

	/**
	 * Helper function to execute through the specified virtual machine.
	 *
	 * @param vm the specified debugging virtual machine.
	 */
	public void execute(DebugVM vm)
	{
		vm.setCurrentLine(srcCodeLineNumber);
	}

	/**
	 * Returns the string representation of initializing this code.
	 *
	 * @return the string representation of initializing this code.
	 */
	@Override
	public String toString()
	{
		return "" + srcCodeLineNumber;
	}
}
