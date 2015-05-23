package view.viewGUI;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import View.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import view.View;

public class ViewGUI extends Observable implements View{

	HashMap<String, Command> commands;
	Queue<Command> commandsList;
	MazeViewWidget Widget;
	
	public ViewGUI(MazeViewWidget widget) {
		super();
		Widget = widget;
		commandsList= new LinkedList<Command>();
	}
	
	public void setCommands(HashMap<String, Command> commands) {
		System.out.println(commands.size());
		this.commands=commands;
	}

	public Command getUserCommand() {
		return commandsList.poll();
	}
	
	public void generateMaze(String name,int rows,int cols){
		if (commands.get("generateMaze")==null)
			System.out.println("Avdv");
		commandsList.add(commands.get("generateMaze"));
		this.setChanged();
		this.notifyObservers(name+" "+rows+","+cols);
	}

	public void solveMaze(String name)
	{
		commandsList.add(commands.get("solveMaze"));
		this.setChanged();
		this.notifyObservers(name);
	}
	public void displaymaze(String name) {
		commandsList.add(commands.get("displayMaze"));
		this.setChanged();
		this.notifyObservers(name);
	}


	public void setproperties(String path) {
		commandsList.add(commands.get("setProperties"));
		this.setChanged();
		this.notifyObservers(path);
		
	}

	//view @Override
	@Override
	public void start() {
	}

	@Override
	public void displayMaze(Maze m) {
		Widget.displayMaze(m);
	}

	@Override
	public void displaySolution(Solution s) {
		Widget.displaySolution(s);
		
	}

	@Override
	public void displayString(String msg) {
		Widget.displayString(msg);
	}

}
