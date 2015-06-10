package model;

import java.util.Observable;

import presenter.PropertiesModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;


/*


 */
public class OnlineModel extends Observable implements Model {

	@Override
	public void generateMaze(String name, int rows, int cols) {
		// TODO Auto-generated method stub

	}

	@Override
	public Maze getMaze(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void solveMaze(Maze m) {
		// TODO Auto-generated method stub

	}

	@Override
	public Solution getSolution(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setProperties(PropertiesModel mproperties) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getClue(String arg) {
		// TODO Auto-generated method stub
		return null;
	}

}
