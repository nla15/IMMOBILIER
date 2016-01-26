package alda.immobilier.criteres;

import java.io.Serializable;
import java.util.ArrayList;
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
	private List<CriteresRechercheEditable> crActifs;
	private CriteresRecherche crTmp;
	
	public CriteresRechercheUtilisateur(){
		u = null;
		crActifs = new ArrayList<>();
		crTmp = null;
	}
	
	@PostConstruct
	public void init(){
		rafraichir();
	}
	
	public void rafraichir(){
		u = ulc.getUtilisateurConnecte();
		
		if ( u != null ){
			List<CriteresRecherche> crs = imDAO.getReqCriteresRecherche(
					"SELECT * FROM CriteresRecherche WHERE idUtilisateur=" + u.getId());
			if ( crs != null ){
				crActifs.clear();
				for (CriteresRecherche cr : crs )
					crActifs.add(new CriteresRechercheEditable(cr));
			}
		} else {
			crActifs = null;
		}
		System.out.println("rafraichir cr tmp " + (crTmp));
	}
	
	/*
	 * Vérifie si une annonce correspond aux critères de recherche.
	 * La priorité est donnée aux critères temporaires, qui sont
	 * enregistrés par clic sur le bouton "Rechercher".
	 */
	public boolean annonceCorrespondCriteres(Annonce a){
		rafraichir();
		System.out.print("crTmp = " + crTmp + " " + "Annonce " + a.getDesignation() + " : ");
		
		if ( crTmp != null ){
			System.out.print("corresp. crit. tmp, OK");
			return crTmp.annonceCorrespond(a);
		} else {
			if ( crActifs.isEmpty() || crActifs == null ){
				return true;
			} else {
				for ( CriteresRechercheEditable cr : crActifs ){
					if ( cr.getCrEncaps().annonceCorrespond(a) ){
						return true;
					}
				}
				return false;
			}
		}
	}
	
	public void modifier(CriteresRecherche cr){
		imDAO.updateCriteresRecherche(cr);
		rafraichir();
	}
	
	public void supprimer(CriteresRecherche cr){
		imDAO.deleteCriteresRecherche(cr);
		rafraichir();
	}
	
	public void setCrTmp(CriteresRecherche cr){
		crTmp = cr;
		System.out.println("Set cr tmp " + (crTmp));
	}
	
	public CriteresRecherche getCrTmp(){
		return crTmp;
	}
	
	public List<CriteresRechercheEditable> getCrActifs(){
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
