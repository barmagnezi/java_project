package view.viewGUI.GameWidget;

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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import view.viewGUI.MazeViewWidget;

public class SelectPic extends BasicWindow{
	int hight,witdh;
	int choise;
	String str;

	MazeViewWidget mazeView;
	public SelectPic(String title, int width, int height, Display disp,String str) {
		super(title, width, height, disp);
		this.witdh=width;
		this.hight=height;
		this.str=str;
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
				if(TMazeName.getText().length()!=0){
					//mazeView.loadMaze(TMazeName.getText());
					str=TMazeName.getText();
					shell.dispose();
				}
				else{
					MessageBox messageBox = new MessageBox(shell,  SWT.OK);
					messageBox.setMessage("The name of the maze can't be empty");
					messageBox.setText("Error");
					messageBox.open();
					TMazeName.setFocus();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		shell.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e){
                Rectangle rect = shell.getBounds();
                if(rect.height!=hight || rect.width != witdh)
                	shell.setBounds(rect.x, rect.y, witdh, hight);
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

	public int getChoise() {
		return choise;
	}

	public String getStr() {
		return str;
	}

}