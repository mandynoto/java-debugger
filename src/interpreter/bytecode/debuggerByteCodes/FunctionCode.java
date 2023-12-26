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
 * Indicates when a function begins.
 *
 * @author mandynoto
 */
public class FunctionCode extends ByteCode
{
	// The name of a function.

	private String functionName;
	// What line the function starts.
	private int startLine;
	// What line the function ends.
	private int endLine;

	/**
	 * Initializes this.
	 *
	 * @param byteCodeArgs
	 */
	@Override
	public void init(ArrayList<String> byteCodeArgs)
	{
		functionName = byteCodeArgs.get(0);
		startLine = Integer.parseInt(byteCodeArgs.get(1));
		endLine = Integer.parseInt(byteCodeArgs.get(2));
	}

	/**
	 * Executes this.
	 *
	 * @param vm
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		execute((DebugVM) vm);
	}

	/**
	 * Helper function to execute this as a debugging code.
	 *
	 * @param vm
	 */
	public void execute(DebugVM vm)
	{
		vm.addFunctionRecord(functionName, startLine, endLine);
		vm.setCurrentLine(startLine);
	}

	/**
	 * Returns the string representation of initializing this code.
	 *
	 * @return the string representation of initializing this code.
	 */
	@Override
	public String toString()
	{
		return String.format(functionName + " " + startLine + " " + endLine);
	}
}
