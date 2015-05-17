package boot;

import hibernate.mazesToFromDatabase;

import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public class tests {

	public static void main(String[] args) {
		 User user = new User();
		 user.setFirstName("Kermit");
		 user.setLastName("Frog");
		 user.setAge(54);
		 user.setEmail("kermit@muppets.com");

		 SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); 
		 Session session = sessionFactory.openSession();
		 UserManager manager = new UserManager(session);

		 manager.saveUser(user);
		 System.out.println("User saved with ID = "+ user.getUserId());

		 session.flush();
		
		
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
