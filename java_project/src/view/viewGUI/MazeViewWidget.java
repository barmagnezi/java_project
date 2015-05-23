package view.viewGUI;


import java.util.HashMap;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

import View.Command;
import algorithms.search.Solution;
import view.View;

public class MazeViewWidget extends Canvas implements View{

	private String mazeName="Not loaded maze";
	private int steps=0;
	brainOfGUI helper=new brainOfGUI();
	
	Label LBmazeName;
	Label LHelp;
	Label LBsteps;
	Button Bstartover;
	MazeDisplayerGUI MazeDisplayer;
	Group GroupCharacters;
	Button[] CharactersButtons;
	Group GroupBackgroundMaze;
	Button[] BackgroundsButtons;
	public void setProperties(String path){
		helper.setproperties(path);
	}
	public void addObserver(Observer presenter){
		helper.addObserver(presenter);
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
				getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						//new CreateNMaze("New Maze", 300, 150, getDisplay()).run();
						MazeDisplayer.setFocus();
					}
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		MazeDisplayer=new MazeDisplayerGUI(this, SWT.BORDER_SOLID);
		MazeDisplayer.setBackground(new Color(null,10,50,30));
		MazeDisplayer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
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
		
		MazeDisplayer.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {

			}
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.keyCode==16777220)
					System.out.println("right");			
				if(arg0.keyCode==16777219)
					System.out.println("left");
				if(arg0.keyCode==16777217)
					System.out.println("up");
				if(arg0.keyCode==16777218)
					System.out.println("down");
			}
		});

	  //button5 - Load settings
	  		Button BLoad=new Button(this, SWT.PUSH);
	  		BLoad.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 2, 1));		
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
	  	MazeDisplayer.setFocus();
		load();

	}
	public void load(){
		this.setBackgroundImage(new Image(null, "resources/images/background.png"));
		LBmazeName.setText("Maze name: "+mazeName);
		LBsteps.setText("Number of steps: "+steps);
		MazeDisplayer.setFocus();
	}
	
	public void generateMaze(String name,int rows,int cols){
		System.out.println("generateMaze");
		helper.generateMaze(name,rows,cols);
	}
	
	public void loadMaze(String name){
		helper.displaymaze(name);		
	}
	
	public void solve(){
		System.out.println("solve");
	}
	
	public void clue(){
		System.out.println("clue");
	}
	
	//View @Override
	
	@Override
	public void start() {
	}
	@Override
	public void setCommands(HashMap<String, Command> commands) {
		helper.setCommands(commands);
	}
	@Override
	public Command getUserCommand() {
		return helper.getUserCommand();
	}
	@Override
	public void displayMaze(algorithms.mazeGenerators.Maze m) {
		System.out.println("MazeDisplayer.showMaze(m);");
		MazeDisplayer.showMaze(m);
	}
	@Override
	public void displaySolution(Solution s) {
		System.out.println("print sol on MazeDisplayer canvas");
	}
	@Override
	public void displayString(String msg) {
		MessageBox messageBox = new MessageBox(this.getShell(),  SWT.OK);
		messageBox.setMessage("help.........");
		messageBox.setText("help");
		messageBox.open();
	}
	

}
