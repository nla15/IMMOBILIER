package alda.immobilier.annonce;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import alda.immobilier.bdd.ImmodbDAO;

@ManagedBean(name="annonceDetails", eager= true)
@SessionScoped
public class AnnonceDetails implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8670272664448467443L;
	private int idDetails;
	private Annonce uneAnnonce;
	private List<Annonce> listAnnonce;
	private Annonce annonceEnCours;
	
	@EJB
	ImmodbDAO imDao;
	
	public AnnonceDetails(){
		annonceEnCours = new Annonce();
		idDetails = 1;
	}

	public int getIdDetails() {
		return idDetails;
	}

	public void setIdDetails(int idDetails) {
		this.idDetails = idDetails;
	}
	
	public Annonce RechercherAnnonce(int id){
		//Annonce annonce = null;
		getListAnnonce();
		for(Annonce a : listAnnonce){
			System.out.println("padanlfor");
		   if (a.getId() == id){
			   uneAnnonce = a;
			   break;
		   }
		}
		
		System.out.println("ME voici "+ uneAnnonce.getDescription() );
		
		return uneAnnonce;
	}
	
	
	public String details(int id){
		setIdDetails(id);
		System.out.println("id dans details: " + id);
		setUneAnnonce(RechercherAnnonce(id));
		System.out.println("rech ann: "+ RechercherAnnonce(id).getDescription());
		return "detailsAnnonce";
	}

	public Annonce getUneAnnonce() {
		return uneAnnonce;
	}

	public void setUneAnnonce(Annonce uneAnnonce) {
		this.uneAnnonce = uneAnnonce;
	}
	
	public List<Annonce> getListAnnonce()
	{
		listAnnonce = imDao.getAllAnnonce();
		return listAnnonce;
	}	


	public void setListAnnonce(List<Annonce> listAnnonce) {
		this.listAnnonce = listAnnonce;
	}

	public Annonce getAnnonceEnCours() {
		annonceEnCours = imDao.getAnnonceById(getIdDetails());
		return annonceEnCours;
	}

	public void setAnnonceEnCours(Annonce annonceEnCours) {
		this.annonceEnCours = annonceEnCours;
	}
	
	
}
