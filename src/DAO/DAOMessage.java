package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import metier.Message;
import metier.Projet;
import metier.Visiteur;

public class DAOMessage extends DAO<Visiteur>{

	public DAOMessage(Connection  conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	public Vector<Message> findAllFromIdDestinataire(int id){
		Vector <Message> toReturn  = new Vector();
		Statement st;
		String sql = "Select * FROM message WHERE idDestinataire='"+id+"'";
		ResultSet rs;
		
		try {
			st= connect.createStatement();

		rs=st.executeQuery(sql);
		
		while (rs.next()){
			// (int id, int idDestinataire, int idEmetteur, String date, String message, int status, String objet)
			Message toAdd = new Message(
					rs.getInt("idMessage"),
					rs.getInt("idDestinataire"),
					rs.getInt("idEmetteur"),
					rs.getString("dateEnvoi"),
					rs.getString("message"),
					rs.getInt("status"),
					rs.getString("objet")
					);
			toReturn.add(toAdd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return toReturn;
	}
	public Vector<Message> findAllFromIdEmetteur(int id){
		Vector <Message> toReturn  = new Vector();
		Statement st;
		String sql = "Select * FROM message WHERE idEmetteur='"+id+"'";
		ResultSet rs;
		
		try {
			st= connect.createStatement();

		rs=st.executeQuery(sql);
		
		while (rs.next()){
			// (int id, int idDestinataire, int idEmetteur, String date, String message, int status, String objet)
			Message toAdd = new Message(
					rs.getInt("idMessage"),
					rs.getInt("idDestinataire"),
					rs.getInt("idEmetteur"),
					rs.getString("dateEnvoi"),
					rs.getString("message"),
					rs.getInt("status"),
					rs.getString("objet")
					);
			toReturn.add(toAdd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return toReturn;
	}
	public void InsertMessage( int idDestinataire, int idEmetteur, String message, String objet){
		PreparedStatement ps = null;
		
		String sql = "INSERT INTO message ( idDestinataire, idEmetteur, dateEnvoi, message, status, objet) VALUES ( ?, ?,'2017-01-01', ?,  '0', ?)";
		try {
			ps = connect.prepareStatement(sql);
			ps.setInt(1, idDestinataire);
			ps.setInt(2, idEmetteur);
			ps.setString(3, message);
			ps.setString(4, objet);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
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
