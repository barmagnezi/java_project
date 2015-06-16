package view.viewGUI.mazeViewWidjet;

/**
* The interface that all the game need to implement to work with our GUI 
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 16.6.2015
*/
public interface CommonGame {
	/**
	 * Return the game that the displayer support
	 * @return The game that the displayer support
	 */
	public  Object getGame();
	/**
	 * Set the game
	 * @param m the game
	 */
	public  void setGame(Object m);
}
