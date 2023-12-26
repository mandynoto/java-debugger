/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode;

import java.util.ArrayList;

import interpreter.VirtualMachine;

/**
 * FALSEBRANCH <label> - pop the top of the stack; if it's false (0) then branch
 * to <label> else execute the next byte code
 *
 * @author mandynoto
 */
public abstract class ByteCode
{

	/**
	 * Executes the specified virtual machine.
	 *
	 * @param vm the specified virtual machine.
	 */
	public abstract void execute(VirtualMachine vm);

	/**
	 * Initializes the contents of the specified array list of byte code
	 * arguments.
	 *
	 * @param byteCodeArgs the specified array list of byte code arguments.
	 */
	public abstract void init(ArrayList<String> byteCodeArgs);

	/**
	 * Returns the name of the ByteCode.
	 *
	 * @return the of the ByteCode.
	 */
	public String getName()
	{
		String codeName = this.getClass().getName().replaceFirst("interpreter.bytecode.", "").replaceAll("Code", "");
		if (codeName.startsWith("debugger"))
		{
			codeName = codeName.replaceFirst("debuggerBytes.", "");
		}

		return codeName.toUpperCase();
	}
}
