package view.viewGUI;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;

import View.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import view.View;

public class MyView extends BasicWindow implements View {

	public MyView(String title, int width, int height) {
		super("Game", width, height);
	}
	
	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(5,false));
		//First button - Create New Maze
		Button BNewMaze=new Button(shell, SWT.PUSH);
		BNewMaze.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));		
		BNewMaze.setText("Create new maze");
		BNewMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						new CreateNMaze("Create a new maze",200,200).run();
					}
				});
				//shell.setLayout(new GridLayout(3,false));
				//FileDialog fd=new FileDialog(shell,SWT.OPEN);
				/*fd.setText("open");
				fd.setFilterPath("");
				String[] filterExt = { "*.png", "*.gif", ".jpg", "*.bmp" };
				fd.setFilterExtensions(filterExt);
				fileName = fd.open();*/
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		//Second button - Open excesting Maze
		Button BOpenMaze=new Button(shell, SWT.PUSH);
		BOpenMaze.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));		
		BOpenMaze.setText("Open a maze");
		BOpenMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				display.syncExec(new Runnable() {
					@Override
					public void run() {
						new CreateNMaze("Create a new maze",200,200).run();
						//MazeViewWidgetStub Stub = new MazeViewWidgetStub(Stub, 0); <<- Inside the window
						//Stub.solve();
					}
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		//Third button - Solve Maze
		Button BSolveMaze=new Button(shell, SWT.PUSH);
		BSolveMaze.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 2, 1));		
		BSolveMaze.setText("Solve a maze");
		BSolveMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				display.syncExec(new Runnable() {
					@Override
					public void run() {
						MazeViewWidgetStub Stub = new MazeViewWidgetStub(Stub, 0);
						Stub.solve();
					}
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		//Third button - Clue
		Button BClue=new Button(shell, SWT.PUSH);
		BClue.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 2, 1));		
		BClue.setText("Give me a clue");
		BClue.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				display.syncExec(new Runnable() {
					@Override
					public void run() {
						MazeViewWidgetStub Stub = new MazeViewWidgetStub(Stub, 0);
						Stub.solve();
					}
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
	}	//initWidgets

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCommands(HashMap<String, Command> commands) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Command getUserCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void displayMaze(Maze m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySolution(Solution s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayString(String msg) {
		// TODO Auto-generated method stub
		
	}
} //Class close
