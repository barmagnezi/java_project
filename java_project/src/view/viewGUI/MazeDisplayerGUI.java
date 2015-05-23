package view.viewGUI;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze;

public class MazeDisplayerGUI extends Canvas {
	public MazeDisplayerGUI(Composite parent, int style) {
		super(parent, style);
	}

	public void showMaze(Maze m){
		System.out.println("senia");
		this.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(new Color(null,0,0,0));
				   e.gc.setBackground(new Color(null,0,0,0));
				   
				   int width=getSize().x;
				   int height=getSize().y;

				   /*int w=width/mazeData[0].length;
				   int h=height/mazeData.length;

				   for(int i=0;i<mazeData.length;i++)
				      for(int j=0;j<mazeData[i].length;j++){
				          int x=j*w;
				          int y=i*h;
				          if(mazeData[i][j]!=0)
				              e.gc.fillRectangle(x,y,w,h);
				          	  //e.gc.drawOval(x, y, w, h);
				        	  //e.gc.drawRectangle(x, y, w, h);
				      }
				   //gameCharecter.x = 3*w;*/
				   //gameCharecter.y = 5*h;
				 //  gameCharecter.paint(e, w, h);
			}
		});
	}
	public void showSolution(){
		
	}
}
