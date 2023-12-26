/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode.debuggerByteCodes;

import interpreter.bytecode.ByteCode;

import java.util.ArrayList;

import interpreter.VirtualMachine;
import interpreter.debugger.DebugVM;

/**
 * Indicates a function's argument and its run time stack offset; For example,
 * the 'x' in f(int x).
 *
 * @author mandynoto
 */
public class FormalCode extends ByteCode
{

	// The pointer to the value, e.g. 'x' in int x.
	private String label;
	// The offset of this label from the runtime stack.
	private int offsetNumber;

	/**
	 * Assigns label and its offset via the specified byte code arguments.
	 *
	 * @param byteCodeArgs the specified byte code arguments.
	 */
	@Override
	public void init(ArrayList<String> byteCodeArgs)
	{
		label = byteCodeArgs.get(0);
		offsetNumber = Integer.parseInt(byteCodeArgs.get(1));
	}

	/**
	 * Executes
	 *
	 * @param vm
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		execute((DebugVM) vm);
	}

	/**
	 * Adds the data this contains into the specified debugging virtual machine.
	 *
	 * @param vm the specified debugging virtual machine.
	 */
	public void execute(DebugVM vm)
	{
		vm.addRecordEntry(label, offsetNumber + vm.runStackSize() - 1);
	}

	/**
	 * Returns the string representation of initializing this code.
	 *
	 * @return the string representation of initializing this code.
	 */
	@Override
	public String toString()
	{
		return String.format(label + " " + offsetNumber);
	}
}
