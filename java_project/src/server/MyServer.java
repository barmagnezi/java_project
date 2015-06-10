package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {
	private int port;
	private boolean run;
	int Allowed;
	static int clientNum=0;
	int dely;
	ClientHandler CH;
	ServerSocket myServer;
	
	public MyServer(int port, int Dely,int numOfClients){
		run=true;
		this.port=port;
		this.Allowed=numOfClients;
		this.dely=Dely;
	}

	public void Start(ClientHandler ch) throws Exception {
		this.CH=ch;
		System.out.println("SERVER side");
		myServer = new ServerSocket(port);
		myServer.setSoTimeout(dely);
		ExecutorService executor = Executors.newFixedThreadPool(Allowed);
		while(run){
			Socket someClient=this.accept();
			executor.execute (new Runnable() {
				@Override
				public void run() {
					try {
						clientNum++;
						System.out.println("Client "+clientNum+" CONNECTED");
						CH.HandleClient(someClient.getInputStream(), someClient.getOutputStream());
						someClient.getInputStream().close();
						someClient.getOutputStream().close();
						someClient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		myServer.close();
		/*
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(someClient.getInputStream()));
		PrintWriter out2Client = new PrintWriter(new OutputStreamWriter(someClient.getOutputStream()));
		
		String line;
		while(!(line=inFromClient.readLine()).equals("exit")){
			System.out.println(line);
			if(line.equals("start")){
				out2Client.println("Hello World!");
				out2Client.flush();
			}else{
				out2Client.println("ACK");
				out2Client.flush();
			}
		}
		out2Client.println("Good bye");
		out2Client.flush();
	
		inFromClient.close();
		out2Client.close();
		someClient.close();
		myServer.close();*/
	}
	public Socket accept(){
		Socket someClient=null;
		try{
			someClient= myServer.accept();
		}catch(java.net.SocketTimeoutException e){
			this.accept();
		}catch (IOException e) {
			System.out.println("error");
		}
		return someClient;
	}
	
	//START
	public void Stop(){
		//close all active servers ..
		run=false;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	public int getAllowed() {
		return Allowed;
	}

	public void setAllowed(int allowed) {
		Allowed = allowed;
	}

	public static int getClientNum() {
		return clientNum;
	}

	public static void setClientNum(int clientNum) {
		MyServer.clientNum = clientNum;
	}

	public int getDely() {
		return dely;
	}

	public void setDely(int dely) {
		this.dely = dely;
	}

	public ClientHandler getCH() {
		return CH;
	}

	public void setCH(ClientHandler cH) {
		CH = cH;
	}
	

}
