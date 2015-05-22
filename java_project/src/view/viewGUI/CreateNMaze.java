package view.viewGUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;

public class CreateNMaze extends BasicWindow{

	public CreateNMaze(String title, int width, int height, Display disp) {
		super(title, width, height,disp);
	}

	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(1,false));		
		MazeViewWidget barcanvas=new MazeViewWidget(shell, SWT.BORDER);	
		barcanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));		
	}

}
