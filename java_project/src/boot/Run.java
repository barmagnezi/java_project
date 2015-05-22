package boot;

import presenter.Presenter;
import view.viewCLI.MyView;
import model.MyModel;
import algorithms.search.*;
/**
* The MyModel class extends Observable and implements Model.
* It is the part that makes all the calculations, such as creating a maze, and finding a solution for it.
* @author  Bar Magnezi(209043827) and Senia Kalma(321969941)
* @version 1.0
* @since 17.5.2015
*/
public class Run {
	public static void main(String[] args) {
		MyModel m = new MyModel();
		MyView v = new MyView();
		Presenter p=new Presenter(v, m);
		v.addObserver(p);
		m.addObserver(p);
		v.start();
	}
}