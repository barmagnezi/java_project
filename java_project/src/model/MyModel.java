package model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public class MyModel implements Model {

	@Override
	public void generateMaze() {
		System.out.println("generateMaze");

	}

	@Override
	public Maze getMaze() {
		System.out.println("getMaze");
		return null;
	}

	@Override
	public void solveMaze(Maze m) {
		System.out.println("solveMaze");

	}

	@Override
	public Solution getSolution() {
		System.out.println("getSolution");
		return null;
	}

	@Override
	public void stop() {
		System.out.println("Stop");

	}

}
