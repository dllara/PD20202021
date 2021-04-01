package com.programacion.book.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQuery(name="Book.findAll", query = "SELECT b FROM Book b ORDER BY b.id")
public class Book {
	@Id
	@SequenceGenerator(name = "bookSequence", 
						sequenceName = "book_id_seq", 
						allocationSize = 1,
						initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
					generator = "bookSequence")
	int id;
	String title;
	String isbn;
	int author_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public int getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}
	
	
}
