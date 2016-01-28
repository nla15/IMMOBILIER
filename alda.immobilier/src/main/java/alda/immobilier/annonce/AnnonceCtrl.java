package alda.immobilier.annonce;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.UploadedFile;

import alda.immobilier.adresse.Region;
import alda.immobilier.adresse.RegionCtrl;
import alda.immobilier.bdd.ImmodbDAO;
import alda.immobilier.criteres.CriteresRechercheUtilisateur;
import alda.immobilier.utilisateur.ConnexionsUtilisateurs;
import alda.immobilier.utilisateur.UserLoginCtrl;

@ManagedBean(name="annonceCtrl", eager= true)
@ViewScoped
public class AnnonceCtrl implements Serializable {
	private static final long serialVersionUID = -1192153369164374436L;

	@ManagedProperty(value="#{UserLoginCtrl}")
	private UserLoginCtrl ulc;
	@ManagedProperty(value="#{connexionsUtilisateurs}")
	private ConnexionsUtilisateurs cu;
	
	@ManagedProperty(value="#{regionCtrl}")
	private RegionCtrl rc;
	@ManagedProperty(value="#{criteresRechercheUtilisateur}")
	private CriteresRechercheUtilisateur cru;
	
	@ManagedProperty(value="#{annonceDetails}")
	private AnnonceDetails annonceDetails;

	@EJB
	ImmodbDAO imDao;
	
	
	private List<Annonce> listAnnonceFiltre;
	//private List<Annonce> listAnnonce;
	
	private Annonce newAnnonce = new Annonce();
	
	private UploadedFile uploadedFile;
	
	private String iDesc, iDesg, ireg, iLibel, iV, icdp;
	private float iSurf, iPrix;
	private int id;
	
	public AnnonceCtrl(){
		newAnnonce.creerVide();
		listAnnonceFiltre = new ArrayList<>();
		
	}
	
	@PostConstruct
	void init(){
		annonceDetails.getAnnonceEnCours();
		id =  annonceDetails.getAnnonceEnCours().getId();
		iDesc = annonceDetails.getAnnonceEnCours().getDescription();
		iDesg = annonceDetails.getAnnonceEnCours().getDesignation();
		iLibel = annonceDetails.getAnnonceEnCours().getAdresseAnn().getLibelle();
		iV = annonceDetails.getAnnonceEnCours().getAdresseAnn().getVille();
		icdp = annonceDetails.getAnnonceEnCours().getAdresseAnn().getCodePostal();
		iSurf = annonceDetails.getAnnonceEnCours().getSurface();
		iPrix = annonceDetails.getAnnonceEnCours().getPrix();		
	}
		
	
	/* Tri des annonces qui correspondent ou non aux criteres
	 * de recherche des utilisateurs.
	 */
	public void filtrerListAnnonce(){
		if ( annonceDetails.getListAnnonce() != null ){
			listAnnonceFiltre.clear();
			for ( Annonce a : annonceDetails.getListAnnonce() ){
				if ( cru.annonceCorrespondCriteres(a))
					listAnnonceFiltre.add(a);
			}
		}
	}
	
