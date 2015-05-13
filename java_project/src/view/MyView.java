package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Observable;



import java.util.Queue;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import presenter.Command;

public class MyView extends Observable implements View {
	NewCLI myCLI;
	BufferedReader in;
	PrintStream out;
	Queue<Command> commands;
	/**
	 * This constructor create view by using the arguments for input and output 
	 * @param r The input for the user
	 * @param out The output for the user
	 */
	public MyView(BufferedReader r,PrintStream out) {
		super();
		this.in=r;
		this.out=out;
	}
	/**
	 * This constructor create view by using the default system output and input
	 */
	public MyView() {
		super();
		this.in=new BufferedReader(new InputStreamReader(System.in));
		this.out=System.out;
	}
	
	@Override
	public void start() {
		System.out.println("start");
		this.setChanged();
		this.notifyObservers("start");
		myCLI.start();
	}

	@Override
	public void setCommands(HashMap<String, Command> commands) {
		System.out.println("setCommands");
		myCLI=new NewCLI(in, out, commands,this);
	}

	@Override
	public Command getUserCommand() {
		System.out.println("getUserCommand");
		return commands.poll();
	}

	@Override
	public void displayMaze(Maze m) {
		System.out.println("displayMaze");

	}

	@Override
	public void displaySolution(Solution s) {
		System.out.println("displaySolution");

	}
	public void Notify(String arg) {
		this.setChanged();
		this.notifyObservers(arg);
	}

}
