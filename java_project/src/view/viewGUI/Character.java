package view.viewGUI;

public class Character {
	int x;
	int y;
	public Character(int x, int y,MazeViewWidget maze) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
}
