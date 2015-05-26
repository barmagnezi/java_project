package view.viewGUI.mazeViewWidjet;

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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import view.viewGUI.GameWidget.BasicWindow;

public class SelectPic extends BasicWindow{
	int hight,witdh;
	int choise;
	int Old;
	String str;

	MazeViewWidget mazeView;
	public SelectPic(String title, int width, int height, Display disp,int CurChoise) {
		super(title, width, height, disp);
		this.witdh=width;
		this.hight=height;
		this.choise=CurChoise;
		this.Old=CurChoise;
	}

	@Override
	protected
	void initWidgets() {
		shell.setLayout(new GridLayout(3,false));
		Label LBUseDef = new Label(shell, SWT.NONE);
		LBUseDef.setText("Use deults:");
		LBUseDef.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 3, 1));
		Button Option1 = new Button(shell , SWT.RADIO);
		Option1.setText("Super Mario");
		Button Option2 = new Button(shell , SWT.RADIO);
		Option2.setText("Senia Kalma");
		Button Option3 = new Button(shell , SWT.RADIO);
		Option3.setText("Bar Magnezi");
		
		if(choise==2)
			Option1.setSelection(true);
		if(choise==989)
			Option2.setSelection(true);
		if(choise==999)
			Option3.setSelection(true);
		
		//Line place holder
		Label place = new Label(shell, SWT.NONE);
		place.setText("");
		place.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 3, 1));
		//Line place holder
		
		Button OrPic = new Button(shell , SWT.CHECK);
		OrPic.setText("Select a picture:");
		OrPic.setLayoutData(new GridData(SWT.LEFT, SWT.DOWN, false, false, 3, 2));

		
		Label LBPicPath = new Label(shell, SWT.NONE);
		LBPicPath.setText("Enter the pictures path:");
		LBPicPath.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 3, 1));
		LBPicPath.setEnabled(false);
		
		
  		Button BLoad=new Button(shell, SWT.PUSH);
  		BLoad.setLayoutData(new GridData(SWT.LEFT, SWT.DOWN, false, false, 1, 1));		
  		BLoad.setText("Open");
  		BLoad.setEnabled(false);
  		BLoad.addSelectionListener(new SelectionListener() {
  			@Override
  			public void widgetSelected(SelectionEvent arg0) {
  					//getDisplay().syncExec(new Runnable() {
  					//@Override
  					//public void run() {
  						FileDialog fd=new FileDialog(shell,SWT.OPEN);
  						fd.setText("Open");
  						fd.setFilterPath("");
  						String[] names= {
  						      "JPG (*.jpg)",
  						      "PNG (*.png)",
  						      "All Files (*.*)"};
  						String[] filterExt = { "*.jpg", "*.png", "*.*"};
  						fd.setFilterNames(names);
  						fd.setFilterExtensions(filterExt);
  						str=fd.open();
  					}
  				//});
  			//}
  			
  			@Override
  			public void widgetDefaultSelected(SelectionEvent arg0) {}
  		});
		
  		Button Bfin=new Button(shell, SWT.PUSH);
  		Bfin.setLayoutData(new GridData(SWT.RIGHT, SWT.RIGHT, false, false, 3, 1));		
  		Bfin.setText("done");
  		Bfin.addSelectionListener(new SelectionListener() {
  			@Override
  			public void widgetSelected(SelectionEvent arg0) {
  					//getDisplay().syncExec(new Runnable() {
  					//@Override
  					//public void run() {
  						if(Option1.getSelection()==true)
  							choise=2;
  						if(Option2.getSelection()==true)
  							choise=989;
  						if(Option3.getSelection()==true)
  							choise=999;
  						shell.dispose();
  					}
  				//});
  			//}
  			
  			
  			@Override
  			public void widgetDefaultSelected(SelectionEvent arg0) {}
  		});
  		
		OrPic.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
					//getDisplay().syncExec(new Runnable() {
					//@Override
					//public void run() {
						if(OrPic.getSelection()==true){
							//Disabling ireleavent
							LBUseDef.setEnabled(false);
							Option1.setEnabled(false);
							Option2.setEnabled(false);
							Option3.setEnabled(false);
							//Enabling relevnt
							BLoad.setEnabled(true);
							LBPicPath.setEnabled(true);
						}else{
							//Disabling ireleavent
							LBUseDef.setEnabled(true);
							Option1.setEnabled(true);
							Option2.setEnabled(true);
							Option3.setEnabled(true);
							//Enabling relevnt
							BLoad.setEnabled(false);
							LBPicPath.setEnabled(false);
						}

					//}
				//});
				
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		shell.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e){
                Rectangle rect = shell.getBounds();
                if(rect.height!=hight || rect.width != witdh)
                	shell.setBounds(rect.x, rect.y, witdh, hight);
			}
		});
		
		//Safe Exit:
		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event arg) {
	            int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;
	            MessageBox messageBox = new MessageBox(shell, style);
	            messageBox.setText("Exit");
	            messageBox.setMessage("Save the settings?");
	            if(messageBox.open() == SWT.YES){
	            	arg.doit = true;
						if(Option1.getSelection()==true)
  							choise=2;
  						if(Option2.getSelection()==true)
  							choise=989;
  						if(Option3.getSelection()==true)
  							choise=999;
	            }else{
	            	arg.doit = true;
	            	if(Old!=choise){
		            	Option1.setSelection(false);
		            	Option2.setSelection(false);
		            	Option3.setSelection(false);
		            	choise=0;
	            	}
	            	str=null;
	            }
	            getDisplay().dispose();
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