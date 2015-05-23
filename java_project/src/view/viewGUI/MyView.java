package view.viewGUI;

import java.util.HashMap;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import View.Command;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import view.View;

public class MyView extends BasicWindow implements View {
	MazeViewWidget gameView;
	String filepath;
	
	public MyView(String title, int width, int height) {
		super(title, width, height);
	}
	public void addObserver(Observer presenter){
		gameView.addObserver(presenter);
	}
	
	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2,false));
		//button1 - Create New Maze
		Button BNewMaze=new Button(shell, SWT.PUSH);
		BNewMaze.setLayoutData(new GridData(SWT.LEFT, SWT.None, false, false, 1, 1));		
		BNewMaze.setText("Create new maze");
		BNewMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						new CreateNMaze("New Maze", 300, 150, display).run();
					}
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		//button2 - Open excesting Maze
		Button BOpenMaze=new Button(shell, SWT.PUSH);
		BOpenMaze.setLayoutData(new GridData(SWT.LEFT, SWT.None, true, false, 1, 1));		
		BOpenMaze.setText("Open the maze");
		BOpenMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						new OpenMaze("Open a maze",250,130, display).run();
					}
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		//button3 - Solve Maze
		Button BSolveMaze=new Button(shell, SWT.PUSH);
		BSolveMaze.setLayoutData(new GridData(SWT.LEFT, SWT.None, false, false, 1, 1));		
		BSolveMaze.setText("Solve the maze");
		BSolveMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						MazeViewWidgetStub Stub = new MazeViewWidgetStub(shell, 0);
						Stub.solve();
					}
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		//button4 - Clue
		Button BClue=new Button(shell, SWT.PUSH);
		BClue.setLayoutData(new GridData(SWT.LEFT, SWT.None, false, false, 1, 1));		
		BClue.setText("Give me a clue");
		BClue.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						MazeViewWidgetStub Stub = new MazeViewWidgetStub(shell, 0);
						Stub.clue();
					}
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		/*//button5 - Load settings
		Button BLoad=new Button(shell, SWT.PUSH);
		BLoad.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 2, 1));		
		BLoad.setText("Load settings");
		BLoad.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						FileDialog fd=new FileDialog(shell,SWT.OPEN);
						fd.setText("open");
						fd.setFilterPath("");
						String[] filterExt = { "*.xml"};
						fd.setFilterExtensions(filterExt);
						filepath = fd.open();
						MazeViewWidgetStub Stub = new MazeViewWidgetStub(shell, 0);

					}
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});*/
		
		gameView=new MazeViewWidget(shell, SWT.BORDER);	
		gameView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		gameView.setFocus();

		
		
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
