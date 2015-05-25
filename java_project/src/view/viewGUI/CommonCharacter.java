package view.viewGUI;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.RGB;


public abstract class CommonCharacter {
	
	public CommonCharacter(int realx, int realy) {
		super();
		this.realx = realx;
		this.realy = realy;
		this.Animation=false;
		updated=false;
	}

	protected int realx;
	protected int realy;
	protected boolean Animation;
	protected String path;
	protected RGB col;
	protected ImageLoader loader = new ImageLoader();
	protected boolean updated;
	
	abstract public void paint(PaintEvent e,int Width,int Hight);
	
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
	public boolean isAnimation() {
		return Animation;
	}
	public void setAnimation(boolean animation) {
		Animation = animation;
	}
	public ImageLoader getLoader() {
		return loader;
	}
	public void setLoader(ImageLoader loader) {
		this.loader = loader;
	}
	public void setLoader(String str) {
    	this.loader = new ImageLoader();
    	loader.load(str);
    	loader.repeatCount=-1;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void setColor(RGB nCol){
		this.col=nCol;
	}

}
