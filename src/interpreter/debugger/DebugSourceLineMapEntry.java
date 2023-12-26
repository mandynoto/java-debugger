/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.debugger;

/**
 * An entry mapping of a source line and its break point status; That is, it is
 * true if it has a break point, otherwise false.
 *
 * @author mandynoto
 */
public class DebugSourceLineMapEntry
{

	private final String sourceLine;
	private boolean isABreakPoint;

	/**
	 * Determines the source line that has a break point via the specified
	 * source line and the specified break point.
	 *
	 * @param sourceLine the specified source line.
	 * @param hasBreakPoint the specified break point.
	 */
	public DebugSourceLineMapEntry(String sourceLine, boolean hasBreakPoint)
	{
		this.isABreakPoint = hasBreakPoint;
		this.sourceLine = sourceLine;
	}

	/**
	 * Returns this entry's source line.
	 *
	 * @return this entry's source line.
	 */
	public String getSourceLine()
	{
		return sourceLine;
	}

	/**
	 * Returns {@code true} if this entry has a break point; otherwise returns
	 * false.
	 *
	 * @return {@code true} if this entry has a break point; otherwise returns
	 * false.
	 */
	public boolean isABreakPoint()
	{
		return isABreakPoint;
	}

	/**
	 * Sets this entry's break point with the specified break-point status.
	 *
	 * @param breakPointStatus the specified break-point status.
	 */
	public void setBreakPoint(boolean breakPointStatus)
	{
		isABreakPoint = breakPointStatus;
	}
}
