package view.viewGUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class CreateNMaze extends BasicWindow{

	public CreateNMaze(String title, int width, int height, Display disp) {
		super(title, width, height,disp);
	}

	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2,false));
		
		new Label(shell, SWT.NONE).setText("Enter the mazes name:");
		
		Text name = new Text(shell, SWT.BORDER);
		
		new Label(shell, SWT.NONE).setText("Enter the mazes size(rows,cols):");
		
		Text s1 = new Text(shell, SWT.BORDER);
		new Label(shell, SWT.NONE).setText(",");
		Text s2 = new Text(shell, SWT.BORDER);

		//button1-Create this maze
		Button BOpenMaze=new Button(shell, SWT.PUSH);
		BOpenMaze.setLayoutData(new GridData(SWT.RIGHT, SWT.None, false, false, 1, 1));		
		BOpenMaze.setText("Create this maze");
		BOpenMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
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
