package boot;

import algorithms.mazeGenerators.Maze;
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
/* 
 * new function for mymodelsk
 * private String getname(Maze maze)
	{
       for (String name : nameMaze.keySet()) {
           if (nameMaze.get(name).equals(maze)) {
               return name;
           }
       }
	   return null;
	}
	*/
