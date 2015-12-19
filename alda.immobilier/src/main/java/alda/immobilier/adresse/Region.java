package alda.immobilier.adresse;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Region")
public class Region implements Serializable{
	private static final long serialVersionUID = -4432362183958244466L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String nomRegion;
	
	public Region(){}
	
	public void creerVide(){
		nomRegion = new String("dft");
	}

	public String getNomRegion() {
		return nomRegion;
	}

	public void setNomRegion(String nomRegion) {
		this.nomRegion = nomRegion;
	}
}
