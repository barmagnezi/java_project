package view.viewGUI;


import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;

public class MazeDisplayerGUI extends Canvas {
	String scrBackground;
	String scrWalls;
	Maze maze;
	GC lastPaint=null;
	CommonCharacter character;
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
				/*if(character!=null && character.isMoved()){
					System.out.println("dsv");
					character.paint(arg0, wallWidth*4, wallHeight*4);
					return;
				}*/
				int width=(maze.getCols()*4+maze.getCols()+1);
				int height=(maze.getRows()*4+maze.getRows()+1);
				if(lastPaint!=null)
					lastPaint.dispose();
				lastPaint=arg0.gc;
				arg0.gc.setBackgroundPattern(new Pattern(null, new Image(null, scrWalls)));
				wallWidth=getSize().x/width;
				wallHeight=getSize().y/height;
				if(character==null)
					character=new TheRedBallCharacter(0, 0);
				
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
				character.paint(arg0, wallWidth*4, wallHeight*4);
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
		   if(m!=maze){
			   this.character=null;
			   maze=m;
		   }
		   setBackgroundImage(new Image(null, scrBackground));
		   redraw();
	}
	
	public void CharMoved(int pos){			//1 for Right, 2 for Up, 3 for Left, 4 for Down
											//5 for down-left,6 for down-Right,7 for up-left,8 for up-right
		getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
    			switch(pos){
    			case 1: character.setRealx((character.getRealx()+1));		//Right
    					break;
    			case 2:	character.setRealy((character.getRealy()-1));		//Up
    					break;
    			case 3:	character.setRealx((character.getRealx()-1));		//Left
						break;
    			case 4:	character.setRealy((character.getRealy()+1));		//Down
						break;
    			case 5:	character.setRealy((character.getRealy()+1));		//down-left
    					character.setRealx(character.getRealx()-1);
						break;
    			case 6:	character.setRealy((character.getRealy()+1));		//down-Right
    					character.setRealx(character.getRealx()+1);
						break;
    			case 7:	character.setRealy((character.getRealy()-1));		//up-left
    					character.setRealx(character.getRealx()-1);
						break;
    			case 8:	character.setRealy((character.getRealy()-1));		//up-right
    					character.setRealx(character.getRealx()+1);
						break;
    			}
    		   redraw();
			}
		});
	}
				
	
	public void showSolution(){
		
	}

	public CommonCharacter getCharacter() {
		return character;
	}
}
