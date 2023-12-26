/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.debugger;

import interpreter.Program;
import interpreter.RunTimeStack;
import interpreter.VirtualMachine;
import interpreter.bytecode.ByteCode;

import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Executes a source program in debug mode.
 *
 * @author mandynoto
 */
public class DebugVM extends VirtualMachine
{

	private ByteCode byteCode;

	private String stepStyle;
	private String traceOutput;

	private boolean isChangedLine;
	protected boolean isTracing;

	private final List<DebugSourceLineMapEntry> sourceCode;
	private final Stack<FunctionEnvironmentRecord> environmentStack;

	/**
	 * Constructs a DebugVM initialized with the specified program and source
	 * code, assuming the function starts out at main.
	 *
	 * @param program The program object to be executed
	 * @param sourceCode The original source code for the program
	 */
	public DebugVM(Program program, List<DebugSourceLineMapEntry> sourceCode)
	{
		super(program);
		this.byteCode = null;
		this.environmentStack = new Stack<>();
		this.isRunning = true;
		this.pc = 0;
		this.returnAddrs = new Stack<>();
		this.runStack = new RunTimeStack();

		FunctionEnvironmentRecord main = new FunctionEnvironmentRecord();
		main.setCurrentLineNumber(1);
		main.setFunctionEndLineNumber(sourceCode.size());
		main.setFunctionName("main");
		main.setFunctionStartLineNumber(1);
		this.environmentStack.add(main);

		this.isChangedLine = false;
		this.isTracing = false;
		this.sourceCode = sourceCode;
	}

	/**
	 * Executes specialized byte code operations.
	 */
	@Override
	public void executeProgram()
	{
		int envStackSize = environmentStack.size();
		while (isStepCondition(envStackSize) && isRunning)
		{

			byteCode = program.getCode(pc);

			byteCode.execute(this);

			pc++;
		}

		stepStyle = null;

	}

	/**
	 * Returns {@code true} if the specified size of the environment stack is valid.
	 *
	 * @param envStackSize the specified environment stack size.
	 * @return {@code true} if the size of the environment stack is valid.
	 */
	private boolean isValidEnvironment(int envStackSize)
	{
		return environmentStack.size() != envStackSize;
	}

	/**
	 * Returns {@code true} if the programmed being debugged is still in
	 * execution; otherwise returns {@code false}.
	 *
	 * @return Returns {@code true} if the programmed being debugged is still in
	 * execution; otherwise returns {@code false}.
	 */
	public boolean isRunning()
	{
		return isRunning;
	}

	// ** START METHODS Step ** //
	/**
	 * Sets the type of step method via the specified step. Types of steps:
	 * "continue, out, into, or over".
	 *
	 * @param step the specified step.
	 */
	public void setStepStyle(String step)
	{
		if (step.matches("continue|into|out|over"))
		{
			stepStyle = step;
		}
	}

	/**
	 * Returns {@code true} on whether or not the fetch and execution will
	 * continue based on the specified original stack size and step condition;
	 * otherwise returns false.
	 *
	 * @param originalStackSize the specified original stack size.
	 *
	 * @return {@code true} on whether or not the fetch and execution will
	 * continue based on the specified original stack size and step condition;
	 * otherwise returns false.
	 */
	private boolean isStepCondition(int originalStackSize)
	{
		boolean isStepCondition = false;
		if (stepStyle.matches("continue"))
		{
			isStepCondition = !isABreakPoint(getCurrentLine())
					|| originalStackSize == environmentStack.size()
					|| !isChangedLine;

			if (isABreakPoint(getCurrentLine()) && isChangedLine)
			{
				isStepCondition = false;
			}

		} else if (stepStyle.matches("out"))
		{
			isStepCondition = environmentStack.size() >= originalStackSize;
			if (isABreakPoint(getCurrentLine()) && isChangedLine)
			{
				isStepCondition = false;
			}

		} else if (stepStyle.matches("into"))
		{
			isStepCondition = environmentStack.size() <= originalStackSize;
			if (!isStepCondition && byteCode.getName().matches("FUNCTION")
					&& environmentStack.peek().getFunctionStartLineNumber() > 0)
			{
				isStepCondition = true;
			}

		} else if (stepStyle.matches("over"))
		{
			isStepCondition = !isChangedLine;
		}

		if (isChangedLine)
		{
			isChangedLine = false;
		}

		return isStepCondition;
	}

