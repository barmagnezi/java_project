package view.viewGUI.mazeViewWidjet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import view.viewGUI.mazeDisplayerAndCharecters.Game;
import algorithms.search.Solution;

public abstract class GameDisplayer extends Canvas {
	Game game;
	
	public GameDisplayer(Composite parent, int style){
		super(parent,style);
	}
	
	public GameDisplayer(Composite parent, int style,String Background, String scrBackground,String scrWalls) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
	}
	/**
	 * Set the character to be on x,y
	 * @param x coordinates
	 * @param y coordinates
	 */
	public abstract void mark(int x,int y);
	
	/**
	 * Function for changing the background design, used by the MazeViewWidget.
	 * @param scrBackground path for the background.
	 * @param scrWalls path for the walls.
	 */
	public abstract void changeBackDesign(String scrBackground,String scrWalls);
	
	/**
	 * Function for changing the character design.
	 * @param charOption type of the wanter char: 1-Ball,1001-Picture,1002-Animation.
	 * @param nColor Color if a ball Character selected.
	 * @param Path for setting a picture/animation character.
	 */
	public abstract void changeCharacter(int charOption, RGB nColor, String path);
	
	/**
	 * Updates the Game show.
	 * @param m The Game we want to display.
	 * @param resetChar Used for resetting the character when setting a new one(true=reset).
	 */
	public abstract void showGame(Game m,boolean resetChar);
	
	/**
	 * Print the solution provided.
	 * @param the solution we want to draw.
	 */
	public abstract void showSolution(Solution s);
	
	/**
	 * @param pos - integer to code the move:
	 * 1 for Right, 2 for Up, 3 for Left, 4 for Down
	 * 5 for down-left,6 for down-Right,7 for up-left,8 for up-right
	 */
	public abstract void CharMoved(int pos);
	
	/**
	 * Start the game all over.
	 */
	public abstract void Startover();
	
	/**
	 * Set the animation to run.
	 * @param delay the delay between each frame of the animation.
	 */
	abstract public void startAnimation(long delay);
	
}
