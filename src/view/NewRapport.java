package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DAO.DAOFactory;
import controleur.Controller;
import metier.Employe;
import metier.cd;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

public class NewRapport extends JDialog {
	int year = Calendar.getInstance().get(Calendar.YEAR);
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		try {
			NewRapport dialog = new NewRapport();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
	/**
	 * Create the dialog.
	 */
	public NewRapport(Vector <Employe> VecEmp) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblChoisissezLesParamtres = new JLabel("Choisissez les  param\u00E8tres du nouveau registre :");
			lblChoisissezLesParamtres.setHorizontalAlignment(SwingConstants.CENTER);
			lblChoisissezLesParamtres.setBounds(28, 11, 380, 14);
			contentPanel.add(lblChoisissezLesParamtres);
		}
		
		JComboBox comboBox = new JComboBox(VecEmp);
		comboBox.setBounds(203, 52, 134, 20);
		
		contentPanel.add(comboBox);
		
		JLabel lblEmploye = new JLabel("Employe :");
		lblEmploye.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmploye.setBounds(90, 55, 67, 14);
		contentPanel.add(lblEmploye);
		
		JLabel lblMois = new JLabel("Mois :");
		lblMois.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMois.setBounds(90, 86, 67, 14);
		contentPanel.add(lblMois);
		
		JLabel lblAnnee = new JLabel("Annee  :");
		lblAnnee.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAnnee.setBounds(100, 117, 57, 14);
		contentPanel.add(lblAnnee);
		
		JComboBox comboBox_1 = new JComboBox(cd.intToMois);
		comboBox_1.setBounds(203, 83, 134, 20);
		contentPanel.add(comboBox_1);
		int difAnnee=year-2016+1;
		String annees[]= new String [difAnnee];
		for (int i =0; i<difAnnee;i++){
			annees[i]=Integer.toString(2016+i);
		}
		JComboBox comboBox_2 = new JComboBox(annees);
		comboBox_2.setBounds(203, 114, 134, 20);
		contentPanel.add(comboBox_2);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						DAOFactory.getDAORapportPMensuel().insertNewRapport(Integer.parseInt((String)comboBox_2.getSelectedItem()), comboBox_1.getSelectedIndex()+1, ((Employe)comboBox.getSelectedItem()).getId());
						Controller.drawNewView();
						setVisible(false);
					}
					
					
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						setVisible(false);
					}
					
				});
				buttonPane.add(cancelButton);
			}
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setVisible(true);
		
		
	}
}
