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
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridBagLayout;
import javax.swing.JButton;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;

import javax.swing.JTextField;

import baza.PripravljalecPodatkov;
import io.CsvImporter;

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
	private CsvImporter csv;
	private JDialog error;
	private JButton btnPreklici;
	private JButton btnZamenjaj;
	private String vnesenaZbirka;
	private PripravljalecPodatkov pripravljalec = GlavnoOkno.podatki;
	
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
		gbc_lblImeZbirke.weighty = 0;
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
		gbc_lblDatoteka.weighty = 0;
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
		
		btnOdpri = new JButton("Odpri");
		btnOdpri.addActionListener(this);
		GridBagConstraints gbc_btnOdpri = new GridBagConstraints();
		gbc_btnOdpri.insets = new Insets(0, 0, 5, 5);
		gbc_btnOdpri.gridx = 1;
		gbc_btnOdpri.gridy = 2;
		contentPanel.add(btnOdpri, gbc_btnOdpri);
		
		btnShrani = new JButton("Shrani");
		btnShrani.setEnabled(false);
		btnShrani.addActionListener(this);
		GridBagConstraints gbc_btnShrani = new GridBagConstraints();
		gbc_btnShrani.insets = new Insets(0, 0, 5, 5);
		gbc_btnShrani.gridx = 2;
		gbc_btnShrani.gridy = 2;
		contentPanel.add(btnShrani, gbc_btnShrani);
		
		pack();
	}
	
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
	
	private void shraniZbirko(){
		vnesenaZbirka = textField.getText();
		if (pripravljalec.getSlovar().containsKey(vnesenaZbirka)){
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
	
	private void potrdiShranjevanje(boolean izbris){
		ArrayList<List<String>> seznamSeznamovVZbirki = csv.getDatoteka();
		
		if (izbris){
			List<String> seznamZbirkZaIzbris = new ArrayList<String>();
			seznamZbirkZaIzbris.add(vnesenaZbirka);
			pripravljalec.izbrisiZbirke(seznamZbirkZaIzbris);
		}
		
		List<String> seznamNovaZbirka = new ArrayList<String>();
		seznamNovaZbirka.add(vnesenaZbirka);
		seznamNovaZbirka.addAll(seznamSeznamovVZbirki.get(0));
		pripravljalec.dodajZbirko(seznamNovaZbirka);
		for (int i = 1; i < seznamSeznamovVZbirki.size(); i++){
			pripravljalec.dodajElement(vnesenaZbirka, seznamSeznamovVZbirki.get(i));
		}
		pripravljalec.dodajElemente();
		System.out.println(pripravljalec.getSlovar());
		dispose();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object vir = arg0.getSource();
		if (vir == btnOdpri){
			if (datoteka != null){
				csv = new CsvImporter();
				try {
					csv.preberiCsv(datoteka);
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
