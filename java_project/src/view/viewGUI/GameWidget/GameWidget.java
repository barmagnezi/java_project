package view.viewGUI.GameWidget;

import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;

import view.View;
import view.viewGUI.mazeViewWidjet.MazeViewWidget;

/**
* Main window interface, setting the default buttons and the main gameView widget.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 31.5.2015
*/
public class GameWidget extends BasicWindow {
	MazeViewWidget gameView;
	String filepath;
	
	public GameWidget(String title, int width, int height) {
		super(title, width, height);
		gameView=new MazeViewWidget(shell, SWT.BORDER);	
		
	}
	public GameWidget(String title, int width, int height,Display d) {
		super(title, width, height,d);
		gameView=new MazeViewWidget(shell, SWT.BORDER);	
		
	}
	public void addObserver(Observer presenter){
		gameView.addObserver(presenter);
	}
	
	@Override
	protected void initWidgets() {
		shell.setLayout(new GridLayout(3,false));
		shell.setBackgroundImage(new Image(null, "resources/images/background.png"));
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		//button1 - Create New Maze
		Button BNewMaze=new Button(shell, SWT.PUSH);
		BNewMaze.setLayoutData(new GridData(SWT.LEFT, SWT.None, false, false, 1, 1));		
		BNewMaze.setText("Create new maze");
		BNewMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						new CreateNMaze("New Maze", 300, 150, display, gameView).run();
						gameView.setFocus();
					}
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		//button2 - Open excesting Maze
		Button BOpenMaze=new Button(shell, SWT.PUSH);
		BOpenMaze.setLayoutData(new GridData(SWT.LEFT, SWT.None, false, false, 1, 1));		
		BOpenMaze.setText("Open the maze");
		BOpenMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						new OpenMaze("Open a maze",250,130, display, gameView).run();
						gameView.setFocus();
					}
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		//button3 - Solve Maze
		Button BSolveMaze=new Button(shell, SWT.PUSH);
		BSolveMaze.setLayoutData(new GridData(SWT.LEFT, SWT.None, false, false, 1, 1));		
		BSolveMaze.setText("Solve a maze");
		BSolveMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						new SolveMaze("Solve a maze", 250, 130, display, gameView).run();
						gameView.setFocus();
					}
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		gameView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		gameView.setFocus();
		shell.addHelpListener(new HelpListener() {
			
			@Override
			public void helpRequested(HelpEvent arg0) {
				MessageBox messageBox = new MessageBox(shell,  SWT.OK);
				messageBox.setMessage("Senia Kalma - 321969941\nBar Magnezi - 209043827 \n\t\t\t\tEnjoy.");
				messageBox.setText("Help");
				messageBox.open();
			}
		});
		
		//http://stackoverflow.com/questions/16899807/swt-modifying-window-close-button-red-x
		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event arg) {
	            int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;
	            MessageBox messageBox = new MessageBox(shell, style);
	            messageBox.setText("Exit");
	            messageBox.setMessage("Close the game?");
	            if(messageBox.open() == SWT.YES){
	            	arg.doit = true;
	            }else{
	            	arg.doit = false;
	            }
			}
		});
	}	//initWidgets
	
	public View getView(){
		return gameView.getView();
	}
	public void start(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				gameView.start();
			}
		}).start();
		run();
		
	}
	public void exit(){
		gameView.exit();
	}
	/*
	@Override
	public void start() {
		this.run();
		gameView.start();
	}
	@Override
	public void setCommands(HashMap<String, Command> commands) {
		gameView.setCommands(commands);
	}
	@Override
	public Command getUserCommand() {
		return gameView.getUserCommand();
	}
	@Override
	public void displayMaze(Maze m) {
		gameView.displayMaze(m);
	}
	@Override
	public void displaySolution(Solution s) {
		gameView.displaySolution(s);
		
	}
	@Override
	public void displayString(String msg) {
		gameView.displayString(msg);
	}*/
} //Class close
