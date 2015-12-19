package alda.immobilier.utilisateur;

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

@Entity
@Table(name="Utilisateur")
public class Utilisateur implements Serializable {
	private static final long serialVersionUID = 1009023740214213207L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private boolean admin;
	private String nom;
	private String prenom;
	private String mobile;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idRefUserLogin", referencedColumnName="id")
	private UserLogin idRefUserLogin;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="adressePostale", referencedColumnName="id")
	private Adresse adressePostale;

	public Utilisateur(){
		super();
	}
	
	public void creerVide(){
		admin = false;
		nom = new String("dft");
		prenom = new String("dft");
		mobile = new String("dft");
		adressePostale = new Adresse();
		adressePostale.creerVide();
	}
	
	public int getId() {
		return id;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Adresse getAdressePostale() {
		return adressePostale;
	}
	public void setAdressePostale(Adresse adressePostale) {
		this.adressePostale = adressePostale;
	}

	public UserLogin getIdRefUserLogin() {
		return idRefUserLogin;
	}

	public void setIdRefUserLogin(UserLogin idRefUserLogin) {
		this.idRefUserLogin = idRefUserLogin;
	}
}
