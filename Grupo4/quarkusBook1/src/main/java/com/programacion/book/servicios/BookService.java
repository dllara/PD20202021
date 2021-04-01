package com.programacion.book.servicios;

import java.util.List;

import com.programacion.book.db.Book;

public interface BookService {
	public List<Book> listarAllBookI();
	public List<Book> listarAllBooksByAutId(int id);
	public Book listarByIdBookI(int id);
	public void crearBookI(Book book);
	public void modificarBookI(Book book, int id);
	public void eliminarBookI(int id);

}
