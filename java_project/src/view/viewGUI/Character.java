package view.viewGUI;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;

public class Character {
	private int x;
	private int y;
	private boolean updated;
	public Character(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	   public void paint(PaintEvent e,int Width,int Hight){
		e.gc.setForeground(new Color(null,255,0,0));
		e.gc.setBackground(new Color(null,255,0,0));
		e.gc.fillOval(x, y, Width, Hight);
		updated=false;
	   }
	
	public boolean isMoved(){
		return this.updated;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}

	public void setX(int x) {
		this.x = x;
		updated=true;
	}

	public void setY(int y) {
		this.y = y;
		updated=true;
	}
}
