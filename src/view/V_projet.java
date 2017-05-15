package view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import DAO.DAOActiviteJ;
import DAO.DAOEmploye;
import DAO.DAOFactory;
import DAO.DAOProjet;
import DAO.DAORapportPMensuel;
import controleur.Controller;
import metier.ActiviteJ;
import metier.Employe;
import metier.Projet;
import metier.RapportPMensuel;
import metier.cd;
import view.V_activiteJ.EditionListener;
import view.includes.Header;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JLayeredPane;
import javax.swing.JDesktopPane;

public class V_projet extends JPanel {
	private JTable result_table;
	private JTree arbre;
	JScrollPane result_zone = new JScrollPane();
	private FenPrincipale FA;
	String intToMois[] = {"Janvier", "Février","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Décembre"};
	String columnName[] = {"Matricule","Nom","Prenom","Ville","Departement"}; 
	Object[][] data = {
			{"1","Diaby","Balamini","Paris","75016"},
			{"2","Moua","Paul","Marseille","13001"},
			{"3","Siarri","Nicolas","Gagny","93220"},
			{"4","Boudiaf","Ryan","Monaco","10000"},
			
	};
	//JButton edit_button = new JButton("Editer");
	int idProjet;
	int jour;
	int mois;
	int annee;
	int km;
	int frais;
	Projet projet;
	
	private JTextField txtKmParcourus;
	private JTextField txtFrais;
	private JTextField txtJour;
	private Employe user;
	private int niveauAcces=-1;
	private int idActiviteJ=-1;
	JLabel lblNomPrnom = new JLabel("");
	JLabel lblMoisAnnee = new JLabel("");
	JLabel lblActivitJournalire = new JLabel("Registre Mensuel");
    DAOActiviteJ DAOActiviteJ = DAOFactory.getDAOActiviteJ();
    Vector<ActiviteJ> allActiviteJ = DAOActiviteJ.findAll();
    Hashtable hsActiviteJ= new Hashtable();
    private JButton add_button;
    DAOEmploye DAOEmploye =  DAOFactory.getDAOEmploye();
    Vector<Employe> allEmploye=new Vector<Employe>();
    Hashtable HSallEmploye = null;
    private int idEmployeSelected=-1;
    private int idRapportSelected=-1;
    private int idActSelected=-1;
    
    DAOProjet DAOProjet =  DAOFactory.getDAOProjet();
    Vector<Projet> allProjet=DAOProjet.findAll();
    Hashtable allProjetHT=  cd.vectorToHastableByUnique(allProjet,"id");
    
    Vector<String> allProjetName= cd.getValFromVec(allProjet,"libelle");
    JComboBox JCBMois = new JComboBox();
    JComboBox JCBProjet = new JComboBox();
    DAORapportPMensuel DAORapportPMensuel = (DAORapportPMensuel) DAOFactory.getDAORapportPMensuel();
    Vector<RapportPMensuel> AllRapport=new Vector<RapportPMensuel>();
    Hashtable allRapportFromSelectedEmploye=null;
    
    JButton btnNewButton = new JButton("Verifier");
    JTree treeToMake =null;
    JPanel panelToMake=null;
    private JTable table;
    private JTable table_1;
    private JLabel label;
    private JLabel label_1;
    private JLabel label_2;
    private JLabel label_3;
    private JLabel label_4;
    private void switchUser(){
		  switch(niveauAcces){
		  	case 0:
		  		allEmploye=DAOEmploye.findAll();
		  		HSallEmploye = cd.vectorToHastableByUnique(allEmploye, "id");
		  		for(int i =0; i<allEmploye.size();i++){
		  			System.out.println("Hollo");
		  			AllRapport.addAll(DAORapportPMensuel.findByEmploye(allEmploye.get(i)));
		  		}
		  		allRapportFromSelectedEmploye=cd.vectorToHastableByUnique(AllRapport,"id");
		  		/**add_button = new JButton("Créer un registre mensuel");
				add_button.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						NewRapport dialog = new NewRapport(allEmploye);
						
					}
					
				});
				add_button.setHorizontalAlignment(SwingConstants.RIGHT);
		  		**/
				break;
		  	case 1:
		  		allEmploye=DAOEmploye.findAll();
		  		HSallEmploye = cd.vectorToHastableByUnique(allEmploye, "id");
		  		for(int i =0; i<allEmploye.size();i++){
		  			System.out.println("Hollo");
		  			AllRapport.addAll(DAORapportPMensuel.findByEmploye(allEmploye.get(i)));
		  		}
		  		allRapportFromSelectedEmploye=cd.vectorToHastableByUnique(AllRapport,"id");
				/**add_button = new JButton("Créer un registre mensuel");
				add_button.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						NewRapport dialog = new NewRapport(allEmploye);
						
					}
					
				});
				add_button.setHorizontalAlignment(SwingConstants.RIGHT);
		  		**/
				break;
		  	case 2:
		  		add_button = new JButton("Enregistrer une nouvelle activité");
				add_button.setHorizontalAlignment(SwingConstants.RIGHT);
				
				
		  		allEmploye.add(user);
		  		break;
		  		
		  }
    }

