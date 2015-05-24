/**
 * 
 */
package view.viewGUI.example;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/**
 * @author HFL
 *
 */
public class MazeDisplay extends Canvas {
	
	int[][] mazeData={
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,1,1,0,1,0,0,1},
			{0,0,1,1,1,1,1,0,0,1,0,1,0,1,1},
			{1,1,1,0,0,0,1,0,1,1,0,1,0,0,1},
			{1,0,1,0,1,1,1,0,0,0,0,1,1,0,1},
			{1,1,0,0,0,1,0,0,1,1,1,1,0,0,1},
			{1,0,0,1,0,0,1,0,0,0,0,1,0,1,1},
			{1,0,1,1,0,1,1,0,1,1,0,0,0,1,1},
			{1,0,0,0,0,0,0,0,0,1,0,1,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,0,1,1},
		};
	
	Timer timer;
	TimerTask myTask;
	GameCharecter gameCharecter;

	
	public MazeDisplay(Composite parent,int style){
        super(parent, style);
        
        setBackground(new Color(null, 255, 70, 255));
        gameCharecter = new GameCharecter(100, 100);
        
        addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				//e.gc.setForeground(new Color(null,0,0,0));
				   e.gc.setBackground(new Color(null,70,50,0));

				   int width=getSize().x;
				   int height=getSize().y;

				   int w=width/mazeData[0].length;
				   int h=height/mazeData.length;

				   for(int i=0;i<mazeData.length;i++)
				      for(int j=0;j<mazeData[i].length;j++){
				          int x=j*w;
				          int y=i*h;
				          if(mazeData[i][j]!=0)
				              e.gc.fillRectangle(x,y,w,h);
				          	  //e.gc.drawOval(x, y, w, h);
				        	  //e.gc.drawRectangle(x, y, w, h);
				          	
				      }
				   //gameCharecter.x = 3*w;
				   //gameCharecter.y = 5*h;
				   gameCharecter.paint(e, w, h);

					
				
				
			}
		});
        
	}
	
	public void start(){
		myTask = new TimerTask() {
			
			@Override
			public void run() {
			
			      getDisplay().syncExec(new Runnable() {

			    		@Override
			    		public void run() {
			    		   Random r=new Random();
			    		   gameCharecter.x = r.nextInt(100);
							gameCharecter.y = r.nextInt(100);
			    		   redraw();
			    		}
			    	 });

				
				/*Random r = new Random();
				gameCharecter.x = r.nextInt(100);
				gameCharecter.y = r.nextInt(100);
				redraw();*/
				
			}
		};
		timer = new Timer();
		timer.scheduleAtFixedRate(myTask, 0, 500);
	}
	
	public void stop(){
		myTask.cancel();
		timer.cancel();
	}

}
