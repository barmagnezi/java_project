package view.viewGUI.mazeViewWidjet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import view.viewGUI.GameWidget.BasicWindow;



public class winnerPage extends BasicWindow {

	public int steps;
	
	public winnerPage(String title, int width, int height,Display display,int steps) {
		super(title,width,height,display);
		this.steps=steps;
	}

	@Override
	protected void initWidgets() {
		shell.setLayout(new GridLayout(1,false));
		shell.setBackgroundImage(new Image(null, "resources/images/back_winner.jpg"));
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		Label LBUseDef = new Label(shell, SWT.FILL);
		LBUseDef.setText("The number of your steps to resolve were:"+steps);
		LBUseDef.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
	}
	@Override
	public void run() {
		initWidgets();
		shell.open();
		// main event loop
		 while(!shell.isDisposed()){ // while window isn't closed

		    // 1. read events, put then in a queue.
		    // 2. dispatch the assigned listener
		    if(!display.readAndDispatch()){ 	// if the queue is empty
		       display.sleep(); 			// sleep until an event occurs 
		    }

		 } // shell is disposed
	}

}
