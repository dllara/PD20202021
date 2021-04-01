package com.programacion.author.rest;

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
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.programacion.author.db.Author;
import com.programacion.author.db.Book;
import com.programacion.author.health.SimpleHealthCheck;
import com.programacion.author.servicios.AuthorService;
import com.programacion.author.servicios.BookService;

import io.smallrye.metrics.exporters.OpenMetricsUnit;

@ApplicationScoped
@Path(value = "/autores")
public class AuthorRest {
	@Inject private AuthorService servicioA;	
	@Inject private SimpleHealthCheck checkS;
	@Inject @RestClient BookService serviceB;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	//@Counted(name="ContadorRestAppAutor", absolute=true,reusable=true,tags={"tag1=value1"})
	@Counted(name = "LLamadasListarAutor")
    @Timed(name = "TiempoEjecucionAutor", unit = MetricUnits.MILLISECONDS)
	public List<Author> listAllAuth(){
		return servicioA.listarAllAutI();
	}
	
	@GET @Path(value = "/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	//@Counted(name="ContadorRestAppAutor", absolute=true,reusable=true,tags={"tag1=value1"})
	@Counted(name = "LLamadasListarAutorId")
    @Timed(name = "TiempoEjecucionAutorId", unit = MetricUnits.MILLISECONDS)
	public Author listAuthById(@PathParam("id") int id){
		return servicioA.listarByIdAuthI(id);
	}
	
	@GET
    @Path("/{id}/libros")
    @Produces(MediaType.APPLICATION_JSON)
	//@Counted(name="ContadorRestAppAutor", absolute=true,reusable=true,tags={"tag1=value1"})
	@Counted(name = "LLamadasListarLibroAutor")
    @Timed(name = "TiempoEjecucionLibroAutor", unit = MetricUnits.MILLISECONDS)
    public List<Book> name(@PathParam("id") int id) {
        return serviceB.listarAllBooksByAutId(id);
    }
	
	@GET @Path(value = "/health-check")
	@Produces(MediaType.APPLICATION_JSON)
	//@Counted(name="ContadorRestAppAutor", absolute=true,reusable=true,tags={"tag1=value1"})
	@Counted(name = "LLamadasSaludAutor")
    @Timed(name = "TiempoEjecucionSaludAutor", unit = MetricUnits.MILLISECONDS)
	public HealthCheckResponse ping() throws JsonProcessingException {
		System.out.println("ok");
		HealthCheckResponse a = checkS.check1().call();
		ObjectMapper mapper = new ObjectMapper();
		String checkJson= mapper.writeValueAsString(a);
		System.out.println(checkJson);
		return checkS.check1().call();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	//@Counted(name="ContadorRestAppAutor", absolute=true,reusable=true,tags={"tag1=value1"})
	@Counted(name = "LLamadasCrearAutor")
    @Timed(name = "TiempoEjecucionCrearAutor", unit = MetricUnits.MILLISECONDS)
	public void crearAuth(Author aut) {
		servicioA.crearAutI(aut);
	}
	
	@PUT @Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Counted(name="ContadorRestAppAutor", absolute=true,reusable=true,tags={"tag1=value1"})
	@Counted(name = "LLamadasActualizarrAutor")
    @Timed(name = "TiempoEjecucionActualizarAutor", unit = MetricUnits.MILLISECONDS)
	public void actualizarAut(@PathParam("id") int id, Author aut) {
		servicioA.modificarAutI(aut, id);
	}
	
	@DELETE @Path("/{id}")
	//@Counted(name="ContadorRestAppAutor", absolute=true,reusable=true,tags={"tag1=value1"})
	@Counted(name = "LLamadasEliminarAutor")
    @Timed(name = "TiempoEjecucionEliminarAutor", unit = MetricUnits.MILLISECONDS)
	public void eliminarAut(@PathParam("id") int id) {
		servicioA.eliminarAutI(id);  
	}
	
	
	
	
	
	
}
