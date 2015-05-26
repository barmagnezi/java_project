package view.viewGUI.GameWidget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.MessageBox;

import view.viewGUI.mazeViewWidjet.MazeViewWidget;

public class testwindow extends BasicWindow {

	public testwindow(String title, int width, int height) {
		super(title, width, height);
	}

	@Override
	protected void initWidgets() {
		shell.setLayout(new GridLayout(1,false));		
		MazeViewWidget barcanvas=new MazeViewWidget(shell, SWT.BORDER);	
		barcanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));	
		barcanvas.addHelpListener(new HelpListener() {
			
			@Override
			public void helpRequested(HelpEvent arg0) {
				MessageBox messageBox = new MessageBox(shell,  SWT.OK);
				messageBox.setMessage("help.........");
				messageBox.setText("help");
				messageBox.open();
			}
		});
	}

}
