package view.viewGUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class MarioAnimation extends CommonCharacter{
	
	int frame=0;
	
	 public MarioAnimation(int realx, int realy) {
		super(realx, realy);
		this.setAnimation(true);
	}
	 
	@Override
	public void paint(PaintEvent e, int Width, int Hight) {
		e.gc.setForeground(new Color(null,255,0,0));
		e.gc.setBackground(new Color(null,255,0,0));
		
	    Image image = new Image(e.display,loader.data[frame].scaledTo(Width, Hight));
	    if(frame==loader.data.length-1)
	    	frame=0;
	    else
	    	frame++;
	    e.gc.drawImage(image, (realx*5+1)*Width/4, (realy*5+1)*Hight/4);


		updated=false;
	}
	 
	 /*public void paint(PaintEvent e,int Width,int Hight, int frame){
			e.gc.setForeground(new Color(null,255,0,0));
			e.gc.setBackground(new Color(null,255,0,0));
			
			/* ORIGINAL
				ImageLoader loader = new ImageLoader();
				loader.load(getClass().getResourceAsStream("Idea_SWT_Animation.gif"));
				Canvas canvas = new Canvas(shell,SWT.NONE);
				image = new Image(display,loader.data[0]);
				int imageNumber;
				final GC gc = new GC(image);
				canvas.addPaintListener(new PaintListener(){
				    public void paintControl(PaintEvent event){
				        event.gc.drawImage(image,0,0);
				    }
				});
			 
			
		    //loader.load(("resources/images/MarioCharAnim.gif"));
		    Image image = new Image(e.display,loader.data[frame].scaledTo(Width, Hight));
		    int imageNumber;
		    GC gc = new GC(image);
		    e.gc.drawImage(image, (realx*5+1)*Width/4, (realy*5+1)*Hight/4);


			updated=false;
		   }*/


}
