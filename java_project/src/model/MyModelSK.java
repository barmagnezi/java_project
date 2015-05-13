package model;

import java.util.Observable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import algorithms.mazeGenerators.DFSMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MazeGenerator;
import algorithms.search.BFSSearcher;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import algorithms.search.aStar.AstarSearcher;
import algorithms.search.aStar.Heuristic;
import algorithms.search.aStar.MazeAirDistance;
import algorithms.search.aStar.MazeManhhetenDistance;

public class MyModelSK extends Observable implements Model {
	MazeGenerator MGenerator;
	Searcher MSolver;
	Maze maze;
	Solution sol;
	int AllowedThreads;
	int cols;
	int rows;
	boolean diag;
	ExecutorService executor;

	@Override
	public void generateMaze(int col,int row) {
		//System.out.println("generateMaze");
		this.cols=col;
		this.rows=row;
		Future<Maze> f = executor.submit(new MazeCallable(MGenerator,cols,rows));
		try {
			maze = f.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} // w is still null…
		// wait till the maze is made after the callable was submitted to a thread
		try {
			maze = f.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		//NOTIFY
	}

	@Override
	public Maze getMaze() {
		return maze;
	}

	@Override
	public void solveMaze(Maze m) {
		//System.out.println("solveMaze");
		Future<Solution> f = executor.submit(new MazeSolveCallable(m,diag,MSolver));
		try {
			sol = f.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} // w is still null…
		// wait till the maze is solved after the callable was submitted to a thread
		try {
			sol = f.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		//NOTIFY
	}

	@Override
	public Solution getSolution() {
		return sol;
	}

	@Override
	public void stop() {
		System.out.println("Stop");
	}
	
	
	public int setGeneralProperties(String str){	//"X col row" - X for number of Allowed Threads.
		/**
		 * Receives a String containing the general values of MyModel
		 * str="5 9 8 ...etc"
		 * 5 as the number of allowed threads.
		 * 9 as the number of cols.
		 * 8 as the number of rows.
		 */
		String splited[] = str.split(" ");
		AllowedThreads = Integer.parseInt(splited[0]);
		executor = Executors.newFixedThreadPool(AllowedThreads);
		cols = Integer.parseInt(splited[1]);
		rows = Integer.parseInt(splited[2]);
		return 1;
	}
	
	public int setMazeProperties(String str){	//"DFS BFS 1"(1 for allowed diag), "Random Astar Air 0"(Air-for AirDistance, 0 for no diag).
		/**
		 * Receives a String containing the values of how we Generate a maze and how we Solve it and a boolean representing if its with(1) or without(0) diagonals.
		 * str="DFS/Random BFS/Astar 1", if Astar chosen we need to specify what Heuristic it will use - "Air/Man".(for example - "DFS Astar Man 0")
		 * If finished successfully, returns 1.
		 * if not enough arguments-for Astar, return 2.-for diag, return 3;
		 * if error, returns -1.
		 */
		String splited[] = str.split(" ");
		boolean flag1=false,flag2=false,flag3=false;
		//Sets Generator:
		if(splited[0].equalsIgnoreCase("DFS")){
			flag1=true;
			MGenerator = new DFSMazeGenerator();
		}
		if(splited[0].equalsIgnoreCase("Random")){
			flag1=true;
			MGenerator = new DFSMazeGenerator();
		}
		//Sets Solver:
		if(splited[1].equalsIgnoreCase("BFS")){
			flag2=true;
			MSolver = new BFSSearcher();
		}
		if(splited[1].equalsIgnoreCase("Astar")){
			flag2=true;
			if(splited.length<2){
				return 0;
			}
			Heuristic Hur = null;
			if(splited[2].equalsIgnoreCase("Air"))
				Hur = new MazeAirDistance();
			if(splited[2].equalsIgnoreCase("Man"))
				Hur = new MazeManhhetenDistance();
			if(Hur==null)
				return 2;
			MSolver = new AstarSearcher(Hur);
		}
		if(Integer.parseInt(splited[3])==0 || Integer.parseInt(splited[3])==1){
			flag3=true;
			if(Integer.parseInt(splited[3])==0)
				diag=false;
			else
				diag=true;
		}
		if(flag1==false || flag2==false)
			return -1;
		if(flag3==false)
			return 3;
		
		return 1;
		
	}

	
	public MazeGenerator getMGenerator() {
		return MGenerator;
	}

	public Searcher getMSolver() {
		return MSolver;
	}

}
