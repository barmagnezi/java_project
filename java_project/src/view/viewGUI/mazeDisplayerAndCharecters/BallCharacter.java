package view.viewGUI.mazeDisplayerAndCharecters;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;

/**
 * Ball character class, extending CommonCharacter and overrides the default
 * painter for the a ball character, and setting the ball color(if its set).
 * 
 * @author Bar Magnezi and Senia Kalma
 * @version 1.0
 * @since 31.5.2015
 */
public class BallCharacter extends CommonCharacter {

	public BallCharacter(int realx, int realy) {
		super(realx, realy);
	}

	public void paint(PaintEvent e, int Width, int Hight) {
		if (col != null)
			e.gc.setBackground(new Color(null, col.red, col.green, col.blue));
		else
			e.gc.setBackground(new Color(null, 255, 0, 0));
		e.gc.fillOval((realx * 5 + 1) * Width / 4, (realy * 5 + 1) * Hight / 4,
				Width, Hight);
		updated = false;
	}

}
