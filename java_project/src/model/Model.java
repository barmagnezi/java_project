package model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public interface Model {
	void generateMaze(String name,int rows, int cols); 
	Maze getMaze(String name); 
	void solveMaze(Maze m); 
	Solution getSolution(String name); 
	void stop(); 
}
