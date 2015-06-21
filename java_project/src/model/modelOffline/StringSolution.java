package model.modelOffline;

import java.util.Stack;

import algorithms.search.Solution;
import algorithms.search.State;

/**
* Saves and represents the solution as a string.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 16.6.2015
*/
public class StringSolution {
	/**
	 * This method convert the solution to string
	 * @param solution The solution that convert
	 * @return The string that express the solution
	 */
	public static String SolutionToString(Solution solution){
		String strsol="";
		Stack<State> sol=new Stack<State>();
		sol.addAll(solution.getSol());
		strsol+=sol.size()+"/";
		while(!sol.isEmpty()){
			strsol+=sol.pop().getState()+"->";
		}
		return strsol;
	}
	/**
	 * This method convert the string that create with this class to Solution
	 * @param solution The string that convert
	 * @return The solution that express the string
	 */
	public static Solution StringToSolution(String solution){
		Stack<State> sol=new Stack<State>();
		String[] sizeSol=solution.split("/");
		int size=Integer.parseInt(sizeSol[0]);
		String[] states=sizeSol[1].split("->");
		for(int i=size-1;i!=-1;i--)
			sol.push(new State(states[i]));
		Solution realsol=new Solution();
		realsol.setSol(sol);
		return realsol;
	}
}
