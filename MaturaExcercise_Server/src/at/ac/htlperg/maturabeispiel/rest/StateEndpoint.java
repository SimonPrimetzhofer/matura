package at.ac.htlperg.maturabeispiel.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import at.ac.htlperg.maturabeispiel.dao.StateDao;
import at.ac.htlperg.maturabeispiel.model.State;

@RequestScoped
@Path("/states")
@Produces("application/json")
@Consumes("application/json")
public class StateEndpoint {

	@Inject
	StateDao dao;
	
	@GET
	public List<State> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		//TODO: retrieve the states 
		final List<State> states = dao.getAll();
		return states;
	}

}
