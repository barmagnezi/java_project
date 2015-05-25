package view.viewGUI;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;

public class BallCharacter extends CommonCharacter{
	
	 public BallCharacter(int realx, int realy) {
		super(realx, realy);
	}

	public void paint(PaintEvent e,int Width,int Hight){
		//e.gc.setForeground(new Color(null,255,100,0));
		if(col!=null)
			e.gc.setBackground(new Color(null, col.red,col.green,col.blue));
		else
			e.gc.setBackground(new Color(null,255,0,0));
		e.gc.fillOval((realx*5+1)*Width/4, (realy*5+1)*Hight/4, Width, Hight);
		updated=false;
	   }
	
	
}
