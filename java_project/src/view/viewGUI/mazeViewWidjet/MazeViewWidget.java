package view.viewGUI.mazeViewWidjet;

import java.util.Observer;

import model.MazeSearchableFixed;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.demo.MazeSearchable;
import algorithms.mazeGenerators.Maze;
import algorithms.search.CommonSearcher;
import algorithms.search.Solution;
import algorithms.search.aStar.AstarSearcher;
import algorithms.search.aStar.MazeAirDistance;
import view.View;
import view.viewGUI.mazeDisplayerAndCharecters.MazeDisplayerGUI;

public class MazeViewWidget extends Canvas {

	String mazeName="Not loaded maze";
	Maze maze=null;
	int steps=0;
	int clue=0;
	ViewGUI ViewGUI=new ViewGUI(this);
	boolean Diagonals =true; //!!!!!need to know by the presenter
	
	public Boolean checkMotionFlag=new Boolean(false); //for check motion function 
	Label LBmazeName;
	Label LHelp;
	Label LBsteps;
	Button Bstartover;
	Button BshowSolution;
	Button BgiveClue;
	MazeDisplayerGUI MazeDisplayer;
	Group GroupCharacters;
	Button[] CharactersButtons;
	Group GroupBackgroundMaze;
	Button[] BackgroundsButtons;
	
	String path;
	int Op;
	
