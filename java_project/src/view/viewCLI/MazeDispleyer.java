package view.viewCLI;

import java.io.PrintStream;

import algorithms.mazeGenerators.Maze;

/**
 * MazeDispleter interface.
 * 
 * @author Bar Magnezi and Senia Kalma
 * @version 1.0
 * @since 17.5.2015
 */
public interface MazeDispleyer {
	/**
	 * prints out received maze to the received outputStream.
	 * 
	 * @param m
	 *            received maze.
	 * @param out
	 *            received outputStream.
	 */
	public void DisplayMaze(Maze m, PrintStream out);
}
