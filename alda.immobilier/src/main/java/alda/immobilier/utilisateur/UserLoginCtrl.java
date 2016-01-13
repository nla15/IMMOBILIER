package alda.immobilier.utilisateur;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import alda.immobilier.adresse.Region;
import alda.immobilier.bdd.immodbDAO;

@ManagedBean(name="UserLoginCtrl", eager= true)
@SessionScoped
public class UserLoginCtrl implements Serializable{

	private static final long serialVersionUID = -8989686930181709270L;
	private UserLogin userLogin;
	private Integer id;
	private String msgDeco;
	
	@EJB
	immodbDAO imDao;
	
	@ManagedProperty(value="#{connexionsUtilisateurs}")
	private ConnexionsUtilisateurs connexions;

	public UserLoginCtrl(){
		this.id = null;
		this.msgDeco = "";
	}
	
	public String getMsgDeco(){
		return msgDeco;
	}

	public ConnexionsUtilisateurs getConnexions() {
		return connexions;
	}

	public void setConnexions(ConnexionsUtilisateurs connexions) {
		this.connexions = connexions;
	}
	
	public void deconnecter(){
		if ( connexions.deconnecterUtilisateur(this.id) ){
			
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			 try {
				ec.redirect(ec.getRequestContextPath() + "/connection.xhtml");
				this.id = null;
				this.userLogin = null;
				msgDeco = "Vous êtes déconnecté. Au revoir!";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
	public void login(String mail, String mdp) throws IOException{
		UserLogin u = imDao.getUser(mail, mdp);
		
		if ( u != null ){

			 if ( connexions.connecterUtilisateur(u.getId() ) ){
					 this.id = u.getId();
					 this.userLogin = u;
					 ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
					 ec.redirect(ec.getRequestContextPath() + "/accueil.xhtml");
			 } else {
				 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Vous êtes déjà connecté."));
			 }
			
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Combinaison email / mot-de-passe incorrecte."));
		}
		
	}
	
	public void createUserLogin(String mail, String mdp) {
		UserLogin newUser = new UserLogin(mail, mdp);
		Utilisateur newUtilisateur = new Utilisateur(); 
		
		if ( !imDao.verifMailExiste(mail) ){
			newUtilisateur.creerVide();
			newUtilisateur.setIdRefUserLogin(newUser);
			Region dr = imDao.getDefaultRegion();
			imDao.insertUtilisateur(newUtilisateur);
			newUtilisateur.getAdressePostale().setRegionAdr(dr);
			imDao.update(newUtilisateur);
			
			 ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			 try {
				ec.redirect(ec.getRequestContextPath() + "/connection.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Adresse mail existe déjà."));	
		}
		
	}
	
	public boolean suisJeCo(){
		return userLogin != null;
	}
	
	public boolean suisJePasCo(){
		return !suisJeCo();
	}
	
	public boolean suisJeCoAdmin(){
		return 	userLogin != null &&
				connexions.getUtilisateur(userLogin.getId()).getAdmin();
	}

	public UserLogin getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(UserLogin user) {
		this.userLogin = user;
	}
	
	public Integer getUserLoginId(){
		return id;
	}
	
	public Utilisateur getUtilisateurConnecte(){
		return suisJeCo() ? connexions.getUtilisateur(id) : null;
	}
}
