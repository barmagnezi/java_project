package view.viewGUI.GameWidget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import view.viewGUI.mazeViewWidjet.MazeViewWidget;

public class CreateNMaze extends BasicWindow{
	int hight, witdh;

	MazeViewWidget mazeView;
	public CreateNMaze(String title, int width, int height, Display disp,MazeViewWidget mazeview) {
		super(title, width, height, disp);
		mazeView=mazeview;
		this.hight=height;
		this.witdh=width;
	}

	@Override
	protected void initWidgets() {
		shell.setLayout(new GridLayout(4,false));
		shell.setBackgroundImage(new Image(null, "resources/images/background.png"));
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		
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
				boolean flag=false;
				if(TMazeName.getText().length()==0){		//Name check(!=null)
					MessageBox messageBox = new MessageBox(shell,  SWT.OK);
					messageBox.setMessage("The name of the maze can't be empty");
					messageBox.setText("Error");
					messageBox.open();
					TMazeName.setFocus();
					flag=true;
				}
				//Rows check, !=null,isInt --> >2
				if(flag==false && Trow.getText().length()==0){
					MessageBox messageBox = new MessageBox(shell,  SWT.OK);
					messageBox.setMessage("The number of rows can't be empty");
					messageBox.setText("Error");
					messageBox.open();
					Trow.setFocus();
					flag=true;
				}
				if(flag==false && !isInteger(Trow.getText())){
					MessageBox messageBox = new MessageBox(shell,  SWT.OK);
					messageBox.setMessage("The number of rows have to be an integer");
					messageBox.setText("Error");
					messageBox.open();
					Trow.setFocus();
					flag=true;
				}else{
					if(flag==false && Integer.parseInt(Trow.getText())<3){
						MessageBox messageBox = new MessageBox(shell,  SWT.OK);
						messageBox.setMessage("The number of rows have to be >2");
						messageBox.setText("Error");
						messageBox.open();
						Trow.setFocus();
						flag=true;
						}
				}
				//Cols check, !=null,isInt --> >2
				if(flag==false && Tcol.getText().length()==0){
					MessageBox messageBox = new MessageBox(shell,  SWT.OK);
					messageBox.setMessage("The number of columns can't be empty");
					messageBox.setText("Error");
					messageBox.open();
					Tcol.setFocus();
					flag=true;
				}
				if(flag==false && !isInteger(Tcol.getText())){
					MessageBox messageBox = new MessageBox(shell,  SWT.OK);
					messageBox.setMessage("The number of columns have to be an integer");
					messageBox.setText("Error");
					messageBox.open();
					Tcol.setFocus();
					flag=true;
				}else{
					if(flag==false && Integer.parseInt(Tcol.getText())<3){
						MessageBox messageBox = new MessageBox(shell,  SWT.OK);
						messageBox.setMessage("The number of columns have to be >2");
						messageBox.setText("Error");
						messageBox.open();
						Tcol.setFocus();
						flag=true;
						}
				}
				if(flag==false){
					mazeView.generateMaze(TMazeName.getText(),Integer.parseInt(Trow.getText()),Integer.parseInt(Tcol.getText()));
					shell.dispose();
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
		
		addSelectOnFocusToText(TMazeName);
		addSelectOnFocusToText(Trow);
		addSelectOnFocusToText(Tcol);
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
