package alda.immobilier.bdd;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import alda.immobilier.adresse.Adresse;
import alda.immobilier.adresse.Region;
import alda.immobilier.annonce.Annonce;
import alda.immobilier.criteres.CriteresRecherche;
import alda.immobilier.utilisateur.UserLogin;
import alda.immobilier.utilisateur.Utilisateur;

@Stateless
public class ImmodbDAO implements Serializable{
	private static final long serialVersionUID = -3154104621056368704L;
	private static final String JPA_UNIT_NAME = "immodbunit";
	
	private EntityManager entityManager;
	
	public ImmodbDAO(){
		entityManager = Persistence.createEntityManagerFactory(
				JPA_UNIT_NAME).createEntityManager();
	}
	
	// Les methodes génériques :
	
	public List<?> getAll(String className){
		try {
			Class<?> cls = Class.forName(className);
			String req = "select * from " + cls.getSimpleName();
			return entityManager.createNativeQuery(req, cls).getResultList();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<?> getRequest(String req, String className){
		Class<?> cls;
		try {
			cls = Class.forName(className);
			return entityManager.createNativeQuery(req, cls).getResultList();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Object insertObject(Object o) {
		entityManager.getTransaction().begin();
		entityManager.persist(o);
		entityManager.getTransaction().commit();
		return o;
	}
	
	public Object updateObject(Object o) {
		entityManager.getTransaction().begin();
		o = entityManager.merge(o);
		entityManager.getTransaction().commit();
		return o;
	}
	
	public void deleteObject(Object o){
		entityManager.getTransaction().begin();
		entityManager.remove(o);
		entityManager.getTransaction().commit();
	}
	
	// CriteresRecherche :
	
	@SuppressWarnings("unchecked")
	public List<CriteresRecherche> getAllCriteresRecherche(){
		return (List<CriteresRecherche>) getAll("alda.immobilier.criteres.CriteresRecherche");
	}
	
	@SuppressWarnings("unchecked")
	public List<CriteresRecherche> getReqCriteresRecherche(String req){
		return (List<CriteresRecherche>) getRequest(req, "alda.immobilier.criteres.CriteresRecherche");
	}
	
	@SuppressWarnings("unchecked")
	public Annonce getAnnonceById(int id){
		return (Annonce) getRequest("select * from Annonce where id = " + id, "alda.immobilier.annonce.Annonce").get(0);
	}
	
	public CriteresRecherche insertCriteresRecherche(CriteresRecherche cr){
		return (CriteresRecherche) insertObject(cr);
	}
	
	public CriteresRecherche updateCriteresRecherche(CriteresRecherche cr){
		return (CriteresRecherche) updateObject(cr);
	}
	
	public void deleteCriteresRecherche(CriteresRecherche cr){
		deleteObject(cr);
	}
	
	// UserLogin :
	
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
	public List<Adresse> getAllAdresse(){
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
	
	public Annonce update(Annonce a) {
		entityManager.getTransaction().begin();
		a = entityManager.merge(a);
		entityManager.getTransaction().commit();
		return a;
	}
}
