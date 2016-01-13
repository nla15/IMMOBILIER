package alda.immobilier.annonce;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import alda.immobilier.utilisateur.ConnexionsUtilisateurs;
import alda.immobilier.utilisateur.UserLoginCtrl;

@ManagedBean(name="annonceCtrl", eager= true)
@SessionScoped
public class AnnonceCtrl implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1192153369164374436L;

	@ManagedProperty(value="#{UserLoginCtrl}")
	private UserLoginCtrl ulc;
	@ManagedProperty(value="#{connexionsUtilisateurs}")
	private ConnexionsUtilisateurs cu;
	
	@EJB
	AnnonceDAO anDAO;
	
	private List<Annonce> listAnnonce;
	private Annonce ann;
	private Annonce newAnnonce = new Annonce();
	
	public AnnonceCtrl(){
		newAnnonce.creerVide();
	}
	
	
	public List<Annonce> getListAnnonce()
	{
		listAnnonce = anDAO.getAllAnnonce();
		 return listAnnonce;
	}	


	public void setListAnnonce(List<Annonce> listAnnonce) {
		this.listAnnonce = listAnnonce;
	}
	
	public Annonce RechercherAnnonce(int id){
		Annonce annonce = null;
		for(Annonce a : listAnnonce){
		   if (a.getId() == id){
			   annonce = a;
			   break;
		   }
		}
		
		return annonce;
	}
	
	public String creerAnnonce() {
		newAnnonce.creerVide();
		newAnnonce.setIdRefUser(cu.getUtilisateur(ulc.getUserLoginId()));
		anDAO.insert(newAnnonce);
		listAnnonce = anDAO.getAllAnnonce();
		
		return "accueil";
		
	}
	
	public String details(int id){
		setAnn(RechercherAnnonce(id));
		
		return "detailsAnnonce";
	}

	public Annonce getAnn() {
		return ann;
	}

	public void setAnn(Annonce an) {
		ann = an;
	}


	public Annonce getNewAnnonce() {
		return newAnnonce;
	}


	public void setNewAnnonce(Annonce newAnnonce) {
		this.newAnnonce = newAnnonce;
	}

	public UserLoginCtrl getUlc() {
		return ulc;
	}


	public void setUlc(UserLoginCtrl ulc) {
		this.ulc = ulc;
	}


	public ConnexionsUtilisateurs getCu() {
		return cu;
	}


	public void setCu(ConnexionsUtilisateurs cu) {
		this.cu = cu;
	}
}
