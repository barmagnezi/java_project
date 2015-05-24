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
import org.eclipse.swt.widgets.Shell;

import view.viewGUI.example.GameCharecter;
import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;

public class MazeDisplayerGUI extends Canvas {
	String scrBackground;
	String scrWalls;
	Maze maze;
	GC lastPaint=null;
	Character character;
	int wallWidth = 0, wallHeight = 0;
	
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
				
				//if(!character.isMoved()){
				{
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
					//if(wallHeight!=getSize().x/width || wallHeight!=getSize().y/height)
					//	character=new Character(getSize().x/width, getSize().y/height);
					wallWidth=getSize().x/width;
					wallHeight=getSize().y/height;
					if(character==null)
						character=new Character(wallWidth, wallHeight);
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
				//}else{
				}{
					character.paint(arg0, wallWidth*4, wallHeight*4);
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
		  // this.character=null;
		   setBackgroundImage(new Image(null, scrBackground));
		   redraw();
	}
	
	public void CharMoved(int pos){			//1 for Right, 2 for Up, 3 for Left, 4 for Down
		getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				/*if(pos==1)	//Want to go right			//When moving work, add this and remove switch
					if(maze.getCell(character.getX(), character.getY()).getRightWall().isExist()==false) //Can go right
						character.setX((character.getX()+wallWidth*5));
					else{
						//Wall on right
					}
				if(pos==2)	//Want to go up
					if(maze.getCell(character.getX(), character.getY()-1).getBottomWall().isExist()==false) //Can go right
						character.setX((character.getX()+wallWidth*5));
					else{
						//Wall on top
					}
				if(pos==3)	//Want to go left
					if(maze.getCell(character.getX()-1, character.getY()).getRightWall().isExist()==false) //Can go right
						character.setX((character.getX()+wallWidth*5));
					else{
						//Wall on left
					}
				if(pos==4)	//Want to go down
					if(maze.getCell(character.getX(), character.getY()).getBottomWall()().isExist()==false) //Can go right
						character.setX((character.getX()+wallWidth*5));
					else{
						//Wall on bottom
					}*/
    			switch(pos){
    			case 1: character.setX((character.getX()+wallWidth*5));		//Right
    					break;
    			case 2:	character.setY((character.getY()-wallHeight*5));		//Up
    					break;
    			case 3:	character.setX((character.getX()-wallWidth*5));		//Left
						break;
    			case 4:	character.setY((character.getY()+wallHeight*5));		//Down
						break;
    			}
    		   redraw();
			}
		});
	}
				
	
	public void showSolution(){
		
	}

	public Character getCharacter() {
		return character;
	}
}
