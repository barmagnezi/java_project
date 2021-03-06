package view.viewGUI.mazeDisplayerAndCharecters;


/**
* The actual class that displays the maze, and the character. (INSIDE WINDOW)
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 31.5.2015
*/
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;

import view.viewGUI.mazeViewWidjet.CommonGame;
import view.viewGUI.mazeViewWidjet.GameDisplayer;
import view.viewGUICharecteres.AnimCharacter;
import view.viewGUICharecteres.BallCharacter;
import view.viewGUICharecteres.CommonCharacter;
import view.viewGUICharecteres.PicCharacter;
import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import algorithms.search.State;

public class MazeDisplayerGUI extends GameDisplayer {
	String Background;
	String scrBackground;
	String scrWall;
	
	int charOp;
	Maze maze;
	GC lastPaint=null;
	//public CommonCharacter character;15.06
	public int wallWidth = 0, wallHeight = 0;
	
	boolean printsol=false; //for paint sol
	Solution sol=null;
	
	int Oldx=0,Oldy=0;	//For saving data when changing the Character
	
	//boolean solveFlag=false; 
	
	RGB Color;	//For changing the character
	String path;
	
	boolean Moved=false;
	
	int frame=0;		//For animation
	TimerTask myTask;
	Timer timer;
	
	private boolean Diagonals;
	



	public MazeDisplayerGUI(Composite parent, int style,String Background, String scrBackground,String scrWalls) {
		
		super(parent, style | SWT.DOUBLE_BUFFERED);
		this.Background=Background;
		this.scrBackground=scrBackground;
		this.scrWall=scrWalls;
		setBackgroundImage(new Image(null, Background));
		charOp=1;
		
		addPaintListener(new PaintListener() {
			@Override
			synchronized public void paintControl(PaintEvent arg0) {
				if(maze==null)
					return;
				//maze.print();
				int width=(maze.getCols()*4+maze.getCols()+1);
				int height=(maze.getRows()*4+maze.getRows()+1);
				if(lastPaint!=null)
					lastPaint.dispose();
				lastPaint=arg0.gc;
				arg0.gc.setBackgroundPattern(new Pattern(null, new Image(null, System.getProperty("user.dir")+"/"+scrWall)));
				wallWidth=(int) Math.floor((double)getSize().x/width);
				wallHeight=(int) Math.floor(((double)getSize().y)/(double)height);
				wallWidth=getSize().x/width;
				wallHeight=getSize().y/height;
				if(character==null){
					setCharacter(0,0);
				}
				//if(Moved==false){
					//The Frame
					//Two rows
					arg0.gc.fillRectangle(0,0,getSize().x,wallHeight);
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
				//}
				/*if(solveFlag==true){
					character.solveStep(arg0, wallWidth*4, wallHeight*4);
					solveFlag=false;
				}
				else*/
					character.paint(arg0, wallWidth*4, wallHeight*4);
				if(printsol==true){
					paintsolution(arg0, wallWidth*4, wallHeight*4);
					//printsol=false;
				}
			}
		});
		
	}
	
	
	/**
	 * Setting the character of the maze by the charOp(int):
	 * 1-Ball Character
	 * 1001-Picture character as set in the path
	 * 1002-Animation character as set in the path
	 */
	private void setCharacter(int x,int y){
		if(charOp==1){
			character=new BallCharacter(x, y);
			if(Color!=null)
				character.setColor(Color);
			character.setAnimation(false);
		}
		if(charOp==1001){	//1001 is a code in SelectPic for an image selected by user
			character=new PicCharacter(x, y);
			character.setAnimation(false);
			character.setPath(path);
		}
		if(charOp==1002){	//1002 is a code in SelectAnim for an Animation selected by user
			character=new AnimCharacter(x, y);
			character.setAnimation(true);
			character.setLoader(path);
		}
	if(character.isAnimation() && myTask==null){
		if(character.getLoader().data[frame].delayTime<100)	//if delay too small to see normally
			startAnimation(100);
		else
			startAnimation(character.getLoader().data[frame].delayTime);
	}
	}
	/**
	 * Set the character to be on x,y
	 * @param x coordinates
	 * @param y coordinates
	 */
	public void mark(int x,int y){
		printsol=false;
		character.setRealx(x);
		character.setRealy(y);
		//solveFlag=true;
		redraw();
	}
	
