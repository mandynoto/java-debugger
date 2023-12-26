/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * READ ; Read an integer; prompt the user for input; put the value just read on
 * top of the stack
 *
 * @author mandynoto
 */
public class ReadCode extends ByteCode
{

	/**
	 * Intentionally does nothing.
	 */
	@Override
	public void init(ArrayList<String> byteCodeArgs)
	{
		// Intentionally does nothing.
	}

	/**
	 * READ ; Read an integer; prompt the user for input; put the value just
	 * read on top of the stack
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		Scanner scanner = new Scanner(System.in);
		System.out.print("Input an integer: ");
		int intValue = scanner.nextInt();

		vm.pushRunStack(intValue);
	}

	/**
	 * Returns the string representation of initializing this code.
	 */
	@Override
	public String toString()
	{
		return "READ";
	}
}
