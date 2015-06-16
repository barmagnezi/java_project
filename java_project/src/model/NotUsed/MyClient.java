package model.NotUsed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MyClient {

	Socket myServer;
	PrintStream writer;
	BufferedReader reader;
	
	public MyClient(int port, String ip) {
		super();
		try {
			myServer=new Socket(ip, port);
			writer=new PrintStream(myServer.getOutputStream());
			reader=new BufferedReader(new InputStreamReader(myServer.getInputStream()));
		} catch (IOException e) {
			System.out.println("can't connected to ip:"+ip+"port:"+port);
		}
		
		
	}
	public void send(String text){
		writer.println(text);
		writer.flush();
	}
/*
	public static void main(String[] args) throws Exception {
		//Client side
		System.out.println("CLIENT side");
		System.out.println("Enter <server ip(localhost)> <server port(5400)>");
		String s;
		Socket myServer=new Socket("localhost", 5400);
		System.out.println("Connected");
		
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(myServer.getInputStream()));
		PrintWriter out2Server = new PrintWriter(new OutputStreamWriter(myServer.getOutputStream()));
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		String line;
		out2Server.println("start");
		out2Server.flush();
		out2Server.println("start");
		out2Server.flush();
		out2Server.println("start");
		out2Server.flush();
		System.out.println("wate to server");
		line=inFromServer.readLine();
		System.out.println("The server send:"+line);
		while(!line.equals("goodbye")){
			line=inFromUser.readLine();
			out2Server.println(line);
			out2Server.flush();
			line=inFromServer.readLine();
		}
		System.out.println("END");
	}*/
}