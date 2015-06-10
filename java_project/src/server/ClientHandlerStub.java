package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;


public class ClientHandlerStub implements ClientHandler {

	@Override
	public void HandleClient(InputStream input, OutputStream outputStream) {
		BufferedReader reader=new BufferedReader(new InputStreamReader(input));
		while(true){
			try {
				String line=reader.readLine();
				System.out.println("The client send"+line);
				if(line.equals("goodbye")){
					System.out.println("The client end the session");
					outputStream.write("goodbye".getBytes());
					outputStream.flush();
				}
				outputStream.write("Ok".getBytes());
				outputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

	}

}
