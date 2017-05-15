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

public class V_activiteJ extends JPanel {
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
	JButton edit_button = new JButton("Editer");
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
				add_button = new JButton("Créer un registre mensuel");
				add_button.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						NewRapport dialog = new NewRapport(allEmploye);
						
					}
					
				});
				add_button.setHorizontalAlignment(SwingConstants.RIGHT);
		  		
				break;
		  	case 1:
		  		allEmploye.add(user);
		  		HSallEmploye = cd.vectorToHastableByUnique(allEmploye, "id");
		  		for(int i =0; i<allEmploye.size();i++){
		  			System.out.println("Hollo");
		  			AllRapport.addAll(DAORapportPMensuel.findByEmploye(allEmploye.get(i)));
		  		}
		  		allRapportFromSelectedEmploye=cd.vectorToHastableByUnique(AllRapport,"id");
		  		add_button = new JButton("Enregistrer une nouvelle activité");
		  		add_button.addActionListener(new CreateActivite());
				add_button.setHorizontalAlignment(SwingConstants.RIGHT);
				JCBMois=new JComboBox(AllRapport);
		  		break;
		  	case 2:
		  		add_button = new JButton("Enregistrer une nouvelle activité");
				add_button.setHorizontalAlignment(SwingConstants.RIGHT);
				
				
		  		allEmploye.add(user);
		  		break;
		  		
		  }
    }

	public V_activiteJ(FenPrincipale menu) {

		
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


		
		search_zone.add(add_button);
		
		// Marge Gauche
		
		JPanel left_border_zone = new JPanel();
		FlowLayout fl_left_border_zone = (FlowLayout) left_border_zone.getLayout();
		fl_left_border_zone.setHgap(15);
		left_border_zone.setBackground(Color.LIGHT_GRAY);
		//add(left_border_zone, BorderLayout.WEST);
		
		// Marge Droite
		
		JPanel right_border_zone = new JPanel();
		FlowLayout fl_right_border_zone = (FlowLayout) right_border_zone.getLayout();
		fl_right_border_zone.setHgap(15);
		right_border_zone.setBackground(Color.LIGHT_GRAY);
		add(right_border_zone, BorderLayout.EAST);
		
		// Zone d'actions sur les visiteurs
		
		JPanel actions_zone = new JPanel();
		actions_zone.setLayout(new FlowLayout(FlowLayout.TRAILING, 30, 15));
		actions_zone.setBackground(Color.LIGHT_GRAY);
		add(actions_zone, BorderLayout.SOUTH);
		
		
		edit_button = new JButton("Editer");
		edit_button.addActionListener(new EditionListener());
		
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
		
		actions_zone.add(edit_button);
		
		JButton del_button = new JButton("Supprimer");
		del_button.setEnabled(false);
		actions_zone.add(del_button);
		
		
		
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
		panelToMake = buildViewForActiviteJ();
		//panelToMake = buildViewForRapportMensuel();
		//panelToMake = buildViewForEmploye();
		result_zone.setViewportView(treeToMake);
		add(panelToMake, BorderLayout.CENTER);

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
		table.setAutoCreateRowSorter(true);
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
		edit_button.setEnabled(false);

		
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
		
		  for (int p=0; p<allActiviteJ.size();p++){
			  hsActiviteJ.put(((ActiviteJ) allActiviteJ.get(p)).getId(),((ActiviteJ) allActiviteJ.get(p)));
		  }
		  DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Registre Employés                  ");
		  DAOEmploye = (DAOEmploye) DAOFactory.getDAOEmploye();

		  
		  //Nous allons ajouter des branches et des feuilles à notre racine
		  for(int i = 0; i < allEmploye.size(); i++){
			    DefaultMutableTreeNode rep = new DefaultMutableTreeNode(allEmploye.get(i).getId() +" - " + allEmploye.get(i).getNom() +" " +allEmploye.get(i).getPrenom());
			    
			    ResultSet rsRapportMensuelEmploye = DAORapportPMensuel.findByEmploye(allEmploye.get(i).getId());

			    
			    //Création des nodes années
			    Vector <Integer> annee= new Vector<Integer>();
			    try {
					while(rsRapportMensuelEmploye.next()){
						boolean doIt = true;
						for (int j=0; j<annee.size();j++){
							if (rsRapportMensuelEmploye.getInt("anneeRapportPreviMensuel")==annee.get(j)){
								doIt = false;
								break;
							}
						}
						if (doIt){
							annee.add(rsRapportMensuelEmploye.getInt("anneeRapportPreviMensuel"));
							System.out.println(rsRapportMensuelEmploye.getInt("anneeRapportPreviMensuel"));
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  //Création d'une hastable faisant le lien entre année et mois
			    Hashtable anneeMois=new Hashtable();
			    System.out.println("Creation de Hashtable anneeMois : essai");
			    for (int j=0; j<annee.size();j++){
			    	anneeMois.put(annee.get(j), new Vector<int[]>());
			    }
			    
			    try {
					rsRapportMensuelEmploye.beforeFirst();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    try {
					while(rsRapportMensuelEmploye.next()){
						//On complète la hastable avec les mois/idrapport
						System.out.println("Creation de Hashtable anneeMois : hasNext!");
						System.out.println(rsRapportMensuelEmploye.getInt("moisRapportPreviMensuel"));
						System.out.println(rsRapportMensuelEmploye.getInt("anneeRapportPreviMensuel"));
						int[] toAdd=new int [2];
						toAdd[0]=rsRapportMensuelEmploye.getInt("moisRapportPreviMensuel");
						toAdd[1]=rsRapportMensuelEmploye.getInt("idRapportPreviMensuel");
						((Vector) anneeMois.get(rsRapportMensuelEmploye.getInt("anneeRapportPreviMensuel"))).add(toAdd);
						System.out.println(anneeMois.get(rsRapportMensuelEmploye.getInt("anneeRapportPreviMensuel")));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    System.out.println("Creation de Hashtable anneeMois : Ok !");
			    System.out.println(anneeMois);
			    
			    //Création d'itérateur pour la hashTable
			    Set<Integer> keys = anneeMois.keySet();
			    Iterator<Integer> itr = keys.iterator();
			 
			    System.out.print("Vérification de itr : ");
			    System.out.println(itr);
			   
			    while (itr.hasNext()) {
			    	int anneeStr = itr.next();
			    	DefaultMutableTreeNode rep3 =null;
			    	for (int j=0; j<((Vector<int[]>) anneeMois.get(anneeStr)).size();j++){
			    		
			    		System.out.println(Integer.toString(((Vector<int[]>) anneeMois.get(anneeStr)).get(j)[1]));
			    		rep3 = new DefaultMutableTreeNode(((Vector<int[]>) anneeMois.get(anneeStr)).get(j)[1] +" - "+ Integer.toString(anneeStr)+" "+intToMois[((Vector<int[]>) anneeMois.get(anneeStr)).get(j)[0]-1]);
			    		for (int k=0; k<allActiviteJ.size();k++){
			    			if (allActiviteJ.get(k).getIdRapport()==((Vector<int[]>) anneeMois.get(anneeStr)).get(j)[1]){
			    				DefaultMutableTreeNode rep4 = new DefaultMutableTreeNode(allActiviteJ.get(k).getId() +" - "+ allActiviteJ.get(k).getJour()+"/"+ Integer.toString(((Vector<int[]>) anneeMois.get(anneeStr)).get(j)[0])+ "/"+Integer.toString(anneeStr));
			    				rep3.add(rep4);
			    			}
			    		}
			    		rep.add(rep3);
			    	}
				      
				    
			    	
			    }
			    
			    //On ajoute la feuille ou la branche à la racine
			    racine.add(rep);
		    
		  }
		  //Nous créons, avec notre hiérarchie, un arbre
		  arbre = new JTree(racine);
		  arbre.addTreeSelectionListener(new SelectionListener());
		  return arbre;
		  //Que nous plaçons sur le ContentPane de notre JFrame à l'aide d'un scroll 
	}
    private void changeViewToRapportPMensuel(){
    	System.out.println("changeViewToRapportPMensuel():");
    	remove(panelToMake);
		panelToMake = buildViewForRapportMensuel();
		add(panelToMake, BorderLayout.CENTER);
		edit_button.setEnabled(false);
		revalidate();
		repaint();
		
    }
    private void changeViewToEmploye(){
    	System.out.println("changeViewToEmploye:");
    	remove(panelToMake);
		panelToMake = buildViewForEmploye();
		add(panelToMake, BorderLayout.CENTER);
		revalidate();
		repaint();
		edit_button.setEnabled(false);
    }
	private void changeView(ActiviteJ pActiviteJ, String pNomPrenom, String pMois, String pAnnee){
		
		System.out.println(allProjetHT.get(pActiviteJ.getIdProjet()));
		JCBProjet.setSelectedItem(((Projet) allProjetHT.get(pActiviteJ.getIdProjet())).getLibelle());
		//System.out.println(((Projet) allProjetHT.get(Integer.toString(pActiviteJ.getIdProjet()))).getLibelle());
		JCBMois.setSelectedItem(pMois.trim());
		txtKmParcourus.setText(Integer.toString(pActiviteJ.getKm()));
		txtFrais.setText(Integer.toString(pActiviteJ.getFrais()));
		txtJour.setText(Integer.toString(pActiviteJ.getJour()));
		lblNomPrnom.setText(pNomPrenom);
		
		JCBMois.setSelectedItem(pMois);
		
		lblActivitJournalire.setText("Activite journalière n°" +Integer.toString(pActiviteJ.getId()));
		
		jour=Integer.parseInt(txtJour.getText().trim());
		mois=Arrays.asList(intToMois).indexOf(JCBMois.getSelectedItem());
		//annee=Integer.parseInt(textField.getText().trim());
		km=Integer.parseInt(txtKmParcourus.getText());
		frais =Integer.parseInt(txtFrais.getText().trim());
		projet=(Projet)JCBProjet.getSelectedItem();
		idProjet=projet.getId();
	}
	private void changeView(){
		remove(panelToMake);
		panelToMake = buildViewForActiviteJ();

		add(panelToMake, BorderLayout.CENTER);
		revalidate();
		repaint();
		
		ActiviteJ aj =(ActiviteJ) hsActiviteJ.get(idActSelected);
		System.out.println("idRapportSelected:"+idRapportSelected);
		JCBMois.removeAllItems();
		
		//Enumeration enu = allRapportFromSelectedEmploye.keys();

		
		
		RapportPMensuel rap= (RapportPMensuel) allRapportFromSelectedEmploye.get(idRapportSelected);
		System.out.println("MyRap :" +rap.getId());
		Employe emp = (Employe) HSallEmploye.get(idEmployeSelected);
		System.out.println("MyEmp :" +emp.getId());
		Vector<RapportPMensuel> rapParEmp =DAORapportPMensuel.findByEmploye(emp);
		
		for (int i=0; i<rapParEmp.size(); i++){
			JCBMois.addItem(rapParEmp.get(i));
		}
		Hashtable hsTemp = cd.vectorToHastableByUnique(rapParEmp, "id");
		//txtProjet.setText(Integer.toString(aj.getIdProjet()));
		System.out.println("Le projet a affiché");
		System.out.println(((Projet) allProjetHT.get(aj.getIdProjet())).getId());
		System.out.println(((Projet) allProjetHT.get(aj.getIdProjet())).getLibelle());
		JCBProjet.setSelectedItem( allProjetHT.get(aj.getIdProjet()));
		
		//System.out.println(((Projet) allProjetHT.get(Integer.toString(pActiviteJ.getIdProjet()))).getLibelle());
		JCBMois.setSelectedItem(hsTemp.get(idRapportSelected));
		//JCBProjet.setSelectedItem(((Projet) allProjetHT.get(pActiviteJ.getIdProjet())).getLibelle());
		txtKmParcourus.setText(Integer.toString(aj.getKm()));
		txtFrais.setText(Integer.toString(aj.getFrais()));
		txtJour.setText(Integer.toString(aj.getJour()));
		lblNomPrnom.setText(emp.getNom()+" "+emp.getPrenom());
		
		//JCBMois.setSelectedItem(cd.intToMois[rap.getMois()-1]);
		//textField.setText(Integer.toString(rap.getAnnee()));
		lblActivitJournalire.setText("Activite journalière n°" +Integer.toString(aj.getId()));
		
		jour=Integer.parseInt(txtJour.getText().trim());
		mois=Arrays.asList(intToMois).indexOf(JCBMois.getSelectedItem());
		//annee=Integer.parseInt(textField.getText().trim());
		km=Integer.parseInt(txtKmParcourus.getText());
		frais =Integer.parseInt(txtFrais.getText().trim());
		projet=(Projet)JCBProjet.getSelectedItem();
		idProjet=projet.getId();
		edit_button.setEnabled(true);
		if (rap.getStatus()==1){
			edit_button.setEnabled(false);
		}
		
	}
	class VerifListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			((RapportPMensuel) allRapportFromSelectedEmploye.get(idRapportSelected)).setStatus(1);
			DAORapportPMensuel.updateStatus(idRapportSelected);
			changeViewToRapportPMensuel();
		}
		
	}
	class CreateActivite implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println(txtJour.getText());
			if (txtJour.getText()!=null & !txtJour.getText().equals("Jour")){
				DAOActiviteJ.insertNewAct(Integer.parseInt(txtJour.getText()), ((RapportPMensuel)JCBMois.getSelectedItem()).getId(), ((Projet)JCBProjet.getSelectedItem()).getId(), Integer.parseInt(txtFrais.getText()), Integer.parseInt(txtKmParcourus.getText()));
				Controller.drawNewView();
			}else{
				remove(panelToMake);
				panelToMake = buildViewForActiviteJ();
				lblActivitJournalire.setText("Nouvelle activité");
				add(panelToMake, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
			
		}
		
	}
	class EditionListener implements ActionListener{
		

		
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			((ActiviteJ)hsActiviteJ.get(idActiviteJ)).update(Integer.parseInt(txtJour.getText()), ((Projet)JCBProjet.getSelectedItem()).getId(), Integer.parseInt(txtFrais.getText()), Integer.parseInt(txtKmParcourus.getText()));
			Controller.drawNewView();
		}
		
		
	}
	
	class SelectionListener implements TreeSelectionListener {

		  public void valueChanged(TreeSelectionEvent se) {
		    JTree tree = (JTree) se.getSource();
		    int id = 0;
		    TreePath tp = tree.getSelectionPath();
		    StringTokenizer defaultTokenizer = new StringTokenizer(tp.toString(), "[,]");
		    int j=0;
		    String nomPrenom="";
		    String mois="";
		    String annee="";
		    while (defaultTokenizer.hasMoreTokens())
		    {

		    	String str = defaultTokenizer.nextToken();
		    	System.out.println(str);
		    	if (j==1){
		    		nomPrenom=str;
		    	}
		    	if (j==2){
		    		annee =str;
		    	}
		    	if (j==3){
		    		
		        	mois = str;
		        	StringTokenizer tokenizer = new StringTokenizer(str,"-] ");
		        	boolean first =false;
				    while (tokenizer.hasMoreTokens())
				    {	
				    	String strBis = tokenizer.nextToken();
				    	
				    	if (!first){
				    		id=Integer.parseInt(strBis);
				    		first=true;
				    	}
				    }
		        }
		        j++;
		    }
		    
		    StringTokenizer tokenizer = new StringTokenizer(nomPrenom,"-] ");
		    idEmployeSelected = Integer.parseInt(tokenizer.nextToken());
		    if (j>2){
			    StringTokenizer tokenizerBis = new StringTokenizer(annee ,"-] ");
			    idRapportSelected = Integer.parseInt(tokenizerBis.nextToken());
			    StringTokenizer tokenizerTer = new StringTokenizer(nomPrenom,"-] ");
			    idActSelected= id;

		    //System.out.println(j);
		    //System.out.println(id +"//"+idEmployeSelected +"//"+ idRapportSelected +"//"+ idActSelected);
		    }
		    //ActiviteJ pActiviteJ, String pNomPrenom, String pMois, String pAnnee
		    idActiviteJ = id;
		    if (j==2){
		    	changeViewToEmploye();
		    }
		    if (j==3){
		    	changeViewToRapportPMensuel();
		    }
		    if (j==4){
		    	changeView();
		    }
		    
		}
	}
}
	
