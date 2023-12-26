/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.debugger;

import java.util.Set;

/**
 * Stores information about a function.
 *
 * @author mandynoto
 */
public class FunctionEnvironmentRecord
{

	private final SymbolTable symbolTable;

	private String functionName = "";

	private int functionStartLineNumber = -1;
	private int functionEndLineNumber = -1;
	private int currentLineNumber = -1;       // that the DebugVM is processing

	public FunctionEnvironmentRecord()
	{
		symbolTable = new SymbolTable();
	}

	/**
	 * Returns the name of the function.
	 *
	 * @return the name of the function.
	 */
	public String getFunctionName()
	{
		return functionName;
	}

	/**
	 * Inputs a variable entry into this function environment record with the
	 * specified variable id, and its specified offset in the run time stack.
	 *
	 * @param variableID The variable's ID
	 * @param offset The offset of the variable in the run time stack
	 */
	public void enter(String variableID, int offset)
	{
		symbolTable.put(variableID, offset);
	}

	/**
	 * Sets the name of the function with the specified function name.
	 *
	 * @param functionName the specified function name.
	 */
	public void setFunctionName(String functionName)
	{
		this.functionName = functionName;
		// Remove beginScope() in line below if you want to test out main.
		beginScope();
	}

	/**
	 * Returns the line number that the function starts at.
	 *
	 * @return the line number that the function starts.
	 */
	public int getFunctionStartLineNumber()
	{
		return functionStartLineNumber;
	}

	/**
	 * Sets the line number that the function starts at with the specified line
	 * number.
	 *
	 * @param functionStartLineNumber the specified line number.
	 */
	public void setFunctionStartLineNumber(int functionStartLineNumber)
	{
		this.functionStartLineNumber = functionStartLineNumber;
	}

	/**
	 * Returns the line number that the function ends.
	 *
	 * @return the line number that the function ends.
	 */
	public int getFunctionEndLineNumber()
	{
		return functionEndLineNumber;
	}

	/**
	 * Sets the line number that the function ends with the specified line
	 * number.
	 *
	 * @param functionEndLineNumber the specified line number.
	 */
	public void setFunctionEndLineNumber(int functionEndLineNumber)
	{
		this.functionEndLineNumber = functionEndLineNumber;
	}

	/**
	 * Returns the line number that the DebugVM is currently processing.
	 *
	 * @return the line number that the DebugVM is currently processing.
	 */
	public int getCurrentLineNumber()
	{
		return currentLineNumber;
	}

	/**
	 * Sets the line number the DebugVM is currently processing with the
	 * specified line number.
	 *
	 * @param currentLineNumber the specified line number.
	 */
	public void setCurrentLineNumber(int currentLineNumber)
	{
		this.currentLineNumber = currentLineNumber;
	}

	/**
	 * Begins the scope of a function through the symbol table.
	 */
	public void beginScope()
	{
		symbolTable.beginScope();
	}

	/**
	 * Sets the fields for a function's name, and the lines it starts and ends.
	 *
	 * @param functionName the specified function name.
	 * @param functionStartLineNumber the specified function start line number.
	 * @param functionEndLineNumber the specified function end line.
	 */
	public void setFunctionInfo(String functionName,
			int functionStartLineNumber,
			int functionEndLineNumber)
	{
		setFunctionName(functionName);
		setFunctionStartLineNumber(functionStartLineNumber);
		setFunctionEndLineNumber(functionEndLineNumber);
	}

	/**
	 * Enters the variable/value pair into the symbol table with the specified
	 * variable identifier and its offset in the runtime stack.
	 *
	 * @param variableID the specified variable value.
	 * @param offset the specified variable value's offset in the runtime stack.
	 */
	public void setVarVal(String variableID, int offset)
	{
		symbolTable.put(variableID, offset);
	}

	/**
	 * Removes the last n var/value pairs from the symbol table.
	 *
	 * @param n the specified last var/value pairs from the symbol table.
	 */
	public void doPop(int n)
	{
		symbolTable.popValues(n);
	}

	/**
	 * Returns {@code true} if the symbol table is empty.
	 *
	 * @return {@code true} if the symbol table is empty.
	 */
	public boolean isEmpty()
	{
		return symbolTable.isEmpty();
	}

	/**
	 * Returns the symbol table contents, save the previous binders.
	 *
	 * Note: Private because it's meant for milestone 2.
	 *
	 * @return the symbol table contents, save the previous binders.
	 */
	private String getSymbolTableContents()
	{
		return symbolTable.getContents();
	}

	/**
	 * Prints a string representation of what this function environment record
	 * contains.
	 *
	 * Note: Private because it's meant for milestone 2 testing.
	 */
	private void dump()
	{
		// Declare string representation of contents of this fer.
		String functionEnvironmentContent = "";

		// Determine how these elements should be displayed.
		String varValuesDump = (isEmpty() ? " " : getSymbolTableContents());
		String funcNameDump = (this.functionName.isEmpty() ? "-" : this.functionName);
		String funcStartLineDump = (this.functionStartLineNumber != -1 ? String.valueOf(this.functionStartLineNumber) : "-");
		String funcEndLineDump = (this.functionEndLineNumber != -1 ? String.valueOf(this.functionEndLineNumber) : "-");
		String curLineDump = (this.currentLineNumber != -1 ? String.valueOf(this.currentLineNumber) : "-");

		// Create template for the the function environment content.
		functionEnvironmentContent = String.format("(<%s>, %s, %s, %s, %s)",
				varValuesDump,
				funcNameDump,
				funcStartLineDump,
				funcEndLineDump,
				curLineDump);

		// Print out the function environment content.
		System.out.print(functionEnvironmentContent);
	}

	/**
	 * Returns a set of the variable IDs currently stored in this function
	 * environment record.
	 *
	 * @return a set of the variable IDs currently stored in this function
	 * environment record.
	 */
	public Set<String> getVariables()
	{
		return symbolTable.getKeys();
	}

	/**
	 * Returns the run-time offset at which the specified variable is stored
	 *
	 * @param variableID	the specified variable ID.
	 *
	 * @return	the run-time offset at which the specified variable is stored
	 */
	public int getVariableOffset(String variableID)
	{
		return symbolTable.getObjectValue(variableID);
	}

	/**
	 * Purposed to test the working condition of processing 6 commands for
	 * testing purposes for milestone 2 submission.
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		FunctionEnvironmentRecord fr = new FunctionEnvironmentRecord();

		fr.beginScope();
		fr.dump();
		System.out.printf("\n\n");

		fr.setFunctionInfo("g", 1, 20);
		fr.dump();
		System.out.printf("\n\n");

		fr.setCurrentLineNumber(5);
		fr.dump();
		System.out.printf("\n\n");

		fr.setVarVal("a", 4);
		fr.dump();
		System.out.printf("\n\n");

		fr.setVarVal("b", 2);
		fr.dump();
		System.out.printf("\n\n");

		fr.setVarVal("c", 7);
		fr.dump();
		System.out.printf("\n\n");

		fr.setVarVal("a", 1);
		fr.dump();
		System.out.printf("\n\n");

		fr.doPop(2);
		fr.dump();
		System.out.printf("\n\n");

		fr.doPop(1);
		fr.dump();
		System.out.printf("\n\n");

	}
}
