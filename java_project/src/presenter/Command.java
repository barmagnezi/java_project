package presenter;


/**
* Command interface.
* All who implements this interface need to write the function doCommand.
*
* @author  Bar Magnezi
* @version 1.0
* @since 3.5.2015
*/
public interface Command {
	/**
	 * This Method getting argument(string) and do something
	 * @param arg The argument that the method can use for something
	 */
	void doCommand(String arg);
}
