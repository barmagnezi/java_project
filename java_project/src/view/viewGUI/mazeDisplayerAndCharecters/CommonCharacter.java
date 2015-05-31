package view.viewGUI.mazeDisplayerAndCharecters;

import java.util.Random;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.RGB;

/**
* CommonCharacter abstract class, containing all the function and all the variables needed for most of the characters.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 31.5.2015
*/
public abstract class CommonCharacter {
	
	public CommonCharacter(int realx, int realy) {
		super();
		this.realx = realx;
		this.realy = realy;
		this.Animation=false;
		this.solutionStep=false;
		updated=false;
	}

	protected int realx;
	protected int realy;
	protected boolean Animation;
	protected String path;
	protected RGB col;
	protected ImageLoader loader = new ImageLoader();
	protected boolean updated;
	protected boolean solutionStep;
	
	abstract public void paint(PaintEvent e,int Width,int Hight);
	
	protected void solveStep(PaintEvent e,int Width,int Hight){
		Random rand = new Random();
		for(int i=0;i<7;i++){
			//http://stackoverflow.com/questions/4246351/creating-random-colour-in-java
			int R = (int)(Math.random()*256);
			int G = (int)(Math.random()*256);
			int B= (int)(Math.random()*256);
			Color color = new Color(null, R, G, B); //random color, but can be bright or dull
			e.gc.setBackground(color);
			//Wait here for 100 milisec..
		}
		//e.gc.setBackground(new Color(null,255,0,0));
		e.gc.fillRectangle((realx*5+1)*Width/4, (realy*5+1)*Hight/4, Width, Hight);
		updated=false;
		
	}
	
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
