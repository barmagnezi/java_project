package view.viewGUI.mazeDisplayerAndCharecters;


/**
* The actual class(extends Canvas) that displayes the maze, and the character. (INSIDE WINDOW)
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
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import algorithms.search.State;

public class MazeDisplayerGUI extends Canvas {
	String scrBackground;
	String scrWalls;
	int charOp;
	Maze maze;
	GC lastPaint=null;
	public CommonCharacter character;
	public int wallWidth = 0, wallHeight = 0;
	
	boolean printsol=false; //for paint sol
	Solution sol=null;
	
	int Oldx=0,Oldy=0;	//For saving data when changing the Character
	
	boolean solveFlag=false; 
	
	RGB Color;	//For changing the character
	String path;
	
	boolean Moved=false;
	
	int frame=0;		//For animation
	TimerTask myTask;
	Timer timer;
	
	public MazeDisplayerGUI(Composite parent, int style,String scrBackground,String scrwalls) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		this.scrBackground=scrBackground;
		this.scrWalls=scrwalls;
		setBackgroundImage(new Image(null, "resources/images/mazedisplayerbackground.png"));
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
				arg0.gc.setBackgroundPattern(new Pattern(null, new Image(null, System.getProperty("user.dir")+"/"+scrWalls)));
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
	private void setCharacter(int x,int y){
		/**
		 * Setting the character of the maze by the charOp(int):
		 * 1-Ball Character
		 * 2-Picutre Character as Mario(MarioChar.png)
		 * 989-Picture Character as Senia Kalma(Senia.png)
		 * 999-Picture Character as Bar Magnezi(Bar.png)
		 * 1001-Picture character as set in the path
		 */
		if(charOp==1){
			character=new BallCharacter(x, y);
			if(Color!=null)
				character.setColor(Color);
			character.setAnimation(false);
		}
		if(charOp==2){
			character=new PicCharacter(x, y);
			character.setAnimation(false);
			character.setPath("resources/images/MarioChar.png");
		}
		if(charOp==989){
			character=new PicCharacter(x, y);
			character.setAnimation(false);
			character.setPath("resources/images/Senia.png");
		}
		if(charOp==999){
			character=new PicCharacter(x, y);
			character.setAnimation(false);
			character.setPath("resources/images/Bar.png");
		}
		if(charOp==1001){	//1001 is a code in SelectPic for an image selected by user
			character=new PicCharacter(x, y);
			character.setAnimation(false);
			character.setPath(path);
		}
		if(charOp==3){
			character=new AnimCharacter(x, y);
			character.setAnimation(true);
			character.setLoader("resources/images/marioAnimation.gif");
		}
		if(charOp==4){
			character=new AnimCharacter(x, y);
			character.setAnimation(true);
			character.setLoader("resources/images/dogAnimation.gif");
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
	public void mark(int x,int y){
		character.setRealx(x);
		character.setRealy(y);
		solveFlag=true;
		redraw();

	}
	
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
			    				timer.cancel();
			    				timer=null;
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
	public void start(){
	}
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
		this.scrWalls=scrWalls;
		if(maze!=null)
			showMaze(maze, false);
	}
	public void Startover(Maze m){
		if(character!=null){
			this.character.setRealx(0);
			this.character.setRealy(0);
		}
		printsol=false;
		setBackgroundImage(new Image(null, scrBackground));
		redraw();
	}
	
	/**
	 * Function for changing the character design, used by the MazeViewWidget.
	 * @param charOption as the charOp integer containing the number representing the wanted char.
	 * @param nColor as the color if a Ball Character selected.
	 * @param path as the path for a picture/animation if one of them selected.
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
		if(maze!=null || character!=null)
			showMaze(maze, true);
	}
	
	/**
	 * Updates the mazes show.
	 * @param m The maze we want to display.
	 * @param resetChar Used for resating the character when setting a new one.
	 */
	public void showMaze(Maze m,boolean resetChar){
		if(Moved==true){
			Oldx=this.character.getRealx();
			Oldy=this.character.getRealy();
		}
		if(resetChar==false){	//If Background changed
			Moved=false;
			if(m!=maze){
				maze=m;
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
	
	public void CharMoved(int pos){			//1 for Right, 2 for Up, 3 for Left, 4 for Down
											//5 for down-left,6 for down-Right,7 for up-left,8 for up-right
		Moved=true;
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
				
	//paint solution!!!!
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
	}
	//!!!!!!!!!!!
	public CommonCharacter getCharacter() {
		return character;
	}
	public int getCharOp() {
		return charOp;
	}
}
