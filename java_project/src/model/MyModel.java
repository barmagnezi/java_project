package model;

import java.util.Observable;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public class MyModel extends Observable implements Model {

	@Override
	public void generateMaze() {
		System.out.println("generateMaze");
		this.setChanged();
		this.notifyObservers("generateMazeCompleted");
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
