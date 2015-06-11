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
	//For Client:
	public Socket myServer;
	public PrintStream writer;
	public ObjectInputStream reader;

	public String res;
	Boolean run;
	
	public Maze m;
	public Solution sol;
	public String clue;
	
	public Object waiter;
	private PropertiesModel properties;
	
	public void recive(){
		String com;
		while(run){
			try {
				com=(String) reader.readObject();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			if(com=="sentMaze")
				
			
		}
	}
	
	public void MyClient(int port, String ip) {
		//super();
		try {
			myServer=new Socket(ip, port);
			writer=new PrintStream(myServer.getOutputStream());
			reader=(new ObjectInputStream(myServer.getInputStream()));
		} catch (IOException e) {
			System.out.println("can't connected to ip: "+ip+" port: "+port);
		}
	}
	public void send(String text){
		writer.println(text);
		writer.flush();
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
			waiter.wait();
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
	public void solveMaze(Maze m) {
		send("solveMaze "+m);
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
			waiter.wait();
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
		new MyClient(properties.getPort(), properties.getIp());
	}
	
	/**
	 * Stops the work and saves the data.
	 */
	@Override
	public void stop() {
		run=false;
		send("exit");
		try {
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
			waiter.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return clue;
	}
}	//Class close