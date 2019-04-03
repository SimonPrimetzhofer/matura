package at.ac.htlperg.maturabeispiel.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import at.ac.htlperg.maturabeispiel.dao.UserDao;
import at.ac.htlperg.maturabeispiel.model.User;

@RequestScoped
@Path("/users")
@Produces("application/json")
@Consumes("application/json")
public class UserEndpoint {

	@Inject
	UserDao dao;
	
	@POST
	public Response create(final User user) {
		//TODO: process the given user 
		//here we use User#getId(), assuming that it provides the identifier to retrieve the created User resource. 
		dao.create(user);
		return Response.created(UriBuilder.fromResource(UserEndpoint.class).path(String.valueOf(user.getId())).build())
				.build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		//TODO: retrieve the user 
		User user = dao.get(id.intValue());
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(user).build();
	}

	@GET
	public List<User> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		//TODO: retrieve the users 
		final List<User> users = dao.getAll();
		return users;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") Long id, final User user) {
		//TODO: process the given user 
		if(user != null && dao.get(id.intValue()) != null)
			dao.update(user);
		else return Response.status(Status.NOT_FOUND).build();
		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		//TODO: process the user matching by the given id 
		if(dao.get(id.intValue()) != null)
			dao.delete(id.intValue());
		else return Response.status(Status.NOT_FOUND).build();
		return Response.ok().build();
	}

}
