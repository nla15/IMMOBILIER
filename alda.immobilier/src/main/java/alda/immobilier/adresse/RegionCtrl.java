package alda.immobilier.adresse;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;

import alda.immobilier.utilisateur.UserLoginCtrl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean(name="regionCtrl")
@ViewScoped
public class RegionCtrl implements Serializable {
	private static final long serialVersionUID = 9148494751527980262L;
	
	@ManagedProperty(value="#{UserLoginCtrl}")
	private UserLoginCtrl ulc;
	@EJB
	private RegionDAO reDAO;
	private String nomRegSelect;
	
	public RegionCtrl(){
	}
	
	@PostConstruct
	public void init(){
		if ( ulc.suisJeCo() ){
			nomRegSelect = ulc.getUtilisateur().getAdressePostale().getRegionAdr().getNomRegion();
		} else {
			nomRegSelect = getRegionParDefaut().getNomRegion();
		}
	}

	public Region trouverRegionParNom(String nomRegion){
		List<Region> regs = getRegions();
		
		for ( Region r : regs ){
			if ( r.getNomRegion().equals(nomRegion) ){
				return r;
			}
		}
		
		return null;
	}
	
	public List<Region> getRegions(){
		return reDAO.getAllRegion();
	}
	
	public String getNomRegSelect(){
		return nomRegSelect;
	}
	
	public void setNomRegSelect(String rs){
		nomRegSelect = rs;
	}
	
	public Region getRegionParDefaut(){
		return getRegions().get(0);
	}
	
	public Region getRegSelect(){
		return trouverRegionParNom(nomRegSelect);
	}

	public UserLoginCtrl getUlc() {
		return ulc;
	}

	public void setUlc(UserLoginCtrl ulc) {
		this.ulc = ulc;
	}
}