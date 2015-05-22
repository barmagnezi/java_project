package view.viewGUI;


import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

public class MazeViewWidget extends Canvas {

	private String mazeName="Not loaded maze";
	private int steps=0;
	
	brainOfGUI helper=new brainOfGUI();
	
	Label LBmazeName;
	Button Bhelp;
	Label LBsteps;
	Button Bstartover;
	Canvas Maze;
	Group GroupCharacters;
	Button[] CharactersButtons;
	Group GroupBackgroundMaze;
	Button[] BackgroundsButtons;
	
	public MazeViewWidget(Composite parent, int style) {
		super(parent, style);
		
		this.setLayout(new GridLayout(2,false));
		LBmazeName=new Label(this, SWT.NONE);	
		LBmazeName.setLayoutData(new GridData(SWT.LEFT, SWT.None, false, false, 1, 1));
		
		Bhelp=new Button(this, SWT.PUSH);
		Bhelp.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false, 1	, 1));	
		Bhelp.setText("help");
		
		LBsteps=new Label(this, SWT.NONE);	
		LBsteps.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
		Bstartover=new Button(this, SWT.PUSH);
		Bstartover.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		Bstartover.setText("start over");
		
		Maze=new Canvas(this, SWT.BORDER_SOLID);
		Maze.setBackground(new Color(null,10,50,30));
		Maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		GroupCharacters=new Group(this, SWT.SHADOW_OUT);
		GroupCharacters.setText("Characters");
		GroupCharacters.setLayout(new GridLayout(3, true));
		GroupCharacters.setLayoutData(new GridData(SWT.LEFT, SWT.DOWN,  false, false));
		
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
			BackgroundsButtons[i].setText("option " + (i + 1));
		}
		BackgroundsButtons[0].setSelection(true);
		
	    this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				//if((arg0.stateMask & ) != 0)
				if(arg0.keyCode==16777220)
					System.out.println("right");				
				if(arg0.keyCode==16777219)
					System.out.println("left");
				if(arg0.keyCode==16777217)
					System.out.println("up");
				if(arg0.keyCode==16777218)
					System.out.println("down");	
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		load();

	}
	public void load(){
		this.setBackgroundImage(new Image(null, "resources/images/background.png"));
		LBmazeName.setText("Maze name: "+mazeName);
		LBsteps.setText("Number of steps: "+steps);
	}
	
	public void generateMaze(String name,int rows,int cols){
		System.out.println("generateMaze");
		helper.generateMaze(name,rows,cols);
	}
	
	public void loadMaze(String name){
		System.out.println("loadMaze");
	}
	
	public void solve(){
		System.out.println("solve");
	}
	
	public void clue(){
		System.out.println("clue");
	}


}
