package gui;


import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import baza.PripravljalecPodatkov;

/**
 * @author campovski
 * Razred poskrbi za izris okna, v katerem lahko vnesemo podatke o zbirki,
 * ki jo zelimo na novo dodati (torej zbirko, katere se ni v bazi). Ob poskusu
 * vnosa zbirke z istim imenom, javi napako.
 */
@SuppressWarnings("serial")
public class NovaZbirkaWindow extends JDialog implements ActionListener{
	private List<JTextField> arrTextField;
	private List<String> stolpci;
	private JButton btnShrani;
	private JPanel contentPanel;
	private int rowCounter = 1;
	private JButton button;
	private JButton btnOk;
	private JDialog error;


	/**
	 * Naredi JDialog, ki omogoca vnos imena zbirke in imen stolpcev.
	 */
	public NovaZbirkaWindow() {
		arrTextField = new ArrayList<JTextField>();
		
		contentPanel = new JPanel();
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		getContentPane().add(contentPanel);
		
		JLabel lblImeZbirke = new JLabel("Ime zbirke");
		GridBagConstraints gbc_lblImeZbirke = new GridBagConstraints();
		gbc_lblImeZbirke.anchor = GridBagConstraints.EAST;
		gbc_lblImeZbirke.insets = new Insets(0, 0, 5, 5);
		gbc_lblImeZbirke.gridx = 1;
		gbc_lblImeZbirke.gridy = 0;
		gbc_lblImeZbirke.weighty = 0;
		contentPanel.add(lblImeZbirke, gbc_lblImeZbirke);
		
		JTextField textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 0;
		contentPanel.add(textField, gbc_textField);
		textField.setColumns(10);
		arrTextField.add(textField);
		
		button = new JButton("+");
		button.addActionListener(this);
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 0;
		gbc_button.gridy = rowCounter;
		contentPanel.add(button, gbc_button);
		
		JLabel lblStolpec = new JLabel("Stolpec 1");
		GridBagConstraints gbc_lblStolpec = new GridBagConstraints();
		gbc_lblStolpec.anchor = GridBagConstraints.EAST;
		gbc_lblStolpec.insets = new Insets(0, 0, 5, 5);
		gbc_lblStolpec.gridx = 1;
		gbc_lblStolpec.gridy = rowCounter;
		gbc_lblStolpec.weighty = 0;
		contentPanel.add(lblStolpec, gbc_lblStolpec);
		
		JTextField textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = rowCounter;
		contentPanel.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		arrTextField.add(textField_1);
		
		btnShrani = new JButton("Shrani");
		GridBagConstraints gbc_btnShrani = new GridBagConstraints();
		gbc_btnShrani.gridx = 2;
		gbc_btnShrani.gridy = rowCounter+1;
		btnShrani.addActionListener(this);
		contentPanel.add(btnShrani, gbc_btnShrani);

		pack();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object vir = e.getSource();
		if (vir == button){
			rowCounter++;
			
			GridBagConstraints gbc_button = new GridBagConstraints();
			gbc_button.insets = new Insets(0, 0, 0, 5);
			gbc_button.gridx = 0;
			gbc_button.gridy = rowCounter;
			contentPanel.add(button, gbc_button);
			
			JLabel lblStolpec = new JLabel("Stolpec "+rowCounter);
			GridBagConstraints gbc_lblStolpec = new GridBagConstraints();
			gbc_lblStolpec.insets = new Insets(0, 0, 0, 5);
			gbc_lblStolpec.gridx = 1;
			gbc_lblStolpec.gridy = rowCounter;
			gbc_lblStolpec.anchor = GridBagConstraints.EAST;
			gbc_lblStolpec.weighty = 0;
			contentPanel.add(lblStolpec, gbc_lblStolpec);
			
			JTextField textField = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 2;
			gbc_textField.gridy = rowCounter;
			contentPanel.add(textField, gbc_textField);
			textField.setColumns(4);
			arrTextField.add(textField);
			
			GridBagConstraints gbc_btnShrani = new GridBagConstraints();
			gbc_btnShrani.gridx = 2;
			gbc_btnShrani.gridy = rowCounter+1;
			contentPanel.add(btnShrani, gbc_btnShrani);
			
			contentPanel.updateUI();
			pack();
		}
		else if (vir == btnShrani){
			stolpci = new ArrayList<String>();
			for (JTextField polje : arrTextField){
				stolpci.add(polje.getText());
			}
			
			if (GlavnoOkno.slovarSS.keySet().contains(stolpci.get(0))){
				error = new JDialog();
				error.setTitle("Napaka");
				
				JPanel contentPanel = new JPanel();
				GridBagLayout gbl_contentPanel = new GridBagLayout();
				gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
				gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
				gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
				gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				contentPanel.setLayout(gbl_contentPanel);
				error.getContentPane().add(contentPanel);
				
				JLabel napis = new JLabel("<html>Zbirka z imenom '"+stolpci.get(0)+"' ze obstaja!<br>Prosim vnesi drugo ime.</html>");
				GridBagConstraints gbc_napis = new GridBagConstraints();
				gbc_napis.insets = new Insets(0, 0, 0, 5);
				gbc_napis.gridx = 0;
				gbc_napis.gridy = 0;
				contentPanel.add(napis, gbc_napis);
				
				btnOk = new JButton("OK");
				btnOk.addActionListener(this);
				GridBagConstraints gbc_ok = new GridBagConstraints();
				gbc_ok.insets = new Insets(0, 0, 0, 5);
				gbc_ok.gridx = 0;
				gbc_ok.gridy = 1;
				contentPanel.add(btnOk, gbc_ok);

				error.pack();
				error.setVisible(true);
			}
			else {
				PripravljalecPodatkov.dodajZbirko(stolpci);
				dispose();
			}
		}
		else if (vir == btnOk){
			error.dispose();
		}
	}

}
