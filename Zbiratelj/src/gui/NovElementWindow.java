package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


/**
 * @author campovski
 * Razred, ki poskrbi za okno, s katerim lahko dodajamo elemente v ze obstojece zbirke.
 */
@SuppressWarnings("serial")
public class NovElementWindow extends JDialog implements ActionListener{

	private JPanel contentPanel;
	private JButton btnNaprej;
	private JButton btnIzhod;
	private JComboBox<String> comboBox;
	private JButton btnNazaj;
	private JButton btnDodaj;
	private JButton btnShrani;
	private String izbranaZbirka;
	private List<JTextField> seznamTextField;

	/**
	 * Create the dialog.
	 */
	public NovElementWindow() {
		contentPanel = new JPanel();
		JScrollPane scroll = new JScrollPane(contentPanel);
		setResizable(false);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gridBagLayout);
		add(scroll);
		
		izberiZbirko();
	}
	
	private void izberiZbirko() {
		JLabel lblZbirka = new JLabel("Zbirka");
		GridBagConstraints gbc_lblZbirka = new GridBagConstraints();
		gbc_lblZbirka.insets = new Insets(0, 0, 5, 5);
		gbc_lblZbirka.anchor = GridBagConstraints.EAST;
		gbc_lblZbirka.gridx = 0;
		gbc_lblZbirka.gridy = 0;
		contentPanel.add(lblZbirka, gbc_lblZbirka);
		
		String[] seznam = new String[GlavnoOkno.slovarSS.keySet().size()];
		int i = 0;
		for (String zbirka : GlavnoOkno.slovarSS.keySet()){
			seznam[i] = zbirka;
			i++;
		}
		comboBox = new JComboBox<String>(seznam);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		gbc_comboBox.gridwidth = 2;
		contentPanel.add(comboBox, gbc_comboBox);
		
		btnNaprej = new JButton("Naprej");
		btnNaprej.addActionListener(this);
		GridBagConstraints gbc_btnNaprej = new GridBagConstraints();
		gbc_btnNaprej.insets = new Insets(0, 0, 0, 5);
		gbc_btnNaprej.gridx = 1;
		gbc_btnNaprej.gridy = 1;
		contentPanel.add(btnNaprej, gbc_btnNaprej);
		
		btnIzhod = new JButton("Izhod");
		btnIzhod.addActionListener(this);
		GridBagConstraints gbc_btnIzhod = new GridBagConstraints();
		gbc_btnIzhod.gridx = 2;
		gbc_btnIzhod.gridy = 1;
		contentPanel.add(btnIzhod, gbc_btnIzhod);

		pack();
	}
	
	private void vnesiElement() {
		contentPanel.removeAll();

		seznamTextField = new ArrayList<JTextField>();
		izbranaZbirka = comboBox.getSelectedItem().toString();
		List<String> stolpci = GlavnoOkno.slovarSS.get(izbranaZbirka).get(0);
		
		JLabel lblZbirka = new JLabel("Zbirka");
		GridBagConstraints gbc_lblZbirka = new GridBagConstraints();
		gbc_lblZbirka.insets = new Insets(0, 0, 5, 5);
		gbc_lblZbirka.anchor = GridBagConstraints.EAST;
		gbc_lblZbirka.gridx = 0;
		gbc_lblZbirka.gridy = 0;
		contentPanel.add(lblZbirka, gbc_lblZbirka);
		
		JLabel lblIzbranaZbirka = new JLabel(izbranaZbirka.toUpperCase());
		GridBagConstraints gbc_lblIzbranaZbirka = new GridBagConstraints();
		gbc_lblIzbranaZbirka.insets = new Insets(0, 0, 5, 5);
		gbc_lblIzbranaZbirka.gridx = 1;
		gbc_lblIzbranaZbirka.gridy = 0;
		gbc_lblIzbranaZbirka.gridwidth = 4;
		contentPanel.add(lblIzbranaZbirka, gbc_lblIzbranaZbirka);
		
		for (int row = 1; row <= stolpci.size(); row++){
			JLabel lblStolpec = new JLabel(stolpci.get(row-1));
			GridBagConstraints gbc_lblStolpec = new GridBagConstraints();
			gbc_lblStolpec.insets = new Insets(0, 0, 5, 5);
			gbc_lblStolpec.anchor = GridBagConstraints.EAST;
			gbc_lblStolpec.gridx = 0;
			gbc_lblStolpec.gridy = row;
			contentPanel.add(lblStolpec, gbc_lblStolpec);
			
			JTextField textField = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 1;
			gbc_textField.gridy = row;
			gbc_textField.gridwidth = 4;
			contentPanel.add(textField, gbc_textField);
			seznamTextField.add(textField);
		}
		
		btnNazaj = new JButton("Nazaj");
		btnNazaj.addActionListener(this);
		GridBagConstraints gbc_btnNazaj = new GridBagConstraints();
		gbc_btnNazaj.gridx = 1;
		gbc_btnNazaj.gridy = stolpci.size()+1;
		btnNazaj.addActionListener(this);
		contentPanel.add(btnNazaj, gbc_btnNazaj);
		
		btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(this);
		GridBagConstraints gbc_btnDodaj = new GridBagConstraints();
		gbc_btnDodaj.gridx = 2;
		gbc_btnDodaj.gridy = stolpci.size()+1;
		btnDodaj.addActionListener(this);
		contentPanel.add(btnDodaj, gbc_btnDodaj);
		
		btnShrani = new JButton("Shrani");
		btnShrani.addActionListener(this);
		GridBagConstraints gbc_btnShrani = new GridBagConstraints();
		gbc_btnShrani.gridx = 3;
		gbc_btnShrani.gridy = stolpci.size()+1;
		btnShrani.addActionListener(this);
		contentPanel.add(btnShrani, gbc_btnShrani);
		
		btnIzhod = new JButton("Prekliči");
		btnIzhod.addActionListener(this);
		GridBagConstraints gbc_btnIzhod = new GridBagConstraints();
		gbc_btnIzhod.gridx = 4;
		gbc_btnIzhod.gridy = stolpci.size()+1;
		contentPanel.add(btnIzhod, gbc_btnIzhod);
		
		pack();
	}
	
	private void dodajVZbirko(){
		List<String> element = new ArrayList<String>();
		for (JTextField polje : seznamTextField){
			element.add(polje.getText());
		}
		GlavnoOkno.podatki.dodajElement(izbranaZbirka, element);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object vir = arg0.getSource();
		if (vir == btnNaprej){
			vnesiElement();
		}
		else if (vir == btnIzhod){
			GlavnoOkno.podatki.izprazniDodajanjeElementov();
			dispose();
		}
		else if (vir == btnNazaj){
			contentPanel.removeAll();
			izberiZbirko();
		}
		else if (vir == btnDodaj){
			dodajVZbirko();
			vnesiElement();
		}
		else if (vir == btnShrani){
			// ERROR printa 2x
			dodajVZbirko();
			GlavnoOkno.podatki.dodajElemente();
			dispose();
		}
	}

}

