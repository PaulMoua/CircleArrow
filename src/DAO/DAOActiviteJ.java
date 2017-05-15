package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import metier.ActiviteJ;
import metier.RapportPMensuel;

public class DAOActiviteJ extends DAO<ActiviteJ>{

	public DAOActiviteJ(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(ActiviteJ obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(ActiviteJ obj) {
		// TODO Auto-generated method stub
		return false;
	}
	public int getCountActiviteJ(int id){
		int toReturn=0;
		Statement st;
		String sql ="Select COUNT(DISTINCT jourActiviteJ, idRapport) as count FROM activiteJ WHERE idRapport IN ( SELECT idRapportPreviMensuel from rapportprevimensuel WHERE idEmploye='"+id+"')";
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
	
	
	public void insertNewAct(int jour, int idRapport, int idProjet, float fraisActiviteJ, float kmActiviteJ){
		
			
			String sql ="INSERT INTO activitej (jourActiviteJ, idRapport, idProjet, fraisActiviteJ,kmActiviteJ) VALUES (?,?,?,?,?)";
			
			try {
				PreparedStatement ps = connect.prepareStatement(sql);
				ps.setInt(1, jour);
				ps.setInt(2, idRapport);
				ps.setInt(3, idProjet);
				ps.setFloat(4, fraisActiviteJ);
				ps.setFloat(5, kmActiviteJ);
				
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	public void update(int id,int idProjet, int jour, int frais, int km) {
		Statement st;
		
		String sql ="UPDATE activitej SET jourActiviteJ='"+jour +"', idProjet='"+idProjet +"', fraisActiviteJ='"+frais+"',kmActiviteJ='"+km+"' WHERE idActivitej='"+id+"'";
		ResultSet rs;
		try {
			st = connect.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int[] findFirstActivite(){
		int[] toReturn= new int[2];
		Statement st;
		ResultSet rs;
		String sql = "Select min(annee), mois";
		try {
			st = connect.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return toReturn;
	}
	public float findAverageKm(int id){
		float toReturn=0;
		Statement st;
		ResultSet rs;
		int div = this.getCountActiviteJ(id);
		String sql = "Select SUM(kmActiviteJ) as sum FROM activiteJ WHERE idRapport IN ( SELECT idRapportPreviMensuel from rapportprevimensuel WHERE idEmploye='"+id+"')";
		try {
			st = connect.createStatement();
			rs =st.executeQuery(sql);
			if (rs.next()){
				toReturn= rs.getFloat("sum")/div;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return toReturn;
		
		
	}
	public float findAverageFrais(int id){
		float toReturn=0;
		Statement st;
		ResultSet rs;
		int div = this.getCountActiviteJ(id);
		String sql = "Select SUM(fraisActiviteJ) as sum FROM activiteJ WHERE idRapport IN ( SELECT idRapportPreviMensuel from rapportprevimensuel WHERE idEmploye='"+id+"')";
		try {
			st = connect.createStatement();
			rs =st.executeQuery(sql);
			if (rs.next()){
				toReturn= rs.getFloat("sum")/div;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return toReturn;
		
		
	}
	public Vector<ActiviteJ> findByRapport(RapportPMensuel rapport) {
		Vector <ActiviteJ> toReturn=new Vector <ActiviteJ>();
		Statement st;
		String toFind="";
		int id = rapport.getId();
		try {
			st = connect.createStatement();
			String sql ="SELECT idActiviteJ, jourActiviteJ, idRapport, idProjet, fraisActiviteJ,kmActiviteJ,status FROM activitej WHERE idRapport='"+id+"'";
			ResultSet rs;
			System.out.println("DAOActiviteJ.findAll():executeQuery");
			rs = st.executeQuery(sql);
			int compte=0;
			while(rs.next()){
				System.out.println("next : " + rs.getString("idActiviteJ"));
				//int id, int jour, int idRapport, int idProjet, int frais, int km
				toReturn.add(new ActiviteJ (rs.getInt("idActiviteJ"), rs.getInt("jourActiviteJ"), rs.getInt("idRapport"), rs.getInt("idProjet"), rs.getInt("fraisActiviteJ"), rs.getInt("kmActiviteJ"), rs.getInt("status")));
				compte++;
			}
			System.out.println("Resultat : "+Integer.toString(compte));
			System.out.println("DAOActiviteJ.findAll():Ok");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toReturn;
	}
	public Vector<ActiviteJ> findAll() {
		Vector <ActiviteJ> toReturn=new Vector <ActiviteJ>();
		Statement st;
		String toFind="";
		try {
			st = connect.createStatement();
			String sql ="SELECT idActiviteJ, jourActiviteJ, idRapport, idProjet, fraisActiviteJ,kmActiviteJ, status FROM activitej";
			ResultSet rs;
			System.out.println("DAOActiviteJ.findAll():executeQuery");
			rs = st.executeQuery(sql);
			int compte=0;
			while(rs.next()){
				System.out.println("next : " + rs.getString("idActiviteJ"));
				//int id, int jour, int idRapport, int idProjet, int frais, int km
				toReturn.add(new ActiviteJ (rs.getInt("idActiviteJ"), rs.getInt("jourActiviteJ"), rs.getInt("idRapport"), rs.getInt("idProjet"), rs.getInt("fraisActiviteJ"), rs.getInt("kmActiviteJ"), rs.getInt("status")));
				compte++;
			}
			System.out.println("Resultat : "+Integer.toString(compte));
			System.out.println("DAOActiviteJ.findAll():Ok");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public float getSumKmByRapport(int id){
		float toReturn =0;
		Statement st;
		try {
			st = connect.createStatement();
			String sql = "SELECT SUM(kmActiviteJ) AS sum FROM activiteJ WHERE idRapport='"+id+"'";
			ResultSet rs;
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				toReturn =rs.getFloat("sum");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return toReturn;
	}
	public float getSumFraisByRapport(int id){
		float toReturn =0;
		Statement st;
		try {
			st = connect.createStatement();
			String sql = "SELECT SUM(fraisActiviteJ) AS sum FROM activiteJ WHERE idRapport='"+id+"'";
			ResultSet rs;
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				toReturn =rs.getFloat("sum");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return toReturn;
	}
	public float getSumKmByProjet(int id){
		float toReturn =0;
		Statement st;
		try {
			st = connect.createStatement();
			String sql = "SELECT SUM(kmActiviteJ) AS sum FROM activiteJ WHERE idProjet='"+id+"'";
			ResultSet rs;
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				toReturn =rs.getFloat("sum");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return toReturn;
	}
	public float getSumFraisByProjet(int id){
		float toReturn =0;
		Statement st;
		try {
			st = connect.createStatement();
			String sql = "SELECT SUM(fraisActiviteJ) AS sum FROM activiteJ WHERE idProjet='"+id+"'";
			ResultSet rs;
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				toReturn =rs.getFloat("sum");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return toReturn;
	}
	public int getCountActiviteJByProjet(int id){
		int toReturn=0;
		Statement st;
		String sql ="Select COUNT(DISTINCT jourActiviteJ, idRapport) as count FROM activiteJ WHERE idProjet='"+id+"'";
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
	
	
	public Vector<float[]> getDistinctProjetsByRapport(int id){
		Vector<float[]> toReturn =new Vector<float[]>();
		Statement st;
		try {
			st = connect.createStatement();
			String sql = "SELECT idProjet, count(DISTINCT jourActiviteJ) AS nbJour, SUM(fraisActiviteJ) AS sumFrais,SUM(kmActiviteJ) AS sumKm  FROM activiteJ WHERE idRapport='"+id+"' GROUP BY idProjet";
			ResultSet rs;
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				float temp[] = new float[4];
				temp[0]= rs.getFloat("idProjet");
				temp[1]= rs.getFloat("nbJour");
				temp[2]= rs.getFloat("sumFrais");
				temp[3]= rs.getFloat("sumKm");
				toReturn.add(temp);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return toReturn;
	}
	@Override
	public boolean update(ActiviteJ obj) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public ActiviteJ find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
