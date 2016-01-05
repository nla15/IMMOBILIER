package alda.immobilier.utilisateur;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@Stateless
public class UtilisateurDAO {
	private EntityManager entityManager;
	private static final String JPA_UNIT_NAME = "immodbunit";
	
	public UtilisateurDAO(){
		entityManager = Persistence.createEntityManagerFactory(
				JPA_UNIT_NAME).createEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	public Utilisateur getUtilisateur(int idUserLogin){
		List<Utilisateur> listU;
		Utilisateur res = null;
		String req = "select * from Utilisateur where idRefUserLogin='"+ idUserLogin +"'";
		listU = entityManager.createNativeQuery(req, Utilisateur.class).getResultList();
		
		if(!listU.isEmpty()){
			res = listU.get(0);
			System.out.println(listU);
		}
		
		return res;
	}
	
	public Utilisateur insertUtilisateur(Utilisateur u) {
		entityManager.getTransaction().begin();
		entityManager.persist(u);
		entityManager.getTransaction().commit();
		return u;
	}
	
	public Utilisateur update(Utilisateur u) {
		entityManager.getTransaction().begin();
		u = entityManager.merge(u);
		entityManager.getTransaction().commit();
		return u;
	}
}
