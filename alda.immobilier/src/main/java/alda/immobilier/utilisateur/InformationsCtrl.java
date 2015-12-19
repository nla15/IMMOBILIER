package alda.immobilier.utilisateur;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/*import alda.immobilier.adresse.AdresseDAO;
import alda.immobilier.adresse.RegionDAO;*/

@ManagedBean(name="informationsCtrl", eager= true)
@ViewScoped
public class InformationsCtrl implements Serializable{
	private static final long serialVersionUID = -3290072609027822120L;

	@EJB
	UtilisateurDAO utDao;
	
	private Utilisateur infosUtil;
	private String mail,
	mdp,
	nom,
	prenom,
	mobile,
	libelle,
	cdp,
	ville,
	nomReg;

	@ManagedProperty(value="#{UserLoginCtrl}")
	private UserLoginCtrl usc;
	
	public InformationsCtrl(){
		infosUtil = null;
	}
	
	@PostConstruct
	public void init(){
		getInfosUtil();
		mail = infosUtil.getIdRefUserLogin().getMail();
		mdp = infosUtil.getIdRefUserLogin().getMdp();
		nom = infosUtil.getNom();
		prenom = infosUtil.getPrenom();
		mobile = infosUtil.getMobile();
		libelle = infosUtil.getAdressePostale().getLibelle();
		cdp = infosUtil.getAdressePostale().getCodePostal();
		ville = infosUtil.getAdressePostale().getVille();
		nomReg = infosUtil.getAdressePostale().getRegionAdr().getNomRegion();
	}

	public UserLoginCtrl getUsc() {
		return usc;
	}

	public void setUsc(UserLoginCtrl usc) {
		this.usc = usc;
	}
	
	public Utilisateur getInfosUtil(){
		System.out.println("getInfosUtil : ");
		UserLogin ul = usc.getUserLogin();
		
		if ( ul != null ){
			Integer uid = ul.getId();
			System.out.println("uid = " + uid);
			System.out.println("utDao = " + utDao);
			infosUtil = utDao.getUtilisateur(uid);
			System.out.println("infosUtil = " + infosUtil);
		} else
			infosUtil = null;
		
		return infosUtil;
	}
	
	public void setInfosUtil(Utilisateur u){
		infosUtil = u;
	
	}
	
	public void enregistrer(String iMail, String iMdp, String iNom,
			 				String iPrenom, String iMobile, String iLibelle,
			 				String iCdp, String iVille, String iNomReg)
	{
		infosUtil.getIdRefUserLogin().setMail(mail);
		infosUtil.getIdRefUserLogin().setMdp(mdp);
		infosUtil.setNom(nom);
		infosUtil.setPrenom(prenom);
		infosUtil.setMobile(mobile);
		infosUtil.getAdressePostale().setLibelle(libelle);
		infosUtil.getAdressePostale().setCodePostal(cdp);
		infosUtil.getAdressePostale().setVille(ville);
		infosUtil.getAdressePostale().getRegionAdr().setNomRegion(nomReg);//*/
		utDao.insertUtilisateur(infosUtil);
	}
	
	public void annuler(){
		init();
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		 try {
			ec.redirect(ec.getRequestContextPath() + "/informations.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getCdp() {
		return cdp;
	}

	public void setCdp(String cdp) {
		this.cdp = cdp;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getNomReg() {
		return nomReg;
	}

	public void setNomReg(String nomReg) {
		this.nomReg = nomReg;
	}

}
