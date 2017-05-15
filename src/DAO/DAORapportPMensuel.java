package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import metier.Employe;
import metier.RapportPMensuel;
import metier.Visiteur;

public class DAORapportPMensuel extends DAO<Visiteur> {
	
	public DAORapportPMensuel(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Visiteur obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Visiteur obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Visiteur obj) {
		// TODO Auto-generated method stub
		return false;
	}
	public void updateStatus(int id){
		
		PreparedStatement ps = null;
		String sql ="Update rapportprevimensuel "
				+ "SET status=1 "
				+ "WHERE idRapportPreviMensuel='"+id+"'";
		try {
			ps = connect.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void insertNewRapport(int annee, int mois, int idEmploye){
		
		Statement st;
		PreparedStatement ps = null;
		String sql ="INSERT INTO rapportprevimensuel ( anneeRapportPreviMensuel, moisRapportPreviMensuel, idEmploye) "
				+" SELECT ?,?,? FROM rapportprevimensuel "
				+ "WHERE NOT EXISTS (SELECT * FROM rapportprevimensuel WHERE anneeRapportPreviMensuel=? AND moisRapportPreviMensuel=? AND idEmploye=?) LIMIT 1";
		
		try {
			ps = connect.prepareStatement(sql);
			ps.setInt(1, annee);
			ps.setInt(2, mois);
			ps.setInt(3, idEmploye);
			ps.setInt(4, annee);
			ps.setInt(5, mois);
			ps.setInt(6, idEmploye);

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
}

	/*
	 * Exécute la requête sql suivante : Recherche tous les rapport correspondant à un idEmploye.
	 * Retourne le ResulSet correspondant
	 */
	
	public Vector findAllDistinct(String valueName){
		Vector toReturn = new Vector();
		Statement st;
		ResultSet rs = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY);

			String sql ="SELECT DISTINCT " + valueName+" FROM rapportprevimensuel WHERE idEmploye='' ORDER BY anneeRapportPreviMensuel,moisRapportPreviMensuel";
			System.out.println("findByEmploye() : Essai");
			

			rs = st.executeQuery(sql);
			System.out.println("findByEmploye() : Ok!");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return toReturn;
	}
	public int findByDate(int mois, int annee, int idEmploye) {
		
		int toReturn= -1;
		
		Statement st;
		ResultSet rs = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY);

			String sql ="SELECT idRapportPreviMensuel FROM rapportprevimensuel WHERE anneeRapportPreviMensuel='" +annee+"' AND moisRapportPreviMensuel='"+mois+"' AND idEmploye='"+idEmploye+"'";
			System.out.println("findByDate : Essai");
			

			rs = st.executeQuery(sql);
			if(rs.next()){
				toReturn= rs.getInt("idRapportPreviMensuel");
				
				System.out.println(toReturn);
				}

			System.out.println("findByDate : Ok!");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return toReturn;

	}
	public int[] findFirstRegforEmploye(int id){
		int[] toReturn= new int[2];
		Statement st;
		ResultSet rs;
		String sql = "Select MIN(anneeRapportPreviMensuel) as min, moisRapportPreviMensuel FROM rapportprevimensuel WHERE idEmploye='" +id+"' GROUP BY moisRapportPreviMensuel  ORDER BY moisRapportPreviMensuel ASC ";
		try {
			st = connect.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()){
				toReturn[0]=rs.getInt("min");
				toReturn[1]=rs.getInt("moisRapportPreviMensuel");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return toReturn;
	}
	public Vector<RapportPMensuel> findByEmploye(Employe employe) {
		int id = employe.getId();
		Vector<RapportPMensuel> toReturn=new Vector<RapportPMensuel>();
		RapportPMensuel toReturnBis=null;
		Statement st;
		ResultSet rs = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY);

			String sql ="SELECT idRapportPreviMensuel, anneeRapportPreviMensuel, moisRapportPreviMensuel, idEmploye, status FROM rapportprevimensuel WHERE idEmploye='" +id+"' ORDER BY anneeRapportPreviMensuel,moisRapportPreviMensuel";
			System.out.println("findByEmploye() : Essai");
			

			rs = st.executeQuery(sql);
			while(rs.next()){
				toReturnBis= new RapportPMensuel (rs.getInt("idRapportPreviMensuel"), rs.getInt("anneeRapportPreviMensuel"),rs.getInt("moisRapportPreviMensuel"), employe, rs.getInt("status"));
				
				System.out.println(Integer.toString(toReturnBis.getId()));
				System.out.println(Integer.toString(toReturnBis.getMois()));
				toReturn.addElement(toReturnBis);
				}

			
			
			System.out.println("findByEmploye() : Ok!");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return toReturn;

	}
	public ResultSet findByEmploye(int id) { 
		Vector<RapportPMensuel> toReturn=new Vector<RapportPMensuel>();
		RapportPMensuel toReturnBis=null;
		Statement st;
		ResultSet rs = null;
		try {
			st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY);

			String sql ="SELECT idRapportPreviMensuel, anneeRapportPreviMensuel, moisRapportPreviMensuel, idEmploye FROM rapportprevimensuel WHERE idEmploye='" +id+"' ORDER BY anneeRapportPreviMensuel,moisRapportPreviMensuel";
			System.out.println("findByEmploye() : Essai");
			

			rs = st.executeQuery(sql);
			System.out.println("findByEmploye() : Ok!");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return rs;
		//if(rs.next()){
			//toReturnBis= new RapportPMensuel (rs.getInt("idRapportPreviMensuel"), rs.getInt("anneeRapportPreviMensuel"),rs.getInt("moisRapportPreviMensuel"), rs.getInt("idEmploye"));
			
			//System.out.println(Integer.toString(toReturnBis.getId()));
			//System.out.println(Integer.toString(toReturnBis.getMois()));
			//toReturn.addElement(toReturnBis);
		//}

	}
	@Override
	public Visiteur find(int id) { 
		// TODO Auto-generated method stub
		return null;
	}
	public int getCountEmployeJByProjet(int id){
		int toReturn=0;
		Statement st;
		String sql ="Select COUNT(DISTINCT idEmploye) as count FROM rapportprevimensuel  LEFT JOIN activitej ON rapportprevimensuel.idRapportPreviMensuel=activitej.idRapport WHERE activitej.idProjet='"+ id+"'";
		ResultSet rs;
		try {
			st = connect.createStatement();
			rs=st.executeQuery(sql);
			if(rs.next()){
				toReturn=rs.getInt("count");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		
		return toReturn;
	}
}
