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

import alda.immobilier.adresse.RegionCtrl;
import alda.immobilier.bdd.immodbDAO;


@ManagedBean(name="informationsCtrl", eager= true)
@ViewScoped
public class InformationsCtrl implements Serializable{
	private static final long serialVersionUID = -3290072609027822120L;

	@ManagedProperty(value="#{regionCtrl}")
	private RegionCtrl rec;
	@ManagedProperty(value="#{UserLoginCtrl}")
	private UserLoginCtrl usc;
	@EJB
	private immodbDAO imDao;
	
	private Utilisateur infosUtil;
	private String mail, mdp, nom, prenom, mobile, libelle, cdp, ville;
	
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
	}

	public Utilisateur getInfosUtil(){
		UserLogin ul = usc.getUserLogin();
		
		if ( ul != null ){
			Integer uid = ul.getId();
			infosUtil = imDao.getUtilisateur(uid);
		} else
			infosUtil = null;
		
		return infosUtil;
	}
	
	public void setInfosUtil(Utilisateur u){
		infosUtil = u;
	}
	
	public void enregistrer()
	{
		infosUtil.getIdRefUserLogin().setMail(mail);
		infosUtil.getIdRefUserLogin().setMdp(mdp);
		infosUtil.setNom(nom);
		infosUtil.setPrenom(prenom);
		infosUtil.setMobile(mobile);
		infosUtil.getAdressePostale().setLibelle(libelle);
		infosUtil.getAdressePostale().setCodePostal(cdp);
		infosUtil.getAdressePostale().setVille(ville);
		infosUtil.getAdressePostale().setRegionAdr( rec.getRegSelect() );
		
		imDao.update(infosUtil);
	}
	
	public String annuler(){
		init();
		return "informations";
	}
	
	public UserLoginCtrl getUsc() {
		return usc;
	}

	public void setUsc(UserLoginCtrl usc) {
		this.usc = usc;
	}
	
	public RegionCtrl getRec() {
		return rec;
	}

	public void setRec(RegionCtrl rec) {
		this.rec = rec;
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
}
