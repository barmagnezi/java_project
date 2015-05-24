package view.viewGUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;

public class MazeDisplayerGUI extends Canvas {
	public MazeDisplayerGUI(Composite parent, int style) {
		super(parent, style);
	}

	public void showMaze(Maze m){

		   setBackgroundImage(new Image(null, "resources/images/grass.png"));
		   int width=m.getCols()*4+m.getCols()+1;
		   int height=m.getRows()*4+m.getRows()+1;
		   
		   addPaintListener(new PaintListener() {
				
				@Override
				public void paintControl(PaintEvent arg0) {
					arg0.gc.setBackgroundPattern(new Pattern(null, new Image(null, "resources/images/trees.png")));
					int wallWidth=getSize().x/width;
					int wallHeight=getSize().y/height;
					/*
					//The Frame
						//Two rows
						arg0.gc.fillRectangle(0,0,wallWidth*width,wallHeight);	
						arg0.gc.fillRectangle(0,wallHeight*height,getSize().x,getSize().y);	
						//Two Columns
						arg0.gc.fillRectangle(wallWidth*width,0,getSize().x,getSize().y);
						arg0.gc.fillRectangle(0,0,wallWidth,wallHeight*height);
						*/
					//The points that connects walls
					for(int i=5;i<width;i+=5)
						for(int j=5;j<height;j+=5)
							arg0.gc.fillRectangle(i*wallWidth,j*wallHeight,wallWidth,wallHeight);
					//The walls
					for(int i=0;i<m.getRows();i++)
						for(int j=0;j<m.getCols();j++){
							Cell c=m.getCell(i, j);
							if(c.getBottomWall().isExist())
								arg0.gc.fillRectangle( (j+(1+j*4)) *wallWidth, (((i+1)*5)) *wallHeight,wallWidth*4,wallHeight);
							if(c.getRightWall().isExist())
								arg0.gc.fillRectangle( (((j+1)*5)) *wallWidth, (i+(1+i*4)) *wallHeight,wallWidth,wallHeight*4);
						}
				}
			});
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
	public void showSolution(){
		
	}
}
