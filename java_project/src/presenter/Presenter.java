package presenter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import view.MyView;
import view.View;
import model.Model;
import model.MyModel;
import model.PropertiesModel;

public class Presenter implements Observer{
	Model model;
	View view;
	HashMap<String, Command> commands;
	
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
		//Mproperties.changeProps();
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
				c.doCommand((String) arg);
			}
			if(o==model){
				if(arg==null)
					return;
				else{
					String[] detailAndName=((String)arg).split(" ");
					if(detailAndName[0].equals("generateMazeCompleted")){
						view.displayMaze(model.getMaze(detailAndName[1]));
					}
					if(detailAndName[0].equals("solveMazeCompleted")){
						view.displaySolution(model.getSolution(detailAndName[1]));
					}
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
		public void doCommand(String arg) {
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
	
	public class displaymazeCommand implements Command {
		@Override
		public void doCommand(String arg) {
			if(model.getMaze(arg)!=null)
				view.displayMaze(model.getMaze(arg));
			else
				System.out.println("Such maze was not found.");	//(bar92)-confirm
		}
	}
	public class solvemazeCommand  implements Command {
		@Override
		public void doCommand(String arg) {
			model.solveMaze(model.getMaze(arg));
		}
	}
	public class displaysolutionCommand  implements Command {
		@Override
		public void doCommand(String arg) {
			if(model.getSolution(arg)!=null)
				view.displaySolution(model.getSolution(arg));
			else
				System.out.println("Such solution was not found."); //(bar92)-confirm
			
		}
	}
	public class exitCommand  implements Command {
		@Override
		public void doCommand(String arg) {
			model.stop();
			//close all we need
		}
	}
		
}
