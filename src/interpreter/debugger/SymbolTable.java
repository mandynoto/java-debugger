/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.debugger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Stores variable IDs and their corresponding run-time stack offset.
 *
 * @author mandynoto
 */
public class SymbolTable
{  // START SymbolTable
	// The symbols in this table.

	private HashMap<String, Binder> symbols;

	// The last variable that was put in this symbol table.
	private String top;

	/**
	 * Constructs a new symbol table.
	 */
	public SymbolTable()
	{
		// Intentionally does nothing.
	}

	/**
	 * Returns the objectValue of the object with the specified key.
	 *
	 * @param key the specified key.
	 * @return the objectValue of the object with the specified key.
	 */
	protected int getObjectValue(String key)
	{
		int objectValue;
		objectValue = (Integer) symbols.get(key).getValue();
		return objectValue;
	}

	/**
	 * Puts the specified variable symbol and its run-time offset in this symbol
	 * table's hash map.
	 *
	 * @param variableSymbol the specified key.
	 * @param runtimestackOffset the specified objectValue.
	 */
	public void put(String variableSymbol, int runtimestackOffset)
	{
		symbols.put(variableSymbol, new Binder(runtimestackOffset, top, symbols.get(variableSymbol)));
		top = variableSymbol;
	}

	/**
	 * Starts a function scope by initializing this symbol table's hash map.
	 */
	public void beginScope()
	{
		symbols = new HashMap<>();
		top = null;
	}

	/**
	 * Removes and replaced the active value with a possibly existing one
	 * in the specified number of pops times.
	 * 
	 * @param numOfPops the specified number of pops.
	 */
	public void popValues(int numOfPops)
	{
		for (int i = 0; i < numOfPops; i++)
		{
			Binder binder = symbols.get(top);

			if (binder.getPrevBinder() != null)
			{
				symbols.put(top, binder.getPrevBinder());
			} else
			{
				symbols.remove(top);
			}

			top = binder.getPrevSymbol();
		}
	}

	/**
	 * Returns {@code true} if this symbol table is empty.
	 *
	 * @return {@code true} if this symbol table is empty.
	 */
	public boolean isEmpty()
	{
		return symbols.isEmpty();
	}

	/**
	 * Returns the active mappings of variable id's and their run time stack 
	 * offset.
	 * 
	 * @return the active mappings of variable id's and their run time stack 
	 * offset.
	 */
	public String getContents()
	{
		String contents = "";
		boolean addComma = false;

		Iterator symbolIterator = symbols.entrySet().iterator();

		while (symbolIterator.hasNext())
		{
			if (addComma)
			{
				contents += ",";
			}

			Map.Entry mapElement = (Map.Entry) symbolIterator.next();
			String symbol = (String) mapElement.getKey();
			int symbolValue = (int) ((Binder) mapElement.getValue()).getValue();

			contents += String.format("%s/%s", symbol, symbolValue);
			addComma = true;
		}

		return contents;
	}

	/**
	 * @return a set of this table's keys.
	 */
	public Set<String> getKeys()
	{
		Set<String> symbolKeys;
		try
		{
			symbolKeys = symbols.keySet();
		} catch (Exception e)
		{
			symbolKeys = Collections.emptySet();
		}

		return symbolKeys;
	}
} // END SymbolTable

/**
 * Stores each entry in SymbolTable.
 *
 * @author mandynoto
 */
class Binder
{ // START Binder
	// The "address" of the object.

	private final Object objectValue;

	// The previous symbol in the equivalent scope.
	private final String prevSymbol;

	// The previous binder for the equivalent symbol.
	private final Binder prevBinder;

	/**
	 * Constructs a new Binder with the specified objectValue, previous symbol,
	 * and previous binder.
	 *
	 * @param value			the specified objectValue.
	 * @param prevSymbol	the specified previous symbol
	 * @param prevBinder	the specified previous binder.
	 */
	Binder(Object value, String prevSymbol, Binder prevBinder)
	{
		this.objectValue = value;
		this.prevSymbol = prevSymbol;
		this.prevBinder = prevBinder;
	}

	/**
	 * Returns the objectValue of this binder.
	 *
	 * @return the objectValue of this binder.
	 */
	Object getValue()
	{
		return objectValue;
	}

	/**
	 * Returns the previous symbol in this binder.
	 *
	 * @returnthe previous symbol in this binder.
	 */
	String getPrevSymbol()
	{
		return prevSymbol;
	}

	/**
	 * Returns the previous binder of this binder.
	 *
	 * @return the previous binder of this binder.
	 */
	Binder getPrevBinder()
	{
		return prevBinder;
	}
} // END Binder
