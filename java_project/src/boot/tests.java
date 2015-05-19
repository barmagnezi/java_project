package boot;

public class tests {

	public static void main(String[] args) {
		/*
		HashMap<String, Maze> nameMaze=new HashMap<>();
		HashMap<Maze, Solution> MazeSol=new HashMap<>();
		Maze m = new Maze(10, 10);	
		Solution sol = new Solution();
		nameMaze.put("senia", m);
		MazeSol.put(m, sol);
		mazesToFromDatabase myf = new mazesToFromDatabase();
		myf.writeToDatabase(nameMaze, MazeSol);
		/*Maze maze=new DFSMazeGenerator().generateMaze(100, 100);
		String strmaze=new StringMaze().MazeToString(maze);
		System.out.println(strmaze.length());
		/*
		 * test for StringMaze
		Maze maze=new DFSMazeGenerator().generateMaze(10, 20);
		maze.print();
		String strmaze=new StringMaze().MazeToString(maze);
		System.out.println(strmaze);
		new StringMaze().StringToMaze(strmaze).print();*/
		/*
		 * test for Stringsol
		 
		Maze m=(new DFSMazeGenerator()).generateMaze(10, 10);		
		m.print();
		MazeSearchable MS=new MazeSearchable(m, false);
		CommonSearcher se=new BFSSearcher();
		Solution sol=se.search(MS);
		sol.print();
		String strsol=StringSolution.SolutionToString(sol);
		System.out.println(strsol);
		StringSolution.StringToSolution(strsol).print();*/
	}

}
