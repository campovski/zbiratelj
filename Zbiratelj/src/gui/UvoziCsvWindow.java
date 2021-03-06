/**
 * 
 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import baza.SqlManager;
import io.CsvManager;

/**
 * @author nina
 *
 */
@SuppressWarnings("serial")
public class UvoziCsvWindow extends JDialog implements ActionListener {

	private JButton btnOdpri;
	private JButton btnShrani;
	private JTextField textField;
	private JTextField textFieldDatoteka;
	private File datoteka;
	private final JPanel contentPanel;
	private CsvManager csv;
	private JDialog error;
	private JButton btnPreklici;
	private JButton btnZamenjaj;
	private String vnesenaZbirka;
	private JComboBox<String> comboBox;
	
	/**
	 * Odpre okno, ki omogoca izbiro datoteke za uvoz in imena zbirke.
	 */
	public UvoziCsvWindow() {
		contentPanel = new JPanel();
		add(contentPanel);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel lblImeZbirke = new JLabel("Ime zbirke");
		GridBagConstraints gbc_lblImeZbirke = new GridBagConstraints();
		gbc_lblImeZbirke.anchor = GridBagConstraints.EAST;
		gbc_lblImeZbirke.insets = new Insets(0, 0, 5, 5);
		gbc_lblImeZbirke.gridx = 0;
		gbc_lblImeZbirke.gridy = 0;
		gbc_lblImeZbirke.weighty = 1;
		contentPanel.add(lblImeZbirke, gbc_lblImeZbirke);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		gbc_textField.gridwidth = 2;
		contentPanel.add(textField, gbc_textField);
		
		JLabel lblDatoteka = new JLabel("Datoteka");
		GridBagConstraints gbc_lblDatoteka = new GridBagConstraints();
		gbc_lblDatoteka.anchor = GridBagConstraints.EAST;
		gbc_lblDatoteka.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatoteka.gridx = 0;
		gbc_lblDatoteka.gridy = 1;
		gbc_lblDatoteka.weighty = 1;
		contentPanel.add(lblDatoteka, gbc_lblDatoteka);
		
		textFieldDatoteka = new JTextField();
		GridBagConstraints gbc_textFieldDatoteka = new GridBagConstraints();
		gbc_textFieldDatoteka.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldDatoteka.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDatoteka.gridx = 1;
		gbc_textFieldDatoteka.gridy = 1;
		gbc_textFieldDatoteka.gridwidth = 2;
		textFieldDatoteka.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(contentPanel);
				if (result == JFileChooser.APPROVE_OPTION){
					datoteka = fileChooser.getSelectedFile();
					textFieldDatoteka.setText(datoteka.getName());
				}
			}
		});
		contentPanel.add(textFieldDatoteka, gbc_textFieldDatoteka);
		
		JLabel lblLocilo = new JLabel("Ločilo");
		GridBagConstraints gbc_lblLocilo = new GridBagConstraints();
		gbc_lblLocilo.anchor = GridBagConstraints.EAST;
		gbc_lblLocilo.insets = new Insets(0, 0, 5, 5);
		gbc_lblLocilo.gridx = 0;
		gbc_lblLocilo.gridy = 2;
		gbc_lblLocilo.weighty = 1;
		contentPanel.add(lblLocilo, gbc_lblLocilo);
		
		String[] seznam = {",", ";", "|", "space"};
		
		comboBox = new JComboBox<String>(seznam);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 2;
		gbc_comboBox.gridwidth = 1;
		contentPanel.add(comboBox, gbc_comboBox);
		
		btnOdpri = new JButton("Odpri");
		btnOdpri.addActionListener(this);
		GridBagConstraints gbc_btnOdpri = new GridBagConstraints();
		gbc_btnOdpri.insets = new Insets(0, 0, 5, 5);
		gbc_btnOdpri.gridx = 1;
		gbc_btnOdpri.gridy = 3;
		contentPanel.add(btnOdpri, gbc_btnOdpri);
		
		btnShrani = new JButton("Shrani");
		btnShrani.setEnabled(false);
		btnShrani.addActionListener(this);
		GridBagConstraints gbc_btnShrani = new GridBagConstraints();
		gbc_btnShrani.insets = new Insets(0, 0, 5, 5);
		gbc_btnShrani.gridx = 2;
		gbc_btnShrani.gridy = 3;
		contentPanel.add(btnShrani, gbc_btnShrani);
		
		pack();
	}
	
	/**
	 * Narise zbirko na dno okna.
	 * @param zbirka
	 */
	private void narisiZbirko(List<List<String>> zbirka){
		JScrollPane scroll = new JScrollPane(contentPanel);
		
		for (int row = 0; row < zbirka.size(); row++){
			for (int column = 0; column < zbirka.get(0).size(); column++){
				JLabel napis = new JLabel(zbirka.get(row).get(column));
				GridBagConstraints gbc_napis = new GridBagConstraints();
				if (column == 0){
					gbc_napis.anchor = GridBagConstraints.WEST;
				}
				else {
					gbc_napis.anchor = GridBagConstraints.EAST;
				}
				gbc_napis.insets = new Insets(0, 0, 5, 5);
				gbc_napis.gridx = column;
				gbc_napis.gridy = row+4;
				contentPanel.add(napis, gbc_napis);
			}
		}
		getContentPane().add(scroll);
	}
	
	/**
	 * Preveri, ali morda zbirka ze obstaja. Ce da, nam ponudi moznost preklica dodajanja
	 * ali pa zamenjave stare zbirke z novo.
	 */
	private void shraniZbirko(){
		vnesenaZbirka = textField.getText();
		if (SqlManager.beriBazo().contains(vnesenaZbirka)){
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
			
			JLabel napis = new JLabel("Zbirka z imenom '"+vnesenaZbirka+"' že obstaja!");
			GridBagConstraints gbc_napis = new GridBagConstraints();
			gbc_napis.insets = new Insets(0, 0, 0, 5);
			gbc_napis.gridx = 0;
			gbc_napis.gridy = 0;
			gbc_napis.gridwidth = 2;
			contentPanel.add(napis, gbc_napis);
			
			btnPreklici = new JButton("Prekliči");
			btnPreklici.addActionListener(this);
			GridBagConstraints gbc_preklici = new GridBagConstraints();
			gbc_preklici.insets = new Insets(0, 0, 0, 5);
			gbc_preklici.gridx = 0;
			gbc_preklici.gridy = 1;
			contentPanel.add(btnPreklici, gbc_preklici);
			
			btnZamenjaj = new JButton("Zamenjaj");
			btnZamenjaj.addActionListener(this);
			GridBagConstraints gbc_zamenjaj = new GridBagConstraints();
			gbc_zamenjaj.insets = new Insets(0, 0, 0, 5);
			gbc_zamenjaj.gridx = 1;
			gbc_zamenjaj.gridy = 1;
			contentPanel.add(btnZamenjaj, gbc_zamenjaj);

			error.pack();
			error.setVisible(true);
		}
		else {
			potrdiShranjevanje(false);
		}
	}
	
	/**
	 * Potrdi shranjevanje. Ce je potreben prepis zbirke, staro najprej izbrisemo.
	 * @param izbris
	 */
	private void potrdiShranjevanje(boolean izbris){
		ArrayList<List<String>> seznamSeznamovVZbirki = csv.getDatoteka();
		List<String> stolpci = seznamSeznamovVZbirki.get(0);
		
		if (izbris){
			List<String> seznamZbirkZaIzbris = new ArrayList<String>();
			seznamZbirkZaIzbris.add(vnesenaZbirka);
			for (String zbirka : seznamZbirkZaIzbris){
				SqlManager.izbrisiZbirko(zbirka);
			}
		}
		
		SqlManager.dodajZbirko(vnesenaZbirka, stolpci);
		
		for (int i = 1; i < seznamSeznamovVZbirki.size(); i++){
			Map<String, String> element = new HashMap<String, String>();
			for (int j = 0; j < stolpci.size(); j++){
				element.put(stolpci.get(j), seznamSeznamovVZbirki.get(i).get(j));
			}
			SqlManager.dodajElement(vnesenaZbirka, element);
		}
		
		dispose();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object vir = arg0.getSource();
		if (vir == btnOdpri){
			String locilo = comboBox.getSelectedItem().toString();
			if (datoteka != null && locilo != null){
				csv = new CsvManager();
				try {
					csv.preberiCsv(datoteka, locilo);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				narisiZbirko(csv.getDatoteka());
				btnShrani.setEnabled(true);
				pack();
			}
		}
		else if (vir == btnShrani){
			shraniZbirko();
		}
		else if (vir == btnPreklici){
			error.dispose();
		}
		else if (vir == btnZamenjaj){
			potrdiShranjevanje(true);
			error.dispose();
		}
	}	
}