	/**
	 * Set the animation to run.
	 * @param delay the delay between each frame of the animation.
	 */
	public void startAnimation(long delay){
		myTask = new TimerTask() {
			@Override
			public void run() {
			if(!isDisposed()){
			      getDisplay().syncExec(new Runnable() {

			    		@Override
			    		public void run() {
			    			if(!character.isAnimation()){
			    				//myTask.cancel();
			    				//timer.cancel();
			    				//timer=null;
			    				frame=0;
			    				//return;
			    			}
			    			else{
			    				if(timer==null){
			    					timer = new Timer();
			    					timer.scheduleAtFixedRate(myTask, 0, delay);
			    				}
			    			}
			    			if(character!=null && charOp!=1 && charOp!=2 && charOp!=989 && charOp!=999 && character.getLoader().data!=null){
			    				if(frame==character.getLoader().data.length-1)
			    					frame=0;
			    				else
			    					frame++;
			    			}else
			    				frame=0;
			    		   if(myTask!=null)
			    			   redraw();
			    		}
			    	 });
				}
			}
		};
		timer = new Timer();
		timer.scheduleAtFixedRate(myTask, 0, delay);
	}
	//public void start(){
	//}
	public void stop(){
		if(myTask!=null && timer!=null){
			myTask.cancel();
			timer.cancel();
		}
	}
	
	/**
	 * Function for changing the background design, used by the MazeViewWidget.
	 * @param scrBackground path for the background.
	 * @param scrWalls path for the walls.
	 */
	public void changeBackDesign(String scrBackground,String scrWalls){
		this.scrBackground=scrBackground;
		this.scrWall=scrWalls;
		if(maze!=null){
			MazeGame g = new MazeGame(maze);
			showGame(g, false);
		}
	}
	/**
	 * Start the game all over.
	 */
	public void Startover(){
		if(character!=null){
			this.character.setRealx(0);
			this.character.setRealy(0);
		}
		printsol=false;
		setBackgroundImage(new Image(null, scrBackground));
		redraw();
	}
	
	/**
	 * Function for changing the character design.
	 * @param charOption type of the wanter char: 1-Ball,1001-Picture,1002-Animation.
	 * @param nColor Color if a ball Character selected.
	 * @param Path for setting a picture/animation character.
	 */
	public void changeCharacter(int charOption, RGB nColor, String path){
		this.charOp=charOption;
		if(nColor!=null)
			this.Color=nColor;
		if(path!=null)
			this.path=path;
		if(this.character==null)
			setCharacter(0,0);
		else
			setCharacter(this.character.getRealx(), this.character.getRealy());
		if(maze!=null || character!=null){
			MazeGame g = new MazeGame(maze);
			showGame(g, true);
		}
	}
	
