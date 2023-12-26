/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.debugger.DebugVM;

/**
 * Extended version of Lit Code for the debugging virtual machine.
 *
 * @author mandynoto
 */
public class LitCode extends interpreter.bytecode.LitCode
{

	/**
	 * Executes this code via the debugging virtual machine.
	 *
	 * @param vm
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		execute((DebugVM) vm);
	}

	/**
	 * Helper function to execute this code through the specified debugging
	 * virtual machine.
	 *
	 * @param vm the specified debugging virtual machine.
	 */
	public void execute(DebugVM vm)
	{
		super.execute(vm);

		// Only adds an entyr if this had a formal.
		if (stringArg != null)
		{
			int offset = vm.runStackSize() - 1;
			vm.addRecordEntry(stringArg, offset);
		}
	}
}
