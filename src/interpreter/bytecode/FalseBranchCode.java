/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

/**
 * FALSEBRANCH <label> - pop the top of the stack; if it's false (0) then branch
 * to <label> else execute the next byte code
 *
 * @author mandynoto
 */
public class FalseBranchCode extends ByteCode
{

	private String label;
	private String labelAddress;

	/**
	 * Assigns the first argument of the specified array list to label.
	 *
	 * @byteCodeArgs the specified array list.
	 */
	@Override
	public void init(ArrayList<String> byteCodeArgs)
	{
		label = byteCodeArgs.get(0);
		labelAddress = label;
	}

	/**
	 * Returns the label address of this false branch code.
	 *
	 * @return
	 */
	public String getLabelAddress()
	{
		return labelAddress;
	}

	/**
	 * Sets the label address of this false branch byte code.
	 *
	 * @param labelAddress
	 */
	public void setLabelAddress(int labelAddress)
	{
		this.labelAddress = Integer.toString(labelAddress);
	}

	/**
	 * Pops the top of the stack and goes to the labelAddress if the current
	 * stack value is 0; otherwise proceeds to the next bytecode.
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		int top = vm.popRunStack();

		if (top == 0)
		{
			vm.changePC(Integer.parseInt(labelAddress));
		}
	}

	/**
	 * Returns the string representation of initializing this code.
	 */
	@Override
	public String toString()
	{
		return String.format("FALSEBRANCH %s<<%s>>", label, labelAddress);
	}
}
