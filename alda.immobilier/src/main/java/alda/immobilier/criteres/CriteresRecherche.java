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
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idRegion", referencedColumnName="id")
	private Region idRegion;
	@OneToOne(cascade = CascadeType.ALL)
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