	public List<Annonce> getListAnnonceFiltre(){
		annonceDetails.getListAnnonce();
		filtrerListAnnonce();
		return listAnnonceFiltre;
	}
	
	
	public String creerAnnonce(String iDesg, String iDesc, float iSurf, float iPrix,
								String iLib, String iCdp, String iVille) {
		
		newAnnonce.creerVide();
		
		//Annonce uneAnnonce = new Annonce();
		newAnnonce.setDesignation(iDesg);
		newAnnonce.setDescription(iDesc);
		newAnnonce.setSurface(iSurf);
		newAnnonce.setPrix(iPrix);
		newAnnonce.getAdresseAnn().setLibelle(iLib);
		newAnnonce.getAdresseAnn().setCodePostal(iCdp);
		newAnnonce.getAdresseAnn().setVille(iVille);
		newAnnonce.getAdresseAnn().setRegionAdr(rc.getRegSelect());
		
		imDao.insert(newAnnonce);
		newAnnonce.setIdRefUser(cu.getUtilisateur(ulc.getUserLoginId()));
		imDao.update(newAnnonce);
		//listAnnonce = imDao.getAllAnnonce();
		//listAnnonce =
		annonceDetails.getListAnnonce();
		
		return "accueil";
		
	}
	
	
	public void ModifierAnnonce(){
		
		System.out.println("MODIER ANN");
		annonceDetails.getAnnonceEnCours().getId();
		annonceDetails.getAnnonceEnCours().setDescription(iDesc);
		annonceDetails.getAnnonceEnCours().setDesignation(iDesg);
		annonceDetails.getAnnonceEnCours().setPrix(iPrix);
		annonceDetails.getAnnonceEnCours().setSurface(iSurf);
		annonceDetails.getAnnonceEnCours().getAdresseAnn().setCodePostal(icdp);
		annonceDetails.getAnnonceEnCours().getAdresseAnn().setLibelle(iLibel);
		annonceDetails.getAnnonceEnCours().getAdresseAnn().setVille(iV);
		annonceDetails.getAnnonceEnCours().getAdresseAnn().setRegionAdr(rc.getRegSelect());
		annonceDetails.getAnnonceEnCours().getIdRefUser().getMobile();
		annonceDetails.getAnnonceEnCours().getIdRefUser().getNom();
		annonceDetails.getAnnonceEnCours().getIdRefUser().getIdRefUserLogin().getMail();
		
		imDao.update(annonceDetails.getAnnonceEnCours());
	}

	public void upload(){
		
		System.out.println("pppppppp");
		
		String fileName = uploadedFile.getFileName();
		String contentType = uploadedFile.getContentType();
		byte[] contents = uploadedFile.getContents();
		
		System.out.println(fileName + "\t" + contentType + "\t" + contents);
	}
	
	public String Annuler(){
		init();
		return "detailsAnnonce";
	}
	
	public Annonce getNewAnnonce() {
		return newAnnonce;
	}


	public void setNewAnnonce(Annonce newAnnonce) {
		this.newAnnonce = newAnnonce;
	}

	public UserLoginCtrl getUlc() {
		return ulc;
	}


	public void setUlc(UserLoginCtrl ulc) {
		this.ulc = ulc;
	}


	public ConnexionsUtilisateurs getCu() {
		return cu;
	}


	public void setCu(ConnexionsUtilisateurs cu) {
		this.cu = cu;
	}


	public RegionCtrl getRc() {
		return rc;
	}


	public void setRc(RegionCtrl rc) {
		this.rc = rc;
	}


	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}


	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
	public ImmodbDAO getImDao() {
		return imDao;
	}

	public void setImDao(ImmodbDAO imDao) {
		this.imDao = imDao;
	}

	public String getiDesc() {
		return iDesc;
	}

	public void setiDesc(String iDesc) {
		this.iDesc = iDesc;
	}

	public String getiDesg() {
		return iDesg;
	}

	public void setiDesg(String iDesg) {
		this.iDesg = iDesg;
	}

	public String getIreg() {
		return ireg;
	}

	public void setIreg(String ireg) {
		this.ireg = ireg;
	}


	public String getiLibel() {
		return iLibel;
	}

	public void setiLibel(String iLibel) {
		this.iLibel = iLibel;
	}

	public String getiV() {
		return iV;
	}

	public void setiV(String iV) {
		this.iV = iV;
	}

	public String getIcdp() {
		return icdp;
	}

	public void setIcdp(String icdp) {
		this.icdp = icdp;
	}

	public float getiSurf() {
		return iSurf;
	}

	public void setiSurf(float iSurf) {
		this.iSurf = iSurf;
	}

	public float getiPrix() {
		return iPrix;
	}

	public void setiPrix(float iPrix) {
		this.iPrix = iPrix;
	}

	public int getId() {
		return id;
	}
	

	public CriteresRechercheUtilisateur getCru() {
		return cru;
	}
	
	public void setCru(CriteresRechercheUtilisateur cru) {
		this.cru = cru;
	}
	
	public AnnonceDetails getAnnonceDetails() {
		return annonceDetails;
	}

	public void setAnnonceDetails(AnnonceDetails annonceDetails) {
		this.annonceDetails = annonceDetails;
	}
}
