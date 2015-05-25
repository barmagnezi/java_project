package view.viewGUI;

import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.demo.MazeSearchable;
import algorithms.mazeGenerators.Cell;
import algorithms.mazeGenerators.Maze;
import algorithms.search.CommonSearcher;
import algorithms.search.Searchable;
import algorithms.search.Solution;
import algorithms.search.State;
import algorithms.search.aStar.AstarSearcher;
import algorithms.search.aStar.MazeAirDistance;
import view.View;

public class MazeViewWidget extends Canvas {

	String mazeName="Not loaded maze";
	Maze maze=null;
	int steps=0;
	ViewGUI ViewGUI=new ViewGUI(this);
	boolean Diagonals =false; //!!!!!need to know by the presenter
	
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
	
	public void setProperties(String path){
		ViewGUI.setproperties(path);
	}
	public void addObserver(Observer presenter){
		ViewGUI.addObserver(presenter);
	}
	public MazeViewWidget(Composite parent, int style) {
		super(parent, style);		
		this.setLayout(new GridLayout(2,false));
		
		LBmazeName=new Label(this, SWT.NONE);	
		LBmazeName.setLayoutData(new GridData(SWT.LEFT, SWT.None, false, false, 1, 1));
		
		LHelp=new Label(this, SWT.PUSH);
		LHelp.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false, 1	, 1));	
		LHelp.setText("Have a question?press F1 :)");
		
		LBsteps=new Label(this, SWT.NONE);	
		LBsteps.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
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
						MazeDisplayer.showMaze(maze,true);
						MazeDisplayer.setFocus();
					}
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		Bstartover.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				MazeDisplayer.setFocus();
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				MazeDisplayer.setFocus();
			}
		});
		
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
		BshowSolution.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				MazeDisplayer.setFocus();
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				MazeDisplayer.setFocus();
			}
		});
		
		BgiveClue=new Button(this, SWT.PUSH);
		BgiveClue.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		BgiveClue.setText("give me clue");
		BgiveClue.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MazeDisplayer.setFocus();
				ViewGUI.getclue(MazeDisplayer.getCharacter().getRealx(), MazeDisplayer.getCharacter().getRealy());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {	
			}
		});
		BgiveClue.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				MazeDisplayer.setFocus();
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				MazeDisplayer.setFocus();
			}
		});
		
		MazeDisplayer=new MazeDisplayerGUI(this, SWT.BORDER_SOLID,"resources/images/grass.png","resources/images/trees.png");
		MazeDisplayer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		GroupCharacters=new Group(this, SWT.SHADOW_OUT);
		GroupCharacters.setText("Characters");
		GroupCharacters.setLayout(new GridLayout(3, true));
		GroupCharacters.setLayoutData(new GridData(SWT.LEFT, SWT.DOWN,  false, false,2,1));
		
		CharactersButtons=new Button[3];
		for(int i = 0; i < 3; i++) {
			CharactersButtons[i]=new Button(GroupCharacters, SWT.RADIO);
			CharactersButtons[i].setText("option " + (i + 1));
		}
		CharactersButtons[0].setSelection(true);
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
				MazeDisplayer.changeDesign("resources/images/grass.png","resources/images/trees.png");
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
				MazeDisplayer.changeDesign("resources/images/desert.jpg","resources/images/brick_texture.jpg");
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
				MazeDisplayer.changeDesign("resources/images/white.png","resources/images/black.png");
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
					if(checkMotion(currentX, currentY, currentX+1, currentY))
						MazeDisplayer.CharMoved(1);
				}			
				if(arg0.keyCode==16777219){			//left
					int currentX=MazeDisplayer.getCharacter().getRealx();
					int currentY=MazeDisplayer.getCharacter().getRealy();
					if(checkMotion(currentX, currentY, currentX-1, currentY))
						MazeDisplayer.CharMoved(3);
				}
				if(arg0.keyCode==16777217){		//up
					int currentX=MazeDisplayer.getCharacter().getRealx();
					int currentY=MazeDisplayer.getCharacter().getRealy();
					if(checkMotion(currentX, currentY, currentX, currentY-1))
						MazeDisplayer.CharMoved(2);
				}
				if(arg0.keyCode==16777218){			//down
					int currentX=MazeDisplayer.getCharacter().getRealx();
					int currentY=MazeDisplayer.getCharacter().getRealy();
					if(checkMotion(currentX, currentY, currentX, currentY+1))
						MazeDisplayer.CharMoved(4);
				}
				//diag
				if(arg0.character=='1'){			//down-left		
					int currentX=MazeDisplayer.getCharacter().getRealx();
					int currentY=MazeDisplayer.getCharacter().getRealy();
					if(checkMotion(currentX, currentY, currentX-1, currentY+1))
						MazeDisplayer.CharMoved(5);
				}
					
				if(arg0.character=='2'){ 			//down-right
					int currentX=MazeDisplayer.getCharacter().getRealx();
					int currentY=MazeDisplayer.getCharacter().getRealy();
					if(checkMotion(currentX, currentY, currentX+1, currentY+1))
						MazeDisplayer.CharMoved(6);
				}
				if(arg0.character=='4'){ 			//up-left
					int currentX=MazeDisplayer.getCharacter().getRealx();
					int currentY=MazeDisplayer.getCharacter().getRealy();
					if(checkMotion(currentX, currentY, currentX-1, currentY-1))
						MazeDisplayer.CharMoved(7);
				}
				if(arg0.character=='5'){ 			//up-right
					int currentX=MazeDisplayer.getCharacter().getRealx();
					int currentY=MazeDisplayer.getCharacter().getRealy();
					if(checkMotion(currentX, currentY, currentX+1, currentY-1))
						MazeDisplayer.CharMoved(8);
				}
				
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
	  						String[] filterExt = { "*.xml"};
	  						fd.setFilterExtensions(filterExt);
	  						if(fd.open()==null)
	  							return;
	  						setProperties(fd.open());
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
		LBsteps.setText("Number of steps: "+steps);;
	}
	
	public void generateMaze(String name,int rows,int cols){
		ViewGUI.generateMaze(name,rows,cols);
	}
	
	public void loadMaze(String name){
		ViewGUI.displaymaze(name);	
	}
	
	public void solve(String name){
		ViewGUI.solveMaze(name);
	}
	
	public void clue(Maze maze){
		System.out.println("clue");
		int x=MazeDisplayer.character.getRealx();
		int y=MazeDisplayer.character.getRealy();
		String sol = "0x0->0x1->0x2.....->8x7->7x7->7x8->7x9->8x9->9x9";	//solve(maze);
		String[] sols = sol.split("->");
		double min=0;
		int GotoX,GotoY;
		for(int i=0; i<sols.length;i++){
			if(Math.sqrt(	Math.pow((x-sols[i].charAt(0)), 2) + Math.pow((y-sols[i].charAt(2)), 2)	)<min){
				min=Math.sqrt(Math.pow((x-sols[i].charAt(0)), 2) + Math.pow((y-sols[i].charAt(2)), 2));
				GotoX=sols[i].charAt(0);
				GotoY=sols[i].charAt(2);
			}
		}
		
		MazeSearchable MS = new MazeSearchable(maze, false);	
		// START state DEFIENED AS 0,0 === WE NEED TO CHANGE IT TO MazeDisplayer.character.getX() , MazeDisplayer.character.getY()
		// GOAL state DEFIEND AS (n-1),(n-1) === WE NEED TO CHANGE IT GO GotoX , GotoY	(Or delete the code before and leave it like this)
		CommonSearcher se=new AstarSearcher(new MazeAirDistance());
		Solution Sol=se.search(MS);
		//Add toString() In Solution - Almost Exactly as Print()
		String last = Sol.toString();
		//Next step(clue) is:
		System.out.println(last.charAt(0)+last.charAt(2));
	}
	
	public void displayMaze(algorithms.mazeGenerators.Maze m) {
		maze=m;
		MazeDisplayer.showMaze(m,false);
	}
	
	public void displaySolution(Solution s) {
		System.out.println("print sol on MazeDisplayer canvas");
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
	}
	public void start() {
		ViewGUI.start();
	}
	public boolean checkMotion(int CurrentX,int CurrentY,int nextX,int nextY){
		boolean flag=true;
		System.out.println();
		if(nextX<0 || nextY<0)
			return false;
		//for Diagonals
		if(CurrentX!= nextX && CurrentY!=nextY){
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
			System.out.println(i);
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
	}
	

}
