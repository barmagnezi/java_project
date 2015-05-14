package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import view.MyView;
import view.View;
import model.Model;
import model.MyModel;

public class Presenter implements Observer{
	Model model;
	View view;
	HashMap<String, Command> commands;
	
	public Presenter(MyView v, MyModel m) {
		model=m;
		view=v;
		commands=new HashMap<String, Command>();
		addAllCommands();
		view.setCommands(commands);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg!=null && ((String)arg).equals("start"))
			return;
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
			view.displayMaze(model.getMaze(arg));
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
			view.displaySolution(model.getSolution(arg));
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
