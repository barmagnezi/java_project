package boot;

import presenter.Presenter;
import view.MyView;
import model.MyModel;

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
