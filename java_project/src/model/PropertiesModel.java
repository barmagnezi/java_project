package model;

import java.beans.XMLDecoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import algorithms.mazeGenerators.DFSMazeGenerator;
import algorithms.mazeGenerators.MazeGenerator;
import algorithms.mazeGenerators.RandomMazeGenerator;
import algorithms.search.BFSSearcher;
import algorithms.search.Searcher;
import algorithms.search.aStar.AstarSearcher;
import algorithms.search.aStar.Heuristic;
import algorithms.search.aStar.MazeAirDistance;
import algorithms.search.aStar.MazeManhhetenDistance;
//name of solvers!!!
//Astar
//Astar
//BFS
public class PropertiesModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int AllowedThreads;
	MazeGenerator MGenerator;
	//Searcher MSolver;
	String nameSolver;
	Heuristic Hue;
	boolean diag;
	
	public PropertiesModel() { 
	}
	private void copyConstructor(PropertiesModel prop) {//bar way bar111
		this.AllowedThreads=prop.getAllowedThreads();
		this.MGenerator=prop.getMGenerator();
		this.Hue=prop.getHue();
		this.diag=prop.isDiag();
		this.nameSolver=prop.getNameSolver();

	}
	public PropertiesModel(InputStream from) {
		
		try{
			XMLDecoder XML = null;	
			XML = new XMLDecoder(from);	
			this.copyConstructor((PropertiesModel) XML.readObject());//bar way bar111
			/*PropertiesModel prop=null;
			prop=(PropertiesModel) XML.readObject();
			this.setAllowedThreads(prop.getAllowedThreads());
			this.setMGenerator(prop.getMGenerator());
			this.setHue(prop.getHue());
			this.setMSolver(prop.getMSolver());
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ (bar90) Doesnt put in the MSolver cuz error
			this.setDiag(prop.isDiag());*/ 
			XML.close();
		}catch(Exception e ){
			System.out.println("no found prop//run default values");
			/*try {
				this.changeProps();
			} catch (IOException e1) {
				e1.printStackTrace();}	^^ ask the user to input data*/
			this.setAllowedThreads(3);				//Setting default values for not found XML.
			System.out.println("setting generator");
			this.setMGenerator(new DFSMazeGenerator());
			System.out.println("set as: "+this.getMGenerator());
			Heuristic Hur = new MazeAirDistance();
			this.setHue(Hur);
			this.setNameSolver("Astar");
			this.setDiag(true);
		}finally{
			
		}	
	}
	

/*
	public void changeProps(){
		String str = null;
		String str2 = null;
		BufferedReader in =new BufferedReader(new InputStreamReader(System.in));
		do{
			System.out.println("Enter number of allowed threads:");
			try {
				str = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();}
			this.setAllowedThreads(Integer.parseInt(str));
		}while(this.getAllowedThreads()<1);
		do{
			System.out.println("Enter a maze generator method(DFS/Random):");
			try {
				str = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();}
			if(str.equalsIgnoreCase("DFS"))
				this.MGenerator = new DFSMazeGenerator();
			if(str.equalsIgnoreCase("Random"))
				this.MGenerator = new RandomMazeGenerator();
		}while(!str.equalsIgnoreCase("DFS") && !str.equalsIgnoreCase("Random"));
		do{
			System.out.println("Enter a maze solver method(BFS/Astar):");
			try {
				str = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();}
			if(str.equalsIgnoreCase("BFS")){
				this.MSolver = new BFSSearcher();
				//I set the Hue-Altought I dont need to-Becuase bug of Solver(DOESNT SAVE MAZE SOLVER)
				//^^^ Didnt work..
			}
			if(str.equalsIgnoreCase("Astar")){
				do{
					System.out.println("Enter a hueristic(Air/Man):");
					try {
						str2 = in.readLine();
					} catch (IOException e) {
						e.printStackTrace();}
					if(str2.equalsIgnoreCase("Air")){
						this.MSolver = new AstarSearcher(new MazeAirDistance());
						this.setHue(new MazeAirDistance());
					}
					if(str2.equalsIgnoreCase("Man")){
						this.MSolver = new AstarSearcher(new MazeManhhetenDistance());
						this.setHue(new MazeManhhetenDistance());
					}
				}while(str.equalsIgnoreCase("Air") && str.equalsIgnoreCase("Man"));
			}
		}while(!str.equalsIgnoreCase("BFS") && !str.equalsIgnoreCase("Astar"));
		do{
			System.out.println("Enter if you want diagonals or not(0/1):");
			try {
				str = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();}
			if(Integer.parseInt(str)==0)
				this.setDiag(false);
			if(Integer.parseInt(str)==1)
				this.setDiag(true);
		}while(Integer.parseInt(str)!=0 && Integer.parseInt(str)!=1);
	}
*/
/*
	public String toString(){
		String str="";
		str+=this.AllowedThreads+" "+this.MGenerator.toString()+" "+this.MSolver.toString();
		if(MSolver.toString().equalsIgnoreCase("BFS"))
			str+=" PlaceholderForNoHue";
		else
			str+=" "+this.Hue.toString();
		if(this.diag==true)
			str+=" 1";
		else
			str+=" 0";
		return str;
	}
*/	
	public int getAllowedThreads() {
		return AllowedThreads;
	}
	public void setAllowedThreads(int allowedThreads) {
		AllowedThreads = allowedThreads;
	}
	public MazeGenerator getMGenerator() {
		return MGenerator;
	}
	public void setMGenerator(MazeGenerator mGenerator) {
		MGenerator = mGenerator;
	}
	/*
	public Searcher getMSolver() {
		return MSolver;
	}
	public void setMSolver(Searcher mSolver) {
		MSolver = mSolver;
	}*/
	public boolean isDiag() {
		return diag;
	}
	public void setDiag(boolean diag) {
		this.diag = diag;
	}
	public String getNameSolver() {
		return nameSolver;
	}
	public void setNameSolver(String nameSolver) {
		this.nameSolver = nameSolver;
	}
	
	/*public void setPropeties(String path) {
		XMLDecoder XML = null;
		try {
			XML = new XMLDecoder(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties properties=(Properties) XML.readObject();
		if(XML==null || properties==null){ // ||m.setProperties(properties.toString())!=1){
			properties.setAllowedThreads(3);				//Setting default values for not found or bad XML file.
			properties.setDiag(true);
			properties.setMGenerator(new DFSMazeGenerator());
			Heuristic Hur = new MazeAirDistance();
			properties.setMSolver(new AstarSearcher(Hur));
			HashMap<String, Maze> nameMaze = null;
			properties.setNameMaze(nameMaze);
			HashMap<Maze, Solution> mazeSol = null;
			properties.setMazeSol(mazeSol);
			//m.setProperties(prop.toString());
			
		}
		XML.close();
	}*/

	public Heuristic getHue() {
		return Hue;
	}

	public void setHue(Heuristic hue) {
		Hue = hue;
	}
}
