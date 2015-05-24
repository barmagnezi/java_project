package view.viewGUI;

import java.awt.Paint;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import view.viewGUI.example.GameCharecter;
import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;

public class MazeDisplayerGUI extends Canvas {
	String scrBackground;
	String scrWalls;
	Maze maze;
	GC lastPaint=null;
	Character character;
	public MazeDisplayerGUI(Composite parent, int style,String scrBackground,String scrwalls) {
		super(parent, style);
		this.scrBackground=scrBackground;
		this.scrWalls=scrwalls;
		setBackgroundImage(new Image(null, "resources/images/mazedisplayerbackground.png"));
		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent arg0) {
				if(maze==null)
					return;
				int width=(maze.getCols()*4+maze.getCols()+1);
				int height=(maze.getRows()*4+maze.getRows()+1);
				//new Character(1, 1, (MazeViewWidget) getParent());
				if(lastPaint!=null)
					lastPaint.dispose();
				lastPaint=arg0.gc;
				arg0.gc.setBackgroundPattern(new Pattern(null, new Image(null, scrWalls)));
				//System.out.println(scrWalls);
				//System.out.println("X: "+getSize().x);
				//System.out.println("Y: "+getSize().y);
				int wallWidth=getSize().x/width;
				int wallHeight=getSize().y/height;
				//System.out.println("X2: "+wallWidth);
				//System.out.println("Y2: "+wallHeight);
				
				//The Frame
					//Two rows
					arg0.gc.fillRectangle(0,0,wallWidth*width,wallHeight);
					arg0.gc.fillRectangle(0,wallHeight*height,getSize().x,getSize().y);	
					//Two Columns
					arg0.gc.fillRectangle(wallWidth*width,0,getSize().x,getSize().y);
					arg0.gc.fillRectangle(0,0,wallWidth,wallHeight*height);
				//The points that connects walls
				for(int i=5;i<width;i+=5)
					for(int j=5;j<height;j+=5)
						arg0.gc.fillRectangle(i*wallWidth,j*wallHeight,wallWidth,wallHeight);
				//The walls
				for(int i=0;i<maze.getRows();i++)
					for(int j=0;j<maze.getCols();j++){
						Cell c=maze.getCell(i, j);
						if(c.getBottomWall().isExist())
							arg0.gc.fillRectangle( (j+(1+j*4)) *wallWidth, (((i+1)*5)) *wallHeight,wallWidth*4,wallHeight);
						if(c.getRightWall().isExist())
							arg0.gc.fillRectangle( (((j+1)*5)) *wallWidth, (i+(1+i*4)) *wallHeight,wallWidth,wallHeight*4);
					}
			}
		});
	}

	public void changeDesign(String scrBackground,String scrWalls){
		this.scrBackground=scrBackground;
		this.scrWalls=scrWalls;
		if(maze!=null)
			showMaze(maze);
	}
	public void showMaze(Maze m){
		   maze=m;
		   setBackgroundImage(new Image(null, scrBackground));
		   redraw();
	}
	
	public void CharMoved(int pos){	//1 for Right, 2 for Up, 3 for Left, 4 for Down
	      getDisplay().syncExec(new Runnable() {
	    		@Override
	    		public void run() {
	    			switch(pos){
	    			case 1: character.x+=1;		//Right
	    					break;
	    			case 2:	character.y+=1;		//Up
	    					break;
	    			case 3:	character.x-=1;		//Left
							break;
	    			case 4:	character.y-=1;		//Down
							break;
	    			}
	    		   redraw();
	    		}
	    	 });
	}
				
	
	public void showSolution(){
		
	}
}
