package view.includes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import metier.Employe;

import java.awt.FlowLayout;

public class Header extends JPanel {
	/**
	 * Constructeur de l'entête.
	 * @param header_footer_font 
	 * @param ptitre
	 */
	
	private String titre;
	private Color header_footer_font;
	private JLabel lblNewLabel = new JLabel(" ");
	public Header(String ptitre){
		
		this.titre=ptitre;
	
		init();
	}
	
	private void init(){
		
		JPanel header = this;
		header.setBackground( new Color(66, 76, 88));
		setLayout(new BorderLayout(0, 0));
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		add(lblNewLabel, BorderLayout.WEST);
		
		//----------/ Texte de l'entete
		JLabel lbl_auth = new JLabel(titre);
		lbl_auth.setVerticalAlignment(SwingConstants.TOP);
		lbl_auth.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_auth.setFont(new Font("Tahoma", Font.BOLD, 17));
		lbl_auth.setForeground(Color.WHITE);
		lbl_auth.setBounds(249, 0, 185, 30);
		header.add(lbl_auth, BorderLayout.EAST);
		
	}

		public void setUserName(Employe user,Color color){
			System.out.println("setUserName " + user.getNom());
			lblNewLabel.setForeground(color);
			lblNewLabel.setText(user.getNiveauAccesLibelle() + " : "+user.getNom() + " "+user.getPrenom() );
			repaint();
			revalidate();
			
		}
	

}
