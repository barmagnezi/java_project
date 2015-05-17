package presenter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import View.Command;
import view.View;
import model.Model;
import model.PropertiesModel;

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
		InputStream from = null;
		try {
			from = new FileInputStream("resources/properties.xml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();}
		PropertiesModel Mproperties = new PropertiesModel(from);
		model.setProperties(Mproperties);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg!=null && ((String)arg).equals("start")){
			model.start();
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
			if(nameAndArguments.length!=2)
				return;
			String[] rowAndcol=nameAndArguments[1].split(","); 
			if(rowAndcol.length!=2)
				return;
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
				view.displayMaze(model.getMaze(arg));
			else
				view.displayString("Such maze was not found.");
		}
	}
	
	/**
	 * Disassembling the solvemazeCommand, setting parameters and calling for the function.
	 */
	public class solvemazeCommand  implements Command {
		@Override
		public void doCommand(String arg,PrintStream out) {
			model.solveMaze(model.getMaze(arg));
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
			model.stop();
			//close all we need
		}
	}

		
}
