package at.ac.htlperg.maturabeispiel.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.ac.htlperg.maturabeispiel.model.Priority;

@Named
@RequestScoped
public class PriorityDao {
	
	@PersistenceContext
	EntityManager em;
	
	public Priority get(int id) {
		return em.find(Priority.class,id);
	}
	
	public List<Priority> getAll() {
		return em.createNamedQuery("Priority.findAll", Priority.class).getResultList();
	}
	
}
