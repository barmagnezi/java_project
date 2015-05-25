package view.viewGUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class MarioCharacter extends CommonCharacter{
	
	 public MarioCharacter(int realx, int realy) {
		super(realx, realy);
	}
	 
	public void paint(PaintEvent e,int Width,int Hight){
			e.gc.setForeground(new Color(null,255,0,0));
			e.gc.setBackground(new Color(null,255,0,0));
			//e.gc.drawImage(new Image(null, "resources/images/MarioChar.png"), Width, Hight);	//Simply show the image
			//e.gc.setBackgroundPattern(new Pattern(null, new Image(null, "resources/images/char1.png")));
			e.gc.fillOval((realx*5+1)*Width/4, (realy*5+1)*Hight/4, Width, Hight);		// CIRCLE
			System.out.println("ASADADS"+e.display);
			Image image = new Image(e.display,"resources/images/MarioChar.png");

			Image scaled = new Image(null, image.getImageData().scaledTo((int)((realx*5+1)*Width/4),(int)((realx*5+1)*Hight/4)));
			e.gc.drawImage(scaled, Width, Hight);
			//e.gc.drawImage(new Image(null, "resources/images/MarioChar.gif"),Width,Hight);
			/*Image ideaImage = new Image(parent, getClass().getResourceAsStream("MarioChar.gif"));
		    Label label = new Label(parent ,SWT.NONE);
		    label.setImage(ideaImage);
		    Canvas canvas = new Canvas(parent,SWT.NO_REDRAW_RESIZE);
		    canvas.addPaintListener(new PaintListener() {
				@Override
				public void paintControl(PaintEvent arg0) {
					e.gc.drawImage(ideaImage,0,0);
					
				}
			});*/

			updated=false;
		   }
}
