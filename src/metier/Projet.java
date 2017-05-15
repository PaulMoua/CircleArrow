package metier;

import java.util.Hashtable;
import java.util.Vector;

import DAO.DAOActiviteJ;
import DAO.DAORapportPMensuel;

public class Projet {
	public int id;
	public String libelle;
	private int etat;
	public String[] etatLibelle = new String[]{
		"En cours", "Termine"
	};

	public int getId() {
		return id;
	}
	public Projet(int id, String libelle, int etat) {
		super();
		this.id = id;
		this.libelle = libelle;
		this.etat = etat;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public int getEtat() {
		return etat;
	}
	public String getEtatS() {
		String toReturn="";
		if (etat==0){
			toReturn="Ouvert";
		}else if(etat==1){
			toReturn="Fermé";
		}
		return toReturn;
	}
	public void setEtat(int etat) {
		this.etat = etat;
	}
	public String toString(){
		return libelle;
	}
	public String getEtatLibelle() {
		return etatLibelle[etat];
	}
	public int getNombreJour(DAOActiviteJ dao) {
		
		return dao.getCountActiviteJByProjet(id);
	}
	public int getNombreEmploye(DAORapportPMensuel dao) {
		
		return dao.getCountEmployeJByProjet(id);
	}
	public float getNombreKm(DAOActiviteJ dao) {
		return dao.getSumKmByProjet(id);
	}
	public float getNombreFrais(DAOActiviteJ dao) {
		return dao.getSumFraisByProjet(id);
	}
	public Vector<Employe>getAllEmploye(Vector allActiviteJ, Vector allRapportM, Vector allEmploye){
		Vector<Employe> toReturn=new Vector<Employe>();
		Hashtable temp = cd.vectorToHastableByParameter(allActiviteJ, "idProjet");
		Hashtable rapport = cd.vectorToHastableByUnique(allRapportM, "id");
		Hashtable employes =cd.vectorToHastableByUnique(allEmploye, "id");
		Vector<RapportPMensuel>tempVectorBis=new Vector();
		Vector tempVector= (Vector) temp.get(id);
		if (tempVector!=null){
			
				
			
			for( int i=0;i<tempVector.size();i++){
				
				if (!tempVector.contains(rapport.get(tempVector.get(i)))){
					int tempint = ((ActiviteJ) tempVector.get(i)).getIdRapport();
					System.out.println(tempint);
					tempVectorBis.add((RapportPMensuel) rapport.get(tempint));
				}
			}
			for( int i=0;i<tempVectorBis.size();i++){
	
					if ( !toReturn.contains(tempVectorBis.get(i).getEmploye())){
						toReturn.add(tempVectorBis.get(i).getEmploye());
					}
				
			}
		}
		return toReturn;
	}
	
	public Vector<RapportPMensuel>getAllRapport(Vector allActiviteJ, Vector allRapportM ){
		Vector<Employe> toReturn=new Vector<Employe>();
		Hashtable temp = cd.vectorToHastableByParameter(allActiviteJ, "idProjet");
		Hashtable rapport = cd.vectorToHastableByUnique(allRapportM, "id");
		Vector<RapportPMensuel>tempVectorBis=new Vector();
		Vector tempVector= (Vector) temp.get(id);
		for( int i=0;i<tempVector.size();i++){
			
			int tempint = ((ActiviteJ) tempVector.get(i)).getIdRapport();
			if (!tempVectorBis.contains((RapportPMensuel) rapport.get(tempint))){
				
				System.out.println(tempint);
				tempVectorBis.add((RapportPMensuel) rapport.get(tempint));
			}
		}
		return tempVectorBis;
	}
	public Vector<ActiviteJ>getAllActivite(Vector allActiviteJ){
		Vector<ActiviteJ> toReturn=new Vector<ActiviteJ>();
		Hashtable temp = cd.vectorToHastableByParameter(allActiviteJ, "idProjet");
		Vector tempVector= (Vector) temp.get(id);
		return tempVector;
	}


}
