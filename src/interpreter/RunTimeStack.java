/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter;

import java.util.ArrayList;
import java.util.Stack;

/**
 * ArrayList implementation of the run time stack, which records and processes
 * the stack of active frames.
 *
 * @author mandynoto
 */
public class RunTimeStack
{

	// A stack of frames needed to access all locations in a current frame.
	private final ArrayList<Integer> runTimeStack;

	// The pointer to where the current frame in memory begins.
	// It's used to record prior frame numbers when calling functions.
	private final Stack<Integer> framePointer;

	/**
	 * Constructs a RunTimeStack.
	 */
	public RunTimeStack()
	{
		runTimeStack = new ArrayList<>();
		framePointer = new Stack<>();

		framePointer.add(0);
	}

	/**
	 * Dumps the information of this run-time stack, i.e. elements of each
	 * frame, for debugging.
	 */
	public void dump()
	{ // Start dump

		int nonMainFramesIndex = 0;
		int runTimeStackSize = runTimeStack.size();
		boolean hasComma = true;

		// Leave this method if runtime stack and framePointer are empty.
		if (runTimeStack.isEmpty() && framePointer.isEmpty())
		{
			return;
		}

		// Record non-main frames from framePointer for analysis ...
		// ... to record the index of where they begin to represent with separate brackets.
		ArrayList<Integer> nonMainFrames = new ArrayList<>();
		for (int i = 0; i < framePointer.size(); i++)
		{
			int frame = framePointer.get(i);
			if (frame > 0)
			{
				nonMainFrames.add(frame);
			}

		}

		System.out.print("[");
		for (int i = 0; i < runTimeStackSize; i++)
		{
			// If there are nonMainFrames
			//  and we're not on the last nonMainFrame,
			//  print new brackets to represent the following new frames 
			//  in the next iteration.
			if (!nonMainFrames.isEmpty() && nonMainFramesIndex < nonMainFrames.size())
			{
				// Don't add a comma for the next iteration if the next 
				//  runtime stack element belongs a different frame,
				//  so we know to print the next element in separate brackets to represent it.
				if (nonMainFrames.get(nonMainFramesIndex) == i + 1)
				{
					hasComma = false;
				}
				// Prime the brackets if we're on the index of a non-main frame.
				if (nonMainFrames.get(nonMainFramesIndex) == i)
				{
					System.out.print("] [");
					nonMainFramesIndex += 1;
				}
			}

			// Print the current runtime stack element.
			System.out.print(runTimeStack.get(i));

			// if runtimestack doesn't only have a single element
			//  and the current runtimestack index is not the last element
			//  and we've detected we need to add a comma 
			if ((runTimeStack.size() != 1) && ((runTimeStack.size() - 1) != i) && hasComma)
			{
				System.out.print(",");
			}

			if (!hasComma)
			{
				hasComma = true;
			}
		}

		System.out.print("]");

	} // End dump

	/**
	 * Returns the top item on this run-time stack.
	 *
	 * @return the top item on this run-time stack.
	 */
	public int peek()
	{
		return runTimeStack.get(runTimeStack.size() - 1);
	}

	/**
	 * Returns the top item that is to be popped from this run-time stack.
	 *
	 * @return the top item that is to be popped from this runtime stack.
	 */
	public int pop()
	{
		return runTimeStack.remove(runTimeStack.size() - 1);
	}

	/**
	 * Returns after pushing the specified item i on this run-time stack.
	 *
	 * @param i the specified item.
	 * @return
	 */
	public int push(int i)
	{
		runTimeStack.add(i);

		return i;
	}

	/**
	 * Returns the size of this run time stack.
	 *
	 * @return the size of this run time stack.
	 */
	public int size()
	{
		return runTimeStack.size();
	}

	/**
	 * Returns the stack elmeent at the specified index.
	 *
	 * @param index the specified index.
	 */
	public int elementAt(int index)
	{
		return runTimeStack.get(index);
	}

	// ************** FRAME POINTER ********************************** //
	/**
	 * Starts a new frame with value of the specified offset in this run-time
	 * stack.
	 *
	 * @param offset the specified offset value which indicates the number of
	 * slots down from the top of this run-time stack for starting a new frame.
	 */
	public void newFrameAt(int offset)
	{
		int newFrame = runTimeStack.size() - offset;
		framePointer.push(newFrame);
	}

	/**
	 * Pops the top frame of this run-time stack.
	 *
	 * Usage This is used when we return from a function. That is, before
	 * popping, the function's return value is at the top of the stack, so we
	 * save the value, pop the top frame, and then push the return value.
	 */
	public void popFrame()
	{
		framePointer.pop();
	}

	public int peekFrame()
	{
		if (framePointer.isEmpty())
		{
			return -1;
		}

		return framePointer.peek();
	}

	/**
	 * Stores the specified offset value loaded into variables into this
	 * run-time stack.
	 *
	 * Usage Used to load variables onto a run-time stack.
	 *
	 * @param offset the specified offset value.
	 * @return the specified offset value loaded into variables into this
	 * run-time stack.
	 */
	public int store(int offset)
	{
		int val = runTimeStack.remove(runTimeStack.size() - 1);
		runTimeStack.set(offset + framePointer.peek(), val);

		return val;
		// Overwrite the value at index 'offset' with the top element of the "stack", which is then removed.
		//int frameOffset = framePointer.peek() + offset;
		//runTimeStack.set(frameOffset, runTimeStack.lastIndexOf(this));
		//return runTimeStack.remove(runTimeStack.size()-1);
	}

	/**
	 * Loads the specified offset value to load variables.
	 *
	 * @param offset the specified offset value.
	 * @return the specified offset value to load variables.
	 */
	public int load(int offset)
	{
//        int data = runTimeStack.get(offset + framePointer.peek());
//        runTimeStack.add(data);
//
//        return data;
		int frameOffset = framePointer.peek() + offset;
		runTimeStack.add(runTimeStack.get(frameOffset));
		int lastElement = runTimeStack.lastIndexOf(this);
		return lastElement;
	}

	/**
	 * Returns the literals which are first loaded this run-time stack.
	 *
	 * Usage In Lit 5, we call this function with 5, push(5).
	 *
	 * @param i the specified value of the literal.
	 * @return the literals which are first loaded this run-time stack.
	 */
	public Integer push(Integer i)
	{
		runTimeStack.add(i);

		return i;
	}

	/**
	 * Returns the items on the current stack.
	 * 
	 * @return the items on the current stack.
	 */
	public ArrayList<String> getArguments()
	{
		ArrayList<String> recentItems = new ArrayList<>();
		// Get the the last framePointer value to know where to start getting items on current stack.
		int startFrameIndex = framePointer.peek();
		// Get the values corresponding to those items.
		/**
		 * for (int i = runTimeStack.size() - argCount; i < runTimeStack.size();
		 * i++) { recentItems.add(String.valueOf(runTimeStack.get(i))); }
		 */
		for (int i = startFrameIndex; i < runTimeStack.size(); i++)
		{
			recentItems.add(String.valueOf(runTimeStack.get(i)));
		}

		return recentItems;
	}

	/**
	 * Resets what is on this stack.
	 */
	public void reset()
	{
		int data = runTimeStack.remove(runTimeStack.size() - 1);

		while (runTimeStack.size() > framePointer.peek())
		{
			runTimeStack.remove(runTimeStack.size() - 1);
		}

		framePointer.pop();
		runTimeStack.add(data);
	}

	/**
	 * Returns the number of frames contained in this stack.
	 *
	 * @return the number of frames contained in this stack.
	 */
	public int frames()
	{
		return framePointer.size();
	}
}
