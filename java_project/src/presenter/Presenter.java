package presenter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;



import view.View;
import View.Command;
import model.Model;

/**
* The Presenter class implements Observer.
* sets the Model, View, and a hashmap of eligible commands.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 17.5.2015
*/
public class Presenter implements Observer{
	Model model;
	View view;
	HashMap<String, Command> commands;
	
	/**
	 * A constructor receiving a View and a Model and sets them as his own.
	 * Also it sets the properties XML to be the properties.
	 * @param v The View object.
	 * @param m The Model object.
	 * @param row The number of rows our maze will have (>2)
	 */
	public Presenter(View v, Model m) {
		model=m;
		view=v;
		commands=new HashMap<String, Command>();
		addAllCommands();
		view.setCommands(commands);
		if(model.isonline())
			setNewProperties("resources/propertiesOnline.xml");
		else
			setNewProperties("resources/properties.xml");
	}
	public void setNewProperties(String path){
		InputStream from = null;
		PropertiesModel Mproperties;
		try {
			from = new FileInputStream(path);
			if(model.isonline())
				Mproperties=new PropertiesModelOnline(from);
			else
				Mproperties = new PropertiesModelOffline(from);
		} catch (Exception e) {
			view.displayString(path+" not found");
			File theDir = new File("resources");
			theDir.mkdirs();
			if(model.isonline())
				Mproperties=new PropertiesModelOnline(null);
			else
				Mproperties = new PropertiesModelOffline(null);
		}		
		model.setProperties(Mproperties);
		view.getDiagsMode(Mproperties.isDiag());
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg!=null && ((String)arg).equals("start")){
			System.out.println("model.start()");
			model.start();
			//view.displayString("\nIf you run the program first time use the 'help -h' command to see how to use this command line interface\n");
			return;
		}
		else{
			if(o == view){
				Command c=view.getUserCommand();
				c.doCommand((String) arg,null);
			}
			if(o==model){
				if(arg==null)
					return;
				else{
					view.displayString((String)arg);
				}					
			}
		}
	}
	

	//create commands
	private void addAllCommands(){
		commands.put("generateMaze", new generateMazeCommand());
		commands.put("displayMaze", new displaymazeCommand());
		commands.put("solveMaze", new solvemazeCommand());
		commands.put("displaySolution", new displaysolutionCommand());
		commands.put("exit", new exitCommand());
		commands.put("help", new helpCommand());
		commands.put("setNewProperties", new setPropertiesCommand());
		commands.put("GetClue", new clueCommand());
		//commands.put("checkMotion", new checkMotionCommand());
	}
	//commands
	
	//the argument must be (name) (int rows),(int cols)
	public class generateMazeCommand implements Command {

		@Override
		/**
		 * Disassembling the generateMaze received command, setting parameters and calling for the function.
		 */
		public void doCommand(String arg,PrintStream out) {
			String[] nameAndArguments=arg.split(" ");
			if(nameAndArguments.length!=2){
				view.displayString("Please enter two arguments");
				return;
			}
			String[] rowAndcol=nameAndArguments[1].split(","); 
			if(rowAndcol.length!=2){
				view.displayString("The second argument must be split with ',' ");
				return;
			}
			int rows=Integer.parseInt(rowAndcol[0]);
			int cols=Integer.parseInt(rowAndcol[1]);
			model.generateMaze(nameAndArguments[0],rows,cols);
		}
	}
	
	/**
	 * Disassembling the displaymazeCommand, setting parameters and calling for the function.
	 */
	public class displaymazeCommand implements Command {
		@Override
		public void doCommand(String arg,PrintStream out) {
			if(model.getMaze(arg)!=null)
				view.displayMaze(model.getMaze(arg),arg);
		}
	}
	
	/**
	 * Disassembling the solvemazeCommand, setting parameters and calling for the function.
	 */
	public class solvemazeCommand  implements Command {
		@Override
		public void doCommand(String arg,PrintStream out) {
			model.solveMaze(arg);
		}
	}
	
	/**
	 * Disassembling the displaysolutionCommand, setting parameters and calling for the function.
	 */
	public class displaysolutionCommand  implements Command {
		@Override
		public void doCommand(String arg,PrintStream out) {
			if(model.getSolution(arg)!=null)
				view.displaySolution(model.getSolution(arg));
			else
				view.displayString("Such solution was not found.");
			
		}
	}
	public class exitCommand  implements Command {
		@Override
		public void doCommand(String arg,PrintStream out) {
			System.out.println("exit");
			model.stop();
			//close all we need
		}
	}
	public class helpCommand  implements Command {
		@Override
		public void doCommand(String arg,PrintStream out) {
			view.displayString("help-show all the commands\n\n"
					+ "generateMaze-create new maze with the parameters\n"
					+ "		syntax:generateMaze <mazeName> <numberOfrows>,<numberOfColumns>\n\n"
					+ "displayMaze-print the maze\n"
					+ "		syntax:displayMaze <mazeName>\n\n"
					+ "solveMaze-create solution to maze and save it\n"
					+ "		syntax:solveMaze <mazeName>\n\n"
					+ "displaySolution-print the solution of maze\n"
					+ "		syntax:displaySolution <mazeName>\n\n"
					+ "exit-exit from program and save all the changes\n"
					+ "---------------------------------------------------------\n");
			
		}
	}
	public class setPropertiesCommand implements Command{

		@Override
		public void doCommand(String arg, PrintStream out) {
			setNewProperties(arg);
		}
		
	}
	
	public class clueCommand implements Command{

		@Override
		public void doCommand(String arg, PrintStream out) {
			view.displayClue(model.getClue(arg));
		}
		
	}
	/*checkMotion not work
	public class checkMotionCommand implements Command{

		@Override
		public void doCommand(String arg, PrintStream out) {
			String[] data=arg.split(" ");
			boolean flag = model.checkMotion(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]));
			System.out.println(flag);
			view.getData(flag, "checkMotion");
		}
		
	}*/

		
}
