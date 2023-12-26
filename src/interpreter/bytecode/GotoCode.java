/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

/**
 * Goes to a specified label.
 *
 * @author mandynoto
 */
public class GotoCode extends ByteCode
{

	private String label;
	private String labelAddress;

	/**
	 * Assigns the first argument of the specified array list to labelAddress.
	 *
	 * @param byteCodeArgs the specified array list.
	 */
	@Override
	public void init(ArrayList<String> byteCodeArgs)
	{
		label = byteCodeArgs.get(0);
		labelAddress = label;

//		try 
//		{
//			Integer.parseInt(label);
//		}
//		catch (NumberFormatException e)
//		{
//            label = labelAddress;
//		}
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
	}

	/**
	 * Sets the location of which byte code to go to.
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		vm.changePC(Integer.parseInt(labelAddress));
	}

	/**
	 * Returns the string representation of initializing this code.
	 */
	@Override
	public String toString()
	{
		return String.format("GOTO %s<<%s>>", label, labelAddress);
	}
}
