package view;

import java.util.HashMap;

import View.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;


public interface View {
	void start(); 
	void setCommands(HashMap<String, Command> commands); 
	Command getUserCommand(); 
	void displayMaze(Maze m); 
	void displaySolution(Solution s);
	void displayString(String msg);
}
