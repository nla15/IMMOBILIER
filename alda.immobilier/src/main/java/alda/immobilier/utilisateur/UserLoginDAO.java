package alda.immobilier.utilisateur;

//import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@Stateless
public class UserLoginDAO {

	private UserLogin user;
	
	private EntityManager entityManager;
	private static final String JPA_UNIT_NAME = "immodbunit";
	
	public UserLoginDAO(){
		user = new UserLogin();
		entityManager = Persistence.createEntityManagerFactory(
				JPA_UNIT_NAME).createEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	public UserLogin getUser(String mail_, String mdp_){
		List<UserLogin> listU;
		String req = "select * from UserLogin where mail='"+ mail_+"' and mdp='" +mdp_ + "'";
		System.out.println(req);
		listU = entityManager.createNativeQuery(req, UserLogin.class).getResultList();
		if(!listU.isEmpty()){
			user = listU.get(0);
			System.out.println(listU);
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

}
