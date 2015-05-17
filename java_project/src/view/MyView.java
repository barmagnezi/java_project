package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import View.Command;
import algorithms.search.Solution;
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
		Thread t=new Thread(new NewCLIRunnable(myCLI));
		t.start();
	}
	
	/**
	 * Sets out hashmap commands with the received hashmap.
	 * @param commands hashmap of string<->command containing out available commands.
	 */
	@Override
	public void setCommands(HashMap<String, Command> commands) {
		myCLI=new NewCLI(in, out, commands,this);
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



}
