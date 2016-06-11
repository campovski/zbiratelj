package gui;


import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
import javax.swing.SwingUtilities;

import baza.SqlManager;

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
	private JButton btnOk;
	private JDialog error;
	private JPanel stolpciPanel;
	private JButton plusButton;
	private JTextField textFieldZbirka;


	/**
	 * Naredi JDialog, ki omogoca vnos imena zbirke in imen stolpcev.
	 */
	public NovaZbirkaWindow() {
		arrTextField = new ArrayList<JTextField>();
		
		contentPanel = new JPanel();
		
		JScrollPane scroll = new JScrollPane(contentPanel);
	
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		add(scroll);
		
		JLabel lblImeZbirke = new JLabel("Ime zbirke");
		GridBagConstraints gbc_lblImeZbirke = new GridBagConstraints();
		gbc_lblImeZbirke.anchor = GridBagConstraints.EAST;
		gbc_lblImeZbirke.insets = new Insets(0, 0, 5, 5);
		gbc_lblImeZbirke.gridx = 0;
		gbc_lblImeZbirke.gridy = 0;
		gbc_lblImeZbirke.weighty = 0;
		contentPanel.add(lblImeZbirke, gbc_lblImeZbirke);
		
		textFieldZbirka = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		contentPanel.add(textFieldZbirka, gbc_textField);
		textFieldZbirka.setColumns(10);
		
		stolpciPanel = new JPanel();
		stolpciPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc_stolpciPanel = new GridBagConstraints();
		gbc_stolpciPanel.anchor = GridBagConstraints.EAST;
		gbc_stolpciPanel.insets = new Insets(0, 0, 5, 5);
		gbc_stolpciPanel.gridx = 0;
		gbc_stolpciPanel.gridy = 1;
		gbc_stolpciPanel.gridwidth = 2;
		contentPanel.add(stolpciPanel, gbc_stolpciPanel);
		
		JLabel lblStolpec = new JLabel("Stolpec 1");
		GridBagConstraints gbc_lblStolpec = new GridBagConstraints();
		gbc_lblStolpec.insets = new Insets(0, 0, 5, 5);
		gbc_lblStolpec.gridx = 0;
		gbc_lblStolpec.gridy = 0;
		stolpciPanel.add(lblStolpec, gbc_lblStolpec);
		
		JTextField textField_1 = new JTextField();
		GridBagConstraints gbc_textField1 = new GridBagConstraints();
		gbc_textField1.insets = new Insets(0, 0, 5, 5);
		gbc_textField1.gridx = 1;
		gbc_textField1.gridy = 0;
		textField_1.setColumns(10);
		stolpciPanel.add(textField_1, gbc_textField1);
		arrTextField.add(textField_1);
		
		plusButton = new JButton("+");
		plusButton.addActionListener(this);
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 0;
		gbc_button.gridy = 2;
		contentPanel.add(plusButton, gbc_button);
		
		btnShrani = new JButton("Shrani");
		GridBagConstraints gbc_btnShrani = new GridBagConstraints();
		gbc_btnShrani.gridx = 1;
		gbc_btnShrani.gridy = 2;
		btnShrani.addActionListener(this);
		contentPanel.add(btnShrani, gbc_btnShrani);

		pack();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object vir = e.getSource();
		if (vir == plusButton){
			rowCounter++;
			
			JLabel lblStolpec = new JLabel("Stolpec "+rowCounter);
			GridBagConstraints gbc_lblStolpec = new GridBagConstraints();
			gbc_lblStolpec.insets = new Insets(0, 0, 5, 5);
			gbc_lblStolpec.gridx = 0;
			gbc_lblStolpec.gridy = rowCounter - 1;
			stolpciPanel.add(lblStolpec, gbc_lblStolpec);
			
			JTextField textField = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.insets = new Insets(0, 0, 5, 5);
			gbc_textField.gridx = 1;
			gbc_textField.gridy = rowCounter - 1;
			textField.setColumns(10);
			stolpciPanel.add(textField, gbc_textField);
			arrTextField.add(textField);
			
			contentPanel.updateUI();
			pack();
		}
		else if (vir == btnShrani && !textFieldZbirka.getText().isEmpty() && textFieldZbirka.getText().trim().length() > 0){
			stolpci = new ArrayList<String>();
			arrTextField.size();
			for (JTextField polje : arrTextField){
				String vnos = polje.getText();
				if (!vnos.isEmpty() && vnos.trim().length() > 0){
					stolpci.add(polje.getText());
				}
			}
			
			if (!stolpci.isEmpty()){
				if (SqlManager.beriBazo().contains(textFieldZbirka.getText())){
					error = new JDialog(SwingUtilities.windowForComponent(this));
					error.setModal(true);
					error.setTitle("Napaka");
					
					JPanel contentPanel = new JPanel();
					GridBagLayout gbl_contentPanel = new GridBagLayout();
					gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
					gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
					gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
					gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
					contentPanel.setLayout(gbl_contentPanel);
					error.getContentPane().add(contentPanel);
					
					JLabel napis = new JLabel("<html>Zbirka z imenom '"+textFieldZbirka.getText()+"' že obstaja!<br>Prosim vnesi drugo ime.</html>");
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
					// TODO obravnavaj razlicne returne
					SqlManager.dodajZbirko(textFieldZbirka.getText(), stolpci);
					dispose();
				}
			}
		}
		else if (vir == btnOk){
			error.dispose();
		}
	}
}
