package model;

import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import presenter.PropertiesModel;
import presenter.PropertiesModelOnline;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public class OnlineModel extends Observable implements Model {

	public PropertiesModelOnline properties;
	
	@Override
	public void start() {
	}
	

	@Override
	public void generateMaze(String name, int rows, int cols) {
		Socket s=connect();
		if(s==null)
			return;
		send(s,"generateMaze " + name + " " + rows + "," + cols);
		this.setChanged();
		this.notifyObservers(read(s));
		disconnect(s);
	}

	@Override
	public Maze getMaze(String name) {
		Socket s = null;
		s=connect();
		send(s,"getMaze " + name);
		
		
		String msg=read(s);
		System.out.println(msg);
		if(msg==null){
			this.setChanged();
			this.notifyObservers("The server does not work properly");
			return null;
		}
		if(msg.equals("sentMaze")){
			msg=read(s);
			System.out.println(msg);
			if(msg==null){
				this.setChanged();
				this.notifyObservers("The server does not work properly");
				return null;
			}
			Maze m=null;
			if(msg.equals("mazeNotFound")||msg.equals("")){
				this.setChanged();
				this.notifyObservers("maze " + name + " not found.");
			}else{
				System.out.println("the maze is:"+msg);
				m=StringMaze.StringToMaze(msg);
			}
			return m;
		}else{
			this.setChanged();
			this.notifyObservers("The server does not work properly");
			return null;
		}
	}

	@Override
	public void solveMaze(String name) {
		Socket s=connect();
		send(s, "solveMaze " + name);
		this.setChanged();
		this.notifyObservers(read(s));
		disconnect(s);
	}

	@Override
	public Solution getSolution(String name) {
		Socket s = null;
		s=connect();
		send(s,"getSolution " + name);
		
		String msg=read(s);
		System.out.println("The server send:(need to be sentSolution)->"+msg);
		if(msg!=null && msg.equals("sentSolution")){
			msg=read(s);
			System.out.println(msg);
			if(msg==null){
				this.setChanged();
				this.notifyObservers("The server does not work properly");
				return null;
			}
			Solution sol=null;
			if(msg.equals("solutionNotFound")){
				this.setChanged();
				this.notifyObservers("solution " + name + " not found.");
				return null;
			}if(msg.length()==0){
				this.setChanged();
				this.notifyObservers("The server does not work properly");
				return null;
			}
			else{
				System.out.print("The sol is111:");
				System.out.println(msg);
				sol=StringSolution.StringToSolution(msg);
			}
			return sol;
		}else{
			this.setChanged();
			this.notifyObservers("The server does not work properly");
			return null;
		}
	}

	@Override
	public String getClue(String arg) {
		Socket s=connect();
		send(s,"GetClue " + arg);
		
		String msg=read(s);
		System.out.println("The server send:(need to be sentclue)->"+msg);
		if(msg!=null && msg.equals("sentClue")){
			msg=read(s);
			System.out.println(msg);
			if(msg==null){
				this.setChanged();
				this.notifyObservers("The server does not work properly");
				return null;
			}
			return msg;
		}else{
			this.setChanged();
			this.notifyObservers("The server does not work properly");
			return null;
		}
	}

	@Override
	public void stop() {
		System.out.println("save properties");
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

	@Override
	public void setProperties(PropertiesModel mproperties) {
		properties=(PropertiesModelOnline) mproperties;
	}



	@Override
	public boolean isonline() {
		return true;
	}
	
	//helpers
	private Socket connect(){
		if(properties==null)
			return null;
		Socket s;
		try {
			s=new Socket(properties.ip, properties.port);
		} catch (IOException e) {
			this.notifyObservers("can't to connect to server(ip:"+properties.ip+",port:"+properties.port+")");
			return null;
		}
		return s;
	}
	private void disconnect(Socket s){
		if(s==null)
			return;
		try {
			s.close();
		} catch (IOException e) {
			this.notifyObservers("can't to connect to server(ip:"+s.getInetAddress().getHostAddress()+",port:"+s.getPort()+")");
		}
	}

	private void send(Socket s,String msg){
		PrintWriter p = null;
		if(s==null||msg==null)
			return;
		try {
			p = new PrintWriter(s.getOutputStream());
		} catch (IOException e) {
			this.setChanged();
			this.notifyObservers("can't to send message to server(ip:"+s.getInetAddress().getHostAddress()+",port:"+s.getPort()+")");
		}
		p.println(msg);
		p.flush();
	}
	private String read(Socket s){
		BufferedReader r;
		String msg;
		try {
			r = new BufferedReader(new InputStreamReader(s.getInputStream()));
			msg= r.readLine();
		} catch (IOException e) {
			this.notifyObservers("can't to recive message to server(ip:"+s.getInetAddress().getHostAddress()+",port:"+s.getPort()+")");
			msg=null;
		}
		return msg;
	}

}
