package alda.immobilier.criteres;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

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
	@ManagedProperty(value="#{criteresRechercheUtilisateur}")
	private CriteresRechercheUtilisateur cru;
	@EJB
	ImmodbDAO imDAO;
	
	private String prixMin, prixMax, surfMin, surfMax;
	private CriteresRecherche crEdit; // Celui qu'on edite dans la barre
	
	public CriteresRechercheCtrl(){
		crEdit = null;
	}
	
	@PostConstruct
	public void init(){}
	
	public void rechercher(){
		cru.setCrTmp(creerCriteresRecherche(false));
	}
	
	public void enregistrer(){
		creerCriteresRecherche(true);
	}
	
	public void modifier(CriteresRecherche cr){
		imDAO.updateCriteresRecherche(cr);
	}
	
	public void supprimer(CriteresRecherche cr){
		imDAO.deleteCriteresRecherche(cr);
	}
	
	private CriteresRecherche creerCriteresRecherche(boolean persister){
		CriteresRecherche ncr = new CriteresRecherche();
		ncr.creerVide();
		
		ncr.setPrixMin( prixMin == "" ? -1.0f : Float.parseFloat(prixMin) );
		ncr.setPrixMax( prixMax == "" ? -1.0f : Float.parseFloat(prixMax) );
		ncr.setSurfaceMin( surfMin == "" ? -1.0f : Float.parseFloat(surfMin) );
		ncr.setSurfaceMax( surfMax == "" ? -1.0f : Float.parseFloat(surfMax) );
		
		if ( persister )
			imDAO.insertCriteresRecherche(ncr);
		
		Utilisateur u = ulc.getUtilisateurConnecte();
		if ( u != null )
			ncr.setIdUtilisateur(u);
		ncr.setIdRegion( rec.getRegSelect() );
		
		if ( persister )
			imDAO.updateCriteresRecherche(ncr);
		
		return ncr;
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

	public CriteresRechercheUtilisateur getCru() {
		return cru;
	}

	public void setCru(CriteresRechercheUtilisateur cru) {
		this.cru = cru;
	}

	public CriteresRecherche getCrEdit() {
		return crEdit;
	}

	public void setCrEdit(CriteresRecherche crEdit) {
		this.crEdit = crEdit;
		if ( this.crEdit != null ){
			this.prixMin = String.valueOf(crEdit.getPrixMin());
			this.prixMax = String.valueOf(crEdit.getPrixMax());
			this.surfMin = String.valueOf(crEdit.getSurfaceMin());
			this.surfMax = String.valueOf(crEdit.getSurfaceMax());
			this.rec.setNomRegSelect(crEdit.getIdRegion().getNomRegion());
		}
	}
}