	// ** END METHODS Step ** //
	// ** START METHODS Source code ** //
	/**
	 * Returns the line number currently being executed.
	 *
	 * @return the line number currently being executed.
	 */
	public int getCurrentLine()
	{
		return environmentStack.peek().getCurrentLineNumber();
	}

	/**
	 * Sets the specified line number to be executed.
	 *
	 * @param lineNumber the specified line number.
	 */
	public void setCurrentLine(int lineNumber)
	{
		// If the specified line number can be executed ...
		if (isValidSourceLocation(lineNumber))
		{
			// ... set this value on the function environment record.
			FunctionEnvironmentRecord record = environmentStack.pop();
			record.setCurrentLineNumber(lineNumber);
			environmentStack.add(record);
			isChangedLine = true;
		}
	}

	/**
	 * Returns {@code true} if the specified line number is a valid source
	 * location. Otherwise returns {@code false}.
	 *
	 * Note: This is a helper function for setting current line.
	 *
	 * @param lineNumber the specified line number.
	 *
	 * @return {@code true} if the specified line number is a valid source
	 * location. Otherwise returns {@code false}.
	 */
	private boolean isValidSourceLocation(int lineNumber)
	{
		return (lineNumber >= 0 && environmentStack.size() == runStack.frames() + 1);
	}

	/**
	 * Returns the source line code at the specified line number.
	 *
	 * @param lineNumber the specified line number.
	 * @return the source line code at the specified line number.
	 */
	public String getSourceLine(int lineNumber)
	{
		return sourceCode.get(lineNumber - 1).getSourceLine();
	}

	/**
	 * Returns the total amount of line numbers in the passed code file.
	 *
	 * @return the total amount of line numbers in the passed code file.
	 */
	public int getSourceCodeLineCount()
	{
		return sourceCode.size();
	}

	// ** END METHODS Source code ** //
	// ** Start METHODS Breakpoint ** //
	/**
	 * Returns {@code true} if the specified line number is a break point.
	 * Otherwise returns false.
	 *
	 * @param lineNumber the specified line number.
	 * @return {@code true} if the specified line number is a break point.
	 * Otherwise returns false.
	 */
	public boolean isABreakPoint(int lineNumber)
	{
		if (lineNumber > 0)
		{
			return sourceCode.get(lineNumber - 1).isABreakPoint();
		} else
		{
			return false;
		}
	}

	/**
	 * Returns {@code true} if setting the specified break point status at the
	 * specified line number is successful; otherwise returns false.
	 *
	 * @param lineNumber the specified line number.
	 * @param breakPointStatus the specified break point status.
	 * @return {@code true} if setting the specified break point status at the
	 * specified line number is successful; otherwise returns false.
	 */
	public boolean setBreakPoint(int lineNumber, boolean breakPointStatus)
	{
		DebugSourceLineMapEntry sourceLine = sourceCode.get(lineNumber);
		String line = sourceLine.getSourceLine();

		if (isValidBreakPoint(line))
		{
			sourceLine.setBreakPoint(breakPointStatus);
			return true;
		}

		return false;
	}

	/**
	 * Returns {@code true} if it is possible to set a break point on the
	 * specified source code line; otherwise returns {@code false}.
	 *
	 * @param line the specified source code line.
	 * @return {@code true} if it is possible to set a break point on the
	 * specified source code line; otherwise returns {@code false}.
	 */
	private boolean isValidBreakPoint(String line)
	{
		return line.contains("int") || line.contains("boolean")
				|| line.contains("if") || line.contains("while")
				|| line.contains("=") || line.contains("{")
				|| line.contains("return");
	}

	// ** END METHODS Source code ** //
	// ** START METHODS Trace ** //
	/**
	 * Sets the isTracing and initializes the string thereof with the specified
	 * tracing status.
	 *
	 * @param isTracing the specified tracing status.
	 */
	public void setTrace(boolean isTracing)
	{
		this.isTracing = isTracing;

		traceOutput = "";
	}

	/**
	 * Returns {@code true} if this virtual machine is in tracing mode;
	 * otherwise returns {@code false}
	 *
	 * @return {@code true} if this virtual machine is in tracing mode;
	 * otherwise returns {@code false}
	 */
	public boolean isTracing()
	{
		return isTracing;
	}

