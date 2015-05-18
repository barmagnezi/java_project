package model;

import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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

/**
* The MyModel class extends Observable and implements Model.
* It is the part that makes all the calculations, such as creating a maze, and finding a solution for it.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 17.5.2015
*/
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
	/**
	 * This method generates a maze using threads with an inputed data.
	 * After it finishes it sent a notification with as "maze "+name+" is ready.".
	 * @param name The name we want to give the maze.
	 * @param col The number of columns our maze will have (>2)
	 * @param row The number of rows our maze will have (>2)
	 */
	public void generateMaze(String name, int col,int row) {
		Future<Maze> f = executor.submit(new MazeCallable(properties.getMGenerator(),col,row));
		Maze maze = null;
		try {
			maze = f.get();
		} catch (InterruptedException e) {
			this.setChanged();
			this.notifyObservers("error while creating a maze.");
		} catch (ExecutionException e) {
			this.setChanged();
			this.notifyObservers("error while creating a maze.");
		} // w is still null�
		// wait till the maze is made after the callable was submitted to a thread
		try {
			maze = f.get();
		} catch (InterruptedException e) {
			this.setChanged();
			this.notifyObservers("error while creating a maze.");
		} catch (ExecutionException e) {
			this.setChanged();
			this.notifyObservers("error while creating a maze.");
		}
		
		while(nameMaze.containsKey(name)){		//if name=small exists, set name=small(1), if exists, name=small(2), ..
			int i=1;
			if(name.contains("(")){
				i=Integer.parseInt(name.substring(name.indexOf("(")+1, name.indexOf(")")));
				i++;
				name=name.substring(0, name.indexOf("(")+1)+i+")";
			}
			else
				name=name+"("+i+")";
		}
		
		nameMaze.put(name, maze);
		updateDataFlag=true;
		this.setChanged();
		this.notifyObservers("maze "+name+" is ready.");
		if(executorFlag==true)
			fin.notify();
	}

	/**
	 * Returns a maze by an inputed name(String).
	 */
	@Override
	public Maze getMaze(String name) {
		if(nameMaze.get(name)==null){
			this.setChanged();
			this.notifyObservers("maze "+name+" not found.");
			return null;
		}
		else
			return nameMaze.get(name);
	}
	
	/**
	 * This method solves a maze using threads.
	 * After it finishes it sent a notification with as "solution "+name+" is ready.".
	 * @param m The maze we want to solve.
	 */
	@Override
	public void solveMaze(Maze m) {
		if(m==null){
			this.setChanged();
			this.notifyObservers("maze "+m+" not found.");
			return;
		}
		Solution sol = null;
		if(!MazeSol.containsKey(m)){
			Future<Solution> f = executor.submit(new MazeSolveCallable(m,properties.isDiag(),Solver));
			try {
				sol = f.get();
			} catch (InterruptedException e) {
				this.setChanged();
				this.notifyObservers("error while creating a solution.");
			} catch (ExecutionException e) {
				this.setChanged();
				this.notifyObservers("error while creating a solution.");
			} // w is still null�
			// wait till the maze is solved after the callable was submitted to a thread
			try {
				sol = f.get();
			} catch (InterruptedException e) {
				this.setChanged();
				this.notifyObservers("error while creating a solution.");
			} catch (ExecutionException e) {
				this.setChanged();
				this.notifyObservers("error while creating a solution.");
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
	
	/**
	 * gets the name of an inputed maze.
	 * @param maze	the maze we want to find it's name.
	 * @return the name of the maze(string).
	 */
	private String getName(Maze maze)
	{
       for (String name : nameMaze.keySet()) {
           if (nameMaze.get(name).equals(maze)) 
               return name;
       }
	   return null;
	}
	
	/**
	 * gets the name of an inputed maze name.
	 * @param name	the name of the solution we want to find it's name.
	 * @return the name of the solution(string).
	 */
	@Override
	public Solution getSolution(String name) {

		if(MazeSol.get(nameMaze.get(name))==null){
			this.setChanged();
			this.notifyObservers("solution "+name+" not found");
			return null;
		}
		else
			return MazeSol.get(nameMaze.get(name));
	}
	
	/**
	 * Reads from the data file and sets the hashmaps by the data read.
	 */
	public void start(){
		readHashmapsFromFile();
	}
	
	/**
	 * Stops the work and saves the data.
	 */
	@Override
	public void stop() {
		this.executorFlag=true;
		executor.shutdown();
		while(!executor.isShutdown()){
			try {
				fin.wait();
			} catch (InterruptedException e1) {
				this.setChanged();
				this.notifyObservers("error while closing the threads.");
			}
		}
		if(updateDataFlag)
			writeHashmapsToFile();
		XMLEncoder e = null;
		try {
			e = new XMLEncoder(new FileOutputStream("resources/properties.xml"));
		} catch (FileNotFoundException e1) {
			this.setChanged();
			this.notifyObservers("error while saving the properties.");
		}
		e.writeObject(this.properties);
		e.flush();
		e.close();
	}
	
	/**
	 * Setting the current properties with an inputed PropertiesModel Object.
	 */
	public void setProperties(PropertiesModel prop){
		executorFlag=false;
		properties=prop;
		if(properties.getNameSolver().equals("BFS"))
			this.Solver=new BFSSearcher();
		else
			if(properties.getNameSolver().equals("Astar"))
				this.Solver=new AstarSearcher(properties.getHue());
			else{
				this.setChanged();
				this.notifyObservers("Properties file error");
			}
		executor = Executors.newFixedThreadPool(properties.getAllowedThreads());
	}
	
	/**
	 * Saves the generated mazes and solution in a file using Huffman encoding.
	 */
	public void writeHashmapsToFile(){
		try {
			PrintWriter writer=new PrintWriter(new HuffmanWriter(new FileOutputStream(properties.getFileDataMazes())));
			//PrintWriter writer=new PrintWriter(new OutputStreamWriter(new FileOutputStream(properties.getFileDataMazes())));
			for(String name:nameMaze.keySet()){
				Maze m=nameMaze.get(name);
				Solution s=MazeSol.get(nameMaze.get(name));
				if(s==null)
					writer.println(name+" "+StringMaze.MazeToString(m)+" x");
				else{
					writer.println(name+" "+StringMaze.MazeToString(m)+" "+StringSolution.SolutionToString(s));
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			this.setChanged();
			this.notifyObservers("error while huffman encoding.");
		}
	}
	
	/**
	 * reades the generated mazes and solution from a file using Huffman encoding.
	 */
	public void readHashmapsFromFile()
	{
		try {
			BufferedReader in=new BufferedReader(new HuffmanReader(new FileInputStream(properties.FileDataMazes)));
			//BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(properties.FileDataMazes)));
			String line;
			while((line=in.readLine())!=null){
				if(line.equals(""))
					break;
				String[] NameMazeSol=line.split(" ");
				Maze m=StringMaze.StringToMaze(NameMazeSol[1]);
				nameMaze.put(NameMazeSol[0],m);
				if(!NameMazeSol[2].equals("x"))
					MazeSol.put(m, StringSolution.StringToSolution(NameMazeSol[2]));			
			}
			in.close();
			//delete the buffer file that create in hufmman reader
	    	try{
	    		 
	    		File file = new File("buffer_reader(dontDeleteWhileUsingTheHuffmanReader)");
	    		file.delete();
	    	}catch(Exception e){
				this.setChanged();
				this.notifyObservers("error "+properties.FileDataMazes+" while huffman encoding.");
	    	}
		} catch (ClassNotFoundException | IOException e) {
			this.setChanged();
			this.notifyObservers("The file :"+properties.FileDataMazes+" that need to save the mazes not found.\nAfter exit will create new file with the mazes that you create.");
		}
	}
}	//Class close