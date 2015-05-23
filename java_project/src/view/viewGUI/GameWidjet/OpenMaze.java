package view.viewGUI.GameWidjet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import view.viewGUI.MazeViewWidget;

public class OpenMaze extends BasicWindow{

	MazeViewWidget mazeView;
	public OpenMaze(String title, int width, int height, Display disp, MazeViewWidget mazeView) {
		super(title, width, height, disp);
		this.mazeView=mazeView;
	}

	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2,false));
		
		Label LBEMazeName = new Label(shell, SWT.NONE);
		LBEMazeName.setText("Enter the mazes name:");
		LBEMazeName.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
		Text TMazeName = new Text(shell, SWT.BORDER);
		TMazeName.setLayoutData(new GridData(SWT.None, SWT.None, false, false, 3, 1));
		

		Button BOpenMaze=new Button(shell, SWT.PUSH);
		BOpenMaze.setLayoutData(new GridData(SWT.RIGHT, SWT.None, false, false, 2, 1));		
		BOpenMaze.setText("Open this maze");
		BOpenMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				mazeView.loadMaze(TMazeName.getText());
				shell.dispose();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		shell.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e){
                Rectangle rect = shell.getBounds();
                if(rect.width != 130 || rect.height!=250)
                    shell.setBounds(rect.x, rect.y, 250, 130);
			}
		});
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