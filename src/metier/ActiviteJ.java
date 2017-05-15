package metier;

import java.util.Hashtable;

import DAO.DAOFactory;

public class ActiviteJ {
	public ActiviteJ(int id, int jour, int idRapport, int idProjet, int frais, int km, int status) {
		super();
		this.jour = jour;
		this.idRapport = idRapport;
		this.idProjet = idProjet;
		this.id = id;
		this.frais = frais;
		this.km = km;
		this.status=status;
	}

	private int jour;
	private int idRapport;
	private int idProjet;
	private int id;
	private int frais;
	private int km;
	private int status;
	public String date;
	public int getJour() {
		return jour;
	}
	public void setJour(int jour) {
		this.jour = jour;
	}
	public int getIdRapport() {
		return idRapport;
	}
	public void setIdRapport(int idRapport) {
		this.idRapport = idRapport;
	}
	public int getIdProjet() {
		return idProjet;
	}
	public void setIdProjet(int idProjet) {
		this.idProjet = idProjet;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFrais() {
		return frais;
	}
	public void setFrais(int frais) {
		this.frais = frais;
	}
	public int getKm() {
		return km;
	}
	public void setKm(int km) {
		this.km = km;
	}
	
	public void update(int jour, int idRapport, int frais, int km){
		System.out.println("ActiviteJ.update()");
		this.jour = jour;	
		this.idProjet = idProjet;
		this.frais = frais;
		this.km = km;
		DAOFactory.getDAOActiviteJ().update(id, idProjet, jour, frais, km);
		
	}
	public void SetDate(Hashtable allRapport){
		RapportPMensuel rapport = (RapportPMensuel) allRapport.get(idRapport);
		date = "";
		date =id + " - "+jour + "/"+ rapport.getMois()+ "/" + rapport.getAnnee();
				
	}
	public String toString(){
		return date;
	}

}