/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;

import java.util.ArrayList;

/**
 * POP n: Pop top n levels of run time stack
 *
 * @author mandynoto
 */
public class BopCode extends ByteCode
{

	private String operator;

	/**
	 * Assigns the first argument of the specified array list to operator.
	 *
	 * @param byteCodeArgs the specified array list.
	 */
	@Override
	public void init(ArrayList<String> byteCodeArgs)
	{
		operator = byteCodeArgs.get(0);
	}

	/**
	 * Pops two items off the stack and performs the operator on them.
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		int argOne = vm.popRunStack();
		int argTwo = vm.popRunStack();

		switch (operator)
		{
			case "+":
				vm.pushRunStack(argOne + argTwo);
				break;
			case "*":
				vm.pushRunStack(argTwo * argOne);
				break;
			case "-":
				vm.pushRunStack(argTwo - argOne);
				break;
			case "/":
				vm.pushRunStack(argTwo / argOne);
				break;
			case "==":
				if (argTwo == argOne)
				{
					vm.pushRunStack(1);
				} else
				{
					vm.pushRunStack(0);
				}
				break;
			case "!=":
				if (argTwo != argOne)
				{
					vm.pushRunStack(1);
				} else
				{
					vm.pushRunStack(0);
				}
				break;
			case "<=":
				if (argTwo <= argOne)
				{
					vm.pushRunStack(1);
				} else
				{
					vm.pushRunStack(0);
				}
				break;
			case ">":
				if (argTwo > argOne)
				{
					vm.pushRunStack(1);
				} else
				{
					vm.pushRunStack(0);
				}
				break;
			case ">=":
				if (argTwo >= argOne)
				{
					vm.pushRunStack(1);
				} else
				{
					vm.pushRunStack(0);
				}
				break;
			case "<":
				if (argTwo < argOne)
				{
					vm.pushRunStack(1);
				} else
				{
					vm.pushRunStack(0);
				}
				break;
			case "|":
				if (argTwo == 1 || argOne == 1)
				{
					vm.pushRunStack(1);
				} else
				{
					vm.pushRunStack(0);
				}
				break;
			case "&":
				if (argTwo == 1 && argOne == 1)
				{
					vm.pushRunStack(1);
				} else
				{
					vm.pushRunStack(0);
				}
				break;
			default:
				break;
		}
	}

	/**
	 * Returns the string representation of initializing this code.
	 */
	@Override
	public String toString()
	{
		return String.format("BOP %s", operator);
	}
}
