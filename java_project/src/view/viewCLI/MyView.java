package view.viewCLI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import view.View;
import View.CLI;
import View.Command;
import algorithms.search.*;
import algorithms.search.aStar.*;
import algorithms.mazeGenerators.Maze;

public class MyView extends Observable implements View {
	NewCLI myCLI;
	BufferedReader in;
	PrintStream out;
	Queue<Command> commandsList;
	/**
	 * This constructor create view by using the arguments for input and output. 
	 * @param r The input for the user.
	 * @param out The output for the user.
	 */
	public MyView(BufferedReader r,PrintStream out) {
		super();
		this.in=r;
		this.out=out;
		commandsList=new LinkedList<Command>();
	}
	/**
	 * This constructor create view by using the default system output and input
	 */
	public MyView() {
		super();
		this.in=new BufferedReader(new InputStreamReader(System.in));
		this.out=System.out;
		commandsList=new LinkedList<Command>();
	}
	
	/**
	 * Starts the view with the myCLI.
	 */
	@Override
	public void start() {
		this.setChanged();
		this.notifyObservers("start");
		Thread t=new Thread(new Runnable() {
			public void run() {
				myCLI.start();
			}
		});
		t.start();
	}
	
	/**
	 * Sets out hashmap commands with the received hashmap.
	 * @param commands hashmap of string<->command containing out available commands.
	 */
	@Override
	public void setCommands(HashMap<String, Command> commands) {
		myCLI=new NewCLI(in, out, commands);
	}
	
	/**
	 * Takes out a user command from our user command queue.
	 */
	@Override
	public Command getUserCommand() {
		return commandsList.poll();
	}

	/**
	 * Displays the received maze using MyMazeDispleyer.
	 * @param m Maze.
	 */
	@Override
	public void displayMaze(Maze m) {
		MyMazeDispleyer md = new MyMazeDispleyer();
		md.DisplayMaze(m, out);
	}
	
	/**
	 * Displays the received solution using MySolutionDispleyer.
	 * @param s Solution.
	 */
	@Override
	public void displaySolution(Solution s) {	
		MySolutionDispleyer sd = new MySolutionDispleyer();
		sd.DisplaySolution(s, out);
	}
	
	//only for NotifyCLIgetViewWeDontUseIt we don't use it
	/**
	 * Notify our observers using the received argument.
	 * @param arg received argument.
	 */
	public void Notify(String arg) {
		this.setChanged();
		this.notifyObservers(arg);
	}
	
	
	/**
	 * Displays a string to our outputStream.
	 */
	@Override
	public void displayString(String msg) {
		out.println(msg);
	}


	//private class for CLI
	private class NewCLI extends CLI{
		/**
		 * This constructor creates CLI that works with the parameters.
		 * @param in The inputstream(BufferedReader) that contain all the command from the user.
		 * @param out The OutputStream(PrintStream) that all the commands will write.
		 * @param commands All the commands that the CLI support.
		 */
		public NewCLI(BufferedReader in, PrintStream out,HashMap<String, Command> commands){
			super(in,out,commands);
		}
		/**
		 * This method start getting commands from the user and add them to commandsList.
		 */
		public void start()
		{
			out.flush();
			out.println("Welcome to the project of Senia&Bar");
			out.print("Enter command: ");

			try {
				String line = in.readLine();
				
				while (!line.equals("exit"))
				{
					String[] sp = line.split(" ", 2);
									
					String commandName = sp[0];
					String arg = null;
					if (sp.length > 1)
						arg = sp[1];
					// Invoke the command
					Command command = Commands.get(commandName);
					if(command==null)
						out.println("There is no such command "+commandName);
					else
						if(arg==null)
							out.println("No argument has been entered");
						else{
							commandsList.add(command);
							setChanged();
							notifyObservers(arg);
						}
					
					out.print("Enter command: ");
					line = in.readLine();
				}
				out.println("Goodbye");
				commandsList.add(Commands.get("exit"));
				setChanged();
				notifyObservers(null);
			} catch (IOException e) {
				out.println("can't read/write from/to in/out streams");
			} finally {
				try {
					in.close();
					out.close();
				} catch (IOException e) {
					out.println("can't close from in/out streams");
				}		
			}	
		}
	}

}
