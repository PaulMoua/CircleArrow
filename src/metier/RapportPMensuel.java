package metier;

import java.util.Vector;

public class RapportPMensuel {
	
	public RapportPMensuel(int id, int annee, int mois, Employe idEmploye, int status) {
		super();
		this.mois = mois;
		this.annee = annee;
		this.employe = idEmploye;
		this.id = id;
		this.status = status;
	}
	private int status;
	private int mois;
	private int annee;
	private Employe employe;
	private int id;
	private Vector<ActiviteJ> activiteJour;
	public int getMois() {
		return mois;
	}
	public void setMois(int mois) {
		this.mois = mois;
	}
	public int getAnnee() {
		return annee;
	}
	public void setAnnee(int annee) {
		this.annee = annee;
	}
	public Employe getIdEmploye() {
		return employe;
	}
	public void setIdEmploye(Employe idEmploye) {
		this.employe = idEmploye;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Vector<ActiviteJ> getActiviteJour() {
		
		return activiteJour;
	}
	public String toString(){
		String toReturn= id+ " - "+cd.intToMois[mois-1] +" "+ annee;
		return toReturn;
		
	}
	public void setActiviteJour(Vector<ActiviteJ> activiteJour) {
		this.activiteJour = activiteJour;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Employe getEmploye() {
		return employe;
	}
	public void setEmploye(Employe employe) {
		this.employe = employe;
	}
}