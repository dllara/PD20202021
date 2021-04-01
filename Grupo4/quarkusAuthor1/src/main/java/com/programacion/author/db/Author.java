package com.programacion.author.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQuery(name="Author.findAll", query = "SELECT a FROM Author a ORDER BY a.id")
public class Author {
	@Id
	@SequenceGenerator(name = "authorSequence", 
						sequenceName = "author_id_seq", 
						allocationSize = 1,
						initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
					generator = "authorSequence")
	int id;
	int age;
	String genre;
	String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	
}
