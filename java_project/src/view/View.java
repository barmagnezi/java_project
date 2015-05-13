package view;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import controller.Command;

public interface View {
	void start(); 
	void setCommands(/* a collection of Commands*/); 
	Command getUserCommand(); 
	void displayMaze(Maze m); 
	void displaySolution(Solution s);
}
