package com.programacion.author.servicios;

import java.util.List;

import com.programacion.author.db.Author;

public interface AuthorService {
	public List<Author> listarAllAutI();
	public Author listarByIdAuthI(int id);
	public void crearAutI(Author aut);
	public void modificarAutI(Author aut, int id);
	public void eliminarAutI(int id);

}
