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
import DAO.DAOMessage;
import DAO.DAOFactory;
import DAO.DAOProjet;
import DAO.DAORapportPMensuel;
import controleur.Controller;
import metier.ActiviteJ;
import metier.Employe;
import metier.Message;
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
import javax.swing.JTextPane;

public class Messagerie extends JPanel {
	private JTable result_table;
	private JTree arbre;
	JScrollPane result_zone = new JScrollPane();
	private FenPrincipale FA;
	JButton edit_button = new JButton("Editer");
	Message message =  null;
	JButton btnEnvoyer = new JButton("Envoyer");
	private Employe user;
	private int niveauAcces=-1;
	private int idActiviteJ=-1;


    private JButton add_button;
    DAOEmploye DAOEmploye =  DAOFactory.getDAOEmploye();
    Vector<Employe> allEmploye=DAOEmploye.findAll();
    Hashtable HSallEmploye = cd.vectorToHastableByUnique(allEmploye, "id");
    DAOMessage DAOMessage =DAOFactory.getDAOMessage();


    
    JButton btnNewButton = new JButton("Verifier");
    JTree treeToMake =null;
    JPanel panelToMake=null;

    private JTextField textField;
    JTextPane txtpnMessage = new JTextPane();
    JComboBox comboBox = new JComboBox(allEmploye);


	public Messagerie(FenPrincipale menu) {

		
		FA = menu;
		user =Controller.getUser();
		niveauAcces = user.getNiveauAcces();
		System.out.println("Niveau : "+niveauAcces);

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
		
		
		btnEnvoyer.addActionListener(new SendAL());
		actions_zone.add(btnEnvoyer);
		
		
		edit_button = new JButton("Nouveau Message");
		edit_button.addActionListener(new SendingAL());
		
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
		panelToMake = buildViewForReading();

		result_zone.setViewportView(treeToMake);
		add(panelToMake, BorderLayout.CENTER);

	
		

	}
	private JPanel buildViewForReading(){
		JPanel toReturn = new JPanel();
		btnEnvoyer.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(18, 87, 456, 175);
		toReturn.add(scrollPane);
		
		JLabel lblMessage = new JLabel("");
		lblMessage.setVerticalAlignment(SwingConstants.TOP);
		lblMessage.setHorizontalAlignment(SwingConstants.LEFT);
		scrollPane.setViewportView(lblMessage);
		
		
		
		toReturn.setLayout(null);
		
		JLabel lblObjet = new JLabel("Objet :");
		lblObjet.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblObjet.setBounds(18, 11, 62, 21);
		toReturn.add(lblObjet);
		
		JLabel lblDestinataire = new JLabel("Destinataire :");
		lblDestinataire.setBounds(18, 62, 88, 14);
		toReturn.add(lblDestinataire);
		
		JLabel lblEmetteur = new JLabel("Emetteur :");
		lblEmetteur.setBounds(18, 39, 88, 14);
		toReturn.add(lblEmetteur);
		
		JLabel lblEmetteur_1 = new JLabel("");
		lblEmetteur_1.setBounds(129, 39, 152, 14);
		toReturn.add(lblEmetteur_1);
		
		JLabel lblDestinataire_1 = new JLabel("");
		lblDestinataire_1.setBounds(129, 62, 152, 14);
		toReturn.add(lblDestinataire_1);
		
		JLabel lblObjet_1 = new JLabel("");
		lblObjet_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblObjet_1.setBounds(104, 11, 415, 21);
		toReturn.add(lblObjet_1);
		if (message!=null){
			lblEmetteur_1.setText("" +HSallEmploye.get(message.getIdEmetteur()) ); 
			lblDestinataire_1.setText( "" +HSallEmploye.get(message.getIdDestinataire())); 
			lblObjet_1.setText(message.getObjet()); 
			lblMessage.setText(message.getMessage()); 
		}
		
		
		
		return toReturn;		
	}
	private JPanel buildViewForSending(){
		JPanel toReturn = new JPanel();
		btnEnvoyer.setEnabled(true);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(18, 87, 456, 175);
		toReturn.add(scrollPane);
		
		
		txtpnMessage.setText("Message");
		scrollPane.setViewportView(txtpnMessage);
		
		
		
		toReturn.setLayout(null);
		
		JLabel lblObjet = new JLabel("Objet :");
		lblObjet.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblObjet.setBounds(18, 11, 62, 21);
		toReturn.add(lblObjet);
		
		JLabel lblDestinataire = new JLabel("Destinataire :");
		lblDestinataire.setBounds(18, 62, 88, 14);
		toReturn.add(lblDestinataire);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.setBounds(138, 13, 325, 20);
		toReturn.add(textField);
		textField.setColumns(10);
		
		
		comboBox.setBounds(138, 59, 210, 20);
		toReturn.add(comboBox);

		
		return toReturn;		
	}
	private JTree buildTree(){
		//Creation d'une racine
		DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Message");
		Vector<Message> MessagesDestines = DAOMessage.findAllFromIdDestinataire(user.getId());
		DefaultMutableTreeNode MessagesReçus = new DefaultMutableTreeNode("Messages reçus");
		for (int i =0; i<MessagesDestines.size();i++){
			DefaultMutableTreeNode toAdd = new DefaultMutableTreeNode(MessagesDestines.get(i));
			MessagesReçus.add(toAdd);
		}
		Vector<Message> MessagesRecus = DAOMessage.findAllFromIdEmetteur(user.getId());
		DefaultMutableTreeNode MessagesEnvoyes = new DefaultMutableTreeNode("Messages envoyés");
		for (int i =0; i<MessagesRecus.size();i++){
			DefaultMutableTreeNode toAdd = new DefaultMutableTreeNode(MessagesRecus.get(i));
			MessagesEnvoyes.add(toAdd);
		}
		racine.add(MessagesReçus);
		racine.add(MessagesEnvoyes);
		  arbre = new JTree(racine);
		  arbre.addTreeSelectionListener(new SelectionListener());
		  return arbre;
		  //Que nous plaçons sur le ContentPane de notre JFrame à l'aide d'un scroll 
	}
	private void changeViewForReading(){
		remove(panelToMake);
		panelToMake = buildViewForReading();

		add(panelToMake, BorderLayout.CENTER);
		revalidate();
		repaint();
		
	}
	private void changeViewForSending(){
		remove(panelToMake);
		panelToMake = buildViewForSending();

		add(panelToMake, BorderLayout.CENTER);
		revalidate();
		repaint();
		
	}
	class SendingAL implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			changeViewForSending();
			
		}
		
	}
	class SendAL implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			DAOMessage.InsertMessage( ((Employe) comboBox.getSelectedItem()).getId(), user.getId(), txtpnMessage.getText(), textField.getText());
			Controller.setAnDrawNewView("Compte-rendus");
		}
		
	}

	

	class SelectionListener implements TreeSelectionListener {

		  public void valueChanged(TreeSelectionEvent se) {
		    JTree tree = (JTree) se.getSource();
		    int id = 0;
		    TreePath tp = tree.getSelectionPath();
		    message = (Message) ((DefaultMutableTreeNode)tp.getLastPathComponent()).getUserObject();
		    changeViewForReading();
		}
	}
}
	
