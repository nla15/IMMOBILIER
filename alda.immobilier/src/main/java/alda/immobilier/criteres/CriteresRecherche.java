package alda.immobilier.criteres;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import alda.immobilier.adresse.Region;
import alda.immobilier.annonce.Annonce;
import alda.immobilier.utilisateur.Utilisateur;

@Entity
@Table(name="CriteresRecherche")
public class CriteresRecherche implements Serializable {
	private static final long serialVersionUID = -2617216379140872494L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private float prixMin;
	private float prixMax;
	private float surfaceMin;
	private float surfaceMax;
	
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name="idRegion", referencedColumnName="id")
	private Region idRegion;
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name="idUtilisateur", referencedColumnName="id")
	private Utilisateur idUtilisateur;
	
	public CriteresRecherche(){}
	
	public void creerVide(){
		prixMin = 0.0f;
		prixMax = 0.0f;
		surfaceMin = 0.0f;
		
		idRegion = null;
		idUtilisateur = null;
	}
	
	public boolean annonceCorrespond(Annonce a){
		float prx = a.getPrix();
		float srf = a.getSurface();
		Region rgn = a.getAdresseAnn().getRegionAdr();
		
		boolean prxOk = (prx >= this.prixMin && prx <= this.prixMax)	||
						(this.prixMin == -1.0f && prx <= this.prixMax) 	||
						(prx >= this.prixMin && this.prixMax == -1.0f) 	||
						(this.prixMin == -1.0f && this.prixMax == -1.0f);
		
		boolean srfOk = (srf >= this.surfaceMin && srf <= this.surfaceMax)		||
						(this.surfaceMin == -1.0f && srf <= this.surfaceMax) 	||
						(srf >= this.surfaceMin && this.surfaceMax == -1.0f) 	||
						(this.surfaceMin == -1.0f && this.surfaceMax == -1.0f);
		
		boolean rgnOk = (rgn == idRegion) || (idRegion == null);
		
		return prxOk && srfOk && rgnOk;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getPrixMin() {
		return prixMin;
	}
	public void setPrixMin(float prixMin) {
		this.prixMin = prixMin;
	}
	public float getPrixMax() {
		return prixMax;
	}
	public void setPrixMax(float prixMax) {
		this.prixMax = prixMax;
	}
	public float getSurfaceMin() {
		return surfaceMin;
	}
	public void setSurfaceMin(float surfaceMin) {
		this.surfaceMin = surfaceMin;
	}
	public float getSurfaceMax() {
		return surfaceMax;
	}
	public void setSurfaceMax(float surfaceMax) {
		this.surfaceMax = surfaceMax;
	}
	public Region getIdRegion() {
		return idRegion;
	}
	public void setIdRegion(Region idRegion) {
		this.idRegion = idRegion;
	}
	public Utilisateur getIdUtilisateur() {
		return idUtilisateur;
	}
	public void setIdUtilisateur(Utilisateur idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}
}
