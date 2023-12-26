/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.debugger.DebugVM;

/**
 * Extended version of Pop code for the debugging virtual machine.
 *
 * @author mandynoto
 */
public class PopCode extends interpreter.bytecode.PopCode
{

	/**
	 * Executes this pop code as a debugging virtual machine via the specified
	 * abstract virtual machine.
	 *
	 * @param vm the specified abstract virtual machine.
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		execute((DebugVM) vm);
	}

	/**
	 * Helper function to execute this through the specified debugging virtual
	 * machine.
	 *
	 * @param vm the specified debugging virtual machine.
	 */
	public void execute(DebugVM vm)
	{
		super.execute(vm);
		vm.popRecordEntries(runtimeStackTopNLevels);
	}
}
