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
		super(title, width, height, disp);
	}

	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(4,false));
		
		Label LBEMazeName = new Label(shell, SWT.NONE);
		LBEMazeName.setText("Enter the mazes name:");
		LBEMazeName.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
		Text TMazeName = new Text(shell, SWT.BORDER);
		TMazeName.setLayoutData(new GridData(SWT.None, SWT.None, false, false, 3, 1));
		
		new Label(shell, SWT.NONE).setText("Enter the mazes size(rows,cols):");
		
		Text Trow = new Text(shell, SWT.BORDER);
		Trow.setLayoutData(new GridData(SWT.LEFT, SWT.NONE, false, false, 1, 1));
		Trow.setText("rows");
		new Label(shell, SWT.NONE).setText(",");
		Text Tcol = new Text(shell, SWT.BORDER);
		Tcol.setText("cols");
		Tcol.setLayoutData(new GridData(SWT.LEFT, SWT.NONE, false, false, 1, 1));

		Button BOpenMaze=new Button(shell, SWT.PUSH);
		BOpenMaze.setLayoutData(new GridData(SWT.RIGHT, SWT.None, false, false, 4, 1));		
		BOpenMaze.setText("Create this maze");
		BOpenMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MazeViewWidgetStub Stub = new MazeViewWidgetStub(shell, 0);
				Stub.generateMaze(TMazeName.getText(),Integer.parseInt(Trow.getText()),Integer.parseInt(Tcol.getText()));
				shell.dispose();
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
