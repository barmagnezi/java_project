package view.viewGUI.GameWidjet;

import model.MyModel;
import presenter.Presenter;

public class viewtest {
	public static void main(String[] args) {
		
		MyModel m = new MyModel();
		GameWidjet v =  new GameWidjet("Game",500,500);
		Presenter p=new Presenter(v.getView(), m);
		v.addObserver(p);
		m.addObserver(p);
		v.run();
		//new MyViewGUI("Game",500,500).run();
	}
}