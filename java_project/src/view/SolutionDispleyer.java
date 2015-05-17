package view;

import java.io.PrintStream;

import algorithms.search.Solution;

/**
* SolutionDispleter interface.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 17.5.2015
*/

public interface SolutionDispleyer {
	/**
	 * prints out received solution to the received outputStream.
	 * @param solution received solution.
	 * @param out received outputStream.
	 */
	public void DisplaySolution(Solution m,PrintStream out);
}
