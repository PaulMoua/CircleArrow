package DAO;
import java.sql.Connection;

public class DAOFactory {

			  protected static final Connection conn = BDDConnexion.getInstance();   
			   
			  public static DAOEmploye getDAOEmploye(){
				    return new DAOEmploye(conn);
				  }
			  public static DAORapportPMensuel getDAORapportPMensuel(){
				    return new DAORapportPMensuel(conn);
				  }
			  public static DAOActiviteJ getDAOActiviteJ(){
				    return new DAOActiviteJ(conn);
				  }
			  public static DAOProjet getDAOProjet(){
				    return new DAOProjet(conn);
				  }
			  public static DAOMessage getDAOMessage(){
				    return new DAOMessage(conn);
				  }
			  
			  

		
			}