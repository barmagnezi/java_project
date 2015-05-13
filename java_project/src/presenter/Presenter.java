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
		commands.put("test", new TestMVPCommand());
		v.setCommands(commands);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(((String)arg).equals("start"))
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
				}					
			}
		}
	}
	//commands
	public class TestMVPCommand implements Command {

		@Override
		public void doCommand(String arg) {
			System.out.println("TestMVPCommand");
		}
		
	}
	//the argument must be name (int rows),(int cols)
	public class generateMazeCommand implements Command {

		@Override
		public void doCommand(String arg) {
			String[] nameAndArguments=arg.split(" ");
			String[] rowAndcol=nameAndArguments[2].split(","); 
			System.out.println("generateMaze");
			int rows=Integer.parseInt(rowAndcol[0]);
			int cols=Integer.parseInt(rowAndcol[1]);
			model.generateMaze(nameAndArguments[0],rows,cols);
			model.generateMaze(nameAndArguments[0],rows, cols);
		}
	}
	
	public class displaymazeCommand implements Command {

		@Override
		public void doCommand(String arg) {
			
		}
	}
		
}
