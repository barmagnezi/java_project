package view.viewGUI.mazeDisplayerAndCharecters;

import view.viewGUI.mazeViewWidjet.CommonGame;
import algorithms.mazeGenerators.Maze;

public class MazeGame extends CommonGame{
	
	Maze m;

	public MazeGame(Maze maze) {
		this.m=maze;
	}

	@Override
	public Object getGame() {
		return m;
	}
	@Override
	public void setGame(Object m) {
		this.m = (Maze) m;
	}
}
