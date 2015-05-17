package hibernate;

import java.util.ArrayList;
import java.util.HashMap;



import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import model.StringMaze;
import model.StringSolution;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public class mazesToFromDatabase {
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;

	public static SessionFactory createSessionFactory() {
	    Configuration configuration = new Configuration();
	    configuration.configure();
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
	            configuration.getProperties()).build();
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	    return sessionFactory;
	}	

	public void writeToDatabase(HashMap<String, Maze> nameMaze,HashMap<Maze, Solution> MazeSol){
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); 
		Session session = sessionFactory.openSession();
		DatabaseManager manager = new DatabaseManager(session);
		 
		for(String name:nameMaze.keySet()){
			hibernateClass row=new hibernateClass();
			row.setName(name);
			row.setMaze(StringMaze.MazeToString(nameMaze.get(name)));
			if(MazeSol.containsKey(nameMaze.get(name)))
				row.setSolution(StringSolution.SolutionToString(MazeSol.get(nameMaze.get(name))));
			else
				row.setSolution("x");
			manager.saveUser(row);
		}	 
		 session.flush();
	}
	public void readFromDatabase(HashMap<String, Maze> nameMaze,HashMap<Maze, Solution> MazeSol){
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();

		Query query = session.createQuery("from Mazes");

		List <hibernateClass>list = query.list();
		Iterator<hibernateClass> it=list.iterator();
		hibernateClass row = null;
		while (it.hasNext()){
			row=it.next();
			nameMaze.put(row.getName(), StringMaze.StringToMaze(row.getMaze()));
			MazeSol.put(StringMaze.StringToMaze(row.getMaze()), StringSolution.StringToSolution(row.getSolution()));
		}
	}
}
