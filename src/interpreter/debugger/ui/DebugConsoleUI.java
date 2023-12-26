/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpreter.debugger.ui;

import interpreter.debugger.DebugVM;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Console for the X Debugger.
 *
 * @author mandynoto
 */
public class DebugConsoleUI
{

	private static DebugVM vm;
	// True if program finished execution.
	private static boolean isHalted;
	// True if user issued a halt command.
	private static boolean isManuallyHalted;

	/**
	 * Like Symbol.java, this manages construction of this class, therefore
	 * saving space.
	 */
	private DebugConsoleUI()
	{
		// Intentionally left blank since we want to  control its construction.
	}

	/**
	 * Prompts the user for commands with the help of the specified debugging
	 * virtual machine.
	 *
	 * @param virtualMachine the specified debugging virtual machine.
	 */
	public static void showPrompt(DebugVM virtualMachine)
	{
		vm = virtualMachine;
		isHalted = false;
		String command;
		String promptIndicator = ">>";

		showFunctionSourceCode();

		// Keep prompting user for commands if the debugger is still running...
		// ... and it has not finished execution.
		while (!isHalted && vm.isRunning())
		{
			try
			{
				System.out.printf("Type ? for help\n");
				System.out.printf(promptIndicator + " ");
				BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
				command = input.readLine().toLowerCase();
				executeCommand(command);
			} catch (IOException ex)
			{
				// Intentionally does nothing.
			}
		}

		// Announce message for user quitting, or that ...
		if (isManuallyHalted)
		{
			System.out.printf("******Execution Halted*******\n");
			return;
		}

		// ... the debugging process has finished.
		System.out.printf("******Execution Finished******\n");
	}

	/**
	 * Executes the user-specified command.
	 *
	 * @param userCommand the specified user command.
	 */
	private static void executeCommand(String userCommand)
	{
		String argument = "";
		if (userCommand.split(" ").length > 1)
		{
			argument = userCommand.split(" ", 2)[1];
		}

		userCommand = userCommand.split(" ")[0];

		if (userCommand.startsWith("?") || userCommand.startsWith("h") || userCommand.startsWith("help"))
		{
			showHelp();
		} else if (userCommand.matches("b") && argument.isBlank())
		{
			showBreakPoints();
		} else if (userCommand.matches("b") && argument.matches("[\\d+\\s*]+"))
		{
			setBreakPoint(argument);
		} else if (userCommand.matches("bc") && argument.matches("[\\d+\\s*]+"))
		{
			clearBreakPoint(argument);
		} else if (userCommand.matches("c"))
		{
			stepContinue();
		} else if (userCommand.matches("f") && argument.isBlank())
		{
			showFunctionSourceCode();
		} else if (userCommand.matches("i"))
		{
			stepInto(); 
		} else if (userCommand.matches("o"))
		{
			stepOut();
		} else if (userCommand.matches("n"))
		{
			stepOver(); 
		} else if ((userCommand.startsWith("q") || userCommand.startsWith("quit")) && argument.isBlank())
		{
			halt();
		} else if (userCommand.startsWith("p"))
		{
			showCallStack(); 
		} else if (userCommand.startsWith("t") && argument.matches("on|off"))
		{
			setTrace(argument);
		} else if (userCommand.matches("v") && argument.isBlank())
		{
			showVariables();
		} else if ((userCommand.startsWith("z") || userCommand.startsWith("clear")) && argument.isBlank())
		{
			clearScreen();
		} else
		{
			System.out.printf("error: '%s %s' is not a valid command.\n\n", userCommand, argument);
		}
	}

	/**
	 * ******* USER COMMAND FUNCTIONS *******
	 */
	/**
	 * Prints the available commands for the X debugger
	 */
	private static void showHelp()
	{
		String message = String.format("%-20s %2$s \n", "Debugger commands:", "Description:");
		message += ""
				+ String.format("%-20s -- %2$s \n",
						"?", "Show a list of available debugging commands.")
				+ String.format("%-20s -- %2$s \n",
						"b", "Show a list of current breakpoints.")
				+ String.format("%-20s -- %2$s \n",
						"b <n,...>", "Set a breakpoint at line n. (Can take multiple values)")
				+ String.format("%-20s -- %2$s \n",
						"bc <n,...>", "Clear the breakpoint at line n. (Can take multiple values)")
				+ String.format("%-20s -- %2$s \n",
						"c", "Continue program execution until the next breakpoint.")
				+ String.format("%-20s -- %2$s \n",
						"f", "Show the function source code or thereof in the scope of a breakpoint.")
				+ String.format("%-20s -- %2$s \n",
						"i", "Step into an existing function call on the current line; otherwise step over.")
				+ String.format("%-20s -- %2$s \n",
						"o", "Step out of current function call activation.")
				+ String.format("%-20s -- %2$s \n",
						"n", "Step over the line.")
				+ String.format("%-20s -- %2$s \n",
						"q", "Quit the X Debugger.")
				+ String.format("%-20s -- %2$s \n",
						"p", "Print stack of function calls, along with line numbers of calling function.")
				+ String.format("%-20s -- %2$s \n",
						"t <on OR off>", "Sets tracing to either on or off.")
				+ String.format("%-20s -- %2$s \n",
						"v", "Show variables in the current stack frame.")
				+ String.format("%-20s -- %2$s \n",
						"z", "Clear the screen. (Only works if this program was executed in the terminal)");

		System.out.printf("%s\n", message);
	}

