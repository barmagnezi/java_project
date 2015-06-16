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

public class OnlineModelWithoutMVP extends Observable implements Model {

	public Socket myServer;
	public PrintStream writer;
	public BufferedReader reader;
	public PropertiesModelOnline properties;
	private Maze correctMaze;
	private Solution correctSolution;

	// private boolean flag=false;//for the server not run the start twice in
	// the first time.
	// first is setprop and second because of presenter
	private Thread listen;

	public Integer getMazeWaiter = new Integer(1);
	public Integer solWaiter = new Integer(1);
	public Integer clueWaiter = new Integer(1);
	private String clue;

	@Override
	public void start() {
		try {
			System.out.println("START ONLINE ASENIAESNAESNIAESNASEAES");
			if (myServer != null)
			System.out.println(myServer.getInetAddress().getHostAddress()+"=?"+properties.ip);
			if (myServer != null&&
					myServer.getPort() == properties.getPort()&&
					 myServer.isConnected())
				if(myServer.getInetAddress().getHostAddress().equals("127.0.0.1")&&
						properties.ip.equals("localhost")||
						 myServer.getInetAddress().getHostAddress()
							.equals(properties.ip))
					return;
			System.out.println("try connect to " + properties.getIp() + "   "
					+ properties.getPort());
			myServer = new Socket(properties.getIp(), properties.getPort());
			System.out.println("connect");
			writer = new PrintStream(myServer.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(
					myServer.getInputStream()));
		} catch (IOException e) {
			this.setChanged();
			this.notifyObservers("can't connected to ip: " + properties.getIp()
					+ " port: " + properties.getPort());
			listen = null;
			return;
		}
		this.notifyObservers("connected to ip: " + properties.getIp()
				+ " port: " + properties.getPort());
		listen = new Thread(new Runnable() {

			@Override
			public void run() {
				recive();
			}
		});
		listen.start();
	}

	@Override
	public void stop() {
		if (listen != null)
			listen.stop();
		try {
			if (myServer != null)
				myServer.close();
		} catch (IOException e) {
			this.notifyObservers("can't disconnect from the server");
		}
		writer = null;
		reader = null;
		
		// save properties
		XMLEncoder e = null;
		try {
			e = new XMLEncoder(new FileOutputStream("resources/propertiesOnline.xml"));
		} catch (FileNotFoundException e1) {
			this.setChanged();
			this.notifyObservers("error while saving the properties.");
		}
		e.writeObject(this.properties);
		e.flush();
		e.close();
	}

	public void send(String text) {
		if (writer != null) {
			writer.println(text);
			writer.flush();
		}
	}

	@Override
	public void generateMaze(String name, int rows, int cols) {
		send("generateMaze " + name + " " + rows + "," + cols);
	}

	@Override
	public Maze getMaze(String name) {
		send("getMaze " + name);
		try {
			synchronized (getMazeWaiter) {
				getMazeWaiter.wait();
				System.out.println("Dsvd");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Dsvd");
		if(correctMaze==null){
			this.setChanged();
			this.notifyObservers("maze " + name + " not found.");
		}
		return correctMaze;
	}

	@Override
	public void solveMaze(String name) {
		send("solveMaze " + name);
	}

	@Override
	public Solution getSolution(String name) {
		send("getSolution " + name);
		try {
			synchronized (solWaiter) {
				solWaiter.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (correctSolution == null) {
			this.setChanged();
			this.notifyObservers("solution " + name + " not found");
			return null;
		} else
			return correctSolution;
	}

	@Override
	public void setProperties(PropertiesModel mproperties) {
		this.properties = (PropertiesModelOnline) mproperties;
		stop();
		start();
	}

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

	public void recive() {
		String command = null;
		while (true) {
			try {
				command = reader.readLine();
				System.out.println(command);
				if (command == "sentMaze") {
					/*
					 * try { helper = reader.readLine(); //helper= <MazeName> }
					 * catch (IOException e) { e.printStackTrace(); }
					 */
					try {
						String h=reader.readLine();
						if(h.equals("mazeNotFound"))
							correctMaze=null;
						else
							correctMaze = StringMaze.StringToMaze(h);
					} catch (IOException e) {
						e.printStackTrace();
					}
					synchronized (getMazeWaiter) {
						getMazeWaiter.notify();						
					}
				}
				if (command.equals("sentSolution")) {
					try {
						correctSolution = StringSolution.StringToSolution(reader
								.readLine());
					} catch (IOException e) {
						e.printStackTrace();
					}
					solWaiter.notify();
				}
				if (command.equals("sentClue")) {
					try {
						clue = reader.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
					clueWaiter.notify();
				}
				if (command.equals("sentDiagsMode")) {
					try {
						System.out.println("sentDiagsMode in if");
						this.setChanged();
						this.notifyObservers("DiagsMode:" + reader.readLine());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
