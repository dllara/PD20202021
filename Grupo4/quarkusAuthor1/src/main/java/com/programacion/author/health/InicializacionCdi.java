package com.programacion.author.health;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;


@ApplicationScoped
public class InicializacionCdi {
	public static final String ID=UUID.randomUUID().toString(); 
	
	@Inject @ConfigProperty(name="configsource.consul.host", defaultValue = "127.0.0.1")
	private String consulHost;
	
	@Inject @ConfigProperty(name="app.name", defaultValue = "author")
	private String appName;
	
	@Inject @ConfigProperty(name="quarkus.http.port", defaultValue = "8080")
	private Integer appPort;
	
	@Inject @ConfigProperty(name="app.uri.health", defaultValue = "/health-check")
	private String appUriHealth;
	
	public void init(@Observes @Initialized(ApplicationScoped.class) Object obj) throws UnknownHostException {
		System.out.println("****************INIT");
		
		ConsulClient client=new ConsulClient("localhost");
	
		NewService newService = new NewService();
		
		newService.setId(ID); //numero de instancia del servicio
		newService.setName(appName);
		newService.setPort(appPort);
		newService.setAddress(InetAddress.getLocalHost().getHostAddress());
		//
		//
		NewService.Check serviceCheck = new NewService.Check();
		serviceCheck.setMethod("GET");
		serviceCheck.setHttp("http://"+consulHost+":"+appPort+ "/autores" +appUriHealth);
		serviceCheck.setInterval("10s");
		serviceCheck.setDeregisterCriticalServiceAfter("20s");
		newService.setCheck(serviceCheck);
	
		
		// Tag
		
		String rule=String.format("traefik.http.routers.%s.rule=PathPrefix(`/%s`)", appName, appName);
		String mids=String.format("traefik.http.routers.%s.middlewares=%s", appName, appName);
		String middleware=String.format("traefik.http.middlewares.%s.stripprefix.prefixes=/%s", appName, appName);
		newService.setTags(Arrays.asList(rule, middleware, mids));
		
		client.agentServiceRegister(newService);
		}

	public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object obj) {
		System.out.println("///////////////////DESTROY");
		ConsulClient client = new ConsulClient(consulHost);
		client.agentServiceDeregister(ID);
	}
}
