package alda.immobilier.bdd;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import alda.immobilier.adresse.Adresse;
import alda.immobilier.adresse.Region;
import alda.immobilier.utilisateur.UserLogin;
import alda.immobilier.utilisateur.Utilisateur;

@Stateless
public class immodbDAO {
	private EntityManager entityManager;
	private static final String JPA_UNIT_NAME = "immodbunit";
	
	public immodbDAO(){
		entityManager = Persistence.createEntityManagerFactory(
				JPA_UNIT_NAME).createEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	public UserLogin getUser(String mail_, String mdp_){
		UserLogin user;
		List<UserLogin> listU;
		String req = "select * from UserLogin where mail='"+ mail_+"' and mdp='" +mdp_ + "'";
		listU = entityManager.createNativeQuery(req, UserLogin.class).getResultList();
		if(!listU.isEmpty()){
			user = listU.get(0);
		}else
			user = null;
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public boolean verifMailExiste(String mail_){
		List<UserLogin> listU;
		String req = "select * from UserLogin where mail='"+ mail_+"'";
		listU = entityManager.createNativeQuery(req, UserLogin.class).getResultList();
		
		return !listU.isEmpty();
		
	}
	
	public UserLogin insertUserLogin(UserLogin u) {
		entityManager.getTransaction().begin();
		entityManager.persist(u);
		entityManager.getTransaction().commit();
		return u;
	}
	
	@SuppressWarnings("unchecked")
	public Utilisateur getUtilisateur(int idUserLogin){
		List<Utilisateur> listU;
		Utilisateur res = null;
		String req = "select * from Utilisateur where idRefUserLogin='"+ idUserLogin +"'";
		listU = entityManager.createNativeQuery(req, Utilisateur.class).getResultList();
		
		if(!listU.isEmpty()){
			res = listU.get(0);
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
	
	@SuppressWarnings("unchecked")
	public List<Adresse>  getAllAdresse(){
		List<Adresse> listAdr;
		String req = "select * from Adresse";
		listAdr = entityManager.createNativeQuery(req, Adresse.class).getResultList();
		
		return listAdr;
	}
	
	@SuppressWarnings("unchecked")
	public List<Region> getAllRegion(){
		List<Region> listReg;
		String req = "select * from Region";
		listReg = entityManager.createNativeQuery(req, Region.class).getResultList();
		
		return listReg;
	}
	
	public Region getDefaultRegion(){
		return getAllRegion().get(0);
	}
}
