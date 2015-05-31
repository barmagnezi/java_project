package view.viewGUI.mazeViewWidjet;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/**
* The MazeViewWidget Interface(extends Canvas) - STUBS.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 31.5.2015
*/
public class MazeViewWidgetStub extends Canvas {
	public MazeViewWidgetStub(Composite parent, int style) {
		super(parent, style);
	}

	public void generateMaze(String name,int rows,int cols){
		System.out.println("generateMaze,name: "+name+" rows,cols: "+rows+","+cols);
	}
	
	public void loadMaze(String name){
		System.out.println("loadMaze,name: "+name);
	}
	
	public void solve(){
		System.out.println("solve");
	}
	
	public void clue(){
		System.out.println("clue");
	}

	
}
