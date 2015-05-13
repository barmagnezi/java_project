package model;

import java.util.concurrent.Callable;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MazeGenerator;

public class MazeCallable implements Callable<Maze>{
	MazeGenerator mg;
	int rows;
	int cols;
	Maze m;
	
	public MazeCallable(MazeGenerator mGenerator, int col, int row) {
		this.mg=mGenerator;
		this.cols=col;
		this.rows=row;
	}
	@Override
	public Maze call() throws Exception {
		m=mg.generateMaze(rows, cols);
		return this.m;
	}
}
