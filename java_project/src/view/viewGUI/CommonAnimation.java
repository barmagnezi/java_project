package view.viewGUI;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;


public abstract class CommonAnimation {
	
	public CommonAnimation(int realx, int realy) {
		super();
		this.realx = realx;
		this.realy = realy;
		updated=false;
	}

	protected int realx;
	protected int realy;
	protected boolean updated;
	protected ImageLoader loader = new ImageLoader();

	
	abstract public void paint(PaintEvent e,int Width,int Hight, int frame);
	
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
	
	public ImageLoader getLoader() {
		return loader;
	}

	public void setLoader(ImageLoader loader) {
		this.loader = loader;
	}

}
