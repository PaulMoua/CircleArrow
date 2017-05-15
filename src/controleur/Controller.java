package controleur;


import java.awt.Color;

import metier.Employe;
import view.Corps;
import view.FenPrincipale;

import view.Menu;
import view.Messagerie;
import view.V_activiteJ;
import view.V_projet;
import view.includes.Footer;
import view.includes.Header;

public class Controller {
		/** Notre controller qui nous permet de generer le contenu des fenetre*/
	static String  toDraw=null;
	static FenPrincipale FA = new FenPrincipale();
	static Employe user;
	private static Color toColor;
	public static Employe getUser() {
		return user;
	}

	public static void setUser(Employe user) {
		Controller.user = user;
	}
	
	public static void main(String[] args) {
		try{
			drawNewView();

			}
		catch (Exception e) {
		 	e.printStackTrace();
		}

	}
	
	//TODO JAVADoc méthode permettant de repeindre la fenêtre 

	public static void setAnDrawNewView(String pToDraw){
		setToDraw(pToDraw);
		drawNewView();
		
	}
	public static void setToDraw(String pToDraw){
		toDraw = pToDraw;
	}
	public static void drawNewView(){
		if (toDraw==null){
			AuthentificationView();
		}else{
			if (user.getNiveauAcces()==0){
				toColor=Color.YELLOW;
			}else if (user.getNiveauAcces()==1){
				toColor=Color.GREEN;
			}
			switch (toDraw) {
			
				case "Menu":
					MenuView();
					break;
				case "Compte-rendus":
					CompteRView();
					break;
				case "Gestion des visiteurs":
					SearchVisiteurView();
					break;
				case "Gestion des praticiens":
					SearchPraticienView();
					break;
			}
		}
		
		
	}
	
	//TODO Javadoc pour les différentes fenetres. 
	//TODO Optimisation
	private static void AuthentificationView(){
		Header menu_header = new Header("Authentification");
		
		FA.setHeader(menu_header);
		
		FA.header_content();
		
		//New footer
		FA.setFooter(new Footer());
		FA.footer_content();
		
		//New corps
		FA.setCorps(new Corps(FA));
		FA.corps();
		
		
		//Parametre de notre fenetre
		FA.setTitle("CircleArrow");
		FA.setVisible(true);
		System.out.println("ConnexionView");
	}
	private static void MenuView(){
		Menu menu = new Menu(FA);
		Header menu_header = new Header("Menu Principal");

		menu.setBgColor(toColor);
		menu_header.setUserName(user,toColor);
		

		FA.remove(FA.getCorps());
		FA.remove(FA.getHeader());
		FA.setCorps(menu);
		FA.setHeader(menu_header);
		FA.remake();
		System.out.println("MenuView");
	}
	private static void CompteRView(){
		Messagerie reporting = new Messagerie(FA);
		FA.remove(FA.getCorps());
		FA.remove(FA.getHeader());
		FA.setCorps(reporting);
		Header menu_header = new Header("Messagerie");
		menu_header.setUserName(user,toColor);
		FA.setHeader(menu_header);
		FA.remake();
		System.out.println("CompteRView");
	}
	private static void SearchVisiteurView(){
		V_activiteJ visiteurs = new V_activiteJ(FA);
		
		FA.remove(FA.getCorps());
		FA.remove(FA.getHeader());
		FA.setCorps(visiteurs);
		Header menu_header = new Header("Registre Employés");
		menu_header.setUserName(user,toColor);
		FA.setHeader(menu_header);
		FA.remake();
		System.out.println("SearchVisiteurView");
	}
	private static void SearchPraticienView(){
		V_projet practiciens = new V_projet(FA);
		FA.remove(FA.getCorps());
		FA.remove(FA.getHeader());
		FA.setCorps(practiciens);
		Header menu_header = new Header("Registre Projets");
		menu_header.setUserName(user,toColor);
		FA.setHeader(menu_header );
		FA.remake();
		System.out.println("SearchPraticienView");
	}


}
