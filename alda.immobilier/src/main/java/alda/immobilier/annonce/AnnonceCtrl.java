package alda.immobilier.annonce;

import java.io.Serializable;
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
	
	@EJB
	ImmodbDAO imDao;
	
	private List<Annonce> listAnnonce;
	private Annonce ann;
	private Annonce newAnnonce = new Annonce();
	
	private UploadedFile uploadedFile;
	
	private String iDesc, iDesg, ireg, iLibel, iV, icdp;
	private float iSurf, iPrix;
	private int id;
	private int idDetailsAnn = 0;
	
	public AnnonceCtrl(){
		newAnnonce.creerVide();
		ann = null;
	}
	
	@PostConstruct
	void init(){
		getAnn();
		id = ann.getId();
		iDesc = ann.getDescription();
		iDesg = ann.getDesignation();
		iLibel = ann.getAdresseAnn().getLibelle();
		iV = ann.getAdresseAnn().getVille();
		icdp = ann.getAdresseAnn().getCodePostal();
		iSurf = ann.getSurface();
		iPrix = ann.getPrix();		
		
	}
	public List<Annonce> getListAnnonce()
	{
		listAnnonce = imDao.getAllAnnonce();
		 return listAnnonce;
	}	


	public void setListAnnonce(List<Annonce> listAnnonce) {
		this.listAnnonce = listAnnonce;
	}
	
	public Annonce RechercherAnnonce(int id){
		setIdDetailsAnn(id);
		System.out.println("idDetl dans Recherch: " + idDetailsAnn);
		//Annonce annonce = null;
		for(Annonce a : listAnnonce){
		   if (a.getId() == id){
			   newAnnonce = a;
			   break;
		   }
		}
		
		System.out.println("ME voici "+ newAnnonce.getDescription() );
		
		return newAnnonce;
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
		//newAnnonce.getAdresseAnn().setRegionAdr(rc.getRegSelect());
		imDao.update(newAnnonce);
		listAnnonce = imDao.getAllAnnonce();
		
		return "accueil";
		
	}
	
	
	public String details(int id){
		setIdDetailsAnn(id);
		System.out.println("id dans details: " + id);
		setAnn(RechercherAnnonce(id));
		System.out.println("rech ann: "+ RechercherAnnonce(id).getDescription());
		return "detailsAnnonce";
	}
	
	public void ModifierAnnonce(){
		
		System.out.println("MODIER ANN");
		ann.getId();
		ann.setDescription(iDesc);
		ann.setDesignation(iDesg);
		ann.setPrix(iPrix);
		ann.setSurface(iSurf);
		ann.getAdresseAnn().setCodePostal(icdp);
		ann.getAdresseAnn().setLibelle(iLibel);
		ann.getAdresseAnn().setVille(iV);
		ann.getAdresseAnn().setRegionAdr(rc.getRegSelect());
		ann.getIdRefUser().getMobile();
		ann.getIdRefUser().getNom();
		ann.getIdRefUser().getIdRefUserLogin().getMail();
		
		imDao.update(ann);
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
	
	public Annonce getAnn() {
		ann = imDao.getAllAnnonce().get(getIdDetailsAnn());
		System.out.println("aanonce courante " + getIdDetailsAnn());
		return ann;
	}
	

	public void setAnn(Annonce an) {
		ann = an;
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
	
	public int getIdDetailsAnn() {
		System.out.println("idDetail dans get" + this.idDetailsAnn);		
		return this.idDetailsAnn;
	}

	public void setIdDetailsAnn(int idDetailsAnn) {
		this.idDetailsAnn = idDetailsAnn;
		System.out.println("idDetail dans set after modif" + this.idDetailsAnn);
	}
}
