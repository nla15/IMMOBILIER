package alda.immobilier.adresse;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Adresse")
public class Adresse implements Serializable {
	private static final long serialVersionUID = -6544044974189697753L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String libelle;
	private String codePostal;
	private String ville;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="regionAdr", referencedColumnName="id")
	private Region regionAdr;
	
	public Adresse(){}

	public void creerVide(){
		libelle = new String("dft");
		codePostal = new String("dft");
		ville = new String("dft");
		regionAdr = null;
	}
	
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public Region getRegionAdr() {
		return regionAdr;
	}
	public void setRegionAdr(Region regionAdr) {
		this.regionAdr = regionAdr;
	}
}
