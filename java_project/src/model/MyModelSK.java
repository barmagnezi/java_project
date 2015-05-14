package model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
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

import presenter.Properties;
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
	Maze maze;
	Solution sol;
	ExecutorService executor;
	HashMap<String, Maze> nameMaze=new HashMap<>();
	HashMap<Maze, Solution> MazeSol=new HashMap<>();

	@Override
	public void generateMaze(String name,int col,int row, Properties prop) {
		Future<Maze> f = executor.submit(new MazeCallable(prop.getMGenerator(),col,row));
		try {
			maze = f.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} // w is still null�
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
		this.setChanged();
		this.notifyObservers("generateMazeCompleted");
	}

	@Override
	public Maze getMaze(String name) {
		return nameMaze.get(name);
	}

	@Override
	public void solveMaze(Maze m, Properties prop) {
		if(!MazeSol.containsKey(m)){
			Future<Solution> f = executor.submit(new MazeSolveCallable(m,prop.isDiag(),prop.getMSolver()));
			try {
				sol = f.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} // w is still null�
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
	public void stop(Properties prop) {
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
		
		//Senia - Get(from Model) & Set properties(To XML)		=== START ===
		XMLEncoder e=new XMLEncoder(new FileOutputStream("resources/Properties.xml"));
		e.writeObject(prop);
		e.flush();
		e.close();
		//														===  END  ===
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
	
}	//Class close
	
	/*public int setProperties(String str){	//"5 DFS BFS 1"(5-Allowed threads,1-diag), "3 Random Astar Air 0"(3-threads,Air-for AirDistance, 0-diag).
		/**
		 * Receives a String containing the values of how how much thereads we allow, how we Generate a maze and how we Solve it and
		 * a boolean representing if its with(1) or without(0) diagonals.
		 * str="3 DFS/Random BFS/Astar 1", if Astar chosen we need to specify what Heuristic it will use -
		 * "Air/Man".(for example - "4 DFS Astar Man 0")
		 * If finished successfully, returns 1.
		 * if error, returns -1.
		 
		String splited[] = str.split(" ");
		boolean flag1=false,flag2=false;
		//Sets Threads:
		AllowedThreads = Integer.parseInt(splited[0]);
		if(AllowedThreads<1)
			return -1;
		executor = Executors.newFixedThreadPool(AllowedThreads);
		//Sets Generator:
		if(splited[1].equalsIgnoreCase("DFS")){
			flag1=true;
			MGenerator = new DFSMazeGenerator();
		}
		if(splited[1].equalsIgnoreCase("Random")){
			flag1=true;
			MGenerator = new DFSMazeGenerator();
		}
		//Sets Solver:
		if(splited[2].equalsIgnoreCase("BFS")){
			flag2=true;
			MSolver = new BFSSearcher();
		}
		if(splited[2].equalsIgnoreCase("Astar")){
			if(splited.length<2){
				return 0;
			}
			Heuristic Hur = null;
			if(splited[3].equalsIgnoreCase("Air"))
				Hur = new MazeAirDistance();
			if(splited[3].equalsIgnoreCase("Man"))
				Hur = new MazeManhhetenDistance();
			if(Hur==null)
				return -1;
			flag2=true;
			this.hur=Hur;
			MSolver = new AstarSearcher(Hur);
		}
		//Sets diagonal, 1-Allowed, 0 Forbidden.
		if(Integer.parseInt(splited[4])==0 || Integer.parseInt(splited[3])==1){
			if(Integer.parseInt(splited[4])==0)
				diag=false;
			else
				diag=true;
		}
		else
			return -1;
		if(flag1==false || flag2==false)
			return -1;
		
		return 1;
		
	}*/