package alda.immobilier.adresse;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@Stateless
public class RegionDAO {
	private EntityManager entityManager;
	private static final String JPA_UNIT_NAME = "immodbunit";
	
	public RegionDAO(){
		entityManager = Persistence.createEntityManagerFactory(
				JPA_UNIT_NAME).createEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	public List<Region> getAllRegion(){
		List<Region> listReg;
		String req = "select * from Region";
		System.out.println(req);
		listReg = entityManager.createNativeQuery(req, Region.class).getResultList();
		
		return listReg;
	}
	
	public Region getDefaultRegion(){
		return getAllRegion().get(0);
	}
}
