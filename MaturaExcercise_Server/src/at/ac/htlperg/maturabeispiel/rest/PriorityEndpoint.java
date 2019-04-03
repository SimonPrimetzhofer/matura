package at.ac.htlperg.maturabeispiel.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import at.ac.htlperg.maturabeispiel.dao.PriorityDao;
import at.ac.htlperg.maturabeispiel.model.Priority;

@RequestScoped
@Path("/priorities")
@Produces("application/json")
@Consumes("application/json")
public class PriorityEndpoint {

	@Inject
	PriorityDao dao;
	
	@GET
	public List<Priority> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		//TODO: retrieve the priorities 
		final List<Priority> priorities = dao.getAll();
		return priorities;
	}

}
