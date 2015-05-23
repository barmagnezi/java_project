package view.viewGUI;

import java.util.HashMap;
import java.util.Observable;
import java.util.Queue;

import View.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import view.View;

public class brainOfGUI extends Observable implements View {
	HashMap<String, Command> commands;
	Queue<Command> commandsList;
	
	public void generateMaze(String name,int rows,int cols){
		commandsList.add(commands.get("generateMaze"));
		this.setChanged();
		this.notifyObservers("name "+rows+","+cols);
	}


	@Override
	public void setCommands(HashMap<String, Command> commands) {
		this.commands=commands;
	}

	@Override
	public Command getUserCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void displayMaze(Maze m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySolution(Solution s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayString(String msg) {
		System.out.println("maze created");
		
	}
}
