package view.viewGUI.mazeDisplayerAndCharecters;

import algorithms.mazeGenerators.Maze;

public class Game {
	Maze m;

	public Game(Maze maze) {
		this.m=maze;
	}

	public Maze getMaze() {
		return m;
	}

	public void setMaze(Maze m) {
		this.m = m;
	}
}