	public V_projet(FenPrincipale menu) {

		
		FA = menu;
		user =Controller.getUser();
		niveauAcces = user.getNiveauAcces();
		System.out.println("Niveau : "+niveauAcces);
		switchUser();
		setBounds(100,100,720,423);
		this.setLayout(new BorderLayout(0, 0));
		
		// Zone de recherche d'utilisateur (Upper)
		
		JPanel search_zone = new JPanel();
		search_zone.setLayout(new FlowLayout(FlowLayout.LEADING, 30, 30));
		search_zone.setBackground(Color.LIGHT_GRAY);
		add(search_zone, BorderLayout.NORTH);
		
		
		JTextField search_field = new JTextField();
		search_field.setHorizontalAlignment(SwingConstants.LEFT);
		search_field.setColumns(20);
		search_zone.add(search_field);
		
		//TODO Continuer
		JButton search_button = new JButton("Rechercher");
		search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				System.out.println("Fonction non implémentée");
				
			}
		});
		search_zone.add(search_button);


		if (add_button!=null){
			search_zone.add(add_button);
		}
		// Marge Gauche
		
		JPanel left_border_zone = new JPanel();
		FlowLayout fl_left_border_zone = (FlowLayout) left_border_zone.getLayout();
		fl_left_border_zone.setHgap(15);
		left_border_zone.setBackground(Color.LIGHT_GRAY);
		
		// Zone d'actions sur les visiteurs
		
		JPanel actions_zone = new JPanel();
		actions_zone.setLayout(new FlowLayout(FlowLayout.TRAILING, 30, 15));
		actions_zone.setBackground(Color.LIGHT_GRAY);
		add(actions_zone, BorderLayout.SOUTH);
		
		
	
		/**
		txtProjet.setText(Integer.toString(pActiviteJ.getIdProjet()));
		System.out.println(allProjetHT.get(pActiviteJ.getIdProjet()));
		JCBProjet.setSelectedItem(((Projet) allProjetHT.get(pActiviteJ.getIdProjet())).getLibelle());
		//System.out.println(((Projet) allProjetHT.get(Integer.toString(pActiviteJ.getIdProjet()))).getLibelle());
		JCBMois.setSelectedItem(pMois.trim());
		txtKmParcourus.setText(Integer.toString(pActiviteJ.getKm()));
		txtFrais.setText(Integer.toString(pActiviteJ.getFrais()));
		txtJour.setText(Integer.toString(pActiviteJ.getJour()));
		lblNomPrnom.setText(pNomPrenom);
		JCBMois.setSelectedItem(pMois);
		textField.setText(pAnnee);
		
		lblActivitJournalire.setText("Activite journalière n°" +Integer.toString(pActiviteJ.getId()));
		
		**/
		
		JButton btnNouveauProjet = new JButton("Nouveau Projet");
		
		btnNouveauProjet.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				NewProjet dialog = new NewProjet();
				Controller.setAnDrawNewView("Gestion des praticiens");
			}
		});
		if (user.getNiveauAcces()!=0){
			btnNouveauProjet.setEnabled(false);
		}
		actions_zone.add(btnNouveauProjet);
		

		
		
		
		JButton exit_button = new JButton("Fermer");
		exit_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.setAnDrawNewView("Menu");
			}
		});
		actions_zone.add(exit_button);
		
		// Zone de resultats de la recherche
		
		
		result_zone.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		result_zone.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(result_zone, BorderLayout.WEST);
		
		
		//result_table = new JTable(data,columnName);
		//result_table.setCellSelectionEnabled(true);
		
		treeToMake = buildTree();
		panelToMake = buildViewForProjet();

		result_zone.setViewportView(treeToMake);
		add(panelToMake, BorderLayout.CENTER);

	
		

	}
	private JPanel buildViewForProjet(){
		JPanel toReturn = new JPanel();
		
		JLabel lblProjet_1 = new JLabel("Projet");
		lblProjet_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblProjet_1.setBounds(10, 11, 151, 40);
		toReturn.add(lblProjet_1);
		
		JLabel lblNomprojet = new JLabel("NomProjet");
		lblNomprojet.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNomprojet.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNomprojet.setBounds(337, 11, 137, 40);
		toReturn.add(lblNomprojet);
		
		JLabel lblStatus_1 = new JLabel("Status :");
		lblStatus_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStatus_1.setBounds(20, 62, 118, 14);
		toReturn.add(lblStatus_1);
		
		JLabel lblNombreDeJour = new JLabel("Nombre de jour :");
		lblNombreDeJour.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombreDeJour.setBounds(30, 87, 108, 14);
		toReturn.add(lblNombreDeJour);
		
		JLabel lblNombreDemploy = new JLabel("Nombre d'employé :");
		lblNombreDemploy.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombreDemploy.setBounds(10, 112, 128, 14);
		toReturn.add(lblNombreDemploy);
		
		JLabel lblFraisTotal = new JLabel("Frais total :");
		lblFraisTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFraisTotal.setBounds(243, 87, 108, 14);
		toReturn.add(lblFraisTotal);
		
		JLabel lblKmTotal = new JLabel("Km total :");
		lblKmTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKmTotal.setBounds(243, 112, 108, 14);
		toReturn.add(lblKmTotal);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(18, 137, 456, 125);
		toReturn.add(scrollPane);
		
		
		
		toReturn.setLayout(null);
		
		JButton btnTerminer = new JButton("Terminer");
		btnTerminer.addActionListener(new TerminateAL());
		btnTerminer.setEnabled(false);
		btnTerminer.setBounds(385, 58, 89, 23);
		toReturn.add(btnTerminer);
		if (projet!=null){
			lblProjet_1.setText("Projet n°"+projet.getId());
			lblNomprojet.setText(projet.libelle);
		
			JLabel label = new JLabel(projet.getEtatLibelle());
			label.setBounds(170, 62, 98, 14);
			toReturn.add(label);
			
			JLabel label_1 = new JLabel(projet.getNombreJour(DAOActiviteJ)+" jrs");
			label_1.setBounds(170, 87, 98, 14);
			toReturn.add(label_1);
			
			JLabel label_2 = new JLabel(projet.getNombreEmploye(DAORapportPMensuel) +"");
			label_2.setBounds(170, 112, 98, 14);
			toReturn.add(label_2);
			
			JLabel label_3 = new JLabel(projet.getNombreFrais(DAOActiviteJ)+" €");
			label_3.setBounds(372, 85, 98, 14);
			toReturn.add(label_3);
			
			JLabel label_4 = new JLabel(projet.getNombreKm(DAOActiviteJ)+ " Km");
			label_4.setBounds(372, 110, 98, 14);
			toReturn.add(label_4);
			scrollPane.setViewportView(makeTableDetails());
			if (projet.getEtat()==0 & user.getNiveauAcces()==0){
				btnTerminer.setEnabled(true);
			}
		}
		
		
		
		return toReturn;		
	}
	private JPanel buildViewForRapportMensuel(){
		JPanel panel = new JPanel();
		panel.setLayout(null);
		String tempB ="";
		
		
		lblActivitJournalire.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblActivitJournalire.setBounds(10, 11, 241, 25);
		tempB="Rapport mensuel n°" + Integer.toString(idRapportSelected);
		lblActivitJournalire.setText(tempB);
		panel.add(lblActivitJournalire);
		lblNomPrnom.setHorizontalTextPosition(SwingConstants.LEFT);
		tempB = ((Employe)HSallEmploye.get(idEmployeSelected)).getNom() + " " + ((Employe)HSallEmploye.get(idEmployeSelected)).getPrenom();
		lblNomPrnom.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNomPrnom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNomPrnom.setBounds(245, 11, 209, 30);
		lblNomPrnom.setText(tempB);
		panel.add(lblNomPrnom);
		
		JLabel lblPeriode = new JLabel("Période :");
		lblPeriode.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPeriode.setBounds(93, 75, 76, 14);
		panel.add(lblPeriode);
		
		JLabel lblKmParourus = new JLabel("Kms  mensuel :");
		lblKmParourus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKmParourus.setBounds(56, 100, 113, 14);
		panel.add(lblKmParourus);
		
		JLabel lblFrais = new JLabel("Frais mensuel :");
		lblFrais.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFrais.setBounds(66, 125, 103, 14);
		panel.add(lblFrais);
		String temp = cd.intToMois[((RapportPMensuel)allRapportFromSelectedEmploye.get(idRapportSelected)).getMois()-1] 
				+ " "
				+Integer.toString(((RapportPMensuel)allRapportFromSelectedEmploye.get(idRapportSelected)).getAnnee());
		JLabel label_1 = new JLabel(temp);
		label_1.setBounds(205, 75, 103, 14);
		panel.add(label_1);
		temp = Float.toString(DAOActiviteJ.getSumKmByRapport(idRapportSelected)) + " Km";
		JLabel label_2 = new JLabel(temp);
		label_2.setBounds(205, 100, 103, 14);
		panel.add(label_2);
		temp = Float.toString(DAOActiviteJ.getSumFraisByRapport(idRapportSelected))+ "€";
		JLabel label_3 = new JLabel(temp);
		label_3.setBounds(205, 125, 103, 14);
		panel.add(label_3);
		Vector<float[]> tempV = DAOActiviteJ.getDistinctProjetsByRapport(idRapportSelected);
		
		for (int i=0; i<tempV.size(); i++){
			temp = allProjetHT.get(((int)tempV.get(i)[0])) + "  "
				+tempV.get(i)[1]+ " jours "
				+tempV.get(i)[2]+ " Km  "
				+tempV.get(i)[3]+ " € ";
		}
		JLabel label_4 = new JLabel(temp);
		label_4.setBounds(326, 150, 209, 14);
		//panel.add(label_4);
		
		String titleB[]={"Projet", "Nombre de jour", "Km parcourus", "Frais"};
		Object[][] tmpArray=new Object[tempV.size()][4];
		for (int i=0; i<tempV.size(); i++){
			tmpArray[i][0]=((int)tempV.get(i)[0]) +" - " + allProjetHT.get(((int)tempV.get(i)[0]));
			tmpArray[i][1]= tempV.get(i)[1];
			tmpArray[i][2]= tempV.get(i)[3];
			tmpArray[i][3]= tempV.get(i)[2];
		}
		table = new JTable(tmpArray, titleB);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(27, 150, 419, 126);
		panel.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("Status :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(123, 50, 46, 14);
		panel.add(lblNewLabel);
		
		temp = cd.intToStatus[((RapportPMensuel) allRapportFromSelectedEmploye.get(idRapportSelected)).getStatus()];
		btnNewButton.addActionListener(new VerifListener());
		btnNewButton.setBounds(343, 46, 89, 23);
		if (niveauAcces==0){
			panel.add(btnNewButton);
		}
		
		
		JLabel lblStatus = new JLabel(temp);
		if (((RapportPMensuel) allRapportFromSelectedEmploye.get(idRapportSelected)).getStatus()==0){
			lblStatus.setForeground(Color.RED);
			btnNewButton.setEnabled(true);
		}else if(((RapportPMensuel) allRapportFromSelectedEmploye.get(idRapportSelected)).getStatus()==1){
			lblStatus.setForeground(Color.BLUE);
			btnNewButton.setEnabled(false);
		}
		
		lblStatus.setBounds(205, 50, 103, 14);
		panel.add(lblStatus);
		

		
		
		
		
	
		
	

		
		
		return panel;
	}
	private JPanel buildViewForActiviteJ() {

		JPanel panel = new JPanel();
		
		panel.setLayout(null);
		
		
		lblMoisAnnee.setBounds(240, 251, 63, 14);
		panel.add(lblMoisAnnee);
		
		
		lblActivitJournalire.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblActivitJournalire.setBounds(10, 11, 241, 25);
		panel.add(lblActivitJournalire);
		lblNomPrnom.setHorizontalTextPosition(SwingConstants.LEFT);
		
		lblNomPrnom.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNomPrnom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNomPrnom.setBounds(245, 11, 209, 30);
		panel.add(lblNomPrnom);
		
		JLabel lblDate = new JLabel("Date :");
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDate.setBounds(86, 72, 76, 14);
		panel.add(lblDate);
		
		JLabel lblProjet = new JLabel("Projet :");
		lblProjet.setHorizontalAlignment(SwingConstants.RIGHT);
		lblProjet.setBounds(86, 124, 76, 14);
		panel.add(lblProjet);
		
		JLabel lblKmParourus = new JLabel("Km parourus :");
		lblKmParourus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKmParourus.setBounds(49, 152, 113, 14);
		panel.add(lblKmParourus);
		
		JLabel lblFrais = new JLabel("Frais :");
		lblFrais.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFrais.setBounds(116, 177, 46, 14);
		panel.add(lblFrais);
		
		txtKmParcourus = new JTextField();
		txtKmParcourus.setHorizontalAlignment(SwingConstants.RIGHT);
		txtKmParcourus.setBounds(223, 152, 103, 20);
		panel.add(txtKmParcourus);
		txtKmParcourus.setColumns(10);
		
		txtFrais = new JTextField();
		txtFrais.setHorizontalAlignment(SwingConstants.RIGHT);
		txtFrais.setBounds(223, 177, 103, 20);
		panel.add(txtFrais);
		txtFrais.setColumns(10);
		
		JLabel lblKm = new JLabel("Km");
		lblKm.setBounds(336, 155, 46, 14);
		panel.add(lblKm);
		
		JLabel label = new JLabel("€");
		label.setBounds(336, 180, 46, 14);
		panel.add(label);
		
		txtJour = new JTextField();
		txtJour.setText("Jour");
		txtJour.setHorizontalAlignment(SwingConstants.RIGHT);
		txtJour.setBounds(223, 72, 46, 20);
		panel.add(txtJour);
		txtJour.setColumns(10);
		
		
		JCBMois.setBounds(224, 97, 141, 20);
		
		panel.add(JCBMois);
		
		for (int i=0; i<allProjetName.size();i++){
			//JCBProjet.addItem(allProjetName.get(i));
			JCBProjet.addItem(allProjet.get(i));
		}
		//JCBProjet.
		//JCBProjet.setEnabled(false);
		JCBProjet.setBounds(223, 127, 133, 20);
		panel.add(JCBProjet );
		
		JLabel lblRegistre = new JLabel("Registre :");
		lblRegistre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRegistre.setBounds(86, 97, 76, 14);
		panel.add(lblRegistre);


		
		return panel;
	}
	private JPanel buildViewForEmploye() {

		JPanel panel = new JPanel();
		
		panel.setLayout(null);
		
		
		lblMoisAnnee.setBounds(240, 251, 63, 14);
		panel.add(lblMoisAnnee);
		String val = "";
		val= "Employe n°" + idEmployeSelected;
		lblActivitJournalire.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblActivitJournalire.setBounds(10, 11, 241, 25);
		lblActivitJournalire.setText(val);
		panel.add(lblActivitJournalire);
		lblNomPrnom.setHorizontalTextPosition(SwingConstants.LEFT);
		val = ((Employe)HSallEmploye.get(idEmployeSelected)).getNom() + " " + ((Employe)HSallEmploye.get(idEmployeSelected)).getPrenom();
		lblNomPrnom.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNomPrnom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNomPrnom.setBounds(245, 11, 209, 30);
		lblNomPrnom.setText(val);
		panel.add(lblNomPrnom);
		
		JLabel lblDate = new JLabel("Première activité :");
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDate.setBounds(30, 72, 132, 14);
		panel.add(lblDate);
		
		JLabel lblJourTravaill = new JLabel("Jours travaillés  :");
		lblJourTravaill.setHorizontalAlignment(SwingConstants.RIGHT);
		lblJourTravaill.setBounds(40, 97, 122, 14);
		panel.add(lblJourTravaill);
		
		JLabel lblKmsParcourusMoyen = new JLabel("Kms moyen :");
		lblKmsParcourusMoyen.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKmsParcourusMoyen.setBounds(30, 122, 132, 14);
		panel.add(lblKmsParcourusMoyen);
		JLabel lblFraisMoyen = new JLabel("Frais moyen :");
		lblFraisMoyen.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFraisMoyen.setBounds(30, 147, 132, 14);
		panel.add(lblFraisMoyen);
		
		int[] tempb = DAORapportPMensuel.findFirstRegforEmploye(idEmployeSelected);
		
		val= tempb[0] + " " +cd.intToMois[tempb [1]-1];
		JLabel label = new JLabel(val);
		label.setBounds(205, 72, 171, 14);
		panel.add(label);
		val = DAOActiviteJ.getCountActiviteJ(idEmployeSelected)+ " ";
		JLabel label_1 = new JLabel(val);
		label_1.setBounds(205, 97, 171, 14);
		panel.add(label_1);
		val = DAOActiviteJ.findAverageKm(idEmployeSelected) + "";
		JLabel label_2 = new JLabel(val);
		label_2.setBounds(205, 122, 171, 14);
		panel.add(label_2);
		val = DAOActiviteJ.findAverageFrais(idEmployeSelected) + "";
		JLabel label_3 = new JLabel(val);
		label_3.setBounds(205, 147, 171, 14);
		panel.add(label_3);
		

		

		

		
		return panel;
	}
	private JTree buildTree(){
		//Creation d'une racine
		Vector <Projet> projetEnCours=new Vector<Projet>();
		Vector <Projet> projetTermine=new Vector<Projet>();
		for (int p=0; p<allProjet.size();p++){
			if (allProjet.get(p).getEtat()==0){
				projetEnCours.addElement(allProjet.get(p));
			}else{
				projetTermine.addElement(allProjet.get(p));
			}

		}
		DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Registre Employés                  ");
		DefaultMutableTreeNode nodeEnCours = new DefaultMutableTreeNode("Projet en cours");
		for(int p =0; p<projetEnCours.size();p++){
			DefaultMutableTreeNode temp= new DefaultMutableTreeNode(projetEnCours.get(p));
			
			Vector<Employe> employes=projetEnCours.get(p).getAllEmploye(allActiviteJ, AllRapport, allEmploye);
			
			for (Employe employe :employes){
				DefaultMutableTreeNode tempBis=new DefaultMutableTreeNode(employe);
				
				Vector<RapportPMensuel> rapports = projetEnCours.get(p).getAllRapport(allActiviteJ, AllRapport);
				for(RapportPMensuel rapport:rapports){
					if (rapport.getEmploye()==employe){
						DefaultMutableTreeNode tempTer=new DefaultMutableTreeNode(rapport);
						Vector<ActiviteJ> acts =projetEnCours.get(p).getAllActivite(allActiviteJ);
						for (ActiviteJ act:acts){
							if (act.getIdRapport()==rapport.getId()){
								act.SetDate(allRapportFromSelectedEmploye);
								DefaultMutableTreeNode tempQuarte=new DefaultMutableTreeNode(act);
								//tempTer.add(tempQuarte);
							}
						}
						//tempBis.add(tempTer);
					}
				}
				//temp.add(tempBis);
			}
			nodeEnCours.add(temp);
		}
		
		DefaultMutableTreeNode nodeTermine = new DefaultMutableTreeNode("Projet Termine");
		for(int p =0; p<projetTermine.size();p++){
			DefaultMutableTreeNode temp= new DefaultMutableTreeNode(projetTermine.get(p));
			Vector<Employe> employes=projetTermine.get(p).getAllEmploye(allActiviteJ, AllRapport, allEmploye);
			for (Employe employe :employes){
				DefaultMutableTreeNode tempBis=new DefaultMutableTreeNode(employe);
				
				Vector<RapportPMensuel> rapports = projetTermine.get(p).getAllRapport(allActiviteJ, AllRapport);
				for(RapportPMensuel rapport:rapports){
					if (rapport.getEmploye()==employe){
						DefaultMutableTreeNode tempTer=new DefaultMutableTreeNode(rapport);
						
						Vector<ActiviteJ> acts =projetTermine.get(p).getAllActivite(allActiviteJ);
						for (ActiviteJ act:acts){
							if (act.getIdRapport()==rapport.getId()){
								act.SetDate(allRapportFromSelectedEmploye);
								DefaultMutableTreeNode tempQuarte=new DefaultMutableTreeNode(act);
								//tempTer.add(tempQuarte);
							}
						}
						//tempBis.add(tempTer);
					}
				}
				//temp.add(tempBis);
			}
			nodeTermine.add(temp);
		}
		racine.add(nodeEnCours);
		racine.add(nodeTermine);
		 
		  arbre = new JTree(racine);
		  arbre.addTreeSelectionListener(new SelectionListener());
		  return arbre;
		  //Que nous plaçons sur le ContentPane de notre JFrame à l'aide d'un scroll 
	}
	private void changeViewForProjet(){
		remove(panelToMake);
		panelToMake = buildViewForProjet();
		
		add(panelToMake, BorderLayout.CENTER);
		revalidate();
		repaint();
		
	}
		
	private JTable makeTableDetails(){
		JTable toReturn=null;
		String[] titre = {
			"     Employe     ", "Date", "frais", "km"
		};
		Vector<ActiviteJ> allActivite = projet.getAllActivite(allActiviteJ);
		Object[][]table = null;
		if (allActivite!=null){
			table=new Object[allActivite.size()][4];
			for (int i=0; i<allActivite.size();i++){
				RapportPMensuel rapport = (RapportPMensuel) allRapportFromSelectedEmploye.get(allActivite.get(i).getIdRapport());
				Employe emp=rapport.getEmploye();
				table[i][0]=emp.getId() + " - "+ emp.getNom() + " "+ emp.getPrenom();
				table[i][1]=allActivite.get(i).getJour() + "/"+rapport.getMois()+"/"+rapport.getAnnee();
				if (user.getNiveauAcces()==0){
					
					table[i][2]=allActivite.get(i).getFrais();
					table[i][3]=allActivite.get(i).getKm();
				}
			}
			toReturn=new JTable(table, titre);
			toReturn.getColumnModel().getColumn(0).setPreferredWidth(200);
		}

		
		return toReturn;
	}
	class VerifListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			DAORapportPMensuel.updateStatus(idRapportSelected);
		}
		
	}
	class CreateActivite implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			DAOActiviteJ.insertNewAct(Integer.parseInt(txtJour.getText()), ((RapportPMensuel)JCBMois.getSelectedItem()).getId(), ((Projet)JCBProjet.getSelectedItem()).getId(), Integer.parseInt(txtFrais.getText()), Integer.parseInt(txtKmParcourus.getText()));
			Controller.drawNewView();
		}
		
	}
	class EditionListener implements ActionListener{
		

		
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			((ActiviteJ)hsActiviteJ.get(idActiviteJ)).update(Integer.parseInt(txtJour.getText()), ((Projet)JCBProjet.getSelectedItem()).getId(), Integer.parseInt(txtFrais.getText()), Integer.parseInt(txtKmParcourus.getText()));
			Controller.drawNewView();
		}
		
		
	}
	class TerminateAL implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			DAOProjet.terminate(projet.getId());
			
		}
		
	}
	class SelectionListener implements TreeSelectionListener {

		  public void valueChanged(TreeSelectionEvent se) {
		    JTree tree = (JTree) se.getSource();
		    int id = 0;
		    TreePath tp = tree.getSelectionPath();
		    projet = (Projet) ((DefaultMutableTreeNode)tp.getLastPathComponent()).getUserObject();
		    changeViewForProjet();
		}
	}
}
	