	public void setProperties(String path){
		ViewGUI.setproperties(path);
	}
	public void addObserver(Observer presenter){
		ViewGUI.addObserver(presenter);
	}
	public MazeViewWidget(Composite parent, int style) {
		super(parent, style);		
		this.setLayout(new GridLayout(2,false));
		getShell().setBackgroundMode(SWT.INHERIT_FORCE);
		LBmazeName=new Label(this, SWT.NONE);	
		LBmazeName.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		
		LHelp=new Label(this, SWT.PUSH);
		LHelp.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false, 1	, 1));	
		LHelp.setText("Have a question?press F1 :)");
		
		LBsteps=new Label(this, SWT.NONE);	
		LBsteps.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		
		Bstartover=new Button(this, SWT.PUSH);
		Bstartover.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		Bstartover.setText("start over");
		Bstartover.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getParent().getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						System.out.println("MazeDisplayer.showMaze(maze,true);");
						MazeDisplayer.Startover(maze);
						steps=0;
						clue=0;
						load();
						MazeDisplayer.setFocus();
					}
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		/*Bstartover.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				MazeDisplayer.setFocus();
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				MazeDisplayer.setFocus();
			}
		});*/
		
		BshowSolution=new Button(this, SWT.PUSH);
		BshowSolution.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1));
		BshowSolution.setText("show solution");
		BshowSolution.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MazeDisplayer.setFocus();
				ViewGUI.displaySolution(mazeName);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {	
			}
		});
		/*BshowSolution.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				MazeDisplayer.setFocus();
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				MazeDisplayer.setFocus();
			}
		});*/
		
		BgiveClue=new Button(this, SWT.PUSH);
		BgiveClue.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		BgiveClue.setText("give me clue");
		BgiveClue.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(maze!=null)
					clue(maze);
				//ViewGUI.getclue(MazeDisplayer.getCharacter().getRealx(), MazeDisplayer.getCharacter().getRealy());
				MazeDisplayer.setFocus();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {	
			}
		});
		/*BgiveClue.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				MazeDisplayer.setFocus();
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				MazeDisplayer.setFocus();
			}
		});*/
		
		MazeDisplayer=new MazeDisplayerGUI(this, SWT.BORDER_SOLID,"resources/images/grass.png","resources/images/trees.png");
		MazeDisplayer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		//if() animation set..
		MazeDisplayer.start();
		//														=== Character Settings ===
		GroupCharacters=new Group(this, SWT.SHADOW_OUT);
		GroupCharacters.setText("Characters");
		GroupCharacters.setLayout(new GridLayout(3, true));
		GroupCharacters.setLayoutData(new GridData(SWT.LEFT, SWT.DOWN,  false, false,2,1));
		GroupCharacters.setBackground(new org.eclipse.swt.graphics.Color(null, new RGB(255, 255, 255)));
		CharactersButtons=new Button[3];
		for(int i = 0; i < 3; i++) {
			CharactersButtons[i]=new Button(GroupCharacters, SWT.RADIO);
			CharactersButtons[i].setText("option " + (i + 1));
		}
		CharactersButtons[0].setSelection(true);
		
		CharactersButtons[0].setText("Circle");
		CharactersButtons[0].addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(CharactersButtons[0].getSelection()==true){
			        ColorDialog cd = new ColorDialog(getShell());
			        cd.setText("ColorDialog Demo");
			        cd.setRGB(new RGB(255, 255, 255));
			        RGB col = cd.open();
			        MazeDisplayer.changeCharacter(1, col, null);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		CharactersButtons[1].setText("Picture");
		CharactersButtons[1].addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(CharactersButtons[1].getSelection()==true){
					SelectPic SP= new SelectPic("Select a picture",300,230, getDisplay(), MazeDisplayer.getCharOp());
					SP.run();
					if(SP.getStr()==null){
						MazeDisplayer.changeCharacter(SP.getChoise(), null, null);
					}else
						MazeDisplayer.changeCharacter(1001, null, SP.getStr());
					MazeDisplayer.setFocus();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		CharactersButtons[2].setText("Animation");
		/*int before1 = 0;
		int coi;
		if(CharactersButtons[0].getSelection()==true)
			before1=0;
		if(CharactersButtons[1].getSelection()==true)
			before1=1;
		if(CharactersButtons[2].getSelection()==true)
			before1=2;
			
			if Clicked No on save setting ==> SP.getChoise()==-2.		
		CharactersButtons[0].setSelection(false);
		CharactersButtons[0].setSelection(false);
		CharactersButtons[0].setSelection(false);
		if(before1==0)
			CharactersButtons[0].setSelection(true);
		if(before1==1)
			CharactersButtons[1].setSelection(true);
		if(before1==1)
			CharactersButtons[2].setSelection(true);
		*/
		
		CharactersButtons[2].addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(CharactersButtons[2].getSelection()==true){
					SelectAnim SP= new SelectAnim("Select an animation",250,200, getDisplay(),MazeDisplayer.getCharOp());
					SP.run();
					System.out.println(SP.getStr());
					System.out.println(SP.getChoise());
					if(SP.getStr()==null){
						MazeDisplayer.changeCharacter(SP.getChoise(), null, null);
					}else
						MazeDisplayer.changeCharacter(1002, null, SP.getStr());
					MazeDisplayer.setFocus();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		//														=== BackGround Settings ===
		GroupBackgroundMaze=new Group(this, SWT.SHADOW_OUT);
		GroupBackgroundMaze.setText("background");
		GroupBackgroundMaze.setLayout(new GridLayout(3, true));
		GroupBackgroundMaze.setLayoutData(new GridData(SWT.LEFT, SWT.DOWN,  false, false));
		
		BackgroundsButtons=new Button[3];
		for(int i = 0; i < 3; i++) {
			BackgroundsButtons[i]=new Button(GroupBackgroundMaze, SWT.RADIO);
		}
		BackgroundsButtons[0].setText("garden");
		BackgroundsButtons[0].addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MazeDisplayer.changeBackDesign("resources/images/grass.png","resources/images/trees.png");
				GroupBackgroundMaze.setBackgroundImage(new Image(null, "resources/images/grass.png"));
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		BackgroundsButtons[1].setText("desert");
		BackgroundsButtons[1].addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MazeDisplayer.changeBackDesign("resources/images/desert.jpg","resources/images/brick_texture.jpg");
				GroupBackgroundMaze.setBackgroundImage(new Image(null, "resources/images/desert.jpg"));
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		BackgroundsButtons[2].setText("Black&White");
		BackgroundsButtons[2].addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MazeDisplayer.changeBackDesign("resources/images/white.png","resources/images/black.png");
				GroupBackgroundMaze.setBackgroundImage(new Image(null, "resources/images/white.png"));
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		BackgroundsButtons[0].setSelection(true);
		GroupBackgroundMaze.setBackgroundImage(new Image(null, "resources/images/grass.png"));
		
		MazeDisplayer.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.keyCode==16777220){		//right
					int currentX=MazeDisplayer.getCharacter().getRealx();
					int currentY=MazeDisplayer.getCharacter().getRealy();
					if(CheckMotion(currentX, currentY, currentX+1, currentY)){
						MazeDisplayer.CharMoved(1);
						steps++;
						checkwin(currentX+1,currentY);
					}
				}			
				if(arg0.keyCode==16777219){			//left
					int currentX=MazeDisplayer.getCharacter().getRealx();
					int currentY=MazeDisplayer.getCharacter().getRealy();
					if(CheckMotion(currentX, currentY, currentX-1, currentY)){
						MazeDisplayer.CharMoved(3);
						steps++;
						checkwin(currentX-1,currentY);
					}
				}
				if(arg0.keyCode==16777217){		//up
					int currentX=MazeDisplayer.getCharacter().getRealx();
					int currentY=MazeDisplayer.getCharacter().getRealy();
					if(CheckMotion(currentX, currentY, currentX, currentY-1)){
						MazeDisplayer.CharMoved(2);
						steps++;
						checkwin(currentX,currentY-1);
					}
				}
				if(arg0.keyCode==16777218){			//down
					int currentX=MazeDisplayer.getCharacter().getRealx();
					int currentY=MazeDisplayer.getCharacter().getRealy();
					if(CheckMotion(currentX, currentY, currentX, currentY+1)){
						MazeDisplayer.CharMoved(4);
						steps++;
						checkwin(currentX,currentY+1);
					}
				}
				//diag
				if(arg0.character=='1'){			//down-left		
					int currentX=MazeDisplayer.getCharacter().getRealx();
					int currentY=MazeDisplayer.getCharacter().getRealy();
					if(CheckMotion(currentX, currentY, currentX-1, currentY+1)){
						MazeDisplayer.CharMoved(5);
						steps++;
						checkwin(currentX-1,currentY+1);
					}
				}
					
				if(arg0.character=='2'){ 			//down-right
					int currentX=MazeDisplayer.getCharacter().getRealx();
					int currentY=MazeDisplayer.getCharacter().getRealy();
					if(CheckMotion(currentX, currentY, currentX+1, currentY+1)){
						MazeDisplayer.CharMoved(6);
						steps++;
						checkwin(currentX+1,currentY+1);
					}
				}
				if(arg0.character=='4'){ 			//up-left
					int currentX=MazeDisplayer.getCharacter().getRealx();
					int currentY=MazeDisplayer.getCharacter().getRealy();
					if(CheckMotion(currentX, currentY, currentX-1, currentY-1)){
						MazeDisplayer.CharMoved(7);
						steps++;
						checkwin(currentX-1,currentY-1);
					}
				}
				if(arg0.character=='5'){ 			//up-right
					int currentX=MazeDisplayer.getCharacter().getRealx();
					int currentY=MazeDisplayer.getCharacter().getRealy();
					if(CheckMotion(currentX, currentY, currentX+1, currentY-1)){
						MazeDisplayer.CharMoved(8);
						steps++;
						checkwin(currentX+1,currentY-1);
					}
				}
				load();
			}
		});

	  //button5 - Load settings
	  		Button BLoad=new Button(this, SWT.PUSH);
	  		BLoad.setLayoutData(new GridData(SWT.RIGHT, SWT.DOWN, false, false, 1, 1));		
	  		BLoad.setText("Load settings");
	  		BLoad.addSelectionListener(new SelectionListener() {
	  			@Override
	  			public void widgetSelected(SelectionEvent arg0) {
	  				getParent().getDisplay().syncExec(new Runnable() {
	  					@Override
	  					public void run() {
	  						FileDialog fd=new FileDialog(getShell(),SWT.OPEN);
	  						fd.setText("open");
	  						fd.setFilterPath("");
	  					  String[] names = {
	  					      "Extensible Markup Language (*.xml)"};
	  						String[] filterExt = { "*.xml"};
	  						fd.setFilterNames(names);
	  						fd.setFilterExtensions(filterExt);
	  						fd.open();
	  						if(fd.getFileName()=="")
	  							return;
	  						setProperties(fd.getFilterPath()+"/"+fd.getFileName());
	  						MazeDisplayer.setFocus();
	  					}
	  				});
	  			}
	  			@Override
	  			public void widgetDefaultSelected(SelectionEvent arg0) {}
	  		});
		this.setBackgroundImage(new Image(null, "resources/images/background.png"));
		load();

	}
	public void load(){
		LBmazeName.setText("Maze name: "+mazeName);
		LBsteps.setText("Number of steps: "+String.valueOf(steps)+"\tNumber of clues: "+String.valueOf(clue));
		
	}
	
	public void generateMaze(String name,int rows,int cols){
		ViewGUI.generateMaze(name,rows,cols);
	}
	
	public void loadMaze(String name){
		ViewGUI.displaymaze(name);	
		if(MazeDisplayer.character!=null){								// Is this Ok ????????????????????????????????????????????????????????????????
			MazeDisplayer.character.setRealx(0);
			MazeDisplayer.character.setRealy(0);
		}
	}
	
	public void solve(String name){
		ViewGUI.solveMaze(name);
	}
	
	public void clue(Maze maze){
		//System.out.println("clue");
		int x=MazeDisplayer.character.getRealx();
		int y=MazeDisplayer.character.getRealy();
		MazeSearchable MS = new MazeSearchableFixed(maze, maze.getCell(y, x), maze.getCell(maze.getRows()-1, maze.getCols()-1), Diagonals, 10, 15); //new MazeSearchable(maze, false);	
		CommonSearcher se=new AstarSearcher(new MazeAirDistance());			//   CELL(ROW,COL)
		Solution Sol=se.search(MS);
		String last[] = Sol.toString().split("->");	//COL,ROW-->COL,ROW
		if(last.length>1){
			clue++;
			int Cluex=Character.getNumericValue(last[1].charAt(0));
			int Cluey=Character.getNumericValue(last[1].charAt(2));
			MazeDisplayer.mark(Cluey, Cluex);
			load();
			checkwin(Cluey , Cluex);
		}
		else{
			checkwin(maze.getCols()-1 , maze.getRows()-1);
		}
			
	}
	
	public void displayMaze(algorithms.mazeGenerators.Maze m) {
		maze=m;
		MazeDisplayer.showMaze(m,false);
	}
	
	/*public void displaySolution(Solution s) {
		//System.out.println("print sol on MazeDisplayer canvas");
		int cols=maze.getCols();
		int rows=maze.getRows();
		int x=MazeDisplayer.character.getRealx();
		int y=MazeDisplayer.character.getRealy();
		while(x!=cols-1 && y!=rows-1){
			//synchronized(MazeDisplayer.character){
				try {
					System.out.println("Sfdbv");
					clue(maze);
					Thread.sleep(500);
					//MazeDisplayer.character.wait(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			//}
			x=MazeDisplayer.character.getRealx();
			y=MazeDisplayer.character.getRealy();
		}
		if(x!=cols-1 || y!=rows-1){
			MazeDisplayer.mark(cols-1, cols-1);
		}
			
			
	}*/
	public void displaySolution(Solution s) {
		MazeDisplayer.showSolution(s);
	}
	
	public void displayString(String msg) {
		
		MessageBox messageBox = new MessageBox(this.getShell(),  SWT.OK);
		messageBox.setMessage(msg);
		messageBox.setText("MESSEGE");
		messageBox.open();
	}
	public View getView() {
		return ViewGUI;
	}
	public void showClue(String clue) {
		String[] RowCol=clue.split(",");
		int row=Integer.parseInt(RowCol[0]);
		int col=Integer.parseInt(RowCol[1]);
		///need print the clue on the 
	}
	public void setMazeName(String name){
		mazeName=name;
		load();
	}
	public void exit(){
		ViewGUI.exit();
		MazeDisplayer.stop();/// BAR ================================================= This stops the therad for the animaton, this line ok?
	}
	public void start() {
		ViewGUI.start();
	}
	/*public boolean checkMotion(int CurrentX,int CurrentY,int nextX,int nextY){
		boolean flag=true;
		//System.out.println();
		if(nextX<0 || nextY<0)
			return false;
		//for Diagonals
		if(CurrentX!= nextX && CurrentY!=nextY){
			if(nextX>maze.getCols()-1 ||nextY>maze.getRows()-1)
				return false;
			int i=0;
			if(CurrentX+1==nextX)
				if(maze.getCell(CurrentY, CurrentX).getRightWall().isExist())
					i++;
			if(CurrentX-1==nextX)
				if(maze.getCell(CurrentY, nextX).getRightWall().isExist())
					i++;
			if(CurrentY+1==nextY)
				if(maze.getCell(CurrentY, CurrentX).getBottomWall().isExist())
					i++;
			System.out.println(CurrentX+","+CurrentY);
			if(CurrentY-1==nextY){maze.getCell(nextY, CurrentX).printRowCol();
				if(maze.getCell(nextY, CurrentX).getBottomWall().isExist())
					i++;}
			if(i==2)
				return false;
			return true;
		}
		if(CurrentX+1==nextX)
			flag=flag&&(!maze.getCell(CurrentY, CurrentX).getRightWall().isExist());
		if(CurrentX-1==nextX)
			flag=flag&&(!maze.getCell(nextY, nextX).getRightWall().isExist());
		if(CurrentY+1==nextY)
			flag=flag&&(!maze.getCell(CurrentY, CurrentX).getBottomWall().isExist());
		if(CurrentY-1==nextY)
			flag=flag&&(!maze.getCell(nextY, nextX).getBottomWall().isExist());
		return flag;
	}*/
	
	
	/*The way with model(better)
	public boolean checkMotion(int CurrentX,int CurrentY,int nextX,int nextY){
		ViewGUI.checkMotion(mazeName, CurrentY, CurrentX, nextY, nextX);
		try {
			checkMotionFlag.wait();
			return checkMotionFlag;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	*/
	public boolean CheckMotion(int CurrentX,int CurrentY,int nextX,int nextY){
		boolean flag=true;
		//System.out.println();
		if(nextX<0 || nextY<0 || nextX==maze.getCols() || nextY==maze.getRows())
			return false;
		if(!(nextX!=CurrentX &&CurrentY!=nextY)){
			if(CurrentX+1==nextX)
				flag=flag&&(!maze.getCell(CurrentY, CurrentX).getRightWall().isExist());
			if(CurrentX-1==nextX)
				flag=flag&&(!maze.getCell(nextY, nextX).getRightWall().isExist());
			if(CurrentY+1==nextY)
				flag=flag&&(!maze.getCell(CurrentY, CurrentX).getBottomWall().isExist());
			if(CurrentY-1==nextY)
				flag=flag&&(!maze.getCell(nextY, nextX).getBottomWall().isExist());
			return flag;
		}else{
			if (Diagonals==true){
				int col=CurrentX;
				int row=CurrentY;
				//right-up
				if(CurrentX+1==nextX && CurrentY-1==nextY){								  	
					if(maze.getCell(row-1, col).getRightWall().isExist() &&      // |_	
							maze.getCell(row-1, col+1).getBottomWall().isExist())//.
						flag=false;
					if(maze.getCell(row, col).getRightWall().isExist()&&			//  |
							maze.getCell(row-1, col).getRightWall().isExist())		// .|
						flag=false;
					if(maze.getCell(row-1, col).getBottomWall().isExist()&&			//_ _
							maze.getCell(row-1, col+1).getBottomWall().isExist())   //.
						flag=false;
					if(maze.getCell(row-1, col).getBottomWall().isExist()&&   ///corner like ._|
							maze.getCell(row, col).getRightWall().isExist())
						flag=false;
					return flag;
				}
				//right-down
				if(CurrentX+1==nextX && CurrentY+1==nextY){								  	
					if(maze.getCell(row+1, col).getRightWall().isExist() &&    //.
							maze.getCell(row, col+1).getBottomWall().isExist())// |-
						flag=false;
					if(maze.getCell(row, col).getRightWall().isExist()&&			// . |
							maze.getCell(row+1, col).getRightWall().isExist())		//   |
						flag=false;
					if(maze.getCell(row, col).getBottomWall().isExist()&&			//.
							maze.getCell(row, col+1).getBottomWall().isExist())   //--
						flag=false;
					if(maze.getCell(row, col).getBottomWall().isExist()&&   ///corner like ._|
							maze.getCell(row, col).getRightWall().isExist())
						flag=false;
					return flag;
				}
				//left-down
				if(CurrentX-1==nextX && CurrentY+1==nextY){ 									  	
					if(maze.getCell(row+1, col-1).getRightWall().isExist() &&    //   .
							maze.getCell(row, col-1).getBottomWall().isExist())  // -|
						flag=false;
					if(maze.getCell(row, col-1).getRightWall().isExist()&&			//   |.
							maze.getCell(row+1, col-1).getRightWall().isExist())	//   |
						flag=false;
					if(maze.getCell(row, col).getBottomWall().isExist()&&			// .
							maze.getCell(row, col-1).getBottomWall().isExist())     //--
						flag=false;
					if(maze.getCell(row, col).getBottomWall().isExist()&&   ///corner like ._|
							maze.getCell(row, col-1).getRightWall().isExist())
						flag=false;
					return flag;
				}
				//left-up
				if(CurrentX-1==nextX && CurrentY-1==nextY){								  	
					if(maze.getCell(row-1, col-1).getRightWall().isExist() &&      // _|	
							maze.getCell(row-1, col-1).getBottomWall().isExist())  //   .
						flag=false;
					if(maze.getCell(row, col-1).getRightWall().isExist()&&				//  |
							maze.getCell(row-1, col-1).getRightWall().isExist())		//  |.
						flag=false;
					if(maze.getCell(row-1, col).getBottomWall().isExist()&&			//_ _
							maze.getCell(row-1, col-1).getBottomWall().isExist())   //  .
						flag=false;
					if(maze.getCell(row-1, col).getBottomWall().isExist()&&   ///corner like ._|
							maze.getCell(row, col-1).getRightWall().isExist())
						flag=false;
					return flag;
				}
			}else
				return false;
		}
		return false;

	}
	
	public void checkwin(int x,int y){
		if(x==maze.getCols()-1 && y==maze.getRows()-1)
			new winnerPage("winner", 270, 240, getDisplay(), steps, clue).run();
	}

}