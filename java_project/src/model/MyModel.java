package model;

import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import model.PropertiesModel;
import algorithms.compression.HuffmanReader;
import algorithms.compression.HuffmanWriter;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Searcher;
import algorithms.search.Solution;

//For temp fix:
import algorithms.search.BFSSearcher;
import algorithms.search.aStar.AstarSearcher;
import algorithms.search.aStar.MazeAirDistance;
import algorithms.search.aStar.MazeManhhetenDistance;

public class MyModel extends Observable implements Model {
	ExecutorService executor;
	HashMap<String, Maze> nameMaze=new HashMap<>();
	HashMap<Maze, Solution> MazeSol=new HashMap<>();
	PropertiesModel properties;
	boolean executorFlag;
	boolean updateDataFlag=false;
	Object fin;
	Searcher Solver;
	@Override
	public void generateMaze(String name, int col,int row) {
		Future<Maze> f = executor.submit(new MazeCallable(properties.getMGenerator(),col,row));
		Maze maze = null;
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
			e.printStackTrace();}
		/*int i=0;
		while(nameMaze.containsKey(name)){		//if name=small exists, set name=small1, if exists, name=small2, ..
			i++;
			if(i!=1)
				name=name.substring(0, name.length()-1);
			name+=i;
			if(i==10)
				i=0;
		}*/
		while(nameMaze.containsKey(name)){
			int i=1;
			if(name.contains("(")){
				i=Integer.parseInt(name.substring(name.indexOf("("), name.indexOf(")")));
				i++;
				name=name.substring(0, name.indexOf("("))+i+")";
			}
			else
				name=name+"("+i+")";
		}
		nameMaze.put(name, maze);
		updateDataFlag=true;
		this.setChanged();
		this.notifyObservers("maze "+name+" is ready");
		if(executorFlag==true)
			fin.notify();
	}

	@Override
	public Maze getMaze(String name) {
		return nameMaze.get(name);
	}

	@Override
	public void solveMaze(Maze m) {
		if(m==null){
			System.out.println("error");
			return;
		}
		Solution sol = null;
		if(!MazeSol.containsKey(m)){
			Future<Solution> f = executor.submit(new MazeSolveCallable(m,properties.isDiag(),Solver));
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
			nameMaze.put(getName(m), m);
			updateDataFlag=true;
		}
		this.setChanged();
		this.notifyObservers("solution for  "+getName(m)+" is ready");
		if(executorFlag==true)
			fin.notify();
	}
	
	private String getName(Maze maze)
	{
       for (String name : nameMaze.keySet()) {
           if (nameMaze.get(name).equals(maze)) {
               return name;
           }
       }
	   return null;
	}

	@Override
	public Solution getSolution(String name) {
		return MazeSol.get(nameMaze.get(name));
	}

	@Override
	public void stop() {
		this.executorFlag=true;
		executor.shutdown();
		while(!executor.isShutdown()){
			try {
				fin.wait();
			} catch (InterruptedException e1) {
				e1.printStackTrace();}
		}
		if(updateDataFlag)
			writeHashmapsToFile();
		/*
		//Huffman vs ZIP ??
		//From Hash maps to "resources/data.bin".
		FileOutputStream out = null;
		ObjectOutputStream out2 = null;
		try {
			out = (new FileOutputStream("resources/data.bin"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();}
		try {
			out2 = new ObjectOutputStream(out);
		} catch (IOException e) {
			e.printStackTrace();}
		try {
			out2.writeObject(nameMaze);		//(bar91) -here it crash when "exit" - because maze isn't serializable
		} catch (IOException e) {
			e.printStackTrace();}
		try {
			out2.writeObject(MazeSol);		//(bar91) - same here
		} catch (IOException e) {
			e.printStackTrace();}
		*/
		//Senia - Get(from Model) & Set properties(To XML)		=== START ===
		XMLEncoder e = null;
		try {
			e = new XMLEncoder(new FileOutputStream("resources/properties.xml"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();}
		e.writeObject(this.properties);
		e.flush();
		e.close();
		//														===  END  ===
	}
	public void start(){
		readHashmapsFromFile();
	}
	/*
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
	*/
	public void setProperties(PropertiesModel prop){
		executorFlag=false;
		properties=prop;
		if(properties.getNameSolver().equals("BFS"))
			this.Solver=new BFSSearcher();
		else
			if(properties.getNameSolver().equals("Astar"))
				this.Solver=new AstarSearcher(properties.getHue());
			else
				System.out.println("error prop");
		/*
		if(this.properties.getMSolver()==null)
			System.out.println("NO MSolver is set, temp fix downVV");	//(bar90), added some imports for this, marked as for temp fix
		if(this.properties.getHue()==null)
			this.properties.setMSolver(new BFSSearcher());
		if(this.properties.getHue().getClass()==new MazeAirDistance().getClass())
			this.properties.setMSolver(new AstarSearcher(new MazeAirDistance()));
		if(this.properties.getHue().getClass()==new MazeManhhetenDistance().getClass())
			this.properties.setMSolver(new AstarSearcher(new MazeManhhetenDistance()));
		if(this.properties.getMSolver()==null)
			System.out.println("BADDDDDDDDDDDDDDDDDDDDD");
		System.out.println("MSolver is set, as: "+this.properties.getMSolver());*/
		executor = Executors.newFixedThreadPool(properties.getAllowedThreads());
	}
	public void writeHashmapsToFile(){
		try {
			PrintWriter writer=new PrintWriter(new HuffmanWriter(new FileOutputStream(properties.getFileDataMazes())));
			for(String name:nameMaze.keySet()){
				Maze m=nameMaze.get(name);
				Solution s=MazeSol.get(m);
				if(s==null)
					writer.println(name+" "+StringMaze.MazeToString(m)+" x");
				else
					writer.println(name+" "+StringMaze.MazeToString(m)+" "+StringSolution.SolutionToString(s));
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("errorr");
		}
	}
	public void readHashmapsFromFile()
	{
		try {
			BufferedReader in=new BufferedReader(new HuffmanReader(new FileInputStream(properties.FileDataMazes)));
			String line;
			while((line=in.readLine())!=null){
				if(line.equals(""))
					break;
				String[] NameMazeSol=line.split(" ");
				nameMaze.put(NameMazeSol[0], StringMaze.StringToMaze(NameMazeSol[1]));
				if(!NameMazeSol[2].equals("x"))
					MazeSol.put(StringMaze.StringToMaze(NameMazeSol[1]), StringSolution.StringToSolution(NameMazeSol[2]));
							
			}
			in.close();
			//delete the buffer file that create in hufmman reader
	    	try{
	    		 
	    		File file = new File("buffer_reader(dontDeleteWhileUsingTheHuffmanReader)");
	    		System.out.println(file.exists());
	    		file.delete();
	    	}catch(Exception e){
	 
	    		e.printStackTrace();
	 
	    	}
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("error "+properties.FileDataMazes);
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