package view;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import controller.Command;

public class MyView implements View {

	@Override
	public void start() {
		System.out.println("start");

	}

	@Override
	public void setCommands() {
		System.out.println("setCommands");

	}

	@Override
	public Command getUserCommand() {
		System.out.println("getUserCommand");
		return null;
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
