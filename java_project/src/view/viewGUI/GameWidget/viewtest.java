package view.viewGUI.GameWidget;

import model.OffLineModel;
import presenter.Presenter;

/**
 * The main MazeViewWidget(extends Canvas), containing all the features and
 * buttons used for showing are maze based game.
 * 
 * @author Bar Magnezi and Senia Kalma
 * @version 1.0
 * @since 31.5.2015
 */
public class viewtest {
	public static void main(String[] args) {

		OffLineModel m = new OffLineModel();
		GameWidget v = new GameWidget("Game", 500, 500);
		Presenter p = new Presenter(v.getView(), m);
		v.addObserver(p);
		m.addObserver(p);
		v.start();
		v.exit();
	}
}
