package alda.immobilier.annonce;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import alda.immobilier.adresse.Adresse;
import alda.immobilier.utilisateur.Utilisateur;



@Entity
@Table(name="Annonce")
public class Annonce implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 90643420483165159L;

	public Annonce(){}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String designation;
	private float prix;
	private float surface;
	private  String description;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="adresseAnn", referencedColumnName="id")
	private Adresse adresseAnn;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idRefUser", referencedColumnName="id")
	private Utilisateur idRefUser;
	
	public void creerVide(){
		
		designation = new String("undefined");
		prix = 0;
		surface = 0;
		description = new String("undefined");		
		adresseAnn = new Adresse();
		adresseAnn.creerVide();
	}
	
	public Adresse getAdresseAnn() {
		return adresseAnn;
	}

	public void setAdresseAnn(Adresse adresseAnn) {
		this.adresseAnn = adresseAnn;
	}

	public Utilisateur getIdRefUser() {
		return idRefUser;
	}

	public void setIdRefUser(Utilisateur idRefUser) {
		this.idRefUser = idRefUser;
	}

	public int getId() {
		return id;
	}
	
	public String getDesignation() {
		return designation;
	}
	
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public float getPrix() {
		return prix;
	}
	public void setPrix(float prix) {
		this.prix = prix;
	}
	public float getSurface() {
		return surface;
	}
	public void setSurface(float surface) {
		this.surface = surface;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