	/**
	 * Sets a break point or breakpoints at the specified source code line
	 * numbers.
	 *
	 * @param srcCodeLineNumbers the specified source code line numbers.
	 */
	private static void setBreakPoint(String srcCodeLineNumbers)
	{
		String[] lines = srcCodeLineNumbers.split(" ");
		String message = "";

		for (String line : lines)
		{
			int lineNumber = Integer.parseInt(line);

			if (lineNumber <= vm.getSourceCodeLineCount())
			{
				boolean success = vm.setBreakPoint(lineNumber - 1, true);
				if (success)
				{
					message += lineNumber + " ";
				} else
				{
					System.out.println("error: cannot set breakpoint on line " + lineNumber + ".");
				}
			} else
			{
				System.out.println("error: line " + lineNumber + " does not exist.");
			}
		}

		if (!message.isEmpty())
		{
			System.out.printf("Breakpoint set: %s\n\n", message);
		}
	}

	/**
	 * Clears a break point or breakpoints at the specified source code line
	 * numbers.
	 *
	 * @param srcCodeLineNumbers the specified source code line numbers.
	 */
	private static void clearBreakPoint(String srcCodeLineNumbers)
	{
		String[] lines = srcCodeLineNumbers.split(" ");
		String message = "";
		for (String line : lines)
		{
			int lineNumber = Integer.parseInt(line);

			if (lineNumber <= vm.getSourceCodeLineCount())
			{
				boolean success = vm.setBreakPoint(lineNumber - 1, false);
				if (success)
				{
					message += lineNumber + " ";
				}
			} else
			{
				System.out.printf("Error: line " + lineNumber + " does not exist.\n");
			}
		}

		if (!message.isEmpty())
		{
			System.out.printf("Breakpoint cleared: %s\n\n", message);
		}
	}

	/**
	 * Prints the currently active breakpoints.
	 *
	 */
	private static void showBreakPoints()
	{
		String breakpoints = "";
		for (int line = 1; line <= vm.getSourceCodeLineCount(); line++)
		{
			if (vm.isABreakPoint(line))
			{
				breakpoints += line + " ";
			}
		}

		if (breakpoints.isEmpty())
		{
			System.out.printf("No breakpoints currently set.\n\n");
			return;
		}

		System.out.printf("Current breakpoints: %s\n\n", breakpoints);
	}

	/**
	 * Executes the specified type of step.
	 *
	 * @param stepType the specified type of step.
	 */
	private static void doStep(String stepType)
	{
		vm.setStepStyle(stepType);
		vm.executeProgram();
		if (vm.isTracing()) {
			System.out.printf("%s\n", vm.getTraceOutput());
		}

		showFunctionSourceCode();
	}

	/**
	 * Passed into doStep to do a continue.
	 */
	private static void stepContinue()
	{
		doStep("continue");
	}

	/**
	 * Passed into doStep to do a step into.
	 */
	private static void stepInto()
	{
		doStep("into");
	}

	/**
	 * Calls doStep to do a step over.
	 */
	private static void stepOver()
	{
		doStep("over");
	}

	/**
	 * Calls doStep to do a step out.
	 */
	private static void stepOut()
	{
		doStep("out");
	}

	/**
	 * Sets tracing in the debug virtual machine with the specified argument.
	 * Can be on or off.
	 * 
	 * @param argument the specified argument.
	 */
	private static void setTrace(String argument)
	{
		if (argument.matches("on"))
		{
			vm.setTrace(true);
		} else
		{
			vm.setTrace(false);
		}
		
		System.out.printf("Tracing %s\n\n", argument);
	}

	/**
	 * Prints the current contents of the call stack.
	 */
	private static void showCallStack()
	{
		String callStack = vm.getCallStack();
		System.out.printf("%s\n", callStack);
	}

	/**
	 * Prints the source code of the current frame.
	 */
	private static void showFunctionSourceCode()
	{
		int current = vm.getCurrentLine();
		int end = vm.getFunctionLastLineNumber();
		int start = vm.getFunctionFirstLineNumber();

		String message = "";

		if (start < 0)
		{
			message += "****"+vm.getFunctionCurrentName().toUpperCase()+"****";
		} else
		{
			for (int line = start; line <= end; line++)
			{
				if (vm.isABreakPoint(line))
				{
					message += "*";
				} else
				{
					message += " ";
				}

				message += String.format("%1$4s %2$s", line + ".", vm.getSourceLine(line));

				if (line == current)
				{
					message += " <-----";
				}

				message += "\n";
			}
		}

		System.out.printf("%s\n", message);
	}

	/**
	 * Prints the active variables on the current frame.
	 */
	private static void showVariables()
	{
		String message = "";
		for (String var : vm.getFunctionVariables())
		{
			message += " " + var + ": " + vm.getVariableValue(var) + "\n";
		}

		if (message.isEmpty())
		{
			System.out.printf("No variables currently officially set.\n");
		}

		System.out.println(message);
	}

	/**
	 * Clears the screen on the terminal console, similar to pressing
	 * control+l on a bash or zsh terminal.
	 */
	private static void clearScreen()
	{
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	/**
	 * Stops the execution and the program of the X debugger.
	 */
	private static void halt()
	{
		vm.endProgram();
		isHalted = true;
		isManuallyHalted = true;
	}
}
