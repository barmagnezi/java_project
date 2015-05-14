package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;
import algorithms.search.Solution;
import presenter.Command;
import algorithms.mazeGenerators.Maze;

public class MyView extends Observable implements View {
	NewCLI myCLI;
	BufferedReader in;
	PrintStream out;
	Queue<Command> commandsList;
	/**
	 * This constructor create view by using the arguments for input and output 
	 * @param r The input for the user
	 * @param out The output for the user
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
	
	@Override
	public void start() {
		System.out.println("start");
		this.setChanged();
		this.notifyObservers("start");
		Thread t=new Thread(new NewCLIRunnable(myCLI));
		t.start();
	}

	@Override
	public void setCommands(HashMap<String, Command> commands) {
		System.out.println("setCommands");
		myCLI=new NewCLI(in, out, commands,this);
	}

	@Override
	public Command getUserCommand() {
		System.out.println("getUserCommand");
		return commandsList.poll();
	}

	@Override
	public void displayMaze(Maze m) {
		System.out.println("displayMaze");
		
		MyMazeDispleyer md = new MyMazeDispleyer();
		md.DisplayMaze(m, out);
	}

	@Override
	public void displaySolution(Solution s) {
		System.out.println("displaySolution");
		
		MySolutionDispleyer sd = new MySolutionDispleyer();
		sd.DisplaySolution(s, out);
		
	}
	public void Notify(String arg) {
		this.setChanged();
		this.notifyObservers(arg);
	}

}
