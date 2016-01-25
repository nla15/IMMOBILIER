package alda.immobilier.criteres;

import java.io.Serializable;

import javax.faces.bean.ManagedProperty;

import alda.immobilier.adresse.RegionCtrl;

public class CriteresRechercheEditable implements Serializable{
	private static final long serialVersionUID = 2700649873363317514L;
	
	@ManagedProperty(value="#{regionCtrl}")
	private RegionCtrl reCtrl;

	private CriteresRecherche crEncaps;

	public CriteresRechercheEditable(CriteresRecherche cible){
		crEncaps = cible;
	}

	public String getPrixMin() {
		return String.valueOf(crEncaps.getPrixMin());
	}

	public void setPrixMin(String prixMin) {
		this.crEncaps.setPrixMin(Float.parseFloat(prixMin));
	}
	
	public String getPrixMax() {
		return String.valueOf(crEncaps.getPrixMax());
	}

	public void setPrixMax(String prixMax) {
		this.crEncaps.setPrixMax(Float.parseFloat(prixMax));
	}

	public String getSurfaceMin() {
		return String.valueOf(crEncaps.getSurfaceMin());
	}

	public void setSurfaceMin(String surfaceMin) {
		this.crEncaps.setSurfaceMin(Float.parseFloat(surfaceMin));
	}

	public String getSurfaceMax() {
		return String.valueOf(crEncaps.getSurfaceMax());
	}

	public void setSurfaceMax(String surfaceMax) {
		this.crEncaps.setSurfaceMax(Float.parseFloat(surfaceMax));
	}
	
	public String getNomRegion(){
		return crEncaps.getIdRegion().getNomRegion();
	}
	
	public void setNomRegion(String nomRegion){
		crEncaps.setIdRegion(reCtrl.trouverRegionParNom(nomRegion));
	}
	
	public CriteresRecherche getCrEncaps(){
		return crEncaps;
	}
	
	public RegionCtrl getReCtrl() {
		return reCtrl;
	}

	public void setReCtrl(RegionCtrl reCtrl) {
		this.reCtrl = reCtrl;
	}
}
