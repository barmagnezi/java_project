package view.viewGUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
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
	
	Label LBmazeName;
	Button Bhelp;
	Label LBsteps;
	Button Bstartover;
	Canvas Maze;
	Group GroupCharacters;
	Group GroupBackgroundMaze;
	
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
		
		Maze=new Canvas(this, SWT.NONE);
		Maze.setBackground(new Color(null,10,50,30));
		Maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		GroupCharacters=new Group(this, SWT.SHADOW_OUT);
		GroupCharacters.setText("Characters");
		GroupCharacters.setLayout(new GridLayout(3, true));
		GroupCharacters.setLayoutData(new GridData(SWT.LEFT, SWT.DOWN,  false, false));
		
		for(int i = 0; i < 3; i++) 
			new Button(GroupCharacters, SWT.RADIO).setText("option " + (i + 1));       

		GroupBackgroundMaze=new Group(this, SWT.SHADOW_OUT);
		GroupBackgroundMaze.setText("background");
		GroupBackgroundMaze.setLayout(new GridLayout(3, true));
		GroupBackgroundMaze.setLayoutData(new GridData(SWT.LEFT, SWT.DOWN,  false, false));
		
		for(int i = 0; i < 3; i++) 
			new Button(GroupBackgroundMaze, SWT.RADIO).setText("option " + (i + 1));  
		
		load();

	}
	public void load(){
		LBmazeName.setText("Maze name: "+mazeName);
		LBsteps.setText("Number of steps: "+steps);

	}


}
