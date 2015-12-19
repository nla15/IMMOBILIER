package alda.immobilier.utilisateur;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UserLogin")
public class UserLogin implements Serializable {
	private static final long serialVersionUID = -915244163657727572L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String mail;
	private String mdp;
	
	public UserLogin(){
		super();
	}
	
	public UserLogin(String mail, String mdp){
		this.mail = mail;
		this.mdp = mdp;
	}
	
	public Integer getId(){
		return id;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
}
