package NotUsed;

import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Observable;
import model.Model;
import model.StringMaze;
import model.StringSolution;
import presenter.PropertiesModel;
import presenter.PropertiesModelOnline;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public class OnlineModelWithMVP extends Observable implements Model {
	public OnlineModelWithMVP() {
		super();
	}

	// For Client:
	public Socket myServer;
	public PrintStream writer;
	public BufferedReader reader;

	public String res;
	protected Boolean run;

	public Maze m;
	public Solution sol;
	public String clue;

	public Object solWaiter = new Object();
	public Object getWaiter = new Object();
	public Object clueWaiter = new Object();

	public PropertiesModelOnline properties;

	public void recive() {
		String com = null;
		String helper = null;
		while (run) {
			try {
				com = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (com == "sentMaze") {
				try {
					helper = reader.readLine(); // helper= <MazeName>
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					m = StringMaze.StringToMaze(reader.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}

				getWaiter.notify();
			}
			if (com == "sentSolution") {
				try {
					sol = StringSolution.StringToSolution(reader.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
				solWaiter.notify();
			}
			if (com == "sentClue") {
				try {
					clue = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				clueWaiter.notify();
			}
			if (com == "sentString") {
				try {
					helper = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (com == "sentDiagsMode") {
				try {
					this.setChanged();
					this.notifyObservers("DiagsMode:" + reader.readLine());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void send(String text) {
		if (writer != null) {
			writer.println(text);
			writer.flush();
		}
	}

	/**
	 * This method generates a maze using threads in SERVER with an inputed
	 * data.
	 * 
	 * @param name
	 *            The name we want to give the maze.
	 * @param col
	 *            The number of columns our maze will have (>2)
	 * @param row
	 *            The number of rows our maze will have (>2)
	 */
	@Override
	public void generateMaze(String name, int col, int row) {
		send("generateMaze " + name + " " + row + "," + col);
	}

	/**
	 * Returns a maze by an inputed name(String).
	 */
	@Override
	public Maze getMaze(String name) {
		send("displayMaze " + name);
		try {
			synchronized (getWaiter) {
				getWaiter.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return m;
	}

	/**
	 * This method solves a maze using the server.
	 * 
	 * @param m
	 *            The maze we want to solve.
	 */
	@Override
	public void solveMaze(String name) {
		send("solveMaze " + name);
	}

	/**
	 * gets the name of an inputed maze name.
	 * 
	 * @param name
	 *            the name of the solution we want to find it's name.
	 * @return the name of the solution(string).
	 */
	@Override
	public Solution getSolution(String name) {
		send("solveMaze " + name);
		try {
			synchronized (solWaiter) {
				solWaiter.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (sol == null) {
			this.setChanged();
			this.notifyObservers("solution " + name + " not found");
			return null;
		} else
			return sol;
	}

	/**
	 * Sets new Client(Connection to server), by the ip and port stored in
	 * properties.
	 */
	public void start() {
		run = true;
		try {
			myServer = new Socket(properties.getIp(), properties.getPort());
			writer = new PrintStream(myServer.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(
					myServer.getInputStream()));
		} catch (IOException e) {
			System.out.println("can't connected to ip: " + properties.getIp()
					+ " port: " + properties.getPort());
		}
	}

	/**
	 * Stops the work and saves the data.
	 */
	@Override
	public void stop() {
		run = false;
		send("exit");
		try {
			if (myServer != null)
				myServer.close();
		} catch (IOException e2) {
			this.setChanged();
			this.notifyObservers("error while closing the connection.");
		}

		XMLEncoder e = null;
		try {
			e = new XMLEncoder(new FileOutputStream(
					"resources/propertiesOnline.xml"));
			e.writeObject(this.properties);
			e.flush();
			e.close();
		} catch (FileNotFoundException e1) {
			this.setChanged();
			this.notifyObservers("error while saving the properties.");
		}
	}

	/**
	 * Setting the current properties with an inputed PropertiesModel Object.
	 */
	public void setProperties(PropertiesModel prop) {
		properties = (PropertiesModelOnline) prop;
		try {
			if (myServer != null)
				myServer.close();
			myServer = new Socket(properties.getIp(), properties.getPort());
			writer = new PrintStream(myServer.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(
					myServer.getInputStream()));
		} catch (IOException e) {
			System.out.println("can't connected to ip: " + properties.getIp()
					+ " port: " + properties.getPort());
		}
	}

	/**
	 * Gets the clue(next cordinates) by the name of the maze and the current
	 * cordinates.
	 */
	@Override
	public String getClue(String arg) {
		send("GetClue " + arg);

		try {
			synchronized (clueWaiter) {
				clueWaiter.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return clue;
	}

	@Override
	public boolean isonline() {
		return true;
	}
} // Class close