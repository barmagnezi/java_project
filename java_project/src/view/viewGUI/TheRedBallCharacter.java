package view.viewGUI;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;

public class TheRedBallCharacter extends CommonCharacter{
	
	 public TheRedBallCharacter(int realx, int realy) {
		super(realx, realy);
	}

	public void paint(PaintEvent e,int Width,int Hight){
			e.gc.setForeground(new Color(null,255,0,0));
			e.gc.setBackground(new Color(null,255,0,0));
			//e.gc.drawImage(new Image(null, "resources/images/char1.png"), Width, Hight);
			//e.gc.setBackgroundPattern(new Pattern(null, new Image(null, "resources/images/char1.png")));
			e.gc.fillOval((realx*5+1)*Width/4, (realy*5+1)*Hight/4, Width, Hight);
			updated=false;
		   }
}
