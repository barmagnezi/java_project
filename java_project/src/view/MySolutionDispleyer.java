package view;

import java.io.PrintStream;
import java.util.Stack;


import algorithms.search.Solution;
import algorithms.search.State;

public class MySolutionDispleyer implements SolutionDispleyer {
	
	/**
	 * prints out received solution to the received outputStream.
	 * @param solution received solution.
	 * @param out received outputStream.
	 */
	@Override
	public void DisplaySolution(Solution solution, PrintStream out) {
		Stack<State> sol=solution.getSol();
		if(sol!=null){
			Stack<State> new_sol=new Stack<State>();
			State x;
			while(sol.size()!=1){
				x=sol.pop();
				out.print(x.getState()+"->");
				new_sol.add(x);
			}
			x=sol.pop();
			out.println(x.getState());
			new_sol.add(x);
			while(new_sol.isEmpty()==false){
				sol.add(new_sol.pop());
			}
		}
		else{
			out.println("No Solution");
		}
	}


}
