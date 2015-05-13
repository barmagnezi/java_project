package view;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;

import presenter.Command;
/**
* The CLI class is command line interface.
*
* @author  Bar Magnezi
* @version 1.0
* @since 5.5.2015
*/
public class NewCLI {
	private BufferedReader in;
	private PrintStream out;
	private HashMap<String, Command> Commands; 
	private MyView view;
	/**
	 * This constructor creates CLI that works with the parameters.
	 * @param in The inputstream(BufferedReader) that contain all the command from the user.
	 * @param out The OutputStream(PrintStream) that all the commands will write.
	 * @param commands All the commands that the CLI support.
	 */
	public NewCLI(BufferedReader in, PrintStream out,HashMap<String, Command> commands,MyView view)
	{
		this.in = in;
		this.out = out;
		this.Commands=commands;
		this.view=view;
	}
	/**
	 * This method start getting commands from the user and do them.
	 */
	public void start()
	{
		out.print("Enter command: ");

		try {
			String line = in.readLine();
			
			while (!line.equals("exit"))
			{
				String[] sp = line.split(" ");
								
				String commandName = sp[0];
				String arg = null;
				if (sp.length > 1)
					arg = sp[1];
				// Invoke the command
				Command command = Commands.get(commandName);
				if(command==null)
					out.println("There is no command "+commandName);
				else
					if(arg==null)
						out.println("No argument has been entered");
					else{
						view.commands.add(command);
						view.Notify(arg);
					}
				
				out.print("Enter command: ");
				line = in.readLine();
			}
			out.print("Goodbye");
						
		} catch (IOException e) {			
			System.out.println("can't read/write from/to in/out streams");
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {				
				System.out.println("can't close from in/out streams");
			}		
		}	
	}
}
