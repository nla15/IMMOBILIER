package alda.immobilier.annonce;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.UploadedFile;

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
	
	public AnnonceCtrl(){
		newAnnonce.creerVide();
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
		Annonce annonce = null;
		for(Annonce a : listAnnonce){
		   if (a.getId() == id){
			   annonce = a;
			   break;
		   }
		}
		
		System.out.println("ME voici "+ annonce.getDescription() );
		
		return annonce;
	}
	
	public String creerAnnonce() {
		newAnnonce.creerVide();
		imDao.insert(newAnnonce);
		newAnnonce.setIdRefUser(cu.getUtilisateur(ulc.getUserLoginId()));
		newAnnonce.getAdresseAnn().setRegionAdr(rc.getRegSelect());
		imDao.update(newAnnonce);
		listAnnonce = imDao.getAllAnnonce();
		
		return "accueil";
		
	}
	
	public String details(int id){
		setAnn(RechercherAnnonce(id));
		
		System.out.println("valeur ann "+ ann.getDescription());
		
		return "detailsAnnonce";
	}
	
	public String ModifierAnnonce(){
		
		System.out.println("MODIER ANN");
		/*ann.setDescription(des);
		ann.setDesignation(desi);
		ann.setPrix(prx);
		ann.setSurface(surface);
		ann.getAdresseAnn().setCodePostal(cdp);
		ann.getAdresseAnn().setLibelle(libelle);
		ann.getAdresseAnn().setVille(v);
		ann.getAdresseAnn().setRegionAdr(reg);
		ann.getIdRefUser().setMobile(mobile);*/
		return "informations";
	}

	public void upload(){
		
		System.out.println("pppppppp");
		
		String fileName = uploadedFile.getFileName();
		String contentType = uploadedFile.getContentType();
		byte[] contents = uploadedFile.getContents();
		
		System.out.println(fileName + "\t" + contentType + "\t" + contents);
	}
	
	public Annonce getAnn() {
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
}
