package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
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
	boolean diag;
	ExecutorService executor;
	HashMap<String, Maze> nameMaze=new HashMap<>();
	HashMap<Maze, Solution> MazeSol=new HashMap<>();

	@Override
	public void generateMaze(String name,int col,int row) {
		//System.out.println("generateMaze");
		Future<Maze> f = executor.submit(new MazeCallable(MGenerator,col,row));
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
		nameMaze.put(name, maze);
		//NOTIFY
	}

	@Override
	public Maze getMaze(String name) {
		return nameMaze.get(name);
	}

	@Override
	public void solveMaze(Maze m) {
		if(!MazeSol.containsKey(m)){
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
			MazeSol.put(m, sol);
		}
		else{
			sol=MazeSol.get(m);
		}
		//NOTIFY
	}

	@Override
	public Solution getSolution(String name) {
		return MazeSol.get(nameMaze.get(name));
	}

	@Override
	public void stop() {
		System.out.println("Stop");
		executor.shutdown();	// ??
		//Huffman vs ZIP ??
		//From Hash maps to "resources/data.bin".
		FileOutputStream out = null;
		ObjectOutputStream out2 = null;
		try {
			out = (new FileOutputStream("resources/data.bin"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			out2 = new ObjectOutputStream(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out2.writeObject(nameMaze);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out2.writeObject(MazeSol);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start(){
		//Reading from "resources/data.bin" to Hash maps.
		FileInputStream in = null;
		ObjectInputStream in2=null;
		try {
			in = (new FileInputStream("resources/data.bin"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			in2 = new ObjectInputStream(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.MazeSol=(HashMap<Maze, Solution>) in2.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.nameMaze=(HashMap<String, Maze>) in2.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public int setGeneralProperties(String str){	//"X" - X for number of Allowed Threads.
		/**
		 * Receives a String containing the general values of MyModel
		 * str="5 ....etc"
		 * 5 as the number of allowed threads.
		 */
		String splited[] = str.split(" ");
		AllowedThreads = Integer.parseInt(splited[0]);
		executor = Executors.newFixedThreadPool(AllowedThreads);
		
		return 1;
	}
	
	public int setMazeProperties(String str){	//"DFS BFS 1"(1 for diag), "Random Astar Air 0"(Air-for AirDistance, 0 for no diag).
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
