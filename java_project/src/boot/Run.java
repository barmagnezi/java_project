package boot;

import org.eclipse.swt.graphics.DeviceData;
import org.eclipse.swt.widgets.Display;

import presenter.Presenter;
import view.viewCLI.ClI_View;
import view.viewGUI.GameWidget.GameWidget;
import model.OffLineModel;
import model.OnlineModel;
/**
* The MyModel class extends Observable and implements Model.
* It is the part that makes all the calculations, such as creating a maze, and finding a solution for it.
* @author  Bar Magnezi(209043827) and Senia Kalma(321969941)
* @version 1.0
* @since 17.5.2015
*/
public class Run {
	public static void main(String[] args) {
		//run_GUI();
		//run_GUI_2users();
		run_GUI_online();
	}
	public static void run_cli(){
		OffLineModel m = new OffLineModel();
		ClI_View v = new ClI_View();
		Presenter p=new Presenter(v, m);
		v.addObserver(p);
		m.addObserver(p);
		v.start();
	}
	public static void run_GUI(){
		OffLineModel m = new OffLineModel();
		GameWidget v =  new GameWidget("Game",500,500);
		Presenter p=new Presenter(v.getView(), m);
		v.addObserver(p);
		m.addObserver(p);
		v.start();
		v.exit();
	}
	public static void run_GUI_online(){
		OnlineModel m = new OnlineModel();
		GameWidget v =  new GameWidget("Game",500,500);
		Presenter p=new Presenter(v.getView(), m);
		v.addObserver(p);
		m.addObserver(p);
		v.start();
		v.exit();
	}
	public static void run_GUI_2users(){
		OffLineModel m = new OffLineModel();
		GameWidget v =  new GameWidget("Game",500,500);
		GameWidget v2 =  new GameWidget("Game",500,500,new Display(new DeviceData()));
		Presenter p=new Presenter(v.getView(), m);
		Presenter p2=new Presenter(v2.getView(), m);
		v.addObserver(p);
		v2.addObserver(p);
		m.addObserver(p);
		m.addObserver(p2);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				v.start();
				v.exit();
			}
		}).start();
		v2.start();
		v2.exit();
	} 
}