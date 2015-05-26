package view.viewGUI.mazeViewWidjet;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import view.viewGUI.GameWidget.BasicWindow;



public class winnerPage extends BasicWindow {

	public int steps;
	public int clues;
	File file = new File("resources/music/champions.wav");
	AudioInputStream stream;
	Clip clip;
	int counter=0;
	
	public winnerPage(String title, int width, int height,Display display,int steps,int clues) {
		super(title,width,height,display);
		this.steps=steps;
		this.clues=clues;
		try {
			//	http://stackoverflow.com/questions/30145570/starting-and-stopping-music-files-in-java
		    stream = AudioSystem.getAudioInputStream(file);
		    clip = AudioSystem.getClip();
		    clip.open(stream);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

	@Override
	protected void initWidgets() {
		shell.setLayout(new GridLayout(1,false));
		shell.setBackgroundImage(new Image(null, "resources/images/back_winner.jpg"));
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		Label LBUseDef = new Label(shell, SWT.FILL);
		LBUseDef.setText("You finished with "+steps+" Steps"+"\n"+"Using "+clues+" Clues.");
		Font font = new Font(display,"Arial",14,SWT.BOLD |SWT.CENTER); 
		LBUseDef.setFont(font);
		LBUseDef.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
		LBUseDef.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1));
		shell.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				if(counter==0){
				    clip.start();
					counter++;
				}else{
					if(clip.isActive())
						clip.close();
					shell.dispose();
				}
					
				
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {}
		});
		//Safe Exit:
		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event arg) {
	            	arg.doit = true;
					if(clip.isActive())
						clip.close();
					else
						clip.start();
			}
		});
		shell.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e){
                Rectangle rect = shell.getBounds();
                if(rect.height!=240 || rect.width != 270)
                	shell.setBounds(rect.x, rect.y, 270, 240);
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
