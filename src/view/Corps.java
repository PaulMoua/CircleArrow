package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;

import view.includes.Header;
import javax.swing.UIManager;

import DAO.DAOEmploye;
import DAO.DAOFactory;
import controleur.Controller;
import metier.Employe;
import metier.Visiteur;

public class Corps extends JPanel {
	private Color entry_font = new Color(238, 241, 246);
	private JTextField ndc_entry;
	private JPasswordField mdp_entry;
	private FenPrincipale FA;
	// Constructeur de la classe Corps
	public Corps(){
		init();
	}
	public Corps(FenPrincipale PFA){
		init();
		FA = PFA;
	}
	
	private void init(){
			//----------/ Zone de connexion
			JPanel connect = this;
			connect.setBounds(100,100,720,468);
			setLayout(new BorderLayout(0, 0));
			
			JPanel panel_4 = new JPanel();
			add(panel_4, BorderLayout.CENTER);
			panel_4.setLayout(null);
			
			ndc_entry = new JTextField();
			ndc_entry.setBounds(378, 157, 150, 20);
			ndc_entry.setColumns(20);
			panel_4.add(ndc_entry);

			
			JPanel panel = new JPanel();
			panel.setBackground(new Color(66, 76, 88));
			panel.setBounds(0, 0, 134, 468);
			panel_4.add(panel);
			connect.setBackground(Color.WHITE);
			
			/* LOGO */
			panel.setLayout(null);
			JLabel logo = new JLabel();
			logo.setIcon(new ImageIcon(Corps.class.getResource("/view/img/logo.png")));
			logo.setBounds(20, 167, 182, 124);
			panel.add(logo);
			
			JPanel Connexion = new JPanel();
			Connexion.setBackground(new Color(206, 206, 206));
			Connexion.setBounds(282, 86, 320, 257);
			panel_4.add(Connexion);
			Connexion.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Nom de Compte :");
			lblNewLabel.setForeground(Color.BLACK);
			lblNewLabel.setBounds(58, 28, 160, 32);
			Connexion.add(lblNewLabel);
			lblNewLabel.setIcon(new ImageIcon(Corps.class.getResource("/view/img/icons/auth/user.png")));
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			JLabel lblNewLabel_1 = new JLabel("  Mot de Passe :");
			lblNewLabel_1.setForeground(Color.BLACK);
			lblNewLabel_1.setBounds(58, 96, 170, 32);
			Connexion.add(lblNewLabel_1);
			lblNewLabel_1.setIcon(new ImageIcon(Corps.class.getResource("/view/img/icons/auth/password.png")));
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			mdp_entry = new JPasswordField();
			mdp_entry.setBounds(98, 139, 152, 20);
			Connexion.add(mdp_entry);
			
			JButton btnNewButton = new JButton("Connexion");
			btnNewButton.setBounds(98, 208, 150, 23);
			Connexion.add(btnNewButton);
			btnNewButton.addActionListener(new Connection());
			
			
			}
			
			
		public class Connection implements ActionListener{
			public void actionPerformed(ActionEvent clic){
				System.out.println("Ok");
				Employe toReturn = null;
				DAOEmploye tEmploye = (DAOEmploye) DAOFactory.getDAOEmploye();
				try {
					toReturn = tEmploye.check(ndc_entry.getText(), String.valueOf(mdp_entry.getPassword()));
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				if (toReturn!=null){
					Controller.setUser(toReturn);
					Controller.setAnDrawNewView("Menu");
					//System.out.println(toReturn);	
				}

			
		}
			
	}
}
