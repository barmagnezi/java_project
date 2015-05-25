package view.viewGUI;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;


public abstract class CommonCharacter {
	
	public CommonCharacter(int realx, int realy) {
		super();
		this.realx = realx;
		this.realy = realy;
		updated=false;
	}

	protected int realx;
	protected int realy;
	protected boolean updated;

	
	abstract  public void paint(PaintEvent e,int Width,int Hight);
	
	public boolean isMoved(){
		return this.updated;
	}

	public int getRealx() {
		return realx;
	}

	public void setRealx(int realx) {
		this.realx = realx;
		this.updated=true;
	}

	public int getRealy() {
		return realy;
	}

	public void setRealy(int realy) {
		this.realy = realy;
		this.updated=true;
	}

}