	/**
	 * Logs an entered or exited function determined by the specified exit
	 * status.
	 *
	 * @param isExit the specified exit status.
	 */
	private void logTrace(boolean isExit)
	{
		String funcName = environmentStack.peek().getFunctionName().split("<<")[0];
		for (int space = 0; space < environmentStack.size(); space++)
		{
			traceOutput += " ";
		}

		if (isExit)
		{
			int returnValue = runStack.peek();
			traceOutput += "exit: " + funcName + ": " + returnValue + "\n";
			return;
		}

		String funcArgs = "";
		for (int index = runStack.peekFrame(); index < runStack.size(); index++)
		{
			funcArgs += runStack.elementAt(index);
			if (index != runStack.size() - 1)
			{
				funcArgs += ",";
			}
		}

		traceOutput += funcName + "(" + funcArgs + ")" + "\n";
	}

	/**
	 * Returns the current trace.
	 *
	 * @return the current trace.
	 */
	public String getTraceOutput()
	{
		return traceOutput;
	}

	/**
	 * Returns the current contents of the call stack.
	 *
	 * @return the current contents of the call stack.
	 */
	public String getCallStack()
	{
		String callStack = "";
		for (int index = environmentStack.size() - 1; index > 0; index--)
		{
			FunctionEnvironmentRecord func = environmentStack.elementAt(index);
			String funcName = func.getFunctionName().split("<<")[0];
			callStack += funcName + ": " + func.getCurrentLineNumber() + "\n";
		}

		return String.format("%s", callStack);
	}

	/**
	 * Adds a new function record, via its specified: name, starting line
	 * number, and ending line number, to the environment stack.
	 *
	 * @param name the specified function name.
	 * @param startLine the specified function's starting line number.
	 * @param endLine the specified function's ending line number.
	 */
	public void addFunctionRecord(String name, int startLine, int endLine)
	{
		FunctionEnvironmentRecord record = new FunctionEnvironmentRecord();
		environmentStack.add(record);
		record.setCurrentLineNumber(getCurrentLine());
		record.setFunctionEndLineNumber(endLine);
		record.setFunctionName(name);
		record.setFunctionStartLineNumber(startLine);

		if (isTracing)
		{
			logTrace(false);
		}
	}

	/**
	 * Removes the function record from the active environment stack.
	 */
	public void popFunctionRecord()
	{
		logTrace(true);

		environmentStack.pop();
	}
	// ** END METHODS Trace ** //

	/**
	 * Returns {@code true} if the active function is intrinsic.
	 *
	 * @return {@code true} if the active function is intrinsic.
	 */
	private boolean isIntrinsicFunction()
	{
		return environmentStack.peek().getFunctionStartLineNumber() <= 0;
	}

	// *** END Trace functions ** //
	// *** START record functions ** //
	/**
	 * Adds an entry to the active function environment record via its specified
	 * label and offset.
	 *
	 * @param label the specified function's label.
	 * @param offset the specified function's offset in the run time stack.
	 */
	public void addRecordEntry(String label, int offset)
	{
		FunctionEnvironmentRecord record = environmentStack.pop();
		record.enter(label, offset);
		environmentStack.add(record);
	}

	/**
	 * Deletes from the active function environment record a number of the
	 * specified count of entries.
	 *
	 * @param popCount the specified number of entries to remove.
	 */
	public void popRecordEntries(int popCount)
	{
		FunctionEnvironmentRecord record = environmentStack.pop();
		record.doPop(popCount);
		environmentStack.add(record);
	}

	/**
	 * Returns the line number at which the active function begins.
	 *
	 * @return the line number at which a function begins.
	 */
	public int getFunctionFirstLineNumber()
	{
		return environmentStack.peek().getFunctionStartLineNumber();
	}

	/**
	 * Returns the line number at which the active function ends.
	 *
	 * @return the line number at which a function ends.
	 */
	public int getFunctionLastLineNumber()
	{
		return environmentStack.peek().getFunctionEndLineNumber();
	}

	/**
	 * Returns the name of the active function.
	 *
	 * @return the name of the active function.
	 */
	public String getFunctionCurrentName()
	{
		return environmentStack.peek().getFunctionName().split("<<")[0];
	}

	/**
	 * Returns the variables that are declared in the active function.
	 *
	 * @return the variables that are declared in the active function.
	 */
	public Set<String> getFunctionVariables()
	{
		Set<String> variableIDList = (Set<String>) environmentStack.peek().getVariables();
		return variableIDList;
	}

	/**
	 * Returns the formal, i.e, the value associated with the specified variable
	 * label.
	 *
	 * @param variableLabel the specified variable label.
	 * @return the formal, i.e, the value associated with the specified variable
	 * label.
	 *
	 */
	public int getVariableValue(String variableLabel)
	{
		int offset = environmentStack.peek().getVariableOffset(variableLabel);
		return runStack.elementAt(offset);
	}
}
