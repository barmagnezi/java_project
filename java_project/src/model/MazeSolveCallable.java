package model;

import java.util.concurrent.Callable;

import algorithms.demo.MazeSearchable;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Searcher;
import algorithms.search.Solution;

public class MazeSolveCallable implements Callable<Solution>{
	Searcher solver;
	boolean diag;
	Maze m;
	Solution sol;
	
	public MazeSolveCallable(Maze maze,boolean diagonal,Searcher solver) {
		this.m=maze;
		this.solver=solver;
		this.diag=diagonal;
	}
	
	@Override
	public Solution call() throws Exception {
		MazeSearchable MS=new MazeSearchable(m, diag);
		sol=solver.search(MS);
		return sol;
	}
}
