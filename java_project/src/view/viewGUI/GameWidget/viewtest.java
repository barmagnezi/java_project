package view.viewGUI.GameWidget;

import model.MyModel;
import presenter.Presenter;

public class viewtest {
	public static void main(String[] args) {
		
		MyModel m = new MyModel();
		GameWidget v =  new GameWidget("Game",500,500);
		Presenter p=new Presenter(v.getView(), m);
		v.addObserver(p);
		m.addObserver(p);
		v.start();
		v.exit();
		//new MyViewGUI("Game",500,500).run();
	}
}