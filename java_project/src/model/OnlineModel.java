package model;

import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import presenter.PropertiesModel;
import presenter.PropertiesModelOnline;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

/**
 * The OnlineModel class extends Observable and implements Model. This class connected to 
 * server that save in the properties and send to him requests and return the answers to the user 
 * like all models.
 * 
 * @author Bar Magnezi and Senia Kalma
 * @version 1.0
 * @since 16.6.2015
 */
public class OnlineModel extends Observable implements Model {
	
	public PropertiesModelOnline properties;
	
	/**
	 * This methods start to run the model
	 */
	@Override
	public void start() {
	}
	

	
	
	/* (non-Javadoc)
	 * @see model.Model#generateMaze(java.lang.String, int, int)
	 */
	@Override
	public void generateMaze(String name, int rows, int cols) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				//System.out.println("OnlineModel,maybe here?VVV");
				Socket s=connect();
				if(s==null)
					return;
				send(s,"generateMaze " + name + " " + rows + "," + cols);
				setChanged();
				notifyObservers(read(s));
				disconnect(s);
			}
		}).start();

	}

	/* (non-Javadoc)
	 * @see model.Model#getMaze(java.lang.String)
	 */
	@Override
	public Maze getMaze(String name) {
		Socket s = null;
		//System.out.println("OnlineMode,getMaze,First Connect will be here VVV");
		s=connect();
		if(s==null)
			return null;
		send(s,"getMaze " + name);
		String msg=read(s);
		//System.out.println(msg);
		String[] parts=msg.split("!");
		/*if(msg==null){	-Dead code ?
			this.setChanged();
			this.notifyObservers("The server does not work properly");
			return null;
		}*/
		msg=parts[0];	//sentMaze
		if(msg.equals("sentMaze")){
			//msg=read(s);
			msg=parts[1];	//mazeNotFound or Actual Maze as string
			//System.out.println(msg);
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
				//System.out.println("the maze is:"+msg);
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
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Socket s=connect();
				if(s==null)
					return;
				send(s, "solveMaze " + name);
				setChanged();
				notifyObservers(read(s));
				disconnect(s);
			}
		}).start();;

	}

	/* (non-Javadoc)
	 * @see model.Model#getSolution(java.lang.String)
	 */
	@Override
	public Solution getSolution(String name) {
		Socket s = null;
		s=connect();
		if(s==null)
			return null;
		send(s,"getSolution " + name);
		
		String msg=read(s);
		//System.out.println("The server send:(need to be sentSolution)->"+msg);
		String[] parts = msg.split("!");
		msg=parts[0];	//sentSolution
		if(msg!=null && msg.equals("sentSolution")){
			//msg=read(s);
			msg=parts[1];	//solutionNotFound or Actual solution as string
			//System.out.println(msg);
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
				//System.out.print("The sol is111:");
				//System.out.println(msg);
				sol=StringSolution.StringToSolution(msg);
			}
			return sol;
		}else{
			this.setChanged();
			this.notifyObservers("The server does not work properly");
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#getClue(java.lang.String)
	 */
	@Override
	public String getClue(String arg) {
		Socket s=connect();
		if(s==null)
			return null;
		send(s,"GetClue " + arg);
		
		String msg=read(s);
		String parts[]=msg.split("!");
		msg=parts[0];	//sentclue
		//System.out.println("The server send:(need to be sentclue)->"+msg);
		if(msg!=null && msg.equals("sentClue")){
			//msg=read(s);
			msg=parts[1];	//x,y
			//System.out.println(msg);
			if(msg==null || msg=="" || !msg.contains(",")){
				/*this.setChanged();
				this.notifyObservers("The server does not work properly");
				return null;*/
				msg=getClue(arg);
			}
			else
				return msg;
		}else{
			this.setChanged();
			this.notifyObservers("The server does not work properly");
			return null;
		}
		return msg;
	}

	/* (non-Javadoc)
	 * @see model.Model#stop()
	 */
	@Override
	public void stop() {
		System.out.println("saving the properties");
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

	/* (non-Javadoc)
	 * @see model.Model#setProperties(presenter.PropertiesModel)
	 */
	@Override
	public void setProperties(PropertiesModel mproperties) {
		properties=(PropertiesModelOnline) mproperties;
	}



	/* (non-Javadoc)
	 * @see model.Model#isonline()
	 */
	@Override
	public boolean isonline() {
		return true;
	}
	
	//helpers
	private Socket connect(){
		//System.out.println("OnlineModel, connectingnagngni ===============");
		if(properties==null)
			return null;
		Socket s;
		try {
			s=new Socket(properties.ip, properties.port);
		} catch (IOException e) {
			this.setChanged();
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
			this.setChanged();
			this.notifyObservers("can't to connect to server(ip:"+s.getInetAddress().getHostAddress()+",port:"+s.getPort()+")");
		}
	}

	private void send(Socket s,String msg){
		PrintWriter p = null;
		if(s==null||msg==null)
			return;
		try {
			p = new PrintWriter(s.getOutputStream());
			//for compress data
			//p = new PrintWriter(new ZipOutputStream(s.getOutputStream()));
		} catch (IOException e) {
			this.setChanged();
			this.notifyObservers("error with the server(ip:"+s.getInetAddress().getHostAddress()+",port:"+s.getPort()+")");
		}
		
		p.println(msg);
		p.flush();
	}
	private String read(Socket s){
		BufferedReader r;
		String msg;
		try {
			r=new BufferedReader(new InputStreamReader(s.getInputStream()));
			//for compress data
			//r=new BufferedReader(new InputStreamReader(new ZipInputStream(s.getInputStream())));
			msg= r.readLine();
		} catch (IOException e) {
			this.notifyObservers("can't to recive message to server(ip:"+s.getInetAddress().getHostAddress()+",port:"+s.getPort()+")");
			msg=null;
		}
		return msg;
	}

}
