package view.viewGUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class MazeViewWidget extends Canvas {

	private String mazeName="Not loaded maze";
	private int steps=0;
	
	Label LBmazeName;
	Button Bhelp;
	Label LBsteps;
	Button Bstartover;
	
	public MazeViewWidget(Composite parent, int style) {
		super(parent, style);
		load();

	}
	public void load(){
		this.setLayout(new GridLayout(2,false));
		
		LBmazeName=new Label(this, SWT.NONE);	
		LBmazeName.setText("Maze name: "+mazeName);
		LBmazeName.setLayoutData(new GridData(SWT.LEFT, SWT.None, false, false, 1, 1));
		
		Bhelp=new Button(this, SWT.PUSH);
		Bhelp.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false, 1	, 1));	
		Bhelp.setText("help");
		
		LBsteps=new Label(this, SWT.NONE);	
		LBsteps.setText("Number of steps: "+steps);
		LBsteps.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
		Bstartover=new Button(this, SWT.PUSH);
		Bstartover.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		Bstartover.setText("start over");
		
		Canvas Maze=new Canvas(this, SWT.NONE);
		Maze.setBackground(new Color(null,10,50,30));
		Maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
	}


}
