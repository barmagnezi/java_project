package view;

import java.io.PrintStream;

import algorithms.mazeGenerators.Maze;

public interface MazeDispleyer {
	public void DisplayMaze(Maze m,PrintStream out);
}
