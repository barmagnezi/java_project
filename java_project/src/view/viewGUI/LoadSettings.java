package view.viewGUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;

public class LoadSettings extends BasicWindow{
	String fileName;

	public LoadSettings(String title, int width, int height, Display disp) {
		super(title, width, height, disp);
	}

	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2,false));
		
		Label LBSettings = new Label(shell, SWT.NONE);
		LBSettings.setText("Choose a settings file:");
		LBSettings.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
		Button openFile=new Button(shell, SWT.PUSH);
		openFile.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));		
		openFile.setText("Open");
		openFile.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd=new FileDialog(shell,SWT.OPEN);
				fd.setText("open");
				fd.setFilterPath("");
				String[] filterExt = { "*.xml"};
				fd.setFilterExtensions(filterExt);
				fileName = fd.open();
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
