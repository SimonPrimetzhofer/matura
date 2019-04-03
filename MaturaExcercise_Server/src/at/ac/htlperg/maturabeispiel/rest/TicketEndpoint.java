package at.ac.htlperg.maturabeispiel.rest;

import java.util.List;
import java.util.stream.Collectors;

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

import at.ac.htlperg.maturabeispiel.dao.PriorityDao;
import at.ac.htlperg.maturabeispiel.dao.StateDao;
import at.ac.htlperg.maturabeispiel.dao.TicketDao;
import at.ac.htlperg.maturabeispiel.dao.UserDao;
import at.ac.htlperg.maturabeispiel.dto.TicketDto;
import at.ac.htlperg.maturabeispiel.model.*;

@RequestScoped
@Path("/tickets")
@Produces("application/json")
@Consumes("application/json")
public class TicketEndpoint {

	@Inject
	TicketDao dao;
	
	@Inject
	UserDao uDao;
	
	@Inject
	StateDao stDao;
	
	@Inject
	PriorityDao pDao;
	
	@POST
	public Response create(final TicketDto ticket) {
		//TODO: process the given ticket 
		//here we use Ticket#getId(), assuming that it provides the identifier to retrieve the created Ticket resource.
		Ticket t = createTicketFromDto(ticket);
		if(t != null) {
			dao.create(t);
			return Response
					.created(UriBuilder.fromResource(TicketEndpoint.class).path(String.valueOf(t.getId())).build())
					.build();
		}
		return Response.status(Status.BAD_REQUEST).build();
		
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		//TODO: retrieve the ticket 
		TicketDto ticket = createTicketDto(dao.get(id.intValue()));
		if (ticket == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(ticket).build();
	}

	@GET
	public List<TicketDto> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		//TODO: retrieve the tickets 
		final List<TicketDto> tickets = dao.getAll().stream().map(ticket -> createTicketDto(ticket))
										.collect(Collectors.toList());
		return tickets;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") Long id, final TicketDto ticket) {
		
		Ticket t = createTicketFromDto(ticket);
		
		if(t != null && dao.get(id.intValue()) != null) {
			//update
			dao.update(t);
			return Response.ok().build();
		}
		
		return Response.status(Status.NOT_FOUND).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		
		if(dao.get(id.intValue()) != null) {
			dao.delete(id.intValue());
			return Response.ok().build();
		}
			
		return Response.status(Status.NOT_FOUND).build();
	}
	
	private TicketDto createTicketDto(final Ticket t) {
		TicketDto dto = null;
		if(t != null) {
			dto = new TicketDto();
			dto.id = t.getId();
			dto.description = t.getDescription();
			dto.submittedOn = t.getSubmittedOn();
			dto.priorityID = t.getPriority().getId();
			dto.priorityName = t.getPriority().getPriorityName();
			dto.stateID = t.getState().getId();
			dto.stateName = t.getState().getStateName();
			dto.userID = t.getUser().getId();
			dto.userName = t.getUser().getUserName();
			dto.firstName = t.getUser().getFirstName();
			dto.lastName = t.getUser().getLastName();
		}
		return dto;
	}
	
	private Ticket createTicketFromDto(final TicketDto t)  {
		Ticket ticket = new Ticket();
		
		if(t != null) {
			
			final State s = stDao.get(t.stateID);
			final User u = uDao.get(t.userID);
			final Priority p = pDao.get(t.priorityID);
			
			if(s != null && u != null && p != null) {
				ticket.setId(t.id);
				ticket.setDescription(t.description);
				ticket.setSubmittedOn(t.submittedOn);
				ticket.setState(s);
				ticket.setPriority(p);
				ticket.setUser(u);
			}
			
		}
		
		return ticket;
	}

}
