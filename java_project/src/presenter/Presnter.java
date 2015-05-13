package presenter;

import java.util.Observable;
import java.util.Observer;

import view.View;
import model.Model;

public class Presnter implements Observer{
	Model model;
	View view;
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
