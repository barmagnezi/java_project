package model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public interface Model {
	void generateMaze(int rows,int Columns); 
	Maze getMaze(); 
	void solveMaze(Maze m); 
	Solution getSolution(); 
	void stop(); 
}
