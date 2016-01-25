package alda.immobilier.criteres;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import alda.immobilier.annonce.Annonce;
import alda.immobilier.bdd.ImmodbDAO;
import alda.immobilier.utilisateur.UserLoginCtrl;
import alda.immobilier.utilisateur.Utilisateur;

@ManagedBean(name="criteresRechercheUtilisateur", eager = true)
@SessionScoped
public class CriteresRechercheUtilisateur implements Serializable{
	private static final long serialVersionUID = -6330945953482977550L;
	
	@ManagedProperty(value="#{UserLoginCtrl}")
	private UserLoginCtrl ulc;
	@EJB
	private ImmodbDAO imDAO;
	private Utilisateur u;
	private List<CriteresRecherche> crActifs;
	private CriteresRecherche crTmp;
	
	public CriteresRechercheUtilisateur(){
		u = null;
		crActifs = null;
		crTmp = null;
	}
	
	@PostConstruct
	public void init(){
		rafraichir();
	}
	
	public void rafraichir(){
		crTmp = null;
		u = ulc.getUtilisateurConnecte();
		
		if ( u != null ){
			crActifs = imDAO.getReqCriteresRecherche(
					"SELECT * FROM CriteresRecherche WHERE idUtilisateur=" + u.getId());
		} else {
			crActifs = null;
		}
	}
	
	/*
	 * Vérifie si une annonce correspond aux critères de recherche.
	 * La priorité est donnée aux critères temporaires, qui sont
	 * enregistrés par clic sur le bouton "Rechercher".
	 */
	public boolean annonceCorrespondCriteres(Annonce a){
		if ( crTmp != null ){
			return crTmp.annonceCorrespond(a);
		} else {
			if ( crActifs.isEmpty() || crActifs == null ){
				return true;
			} else {
				for ( CriteresRecherche cr : crActifs )
					if ( !cr.annonceCorrespond(a) )
						return false;
				
				return true;
			}
		}
	}
	
	public void setCrTmp(CriteresRecherche cr){
		crTmp = cr;
	}
	
	public CriteresRecherche getCrTmp(){
		return crTmp;
	}
	
	public List<CriteresRecherche> getCrActifs(){
		rafraichir();
		return crActifs;
	}
	
	public UserLoginCtrl getUlc() {
		return ulc;
	}

	public void setUlc(UserLoginCtrl ulc) {
		this.ulc = ulc;
	}
}
