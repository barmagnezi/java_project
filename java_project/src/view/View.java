package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import presenter.Command;

public interface View {
	void start(); 
	void setCommands(HashMap<String, Command> commands); 
	Command getUserCommand(); 
	void displayMaze(Maze m); 
	void displaySolution(Solution s);
}
