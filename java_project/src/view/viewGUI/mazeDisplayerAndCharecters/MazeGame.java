package view.viewGUI.mazeDisplayerAndCharecters;

import view.viewGUI.mazeViewWidjet.CommonGame;
import algorithms.mazeGenerators.Maze;
/**
* The class implements from CommonGame and contains maze
*  
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 16.6.2015
*/
public class MazeGame implements CommonGame{
	
	Maze m;

	public MazeGame(Maze maze) {
		this.m=maze;
	}
	
	/**
	 * return the maze
	 */
	@Override
	public Object getGame() {
		return m;
	}
	/**
	 * set the maze
	 * @param m The maze we want to save in the class
	 */
	@Override
	public void setGame(Object m) {
		this.m = (Maze) m;
	}
}
