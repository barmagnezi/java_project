package view.viewGUI.GameWidget;

import algorithms.demo.MazeSearchable;
import algorithms.mazeGenerators.Maze;
import algorithms.search.CommonSearcher;
import algorithms.search.Solution;
import algorithms.search.aStar.AstarSearcher;
import algorithms.search.aStar.MazeAirDistance;

public class test {
	public static void main(String[] args) {
		new testwindow("bar",500,500).run();
	}
}

/*
	public void clue(Maze maze){
		//System.out.println("clue");
		int x=MazeDisplayer.character.getRealx();
		int y=MazeDisplayer.character.getRealy();
		MazeSearchable MS = new MazeSearchable(maze, maze.getCell(y, x), maze.getCell(maze.getRows()-1, maze.getCols()-1), Diagonals, 10, 15); //new MazeSearchable(maze, false);	
		CommonSearcher se=new AstarSearcher(new MazeAirDistance());
		Solution Sol=se.search(MS);

		String last[] = Sol.toString().split("->");
		//System.out.println(good);
		//Next step(clue) is:
		if(x!=maze.getCols()-1 || y!=maze.getCols()-1)
			good=true;
		if(good){		//if good=false we have finished, next iteration will be error without this if.
			boolean inflag=true;
			//System.out.println(last[0]);
			if(x==maze.getCols()-1 && y==maze.getCols()-1 || x==maze.getCols()-2 && y==maze.getCols()-1 || x==maze.getCols()-1 && y==maze.getCols()-2)
				inflag=false;
			if(inflag){
				int Cluex=Character.getNumericValue(last[1].charAt(0));
				int Cluey=Character.getNumericValue(last[1].charAt(2));
				//System.out.println(maze.getCols());
				if(Cluex==maze.getCols()-1 && Cluey==maze.getCols()-1)
					good=false;
				if(Cluex!=maze.getCols() && Cluex!=maze.getCols())
					MazeDisplayer.mark(Cluey, Cluex);		//Swap because in the solver it set as col,row and here its x,y
				//System.out.println(good);
				//System.out.println(last[0]);
				//System.out.println("next x is: "+Cluex+" next y is: "+Cluey);
			}else
				MazeDisplayer.mark(maze.getCols()-1, maze.getCols()-1);
		}
	}
*/