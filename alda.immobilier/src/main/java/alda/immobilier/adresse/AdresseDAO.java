package alda.immobilier.adresse;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@Stateless
public class AdresseDAO {
	private EntityManager entityManager;
	private static final String JPA_UNIT_NAME = "immodbunit";
	
	public AdresseDAO(){
		entityManager = Persistence.createEntityManagerFactory(
				JPA_UNIT_NAME).createEntityManager();
	}

	@SuppressWarnings("unchecked")
	public List<Adresse>  getAllAdresse(){
		List<Adresse> listAdr;
		String req = "select * from Adresse";
		System.out.println(req);
		listAdr = entityManager.createNativeQuery(req, Adresse.class).getResultList();
		
		return listAdr;
	}
}
