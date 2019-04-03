package at.ac.htlperg.maturabeispiel.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import at.ac.htlperg.maturabeispiel.model.User;

@Named
@RequestScoped
public class UserDao {

	@PersistenceContext
	EntityManager em;
	
	public List<User> getAll() {
		return em.createNamedQuery("User.findAll", User.class).getResultList();
	}
	
	public User get(int id) {
		return em.find(User.class, id);
	}
	
	@Transactional
	public void create(User toInsert) {
		em.persist(toInsert);
	}
	
	@Transactional
	public void update(User newUser) {
		User toUpdate = get(newUser.getId());
		if(toUpdate != null) {
			toUpdate.setFirstName(newUser.getFirstName());
			toUpdate.setLastName(newUser.getLastName());
			toUpdate.setUserName(newUser.getUserName());
			
			em.merge(toUpdate);
		}
		
		
	}
	
	@Transactional
	public void delete(int id) {
		User toDelete = get(id);
		if(toDelete != null) {
			em.remove(toDelete);
		}
	}
	
}
