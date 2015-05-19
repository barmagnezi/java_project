package hibernate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import model.StringMaze;
import model.StringSolution;
import algorithms.compression.HuffmanWriter;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

@Entity
@Table(name="mazesinfo")
public class HibernateClass {

	int id;
	String name;
	String maze;
	String solution;
	
	@Id
	@TableGenerator(name="mazeid", table="mazetb", pkColumnName="mazekey", pkColumnValue="mazeval", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="mazeid")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=31800)
	public String getMaze() {
		return maze;
	}
	public void setMaze(String maze) {
		this.maze = maze;
	}
	@Column(length=31800)
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
}
