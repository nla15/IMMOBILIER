package alda.immobilier.annonce;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;


@Stateless
public class AnnonceDAO {

	private EntityManager entityManager;
	private static final String JPA_UNIT_NAME = "immodbunit";
	
	public AnnonceDAO(){
		entityManager = Persistence.createEntityManagerFactory(
				JPA_UNIT_NAME).createEntityManager();
	}

	@SuppressWarnings("unchecked")
	public List<Annonce>  getAllAnnonce(){
		
		List<Annonce> listAnnonce;
		String req = "select * from Annonce";
		listAnnonce = entityManager.createNativeQuery(req, Annonce.class).getResultList();
		
		return listAnnonce;
	}	
	
	public Annonce insert(Annonce a) {
		entityManager.getTransaction().begin();
		entityManager.persist(a);
		entityManager.getTransaction().commit();
		return a;
	}
}
