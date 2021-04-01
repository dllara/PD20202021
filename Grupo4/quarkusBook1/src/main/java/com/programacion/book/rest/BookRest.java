package com.programacion.book.rest;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.programacion.book.db.Book;
import com.programacion.book.health.HealthServiceBook;
import com.programacion.book.servicios.BookService;

@ApplicationScoped
@Path("/libros")
public class BookRest {
	@Inject BookService servicioBook;
	@Inject HealthServiceBook checkS;
	
	@GET @Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Counted(name = "LLamadasListarLibro")
    @Timed(name = "TiempoEjecucionListarLibro", unit = MetricUnits.MILLISECONDS)
	public Book listAllBookByid(@PathParam("id") int id) {
		return servicioBook.listarByIdBookI(id);
	}
	
	@GET @Path("/{id}/author")
	@Produces(MediaType.APPLICATION_JSON)
	@Counted(name = "LLamadasListarLibroId")
    @Timed(name = "TiempoEjecucionLibroId", unit = MetricUnits.MILLISECONDS)
	public List<Book> listAllBookByidAut(@PathParam("id") int id) {
		return servicioBook.listarAllBooksByAutId(id);
	}
	
	@GET @Path(value = "/health-check")
	@Produces(MediaType.APPLICATION_JSON)
	@Counted(name = "LLamadasSaludLibro")
    @Timed(name = "TiempoEjecucionSaludLibro", unit = MetricUnits.MILLISECONDS)
	public HealthCheckResponse ping() throws JsonProcessingException {
		System.out.println("ok");
		HealthCheckResponse a = checkS.check1().call();
		ObjectMapper mapper = new ObjectMapper();
		String checkJson="";
		checkJson = mapper.writeValueAsString(a);
		System.out.println(checkJson);
		return checkS.check1().call();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Counted(name = "LLamadasCrearLibro")
    @Timed(name = "TiempoEjecucionCrearLibro", unit = MetricUnits.MILLISECONDS)
	public void crearBook(Book book) {
		servicioBook.crearBookI(book);
	}
	
	@PUT @Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Counted(name = "LLamadasActualizarLibro")
    @Timed(name = "TiempoEjecucionActualizarLibro", unit = MetricUnits.MILLISECONDS)
	public void actualizarBook(@PathParam("id") int id, Book book) {
		servicioBook.modificarBookI(book, id);
	}
	
	@DELETE @Path("/{id}")
	@Counted(name = "LLamadasEliminarAutor")
	@Metered(name = "TiempoEjecucionEliminarAutor", unit = MetricUnits.MILLISECONDS, description = "metricas para eliminar author", absolute = true)
	public void eliminar(@PathParam("id") int id) {
		servicioBook.eliminarBookI(id);  
	}

}
