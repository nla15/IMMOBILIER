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

import alda.immobilier.adresse.RegionDAO;

@ManagedBean(name="UserLoginCtrl", eager= true)
@SessionScoped
public class UserLoginCtrl implements Serializable{

	private static final long serialVersionUID = -8989686930181709270L;
	private UserLogin userLogin;
	private Integer id;
	private String msgDeco;
	
	@EJB
	UserLoginDAO ulDao;
	@EJB
	UtilisateurDAO utDao;
	@EJB
	RegionDAO reDao;
	
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
		//System.out.println("Mail : " + mail + " mdp : " + mdp);
		UserLogin u = ulDao.getUser(mail, mdp);
		
		if ( u != null ){
			// Login
			//System.out.println("owi");

			 if ( connexions.connecterUtilisateur(u.getId() ) ){
					 this.id = u.getId();
					 this.userLogin = u;
					 ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
					 ec.redirect(ec.getRequestContextPath() + "/accueil.xhtml");
			 } else {
				 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Vous êtes déjà connecté."));
			 }
			
		} else {
			// Erreur
			//System.out.println("onon");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Combinaison email / mot-de-passe incorrecte."));
		}
		
	}
	
	public void createUserLogin(String mail, String mdp) {
		UserLogin newUser = new UserLogin(mail, mdp);
		Utilisateur newUtilisateur = new Utilisateur(); 
		//Region dftRegion = reDao.getDefaultRegion();
		//System.out.println("mon user: "+ newUser);
		
		if ( !ulDao.verifMailExiste(mail) ){
			//System.out.println("On va set l'userlogin pour l'utilisateur");
			newUtilisateur.creerVide();
			newUtilisateur.setIdRefUserLogin(newUser);
			//newUtilisateur.getAdressePostale().setRegionAdr(dftRegion);
			//System.out.println("On va insérer le nv utilisateur");
			utDao.insertUtilisateur(newUtilisateur);
			//System.out.println("Bon ok");
			
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

	public UserLogin getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(UserLogin user) {
		this.userLogin = user;
	}
	
	public Integer getUserLoginId(){
		return id;
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
}
