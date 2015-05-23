package view.viewGUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

public class testwindow extends BasicWindow {

	public testwindow(String title, int width, int height) {
		super(title, width, height);
	}

	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(1,false));		
		MazeViewWidget barcanvas=new MazeViewWidget(shell, SWT.BORDER);	
		barcanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));		
	}

}
