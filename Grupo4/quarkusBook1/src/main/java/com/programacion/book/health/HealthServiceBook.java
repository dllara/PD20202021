package com.programacion.book.health;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@ApplicationScoped
public class HealthServiceBook {
	@Produces
	@Liveness
	public HealthCheck check1() {
	    return () -> HealthCheckResponse.named("service-book")
	    		.up().withData("live", "OK").build();
	  }

}
