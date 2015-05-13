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
		if(o == view){
			Command c=view.getUserCommand();
			c.doCommand((String) arg);
		}
			
	//commands
		public class TestMVPCommand implements Command {

			@Override
			public void doCommand(String arg) {
				

			}

		}
			
		
	}
}