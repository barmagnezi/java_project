package boot;

import algorithms.demo.MazeSearchable;
import algorithms.mazeGenerators.DFSMazeGenerator;
import algorithms.search.Searchable;
import algorithms.search.Solution;
import algorithms.search.aStar.AstarSearcher;
import algorithms.search.aStar.MazeAirDistance;

public class tests {

	public static void main(String[] args) {
		test2();
	}
	public void test1(){
		/*
		HashMap<String, Maze> nameMaze=new HashMap<>();
		HashMap<Maze, Solution> MazeSol=new HashMap<>();
		Maze m = new Maze(10, 10);	
		Solution sol = new Solution();
		nameMaze.put("senia", m);
		MazeSol.put(m, sol);
		mazesToFromDatabase myf = new mazesToFromDatabase();
		myf.writeToDatabase(nameMaze, MazeSol);
		/*Maze maze=new DFSMazeGenerator().generateMaze(100, 100);
		String strmaze=new StringMaze().MazeToString(maze);
		System.out.println(strmaze.length());
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
	public static void test2(){
		AstarSearcher se=new AstarSearcher(new MazeAirDistance());
		Searchable maze=new MazeSearchable(new DFSMazeGenerator().generateMaze(10, 10), true);
		Solution sol = se.search(maze);
		sol.print();
	}
	

}
