/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.bytecode.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.debugger.DebugVM;

/**
 * Extended version of the return code for the debugging virtual machine.
 *
 * @author mandynoto
 */
public class ReturnCode extends interpreter.bytecode.ReturnCode
{

	/**
	 * Helper function to execute through the specified virtual machine.
	 *
	 * @param vm the specified virtual machine.
	 */
	@Override
	public void execute(VirtualMachine vm)
	{
		execute((DebugVM) vm);
	}

	/**
	 * The delegated execution of this code via the specified debugging virtual
	 * machine.
	 *
	 * @param vm the specified debugging virtual machine.
	 */
	public void execute(DebugVM vm)
	{
		super.execute(vm);
		vm.popFunctionRecord();
	}
}
