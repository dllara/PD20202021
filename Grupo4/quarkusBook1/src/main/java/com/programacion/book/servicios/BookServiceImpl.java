package com.programacion.book.servicios;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.programacion.book.db.Book;

@ApplicationScoped
public class BookServiceImpl implements BookService{
	@PersistenceContext EntityManager em;

	@Override
	public List<Book> listarAllBookI() {
		return em.createNamedQuery("Book.findAll", Book.class).getResultList();
	}
	
	@Override
	public List<Book> listarAllBooksByAutId(int id) {
		return em.createQuery("SELECT b FROM Book b WHERE b.author_id=:author_id").setParameter("author_id", id).getResultList();
	}

	@Override
	public Book listarByIdBookI(int id) {
		return em.find(Book.class, id);
	}

	@Override
	@Transactional
	public void crearBookI(Book book) {
		em.persist(book);
	}

	@Override
	@Transactional
	public void modificarBookI(Book book, int id) {
		Book bookAux=em.find(Book.class, id);
		bookAux.setIsbn(book.getIsbn());
		bookAux.setTitle(book.getTitle());
		bookAux.setAuthor_id(book.getAuthor_id());
	}

	@Override
	@Transactional
	public void eliminarBookI(int id) {
		em.remove(em.find(Book.class, id));
	}	

}
