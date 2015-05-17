package view;

import java.io.PrintStream;

import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;

/**
* MyModelDispleter class implements MazeDispleter.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 17.5.2015
*/
public class MyMazeDispleyer implements MazeDispleyer {
	
	/**
	 * prints out received maze to the received outputStream.
	 * @param m received maze.
	 * @param out received outputStream.
	 */
	@Override
	public void DisplayMaze(Maze m, PrintStream out) {
		int cols=m.getCols();
		int rows=m.getRows();
		
		for (int j = 0; j < cols; j++)
			out.print("__");
		out.println("_");
		
		for (int i = 0; i < rows; i++) {
			out.print("|");
			for (int j = 0; j < cols; j++) {				
				Cell cell = m.getCell(i, j);
				if (cell.getBottomWall().isExist())
					out.print("_");
				else
					out.print(" ");
				
				if (cell.getRightWall().isExist())
					out.print("|");
				else
					out.print(" ");	
								
			}
			out.println();
		}
	}

}
