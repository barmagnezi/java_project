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

			Image image = new Image(e.display,"resources/images/MarioChar.png");
			Image scaled = new Image(null, image.getImageData().scaledTo(Width,Hight));
			e.gc.drawImage(scaled, (realx*5+1)*Width/4, (realy*5+1)*Hight/4);
			
			//Tests:
			/*Image ideaImage = new Image(e.display,"resources/images/MarioChar.png");
		    Label label = new Label(e.display.getShells()[0],SWT.NONE);
		    label.setImage(ideaImage);
		    Canvas canvas = new Canvas(shell,SWT.NO_REDRAW_RESIZE);
		    canvas.addPaintListener(new PaintListener() {
		        public void paintControl(PaintEvent e) {
		         e.gc.drawImage(ideaImage,0,0);
		        }
		    });*/



			updated=false;
		   }
}
