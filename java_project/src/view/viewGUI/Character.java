package view.viewGUI;

import java.lang.ProcessBuilder.Redirect;

import org.eclipse.swt.custom.PaintObjectEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Event;

public class Character {
	int x;
	int y;
	public Character(int x, int y,MazeViewWidget maze) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
}
