package view.viewGUI;

import model.MyModel;
import presenter.Presenter;

public class viewtest {
	public static void main(String[] args) {
		/*
		MyModel m = new MyModel();
		MyView v =  new MyView("Game",500,500);
		Presenter p=new Presenter(v, m);
		v.addObserver(p);
		m.addObserver(p);
		v.start();*/
		new MyView("Game",500,500).run();
	}
}