/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

/**
 * RETURN <funcname>; return from the current function;
 * <funcname> is used as a comment to indicate the current function.
 *
 * RETURN is generated for intrinsic functions
 *
 * @author mandynoto
 */
public class ReturnCode extends ByteCode
{

	// To know the name of the function of where we're returning to.
	private String functionName;
	private String previousFunctionNameAddress;
	// To know the location of the function where we are returning to.
	private int previousPC;
	private int functionExit;
	// The argument/s passed into the returning function.
	private ArrayList<String> functionArg;

	/**
	 * Assigns the first argument of the specified array list to functionName,
	 * appropriately.
	 *
	 * @param byteCodeArgs the specified array list.
	 */
	@Override
	public void init(ArrayList<String> byteCodeArgs)
	{
		if (!byteCodeArgs.isEmpty())
		{
			functionName = byteCodeArgs.get(0);
		}
	}

	/**
	 * Return from the current function by through the memory of the specified
	 * virtual machine, vm.
	 *
	 * @param vm the specified virtual machine.
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		vm.reset();
		previousPC = vm.getPreviousProgramCounter();
		previousFunctionNameAddress = vm.getProgramAddress(previousPC);
		functionExit = vm.peekRunStack();
		vm.changePC(previousPC);
		functionArg = vm.getArguments();
	}

	/**
	 * Returns the string representation of the function we are returning to.
	 */
	@Override
	public String toString()
	{
		String toReturn = String.format("RETURN ");

		// This means the function we're returnting to is intrinsic.
		if (functionName == null)
		{
			return toReturn;
		}

		// This means that we discovered an intrinsic function,
		// one that does not take any arguments.
		if (functionArg.isEmpty())
		{
			//toReturn += String.format("f");
			toReturn += String.format("%s", functionName);
			return toReturn;
		}

		toReturn += String.format("%s<<%s>>\texit: %d", functionName, previousFunctionNameAddress, functionExit);

		return toReturn;
	}
}
