package view.viewGUI.mazeDisplayerAndCharecters;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

public class AnimCharacter extends CommonCharacter{
	
	int frame=0;
	
	 public AnimCharacter(int realx, int realy) {
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
}
