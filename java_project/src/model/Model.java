package model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public interface Model {
	public void generateMaze(String name,int rows, int cols); 
	public Maze getMaze(String name); 
	public void solveMaze(Maze m); 
	public Solution getSolution(String name); 
	public void start();
	public void stop();
	public void setProperties(PropertiesModel mproperties);
}
