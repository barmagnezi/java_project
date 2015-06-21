package junit;

import static org.junit.Assert.*;

import java.util.Stack;

import model.modelOffline.MazeSearchableFixed;

import org.junit.Test;

import algorithms.mazeGenerators.DFSMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Searchable;
import algorithms.search.State;
import algorithms.search.aStar.MazeAirDistance;
/**
 * The class test the Astar Searcher by few of checks
 * 
 * @author Bar Magnezi(209043827) and Senia Kalma(321969941)
 * @version 1.0
 * @since 16.6.2015
 *
 */
public class AstarSearcherTest {

	@Test
	public void test() {

		try {
			for (int i = 0; i < 3; i++) {
				AstarSearcher AstarSearcher = new AstarSearcher(
						new MazeAirDistance());
				Maze mm = new DFSMazeGenerator().generateMaze(7, 7);
				mm.print();
				Searchable maze = new MazeSearchableFixed(mm, true);
				Stack<State> sol = AstarSearcher.search(maze).getSol();
				while (sol.size() != 1) {
					String[] Current = sol.pop().getState().split("x");
					State nextstate = sol.pop();
					System.out.println(nextstate.getState());
					String[] next = nextstate.getState().split("x");
					if (!CheckMotion(Integer.parseInt(Current[1]),
							Integer.parseInt(Current[0]),
							Integer.parseInt(next[1]),
							Integer.parseInt(next[0]), mm, true))
						fail("solution from Astar wrong");
					sol.add(nextstate);
				}
				sol = null;
				maze = null;
				mm = null;
			}
		} catch (Exception s) {
			fail("Astar dont work on maze 10,10 with diag");
		}
		try {
			for (int i = 0; i < 10; i++) {
				AstarSearcher AstarSearcher = new AstarSearcher(
						new MazeAirDistance());
				Maze mm = new DFSMazeGenerator().generateMaze(3, 3);
				Searchable maze = new MazeSearchableFixed(mm, true);
				Stack<State> sol = AstarSearcher.search(maze).getSol();
				while (sol.size() != 1) {
					String[] Current = sol.pop().getState().split("x");
					State nextstate = sol.pop();
					String[] next = nextstate.getState().split("x");
					if (!CheckMotion(Integer.parseInt(Current[1]),
							Integer.parseInt(Current[0]),
							Integer.parseInt(next[1]),
							Integer.parseInt(next[0]), mm, true))
						fail("solution from Astar wrong");
					sol.add(nextstate);
				}
			}
		} catch (Exception s) {
			fail("Astar dont work on maze 10,10 without diag");
		}

	}

	public boolean CheckMotion(int CurrentCol, int CurrentRow, int nextCol,
			int nextRow, Maze maze, boolean Diagonals) {
		boolean flag = true;
		if (nextCol < 0 || nextRow < 0 || nextCol == maze.getCols()
				|| nextRow == maze.getRows())
			return false;
		if (!(nextCol != CurrentCol && CurrentRow != nextRow)) {
			if (CurrentCol + 1 == nextCol)
				flag = flag
						&& (!maze.getCell(CurrentRow, CurrentCol)
								.getRightWall().isExist());
			if (CurrentCol - 1 == nextCol)
				flag = flag
						&& (!maze.getCell(nextRow, nextCol).getRightWall()
								.isExist());
			if (CurrentRow + 1 == nextRow)
				flag = flag
						&& (!maze.getCell(CurrentRow, CurrentCol)
								.getBottomWall().isExist());
			if (CurrentRow - 1 == nextRow)
				flag = flag
						&& (!maze.getCell(nextRow, nextCol).getBottomWall()
								.isExist());
			return flag;
		} else {
			if (Diagonals == true) {
				int col = CurrentCol;
				int row = CurrentRow;
				// right-up
				if (CurrentCol + 1 == nextCol && CurrentRow - 1 == nextRow) {
					if (maze.getCell(row - 1, col).getRightWall().isExist() && // |_
							maze.getCell(row - 1, col + 1).getBottomWall()
									.isExist())// .
						flag = false;
					if (maze.getCell(row, col).getRightWall().isExist() && // |
							maze.getCell(row - 1, col).getRightWall().isExist()) // .|
						flag = false;
					if (maze.getCell(row - 1, col).getBottomWall().isExist() && // _
																				// _
							maze.getCell(row - 1, col + 1).getBottomWall()
									.isExist()) // .
						flag = false;
					if (maze.getCell(row - 1, col).getBottomWall().isExist() && // /corner
																				// like
																				// ._|
							maze.getCell(row, col).getRightWall().isExist())
						flag = false;
					return flag;
				}
				// right-down
				if (CurrentCol + 1 == nextCol && CurrentRow + 1 == nextRow) {
					if (maze.getCell(row + 1, col).getRightWall().isExist() && // .
							maze.getCell(row, col + 1).getBottomWall()
									.isExist())// |-
						flag = false;
					if (maze.getCell(row, col).getRightWall().isExist() && // .
																			// |
							maze.getCell(row + 1, col).getRightWall().isExist()) // |
						flag = false;
					if (maze.getCell(row, col).getBottomWall().isExist() && // .
							maze.getCell(row, col + 1).getBottomWall()
									.isExist()) // --
						flag = false;
					if (maze.getCell(row, col).getBottomWall().isExist() && // /corner
																			// like
																			// ._|
							maze.getCell(row, col).getRightWall().isExist())
						flag = false;
					return flag;
				}
				// left-down
				if (CurrentCol - 1 == nextCol && CurrentRow + 1 == nextRow) {
					if (maze.getCell(row + 1, col - 1).getRightWall().isExist()
							&& // .
							maze.getCell(row, col - 1).getBottomWall()
									.isExist()) // -|
						flag = false;
					if (maze.getCell(row, col - 1).getRightWall().isExist() && // |.
							maze.getCell(row + 1, col - 1).getRightWall()
									.isExist()) // |
						flag = false;
					if (maze.getCell(row, col).getBottomWall().isExist() && // .
							maze.getCell(row, col - 1).getBottomWall()
									.isExist()) // --
						flag = false;
					if (maze.getCell(row, col).getBottomWall().isExist() && // /corner
																			// like
																			// ._|
							maze.getCell(row, col - 1).getRightWall().isExist())
						flag = false;
					return flag;
				}
				// left-up
				if (CurrentCol - 1 == nextCol && CurrentRow - 1 == nextRow) {
					if (maze.getCell(row - 1, col - 1).getRightWall().isExist()
							&& // _|
							maze.getCell(row - 1, col - 1).getBottomWall()
									.isExist()) // .
						flag = false;
					if (maze.getCell(row, col - 1).getRightWall().isExist() && // |
							maze.getCell(row - 1, col - 1).getRightWall()
									.isExist()) // |.
						flag = false;
					if (maze.getCell(row - 1, col).getBottomWall().isExist() && // _
																				// _
							maze.getCell(row - 1, col - 1).getBottomWall()
									.isExist()) // .
						flag = false;
					if (maze.getCell(row - 1, col).getBottomWall().isExist() && // /corner
																				// like
																				// ._|
							maze.getCell(row, col - 1).getRightWall().isExist())
						flag = false;
					return flag;
				}
			} else
				return false;
		}
		return false;

	}

}
