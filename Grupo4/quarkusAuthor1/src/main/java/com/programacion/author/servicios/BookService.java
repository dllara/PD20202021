package com.programacion.author.servicios;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.programacion.author.db.Book;

@Path("/libros")
@RegisterRestClient
public interface BookService {

    @GET
    @Path("/{id}/author")
    @Produces(MediaType.APPLICATION_JSON)
    List<Book> listarAllBooksByAutId(@PathParam("id") int id);
}
