package model;

import java.util.Observable;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public class MyModel extends Observable implements Model {

	@Override
	public void generateMaze(String name, int rows,int cols) {
		System.out.println("generateMaze");
		this.setChanged();
		this.notifyObservers("generateMazeCompleted "+name);
	}

	@Override
	public Maze getMaze(String name) {
		System.out.println("getMaze");
		return null;
	}

	@Override
	public void solveMaze(Maze m) {
		System.out.println("solveMaze");
		this.setChanged();
		this.notifyObservers("solveMazeCompleted "+"mazeName"); //only for example
		//this.notifyObservers("solveMazeCompleted "+name);
	}

	@Override
	public Solution getSolution(String name) {
		System.out.println("getSolution");
		return null;
	}

	@Override
	public void stop() {
		System.out.println("Stop");

	}

}
