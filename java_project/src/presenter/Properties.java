package presenter;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;

import algorithms.mazeGenerators.DFSMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MazeGenerator;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import algorithms.search.aStar.AstarSearcher;
import algorithms.search.aStar.Heuristic;
import algorithms.search.aStar.MazeAirDistance;

public class Properties implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int AllowedThreads;
	MazeGenerator MGenerator;
	Searcher MSolver;
	Heuristic Hue;
	boolean diag;
	HashMap<String, Maze> nameMaze=new HashMap<>();
	HashMap<Maze, Solution> MazeSol=new HashMap<>();
	
	public Properties() {
	}
	
	public Properties(String path) {
		XMLDecoder XML = null;
		try {
			XML = new XMLDecoder(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties prop=(Properties) XML.readObject();
		if(XML==null || prop==null){
			this.setAllowedThreads(3);				//Setting default values for not found XML.
			this.setMGenerator(new DFSMazeGenerator());
			Heuristic Hur = new MazeAirDistance();
			this.setHue(Hur);
			this.setMSolver(new AstarSearcher(Hur));
			this.setDiag(true);
			HashMap<String, Maze> nameMaze = null;
			this.setNameMaze(nameMaze);
			HashMap<Maze, Solution> mazeSol = null;
			this.setMazeSol(mazeSol);
		}
		else{
			this.setAllowedThreads(prop.getAllowedThreads());
			this.setMGenerator(getMGenerator());
			this.setHue(prop.getHue());
			this.setMSolver(prop.getMSolver());
			this.setDiag(prop.isDiag());
			this.setMazeSol(prop.getMazeSol());
			this.setNameMaze(prop.getNameMaze());
		}
		XML.close();
	}

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
	public Searcher getMSolver() {
		return MSolver;
	}
	public void setMSolver(Searcher mSolver) {
		MSolver = mSolver;
	}
	public boolean isDiag() {
		return diag;
	}
	public void setDiag(boolean diag) {
		this.diag = diag;
	}
	public HashMap<String, Maze> getNameMaze() {
		return nameMaze;
	}
	public void setNameMaze(HashMap<String, Maze> nameMaze) {
		this.nameMaze = nameMaze;
	}
	public HashMap<Maze, Solution> getMazeSol() {
		return MazeSol;
	}
	public void setMazeSol(HashMap<Maze, Solution> mazeSol) {
		MazeSol = mazeSol;
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