	/**
	 * Updates the mazes show.
	 * @param m The maze we want to display.
	 * @param resetChar Used for resetting the character when setting a new one(true=reset).
	 */
	@Override
	public void showGame(CommonGame m,boolean resetChar){
		printsol=false;
		if(Moved==true){
			Oldx=this.character.getRealx();
			Oldy=this.character.getRealy();
		}
		if(resetChar==false){	//If Background changed
			Moved=false;
			if(m.getGame()!=maze){
				maze=(Maze) m.getGame();
			}
			setBackgroundImage(new Image(null, scrBackground));
			redraw();
		}
		if(resetChar==true){	//if Character Changed
			if(this.character!=null){
				this.character=null;
			}
			setBackgroundImage(new Image(null, scrBackground));
			if(character==null){
				setCharacter(Oldx,Oldy);
			}
			redraw();
		}
	}
	/**
	 * @param pos - integer to code the move:
	 * 1 for Right, 2 for Up, 3 for Left, 4 for Down
	 * 5 for down-left,6 for down-Right,7 for up-left,8 for up-right
	 */
	public void CharMoved(int pos){			//1 for Right, 2 for Up, 3 for Left, 4 for Down
											//5 for down-left,6 for down-Right,7 for up-left,8 for up-right
		Moved=true;
		printsol=false;
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
				
	/**
	 * Print the solution provided.
	 * @param the solution we want to draw.
	 */
	public void showSolution(Solution s){
		this.sol=s;
		this.printsol=true;
		redraw();
	}
	/**
	 * Paints a solution.
	 * @param arg0 as the PaintEvent.
	 * @param Width
	 * @param Height
	 */
	private void paintsolution(PaintEvent arg0, int Width,int Height) {
		if(sol!=null){
			Stack<State> stack = new Stack<State>();
			stack.addAll(sol.getSol());
			/*long time;
			if(stack.size()*200<7000)
				 time=200;
			else
				time=7000/stack.size();
				*/
			while(!stack.isEmpty()){
				/*try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					System.out.println("error by Threads");
				}*/
				
				String[] rowCol=stack.pop().getState().split("x");
				int row=Integer.parseInt(rowCol[0]);
				int col=Integer.parseInt(rowCol[1]);	
				arg0.gc.fillRectangle((col*5+1)*Width/4, (row*5+1)*Height/4, Width, Height);
			}
		}else
			System.out.println("Error in solving the maze");
			
	}
	/**
	 * check if charecter can move from Current to next		(x,y) 
	 * @param CurrentX The current x
	 * @param CurrentY The current y
	 * @param nextX	The next x after the Motion
	 * @param nextY The next y after the Motion
	 */
	@Override
	public boolean CheckMotion(int CurrentX, int CurrentY, int nextX, int nextY) {
		boolean flag = true;
		// System.out.println();
		if (nextX < 0 || nextY < 0 || nextX == maze.getCols()
				|| nextY == maze.getRows())
			return false;
		if (!(nextX != CurrentX && CurrentY != nextY)) {
			if (CurrentX + 1 == nextX)
				flag = flag
						&& (!maze.getCell(CurrentY, CurrentX).getRightWall()
								.isExist());
			if (CurrentX - 1 == nextX)
				flag = flag
						&& (!maze.getCell(nextY, nextX).getRightWall()
								.isExist());
			if (CurrentY + 1 == nextY)
				flag = flag
						&& (!maze.getCell(CurrentY, CurrentX).getBottomWall()
								.isExist());
			if (CurrentY - 1 == nextY)
				flag = flag
						&& (!maze.getCell(nextY, nextX).getBottomWall()
								.isExist());
			return flag;
		} else {
			if (Diagonals == true) {
				int col = CurrentX;
				int row = CurrentY;
				// right-up
				if (CurrentX + 1 == nextX && CurrentY - 1 == nextY) {
					if (maze.getCell(row - 1, col).getRightWall().isExist() && // |_
							maze.getCell(row - 1, col + 1).getBottomWall()
									.isExist())// .
						flag = false;
					if (maze.getCell(row, col).getRightWall().isExist() && // |
							maze.getCell(row - 1, col).getRightWall().isExist()) // .|
						flag = false;
					if (maze.getCell(row - 1, col).getBottomWall().isExist() && // _
																				// _
							maze.getCell(row - 1, col + 1).getBottomWall()
									.isExist()) // .
						flag = false;
					if (maze.getCell(row - 1, col).getBottomWall().isExist() && // /corner
																				// like
																				// ._|
							maze.getCell(row, col).getRightWall().isExist())
						flag = false;
					return flag;
				}
				// right-down
				if (CurrentX + 1 == nextX && CurrentY + 1 == nextY) {
					if (maze.getCell(row + 1, col).getRightWall().isExist() && // .
							maze.getCell(row, col + 1).getBottomWall()
									.isExist())// |-
						flag = false;
					if (maze.getCell(row, col).getRightWall().isExist() && // .
																			// |
							maze.getCell(row + 1, col).getRightWall().isExist()) // |
						flag = false;
					if (maze.getCell(row, col).getBottomWall().isExist() && // .
							maze.getCell(row, col + 1).getBottomWall()
									.isExist()) // --
						flag = false;
					if (maze.getCell(row, col).getBottomWall().isExist() && // /corner
																			// like
																			// ._|
							maze.getCell(row, col).getRightWall().isExist())
						flag = false;
					return flag;
				}
				// left-down
				if (CurrentX - 1 == nextX && CurrentY + 1 == nextY) {
					if (maze.getCell(row + 1, col - 1).getRightWall().isExist()
							&& // .
							maze.getCell(row, col - 1).getBottomWall()
									.isExist()) // -|
						flag = false;
					if (maze.getCell(row, col - 1).getRightWall().isExist() && // |.
							maze.getCell(row + 1, col - 1).getRightWall()
									.isExist()) // |
						flag = false;
					if (maze.getCell(row, col).getBottomWall().isExist() && // .
							maze.getCell(row, col - 1).getBottomWall()
									.isExist()) // --
						flag = false;
					if (maze.getCell(row, col).getBottomWall().isExist() && // /corner
																			// like
																			// ._|
							maze.getCell(row, col - 1).getRightWall().isExist())
						flag = false;
					return flag;
				}
				// left-up
				if (CurrentX - 1 == nextX && CurrentY - 1 == nextY) {
					if (maze.getCell(row - 1, col - 1).getRightWall().isExist()
							&& // _|
							maze.getCell(row - 1, col - 1).getBottomWall()
									.isExist()) // .
						flag = false;
					if (maze.getCell(row, col - 1).getRightWall().isExist() && // |
							maze.getCell(row - 1, col - 1).getRightWall()
									.isExist()) // |.
						flag = false;
					if (maze.getCell(row - 1, col).getBottomWall().isExist() && // _
																				// _
							maze.getCell(row - 1, col - 1).getBottomWall()
									.isExist()) // .
						flag = false;
					if (maze.getCell(row - 1, col).getBottomWall().isExist() && // /corner
																				// like
																				// ._|
							maze.getCell(row, col - 1).getRightWall().isExist())
						flag = false;
					return flag;
				}
			} else
				return false;
		}
		return false;

	}
	/**
	 * Check if the x and y in the end of the game
	 * @param x	the current x 
	 * @param y	the current y 
	 */
	@Override
	public boolean checkwin(int x, int y) {
		if (x == maze.getCols() - 1 && y == maze.getRows() - 1)
			return true;
		return false;
	}

	//setters getters
	public CommonCharacter getCharacter() {
		return character;
	}
	public int getCharOp() {
		return charOp;
	}
	
	public boolean isDiagonals() {
		return Diagonals;
	}

	@Override
	public void setDiagonals(boolean diagonals) {
		Diagonals = diagonals;
	}


}
