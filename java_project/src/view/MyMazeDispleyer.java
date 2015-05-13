package view;

import java.io.PrintStream;

import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;

public class MyMazeDispleyer implements MazeDispleyer {

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
