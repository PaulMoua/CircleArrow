package view;


import javax.swing.JPanel;

import controleur.Controller;
import metier.Employe;
import view.includes.Header;

import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu extends JPanel{
	
	private String purl;
	private String img;
	private FenPrincipale FA;
	private Color bgColor;
	private JPanel panel = new JPanel();
	/* Creation de l'image a la volée */
	private ImageIcon image(String img){
		purl = "/view/img/icons/";
		ImageIcon image = new ImageIcon(this.getClass().getResource(purl+img));
		return image;
	}
	
	public Menu(FenPrincipale PFA){
		FA = PFA;
		
		String[] listName = new String[5];
		listName[0] = "Messagerie";
		listName[1] = "Registre des Employés";
		listName[2] = "Registre des Projets";
		listName[4] = "Quitter";
		setLayout(null);
		
		panel.setBounds(0, 0, 150, 440);
		panel.setBackground(bgColor);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setIcon(new ImageIcon(Menu.class.getResource("/view/img/logo.png")));
		lblNewLabel.setBounds(22, 144, 241, 154);
		panel.add(lblNewLabel);
		
		/* LABEL NOM A COTER DES BUTTONS*/
		int pos = 15;
		for(int i = 0; i < 5; i++){
			String s = listName[i];
			JLabel t = new JLabel(s);
			t.setBounds(290, pos, 260, 75);
			t.setFont(new Font("Thoma", Font.BOLD, 18));
			pos += 85;
			add(t);
		}

		/*
		 * Bouton compte rendu qui renvoi la vue 
		 */
		JButton compteRendusBtn = new JButton(image("comtpe-rendu.png"));
		compteRendusBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Clic) {
				Controller.setAnDrawNewView("Compte-rendus");
			}
		});
		compteRendusBtn.setBounds(200, 15, 70, 70);
		add(compteRendusBtn);
		compteRendusBtn.setFocusPainted(false);
		
		/*
		 * Bouton Visiteurs qui renvoi la vue 
		 */
		JButton visiteursBtn = new JButton(image("visiteurs.png"));
		visiteursBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.setAnDrawNewView("Gestion des visiteurs");
			}
		});
		visiteursBtn.setBounds(200, 100, 70, 70);
		add(visiteursBtn);
		visiteursBtn.setFocusPainted(false);
		
		/*
		 * Bouton Practiciens qui renvoi la vue 
		 */
		JButton practiciensBtn = new JButton(image("projet.png"));
		practiciensBtn.setBounds(200, 185, 70, 70);
		add(practiciensBtn);
		practiciensBtn.setFocusPainted(false);
		practiciensBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				Controller.setAnDrawNewView("Gestion des praticiens");
			}
		});
		
		
		
		/*
		 * Bouton Quitter qui quitte le programme
		 */
		JButton btnQuitter = new JButton(image("deco.png"));
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent quitter) {
				System.exit(0);
			}
		});
		btnQuitter.setBounds(200, 355, 70, 70);
		add(btnQuitter);
		btnQuitter.setFocusPainted(false);
		/* FIN DE BOUTON QUITTER */
		
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
		panel.setBackground(bgColor);
		this.repaint();
		this.revalidate();
	}


}
