package DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import metier.Employe;

public class DAOEmploye extends DAO<Employe> {
	
	public DAOEmploye(Connection conn) {
		super(conn);
	}
	@Override
	public boolean create(Employe obj) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean delete(Employe obj) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean update(Employe obj) {
		// TODO Auto-generated method stub
		return false;
	}
	public Employe check(String pnom, String ppass) throws SQLException {
		Employe toReturn=null;
		Statement st=connect.createStatement();
		String sql ="SELECT idEmploye, nomEmploye, prenomEmploye, mdpEmploye, mailEmploye,niveauAccesEmploye FROM employe WHERE nomEmploye='" +pnom+"' AND mdpEmploye='"+ppass+"'";
		ResultSet rs=st.executeQuery(sql);
		if(rs.next()){
			toReturn= new Employe (rs.getInt("idEmploye"), rs.getString("nomEmploye"), rs.getString("prenomEmploye"), rs.getString("mdpEmploye"), rs.getString("mailEmploye"), rs.getInt("niveauAccesEmploye"));
			System.out.println("Identification Ok!");
			System.out.println(toReturn.getNom());
			System.out.println(toReturn.getPrenom());
		}
		return toReturn;
	}
	public Vector <Employe> findAll() { 
		Vector <Employe> toReturn=new Vector <Employe>();
		Statement st;
		String toFind="";

		try {
			st = connect.createStatement();

			String sql ="SELECT idEmploye, nomEmploye, prenomEmploye, mdpEmploye, mailEmploye,niveauAccesEmploye FROM employe";
			ResultSet rs;

			rs = st.executeQuery(sql);
			int compte=0;
			while(rs.next()){
				
				System.out.println("next");
				toReturn.add(new Employe (rs.getInt("idEmploye"),
						rs.getString("nomEmploye"),
						rs.getString("prenomEmploye"),
						rs.getString("mdpEmploye"),
						rs.getString("mailEmploye"),
						rs.getInt("niveauAccesEmploye")));
				compte++;
			}
			System.out.println(Integer.toString(compte));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toReturn;
		
	}
	@Override
	public Employe find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
