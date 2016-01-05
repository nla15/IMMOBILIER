package alda.immobilier.utilisateur;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="connexionsUtilisateurs", eager = true )
@ApplicationScoped
public class ConnexionsUtilisateurs implements Serializable{

	private static final long serialVersionUID = 5067972683860874874L;
	private ArrayList<Integer> connectes;
	
	@EJB
	UtilisateurDAO utDao;
	
	public ConnexionsUtilisateurs(){
		connectes = new ArrayList<Integer>();
	}
	
	public boolean connecterUtilisateur(Integer id){
		if ( !connectes.contains(id) ){
			connectes.add(id);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deconnecterUtilisateur(Integer id){
		if ( connectes.contains(id) ){
			connectes.remove(id);
			return true;
		} else {
			return false;
		}
	}
	
	public List<Integer> getConnectes(){
		return connectes;
	}
	
	public Utilisateur getUtilisateur(Integer id){
		return utDao.getUtilisateur(id);
	}
}
