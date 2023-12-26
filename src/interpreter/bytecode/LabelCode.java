/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

/**
 * Targets the branch labels.
 *
 * @author mandynoto
 */
public class LabelCode extends ByteCode
{

	private String label;
	private String labelAddress;
	private boolean hasLabelAddress;

	/**
	 * Assigns the first argument of the specified array list to frameNumber.
	 *
	 * @param byteCodeArgs the specified array list.
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
	 * Sets the label address of this goto byte code.
	 *
	 * @param labelAddress
	 */
	public void setLabelAddress(int labelAddress)
	{
		this.labelAddress = Integer.toString(labelAddress);
		hasLabelAddress = true;
	}

	/**
	 * Returns the address of this label code.
	 *
	 * @return the address of this label code.
	 */
	public String getAddress()
	{
		return labelAddress;
	}

	/**
	 * Determines if this code is associated with a label address and
	 * appropraitely sets it.
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		int currentAddress = vm.peekFrame();
		if (currentAddress == -1)
		{
			return;
		}

		hasLabelAddress = true;
		labelAddress = String.valueOf(vm.peekProgramCounter());
	}

	/**
	 * Returns the string representation of initializing this code.
	 */
	@Override
	public String toString()
	{
		String toReturn = String.format("LABEL %s", label);

		if (hasLabelAddress)
		{
			toReturn += String.format("<<%s>>", labelAddress);
		}
		hasLabelAddress = false;
		return toReturn;
	}
}
