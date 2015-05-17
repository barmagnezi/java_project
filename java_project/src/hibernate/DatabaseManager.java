package hibernate;

import org.hibernate.Session;

public class DatabaseManager {
	 private Session session = null;
	 public DatabaseManager(Session session) {
	  if(session == null)
	    throw new 
	      RuntimeException("Invalid session object.");
	  this.session = session;
	 }
	 public void saveUser(hibernateClass user){
	  session.save(user);
	 }
	 public void updateUser(hibernateClass user){
	  session.update(user);
	 }
	 public void deleteUser(hibernateClass user) {
	  session.delete(user);
	 }

}
