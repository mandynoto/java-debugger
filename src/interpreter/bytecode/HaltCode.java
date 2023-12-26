/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

/**
 * Halts the execution of the program.
 *
 * @author mandynoto
 */
public class HaltCode extends ByteCode
{

	/**
	 * Intentionally does nothing since this ends the virtual machine.
	 */
	@Override
	public void init(ArrayList<String> byteCodeArgs)
	{
		// Intentionally left empty since this ends the virtual machine.
	}

	/**
	 * Ends the specified virtual machine, vm.
	 *
	 * @param vm the specified virtual machine.
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		vm.endProgram();
	}

	/**
	 * Returns the string representation of initializing this code.
	 */
	@Override
	public String toString()
	{
		return "HALT";
	}
}
