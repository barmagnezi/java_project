package model;

import hibernate.HibernateClass;

import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import presenter.PropertiesModel;
import algorithms.compression.HuffmanReader;
import algorithms.compression.HuffmanWriter;
import algorithms.mazeGenerators.Maze;
import algorithms.search.BFSSearcher;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import algorithms.search.aStar.AstarSearcher;
import algorithms.search.aStar.MazeAirDistance;

public class OnlineModel extends Observable implements Model {
	public OnlineModel() {
		super();
		
	}

	//For Client:
	public Socket myServer;
	public PrintStream writer;
	public ObjectInputStream reader;

	public String res;
	protected Boolean run;
	
	public Maze m;
	public Solution sol;
	public String clue;
	
	public Object solWaiter;
	public Object getWaiter;
	public Object clueWaiter;
	
	private PropertiesModel properties;
	
	public void recive(){
		String com = null;
		String helper=null;
		while(run){
			try {
				com=(String) reader.readObject();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			if(com=="sentMaze"){
					try {
						helper = (String) reader.readObject();	//helper= <MazeName>
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					try {
						m=(Maze) reader.readObject();
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					
					getWaiter.notify();
			}
			if(com=="sentSolution"){
					try {
						sol=(Solution) reader.readObject();
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					solWaiter.notify();
			}
			if(com=="sentClue"){
					try {
						clue=(String) reader.readObject();
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					clueWaiter.notify();
			}
			if(com=="sentString"){
					try {
						helper=(String) reader.readObject();
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
			}
			if(com=="sentDiagsMode"){
					try {
						properties.setDiag((boolean) reader.readObject());
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	public void MyClient(int port, String ip) {
		//super();
		
	}
	public void send(String text){
		if(writer!=null){
			writer.println(text);
			writer.flush();
		}
	}

	/**
	 * This method generates a maze using threads in SERVER with an inputed data.
	 * @param name The name we want to give the maze.
	 * @param col The number of columns our maze will have (>2)
	 * @param row The number of rows our maze will have (>2)
	 */
	@Override
	public void generateMaze(String name, int col, int row) {
		send("generateMaze "+name+" "+row+","+col);
	}

	/**
	 * Returns a maze by an inputed name(String).
	 */
	@Override
	public Maze getMaze(String name) {
		send("displayMaze "+name);
		try {
			getWaiter.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return m;
	}
	
	/**
	 * This method solves a maze using the server.
	 * @param m The maze we want to solve.
	 */
	@Override
	public void solveMaze(String name) {
		send("solveMaze "+name);
	}
	
	/**
	 * gets the name of an inputed maze name.
	 * @param name	the name of the solution we want to find it's name.
	 * @return the name of the solution(string).
	 */
	@Override
	public Solution getSolution(String name) {
		send("solveMaze "+name);
		try {
			solWaiter.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(sol==null){
			this.setChanged();
			this.notifyObservers("solution "+name+" not found");
			return null;
		}
		else
			return sol;
	}
	
	/**
	 * Sets new Client(Connection to server), by the ip and port stored in properties.
	 */
	public void start(){
		run=true;
		try {
			myServer=new Socket(properties.getIp(), properties.getPort());
			writer=new PrintStream(myServer.getOutputStream());
			reader=(new ObjectInputStream(myServer.getInputStream()));
		} catch (IOException e) {
			System.out.println("can't connected to ip: "+properties.getIp()+" port: "+properties.getPort());
		}
	}
	
	/**
	 * Stops the work and saves the data.
	 */
	@Override
	public void stop() {
		run=false;
		send("exit");
		try {
			if(myServer!=null)
				myServer.close();
		} catch (IOException e2) {
			this.setChanged();
			this.notifyObservers("error while closing the connection.");
		}
		
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
		properties=prop;
		MyClient(prop.getPort(), prop.getIp());
	}
	
	/**
	 * Gets the clue(next cordinates) by the name of the maze and the current cordinates.
	 */
	@Override
	public String getClue(String arg) {
		send("GetClue "+arg);
		
		try {
			clueWaiter.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return clue;
	}
}	//Class close