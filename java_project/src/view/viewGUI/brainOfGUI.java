package view.viewGUI;

import java.util.HashMap;
import java.util.Observable;
import java.util.Queue;

import View.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import view.View;

public class brainOfGUI extends Observable {
	HashMap<String, Command> commands;
	Queue<Command> commandsList;
	
	public void generateMaze(String name,int rows,int cols){
		commandsList.add(commands.get("generateMaze"));
		this.setChanged();
		this.notifyObservers(name+" "+rows+","+cols);
	}


	public void setCommands(HashMap<String, Command> commands) {
		this.commands=commands;
	}

	
	public Command getUserCommand() {
		return commandsList.poll();
	}


	public void displaymaze(String name) {
		commandsList.add(commands.get("displayMaze"));
		this.setChanged();
		this.notifyObservers(name);
	}

}
