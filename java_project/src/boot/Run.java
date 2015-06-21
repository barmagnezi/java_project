package boot;

import presenter.Presenter;
import view.View;
import view.viewCLI.ClI_View;
import view.viewGUI.GameWidget.GameWindow;
import model.Model;
import model.modelOffline.OffLineModel;
import model.modelOnline.OnlineModel;

/**
 * The class run the project online
 * 
 * @author Bar Magnezi(209043827) and Senia Kalma(321969941)
 * @version 1.0
 * @since 17.5.2015
 */
public class Run {
	public static void main(String[] args) {
		run_GUI_online();
	}

	public static void run_cli() {
		OffLineModel m = new OffLineModel();
		ClI_View v = new ClI_View();
		Presenter p = new Presenter(v, m);
		v.addObserver(p);
		m.addObserver(p);
		v.start();
	}

	public static void run_GUI() {
		OffLineModel m = new OffLineModel();
		GameWindow v = new GameWindow("Game", 500, 500);
		Presenter p = new Presenter(v.getView(), m);
		v.addObserver(p);
		m.addObserver(p);
		v.start();
		v.exit();
	}

	public static void run_GUI_online() {
		OnlineModel m = new OnlineModel();
		GameWindow v = new GameWindow("Game", 500, 500);
		Presenter p = new Presenter(v.getView(), m);
		v.addObserver(p);
		m.addObserver(p);
		v.start();
		v.exit();
	}

}