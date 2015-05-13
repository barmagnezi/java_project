package view;

import java.util.HashMap;
import java.util.Observable;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import presenter.Command;

public class MyView extends Observable implements View {
	HashMap<String, Command> commands;

	@Override
	public void start() {
		System.out.println("start");
		this.setChanged();
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void setCommands(HashMap<String, Command> commands) {
		System.out.println("setCommands");
		this.commands=commands;
		
	}

	@Override
	public Command getUserCommand() {
		System.out.println("getUserCommand");
		return commands.get("test");
	}

	@Override
	public void displayMaze(Maze m) {
		System.out.println("displayMaze");

	}

	@Override
	public void displaySolution(Solution s) {
		System.out.println("displaySolution");

	}

}
