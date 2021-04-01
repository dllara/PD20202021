package com.programacion.author.servicios;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.programacion.author.db.Author;


@ApplicationScoped
public class AuthorServiceImpl implements AuthorService{
	@PersistenceContext EntityManager em;
	
	@Override
	public List<Author> listarAllAutI() {
		return em.createNamedQuery("Author.findAll", Author.class).getResultList();
	}

	@Override
	public Author listarByIdAuthI(int id) {
		return em.find(Author.class, id);
	}

	@Override
	@Transactional
	public void crearAutI(Author aut) {
		em.persist(aut);
		
	}

	@Override
	@Transactional	
	public void modificarAutI(Author aut, int id) {
		Author autAux=em.find(Author.class, id);
		autAux.setAge(aut.getAge());
		autAux.setGenre(aut.getGenre());
		autAux.setName(aut.getName());
		
	}

	@Override
	@Transactional
	public void eliminarAutI(int id) {
		em.remove(em.find(Author.class, id));
	}

}
