package at.ac.htlperg.maturabeispiel.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.ac.htlperg.maturabeispiel.model.State;

@Named
@RequestScoped
public class StateDao {
	
	@PersistenceContext
	EntityManager em;
	
	public State get(int id) {
		return em.find(State.class,id);
	}
	
	public List<State> getAll() {
		return em.createNamedQuery("State.findAll", State.class).getResultList();
	}
	
}
