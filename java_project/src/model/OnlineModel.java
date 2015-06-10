package model;

import java.util.Observable;

import presenter.PropertiesModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;


/*
package pack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MyClient {

	public static void main(String[] args) throws Exception {
		//Client side
		System.out.println("CLIENT side");
		System.out.println("Enter <server ip(localhost)> <server port(5400)>");
		String s;
		//Scanner in = new Scanner(System.in);
	    //s = in.nextLine();
	    //String[] ServerProp = s.split(" ");
		//Socket myServer=new Socket(ServerProp[0], Integer.parseInt(ServerProp[1]));
		Socket myServer=new Socket("localhost", 5400);
		System.out.println("Connected");
		
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(myServer.getInputStream()));
		PrintWriter out2Server = new PrintWriter(new OutputStreamWriter(myServer.getOutputStream()));
		
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		String line;
		out2Server.println("start");
		out2Server.flush();
		while(!((line=inFromServer.readLine()).equals("good bye"))){
			System.out.println(line);
			line=inFromUser.readLine();
			out2Server.println(line);
			out2Server.flush();
		}
		System.out.println("END");
	}
}

 */
public class OnlineModel extends Observable implements Model {

	@Override
	public void generateMaze(String name, int rows, int cols) {
		// TODO Auto-generated method stub

	}

	@Override
	public Maze getMaze(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void solveMaze(Maze m) {
		// TODO Auto-generated method stub

	}

	@Override
	public Solution getSolution(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setProperties(PropertiesModel mproperties) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getClue(String arg) {
		// TODO Auto-generated method stub
		return null;
	}

}
