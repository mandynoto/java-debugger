/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

/**
 * STORE n <id> - pop the top of the stack; store value into the offset n from
 * the start of the frame; <id> is used as a comment, it’s the variable name
 * where the data is stored
 *
 * @author mandynoto
 */
public class StoreCode extends ByteCode
{

	int offsetNumber;
	String varName;
	int topOfStack;

	/**
	 * Assigns the first and second argument of the specified array list to
	 * offsetNumber and varName, respectively.
	 */
	@Override
	public void init(ArrayList<String> byteCodeArgs)
	{
		offsetNumber = Integer.parseInt(byteCodeArgs.get(0));
		varName = byteCodeArgs.get(1);
	}

	/**
	 * STORE n <id> - pop the top of the stack; store value into the offset n
	 * from the start of the frame; <id> is used as a comment, it’s the variable
	 * name where the data is stored
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		vm.store(offsetNumber);
		topOfStack = vm.peekRunStack();
	}

	/**
	 * Returns the string representation of initializing this code.
	 */
	@Override
	public String toString()
	{
		String storeCodeInitializationStringRepresentation = "Store"
				+ " "
				+ offsetNumber
				+ " "
				+ varName
				+ "\t"
				+ varName
				+ " = "
				+ topOfStack;

		return storeCodeInitializationStringRepresentation;
	}
}
