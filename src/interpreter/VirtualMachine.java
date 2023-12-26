/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter;

import interpreter.bytecode.ByteCode;
import interpreter.bytecode.CallCode;
import interpreter.bytecode.ReadCode;

import java.util.Stack;
import java.util.ArrayList;

/**
 * Maintains the state of the running program by executing each byte code that
 * is loaded into the program. It keeps track of the current position in the
 * program; also, it holds a reference to the run time stack.
 *
 * @author mandynoto
 */
public class VirtualMachine
{

	protected RunTimeStack runStack;

	// The program counter, i.e. the bytecode index.
	protected int pc;

	// Push/pop when call/return functions.
	// Used to know the location to return to from a function.
	protected Stack<Integer> returnAddrs;

	// True while the runStack is dumping for debugging; set by DumpCode.
	private boolean isDumping = false;

	// True if isDumping was previously true.
	private boolean wasDumping = false;

	// True while the VM is running.
	protected boolean isRunning;

	// The bytecode program.
	protected Program program;

	/**
	 * Constructs a virtual machine with the specified program.
	 *
	 * @param program the specified program.
	 */
	public VirtualMachine(Program program)
	{
		this.program = program;
	}

	/**
	 * Executes this program.
	 */
	public void executeProgram()
	{
		pc = 0;
		runStack = new RunTimeStack();
		returnAddrs = new Stack();
		isRunning = true;

		while (isRunning)
		{
			ByteCode code = program.getCode(pc);
			code.execute(this);

			// If dumping mode was turned on by a DumpCode bytecode ...
			if (isDumping || wasDumping)
			{
				// ... Print out runtime stack and frame information to debug results.
				System.out.printf("%s", code);

				System.out.println();

				runStack.dump();
				System.out.println();
				if (!isDumping)
				{
					wasDumping = false;
				}
			}

			if (!(code instanceof CallCode))
			{
				pc += 1;
			}
		}
	}

	/**
	 * Determines whether to print out dumping information based on the
	 * specified Boolean value.
	 *
	 * @param bool the specified Boolean value.
	 */
	public void setDumpMode(boolean bool)
	{
		isDumping = bool;
		if (bool == true)
		{
			wasDumping = bool;
		}
	}

	/**
	 * Saves the current program counter to recall where continue from calling a
	 * function.
	 */
	public void savePC()
	{
		returnAddrs.push(pc);
	}

	/**
	 * Returns the previous program counter, to continue from where a function
	 * was called.
	 */
	public int getPreviousProgramCounter()
	{
		return returnAddrs.pop();
	}

	/**
	 * Returns the program address associated with the specified pc.
	 *
	 * @param pc the specified pc, the program counter.
	 * @return the program address of the specified pc.
	 */
	public String getProgramAddress(int pc)
	{
		CallCode callCode = (CallCode) program.getCode(pc);
		return callCode.getFunctionNameAddress();
	}

	/**
	 * Returns the current program counter.
	 *
	 * @return the current program counter.
	 */
	public int peekProgramCounter()
	{
		return pc;
	}

	/**
	 * Returns {@code true} if the return address stack is not empty.
	 *
	 * @return {@code true} if the returna ddress stack is not empty.
	 */
	public boolean hasPreviousProgramCounter()
	{
		return !returnAddrs.isEmpty();
	}

	public int peekPreviousProgramCounter()
	{
		if (!hasPreviousProgramCounter())
		{
			return -1;
		}

		return returnAddrs.peek();
	}

	/**
	 * Stops the execution of this virtual machine.
	 */
	public void endProgram()
	{
		isRunning = false;
	}

	/**
	 * Changes the program counter to the specified value, n.
	 *
	 * @param n the specified program counter value.
	 */
	public void changePC(int n)
	{
		pc = n;
	}

	/**
	 * ***********************************************
	 * Delegated runStack methods; See RunTimeStack.java for details on their
	 * functionality.
	 ***********************************************
	 */
	/**
	 * Pops the top n levels from the top of the stack.
	 *
	 * @param n the specified levels.
	 */
	public void popTopNLevelsFromStack(int n)
	{
		if (n < runStack.size())
		{
			while (n > 0)
			{
				runStack.pop();
				n -= 1;
			}
		}
	}

	/**
	 * Returns the function arguments for the current function.
	 */
	public ArrayList<String> getArguments()
	{
		return runStack.getArguments();
	}

	public int popRunStack()
	{
		return runStack.pop();
	}

	public void newFrameAt(int offset)
	{
		runStack.newFrameAt(offset);
	}

	public int peekFrame()
	{
		return runStack.peekFrame();
	}

	public int peekRunStack()
	{
		return runStack.peek();
	}

	public void pushRunStack(int n)
	{
		runStack.push(n);
	}

	public void store(int n)
	{
		runStack.store(n);
	}

	public void load(int n)
	{
		runStack.load(n);
	}

	public void lit(Integer i)
	{
		runStack.push(i);
	}

	public void reset()
	{
		runStack.reset();
	}

	public int runStackSize()
	{
		return runStack.size();
	}
}
