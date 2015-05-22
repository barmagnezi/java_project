package view.viewCLI;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

import View.CLI;
import View.Command;

/**
* The CLI class is command line interface.
*
* @author  Bar Magnezi
* @version 1.0
* @since 5.5.2015
*/
public class NotifyCLIgetViewWeDontUse extends CLI{
	private MyView view;
	/**
	 * This constructor creates CLI that works with the parameters.
	 * @param in The inputstream(BufferedReader) that contain all the command from the user.
	 * @param out The OutputStream(PrintStream) that all the commands will write.
	 * @param commands All the commands that the CLI support.
	 */
	public NotifyCLIgetViewWeDontUse(BufferedReader in, PrintStream out,HashMap<String, Command> commands,MyView view){
		super(in,out,commands);
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
				String[] sp = line.split(" ", 2);
								
				String commandName = sp[0];
				String arg = null;
				if (sp.length > 1)
					arg = sp[1];
				// Invoke the command
				Command command = Commands.get(commandName);
				if(command==null)
					out.println("There is no such command "+commandName);
				else
					if(arg==null)
						out.println("No argument has been entered");
					else{
						view.commandsList.add(command);
						view.Notify(arg);
					}
				
				out.print("Enter command: ");
				line = in.readLine();
			}
			out.println("Goodbye");
			view.commandsList.add(Commands.get("exit"));
			view.Notify(null);			
		} catch (IOException e) {
			out.println("can't read/write from/to in/out streams");
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				out.println("can't close from in/out streams");
			}		
		}	
	}
}
