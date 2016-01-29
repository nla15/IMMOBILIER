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
	public UserLogin getUser(String mail_, String mdp_){
		return (UserLogin) getRequest("select * from UserLogin where mail='"+ mail_+"' and mdp='" +mdp_ + "'",
				"alda.immobilier.utilisateur.UserLogin").get(0);
	}
	
	public boolean verifMailExiste(String mail_){
		return !getRequest("select * from UserLogin where mail='"+ mail_+"'", "alda.immobilier.utilisateur.UserLogin").isEmpty();
	}
	
	public UserLogin insertUserLogin(UserLogin u) {
		insertObject(u);
		return u;
	}
	
	public Utilisateur getUtilisateur(int idUserLogin){
		return (Utilisateur) getRequest("select * from Utilisateur where idRefUserLogin='"+ idUserLogin +"'",
					"alda.immobilier.utilisateur.Utilisateur").get(0);
	}
	
	public Utilisateur insertUtilisateur(Utilisateur u) {
		insertObject(u);
		return u;
	}
	
	public Utilisateur update(Utilisateur u) {
		updateObject(u);
		return u;
	}
	
	@SuppressWarnings("unchecked")
	public List<Adresse> getAllAdresse(){
		return (List<Adresse>) getAll("alda.immobilier.adresse.Adresse");
	}
	
	@SuppressWarnings("unchecked")
	public List<Region> getAllRegion(){
		return (List<Region>) getAll("alda.immobilier.adresse.Region");
	}
	
	public Region getDefaultRegion(){
		return getAllRegion().get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<Annonce>  getAllAnnonce(){
		return (List<Annonce>) getAll("alda.immobilier.annonce.Annonce");
	}
	
	public Annonce insert(Annonce a) {
		insertObject(a);
		return a;
	}
	
	public Annonce update(Annonce a) {
		updateObject(a);
		return a;
	}
	
	public void deleteUneAnnonce(Annonce a){
		deleteObject(a);
	}
}
