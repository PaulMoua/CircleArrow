package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DAO.DAOFactory;
import controleur.Controller;
import metier.Employe;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class NewProjet extends JDialog {

	private JPanel contentPane;
	private JTextField textField;
	private JButton btnAnnuler;
	private JButton btnConfirmer;

	

	/**
	 * Create the frame.
	 */
	public NewProjet() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEntrezLeNom = new JLabel("Entrez le nom du nouveau projet :");
		lblEntrezLeNom.setBounds(10, 36, 424, 14);
		lblEntrezLeNom.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblEntrezLeNom);
		
		textField = new JTextField();
		textField.setBounds(91, 61, 250, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnAnnuler = new JButton("Annuler");
		btnAnnuler.setActionCommand("Cancel");
		btnAnnuler.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
			
		});
		btnAnnuler.setBounds(340, 227, 89, 23);
		contentPane.add(btnAnnuler);
		
		btnConfirmer = new JButton("Confirmer");
		btnConfirmer.setActionCommand("OK");
		btnConfirmer.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				DAOFactory.getDAOProjet().newProjet(textField.getText());
				
				setVisible(false);
			}
		});
		btnConfirmer.setBounds(241, 227, 89, 23);
		contentPane.add(btnConfirmer);
		setModal(true);
		setVisible(true);
		
		
	}
}
