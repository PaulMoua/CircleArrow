package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import metier.Projet;
import metier.Visiteur;

public class DAOProjet extends DAO<Visiteur>{

	public DAOProjet(Connection  conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	public void newProjet(String libelle){
		PreparedStatement ps = null;
		
		String sql = "INSERT INTO projet (libelleProjet, etat) VALUES (?, '0')";
		try {
			ps = connect.prepareStatement(sql);

			ps.setString(1, libelle);

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	}
	public void terminate(int id){
		Statement st;
		String sql="Update projet SET etat='1' WHERE idProjet='"+id+"'";
		
		try {
			st=connect.createStatement();
			st.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	public Vector <Projet> findAll() { 
		Vector <Projet> toReturn=new Vector <Projet>();
		Statement st;
		String toFind="";

		try {
			st = connect.createStatement();

			String sql ="SELECT idProjet, libelleProjet, etat FROM projet";
			ResultSet rs;
			System.out.println("DAOProject.findAll(): executeQuery()");
			rs = st.executeQuery(sql);
			int compte=0;
			while(rs.next()){
				System.out.println("      next");
				toReturn.add(new Projet (rs.getInt("idProjet"), rs.getString("libelleProjet"), rs.getInt("etat")));
				compte++;
			}
			System.out.print("DAOProject.findAll(): resultat :");
			System.out.println(Integer.toString(compte));
		} catch (SQLException e) {
			System.out.print("DAOProject.findAll(): erreur");
			e.printStackTrace();
		}
		return toReturn;
		
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

	@Override
	public Visiteur find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
