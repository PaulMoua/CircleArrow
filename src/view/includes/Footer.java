package view.includes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class Footer extends JPanel { 
	private Color header_footer_font = new Color(66, 76, 88);
	
	public Footer( ){
		init();
	}
	
	private void init(){
		//----------/ Zone du bas de page
		JPanel footer = this;
		
		footer.setBackground(header_footer_font);
		setLayout(new BorderLayout(0, 0));
		
		//----------/ Texte Droit du bas de page
		JLabel lbl_date = new JLabel("CircleArrow 2016/2017");
		lbl_date.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_date.setForeground(Color.WHITE);
		lbl_date.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_date.setBounds(307, 0, 87, 14);
		footer.add(lbl_date);
		
		//----------/ Texte Gauche du bas de page
		JLabel lbl_team = new JLabel("Diaby/Boudiaf/Moua/Siarri");
		lbl_team.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_team.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_team.setForeground(Color.WHITE);
		lbl_team.setBounds(0, 0, 169, 14);
		footer.add(lbl_team, BorderLayout.WEST);
	}

	public Color getHeader_footer_font() {
		return header_footer_font;
	}

	public void setHeader_footer_font(Color header_footer_font) {
		this.header_footer_font = header_footer_font;
		setBackground(header_footer_font);
		this.repaint();
		this.revalidate();
	}

}
