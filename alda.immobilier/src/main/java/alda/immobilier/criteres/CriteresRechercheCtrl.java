package alda.immobilier.criteres;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import alda.immobilier.adresse.RegionCtrl;
import alda.immobilier.bdd.ImmodbDAO;
import alda.immobilier.utilisateur.UserLoginCtrl;
import alda.immobilier.utilisateur.Utilisateur;

@ManagedBean(name="criteresRechercheCtrl")
@ViewScoped
public class CriteresRechercheCtrl implements Serializable {
	private static final long serialVersionUID = 538611586354073594L;
	
	@ManagedProperty(value="#{UserLoginCtrl}")
	private UserLoginCtrl ulc;
	@ManagedProperty(value="#{regionCtrl}")
	private RegionCtrl rec;
	@EJB
	ImmodbDAO imDAO;
	
	private String prixMin, prixMax, surfMin, surfMax;

	public CriteresRechercheCtrl(){}
	
	@PostConstruct
	public void init(){}
	
	public void reinit(){
		prixMin = "";
		prixMax = "";
		surfMin = "";
		surfMax = "";
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("accueil.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void rechercher(){

		System.out.println("Rechercher");
		cru.setCrTmp(creerCriteresRecherche(false));
		reinit();
	}
	
	public void enregistrer(){
		creerCriteresRecherche(true);
		reinit();
	}
	
	private CriteresRecherche creerCriteresRecherche(boolean persister){

		CriteresRecherche ncr = new CriteresRecherche();
		ncr.creerVide();
		ncr.setPrixMin( Float.parseFloat(prixMin) );
		ncr.setPrixMax( Float.parseFloat(prixMax) );
		ncr.setSurfaceMin( Float.parseFloat(surfMin) );
		ncr.setSurfaceMax( Float.parseFloat(surfMax) );
		imDAO.insertCriteresRecherche(ncr);
		
		Utilisateur u = ulc.getUtilisateurConnecte();
		if ( u != null )
			ncr.setIdUtilisateur(u);
		ncr.setIdRegion( rec.getRegSelect() );
		imDAO.updateCriteresRecherche(ncr);
	}

	public RegionCtrl getRec() {
		return rec;
	}

	public void setRec(RegionCtrl rec) {
		this.rec = rec;
	}

	public UserLoginCtrl getUlc() {
		return ulc;
	}

	public void setUlc(UserLoginCtrl ulc) {
		this.ulc = ulc;
	}
	
	public String getPrixMin() {
		return prixMin;
	}

	public void setPrixMin(String prixMin) {
		this.prixMin = prixMin;
	}

	public String getPrixMax() {
		return prixMax;
	}

	public void setPrixMax(String prixMax) {
		this.prixMax = prixMax;
	}

	public String getSurfMin() {
		return surfMin;
	}

	public void setSurfMin(String surfMin) {
		this.surfMin = surfMin;
	}

	public String getSurfMax() {
		return surfMax;
	}

	public void setSurfMax(String surfMax) {
		this.surfMax = surfMax;
	}
}
