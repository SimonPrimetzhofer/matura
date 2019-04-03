package at.ac.htlperg.maturabeispiel.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import at.ac.htlperg.maturabeispiel.model.Ticket;

@Named
@RequestScoped
public class TicketDao {
	
	@PersistenceContext
	EntityManager em;
	
	public List<Ticket> getAll() {
		return em.createNamedQuery("Ticket.findAll", Ticket.class).getResultList();
		//return em.createQuery("Select t from Ticket t LEFT JOIN FETCH t.assignments", Ticket.class).getResultList();
	}
	
	public Ticket get(int id) {
		return em.find(Ticket.class, id);
	}
	
	@Transactional
	public void create(Ticket toInsert) {
		em.persist(toInsert);
		
		System.out.println(toInsert.getId());
	}
	
	@Transactional
	public void update(Ticket toUpdate) {
		
		Ticket ticket = get(toUpdate.getId());
		System.out.println(toUpdate.getDescription() + " " + ticket.getDescription());
		
		if(ticket != null) {
			ticket.setDescription(toUpdate.getDescription());
			ticket.setSubmittedOn(toUpdate.getSubmittedOn());
			ticket.setPriority(toUpdate.getPriority());
			ticket.setState(toUpdate.getState());
			ticket.setUser(toUpdate.getUser());
			
			
			em.merge(ticket);
		}
		
	}
	
	@Transactional
	public void delete(int id) {
		Ticket toRemove = get(id);
		if(toRemove != null) {
			em.remove(toRemove);
		}	
	}
}
