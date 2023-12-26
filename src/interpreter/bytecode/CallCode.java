/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

/**
 * CALL <funcname> - transfer control to the indicated function
 *
 * @author mandynoto
 */
public class CallCode extends ByteCode
{

	// The name of the function.
	private String functionName;
	// The address of the function.
	private String functionNameAddress;
	// The argument/s passed into the function.
	private ArrayList<String> functionArg;

	/**
	 * Assigns the first argument of the specified byteCodeArgs to
	 * functionNameAddress.
	 */
	@Override
	public void init(ArrayList<String> byteCodeArgs)
	{
		functionName = byteCodeArgs.get(0);
		functionNameAddress = functionName;
	}

	/**
	 * Gets the name of the function of this call code.
	 *
	 * @return the name of the function of this call code.
	 */
	public String getFunctionNameAddress()
	{
		return functionNameAddress;
	}

	public void setFunctionNameAddress(int i)
	{
		functionNameAddress = Integer.toString(i);
	}

	/**
	 * Changes the specified virtual machine's program counter to this one.
	 *
	 * @param vm the specified virtual machine.
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		vm.savePC();
		vm.changePC(Integer.parseInt(functionNameAddress));
		functionArg = vm.getArguments();
	}

	/**
	 * Returns the string representation of the address of the function we're
	 * returning to.
	 */
	@Override
	public String toString()
	{
		String toReturn = String.format("CALL ");
		// This means that we discovered an intrinsic function,
		// one that does not take any arguments.
		if (functionArg.isEmpty())
		{
			toReturn += String.format("%s", functionName);
			return toReturn;
		}

		toReturn += String.format("%s<<%s>>\t", functionName, functionNameAddress);
		toReturn += String.format("%s(", functionName);
		for (int i = 0; i < functionArg.size(); i++)
		{
			toReturn += functionArg.get(i);
			if ((functionArg.size() > 1) && (i != functionArg.size() - 1))
			{
				toReturn += ",";
			}
		}

		functionArg.clear();
		toReturn += ")";

		return toReturn;
	}
}
