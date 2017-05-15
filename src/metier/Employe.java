package metier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import DAO.DAOFactory;
import DAO.DAORapportPMensuel;

public class Employe {
	public Employe(int id, String nom, String prenom, String motDePass, String mail, int niveauAcces) {
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.motDePass = motDePass;
		this.mail = mail;
		this.niveauAcces = niveauAcces;
		
	}

	private int id;
	private String prenom;
	private String nom;
	private String motDePass;
	private String mail;
	private int niveauAcces;
	private Vector<RapportPMensuel> RapportPMensuel = new Vector<RapportPMensuel>();
	private ResultSet RapportPMensuelBis = null;
	private String[] niveauAccesLibelle ={
			"Superviseur","Employe"};
	public Employe() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Stocke dans la variable RapportPMensuel tous les rapport mensuels appartenant �? l'Employe.
	 */
	public Vector<RapportPMensuel> getRapportPMensuel() throws SQLException{
		DAORapportPMensuel DAORapportPMensuel=(DAORapportPMensuel) DAOFactory.getDAORapportPMensuel();
		RapportPMensuel =DAORapportPMensuel.findByEmploye(this);
		System.out.println("getRapportPMensuel() : Ok!");
		return RapportPMensuel;
	}
	/**
	 * Fournit les noms des colonnes tels qu'ils apparaîtront dans le tableau
	 * �? écrire �? la main.
	 */
	public String[] getColumnName(){
		String columnName[] ={"mois","annee"};
		return columnName;
	}
	/**
	 * Doit fournir les noms des colonnes provenant des tables SQL.
	 * �? écrire �? la main.
	 */
	public String[] getCalledColumnName(){
		String columnName[] ={"mois","annee"};
		return columnName;
	}
	public String getNiveauAccesLibelle(){
		
		return niveauAccesLibelle[niveauAcces];
	}
	
	/**
	 * Permet d'obtenir un objet pouvant être inséré dans un JTable.
	 * 
	 */
	public Object[][] getTable() throws SQLException{
		getRapportPMensuel();
		int size = 0;
		String tableName = "RapportPreviMensuel";
		try {
			System.out.println("RapportPMensuelBis : Essai!");
			RapportPMensuelBis.last();
		    size = RapportPMensuelBis.getRow();
		    RapportPMensuelBis.beforeFirst();
		    System.out.println("RapportPMensuelBis : Ok");
		}catch(Exception ex) {
		    size=0;
		}
		Object[][] data = new Object[size][getColumnName().length];
		int i =0;
		while (RapportPMensuelBis.next()){
			for (int j = 0; j<getColumnName().length; j++ ){
				data[i][j]=RapportPMensuelBis.getString(getColumnName()[j]+tableName);
			}
			i++;
		}
		
		return data;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getMotDePass() {
		return motDePass;
	}
	public void setMotDePass(String motDePass) {
		this.motDePass = motDePass;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public int getNiveauAcces() {
		return niveauAcces;
	}
	public void setNiveauAcces(int niveauAcces) {
		this.niveauAcces = niveauAcces;
	}
	public String toString(){
		String toReturn="";
		toReturn = id+ " - "+ nom +" " +prenom;
		return toReturn;
	}
	
}
