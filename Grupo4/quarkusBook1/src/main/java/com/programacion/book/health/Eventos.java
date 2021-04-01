package com.programacion.book.health;

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
public class Eventos {
public static final String ID=UUID.randomUUID().toString(); 
	
	@Inject @ConfigProperty(name="configsource.consul.host", defaultValue = "127.0.0.1")
	private String consulHost;
	
	@Inject @ConfigProperty(name="app.name", defaultValue = "book")
	private String appName;
	
	@Inject @ConfigProperty(name="quarkus.http.port", defaultValue = "8090")
	private Integer appPort;
	
	@Inject @ConfigProperty(name="app.uri.health", defaultValue = "/health-check")
	private String appUriHealth;
	
	public void init(@Observes @Initialized(ApplicationScoped.class) Object obj) throws UnknownHostException {
		System.out.println("-----STAR-----");
		
		ConsulClient client=new ConsulClient("localhost");
	
		NewService newService = new NewService();
		
		newService.setId(ID); //numero de instancia del servicio
		newService.setName(appName);
		newService.setPort(appPort);
		newService.setAddress(InetAddress.getLocalHost().getHostAddress());

		NewService.Check serviceCheck = new NewService.Check();
		serviceCheck.setMethod("GET");
		serviceCheck.setHttp("http://"+consulHost+":"+appPort+"/libros"+appUriHealth);
		serviceCheck.setInterval("10s");
		serviceCheck.setDeregisterCriticalServiceAfter("20s");
		newService.setCheck(serviceCheck);
		
		// Tag
		
		//RULE: traefik.http.routers.(router_name).rule=PathPrefix('/mp-author')
		String rule=String.format("traefik.http.routers.%s.rule=PathPrefix(`/%s`)", appName, appName);
		
		//middleware: traefik.http.middlewares.(router_name).tipo.params=valor
		String middleware=String.format("traefik.http.middlewares.%s.stripprefix.prefixes=/%s", appName, appName);
		
		String mids=String.format("traefik.http.routers.%s.middlewares=%s", appName, appName);
		
		newService.setTags(Arrays.asList(rule, middleware, mids));
		
		
		client.agentServiceRegister(newService);
		

		client.agentServiceRegister(newService);		
	}

	public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object obj) {
		System.out.println("-----FINISH-----");
		ConsulClient client = new ConsulClient(consulHost);
		client.agentServiceDeregister(ID);
	}
}
