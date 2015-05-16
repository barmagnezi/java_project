package boot;

import model.StringMaze;
import model.StringSolution;
import algorithms.demo.MazeSearchable;
import algorithms.mazeGenerators.DFSMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.search.BFSSearcher;
import algorithms.search.CommonSearcher;
import algorithms.search.Solution;

public class tests {

	public static void main(String[] args) {
		/*
		 * test for StringMaze
		Maze maze=new DFSMazeGenerator().generateMaze(10, 20);
		maze.print();
		String strmaze=new StringMaze().MazeToString(maze);
		System.out.println(strmaze);
		new StringMaze().StringToMaze(strmaze).print();*/
		/*
		 * test for Stringsol
		 
		Maze m=(new DFSMazeGenerator()).generateMaze(10, 10);		
		m.print();
		MazeSearchable MS=new MazeSearchable(m, false);
		CommonSearcher se=new BFSSearcher();
		Solution sol=se.search(MS);
		sol.print();
		String strsol=StringSolution.SolutionToString(sol);
		System.out.println(strsol);
		StringSolution.StringToSolution(strsol).print();*/
	}

}
