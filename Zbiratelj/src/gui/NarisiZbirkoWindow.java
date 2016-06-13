package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import baza.SqlManager;

@SuppressWarnings("serial")
public class NarisiZbirkoWindow extends JDialog implements ActionListener{

	private JPanel contentPane;
	private HashMap<Integer, Integer> id;
	private HashMap<JCheckBox, Integer> slovarCheckBoxElement;
	private JButton buttonShraniSpremembe;
	private JMenuItem mntmOmogociUrejanje;
	private JMenuItem mntmKonajUrejanje;
	private JSeparator separator;
	private JMenuItem mntmIzhod;
	private boolean urejanjeOmogoceno;
	private String zbirkaZaRisanje;

	/**
	 * Naredi frame.
	 * @param zbirkaZaRisanje
	 */
	public NarisiZbirkoWindow(String zbirkaZaRisanje){
		
		this.zbirkaZaRisanje = zbirkaZaRisanje;
		urejanjeOmogoceno = false;
		
		setTitle(this.zbirkaZaRisanje);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMeni = new JMenu("Meni");
		menuBar.add(mnMeni);
		
		mntmOmogociUrejanje = new JMenuItem("Omogoči urejanje");
		mnMeni.add(mntmOmogociUrejanje);
		KeyStroke keyStrokeOmogociUrejanje = KeyStroke.getKeyStroke("control E");
		mntmOmogociUrejanje.setAccelerator(keyStrokeOmogociUrejanje);
		mntmOmogociUrejanje.addActionListener(this);
		
		mntmKonajUrejanje = new JMenuItem("Končaj urejanje");
		mnMeni.add(mntmKonajUrejanje);
		KeyStroke keyStrokeKonajUrejanje = KeyStroke.getKeyStroke("control D");
		mntmKonajUrejanje.setAccelerator(keyStrokeKonajUrejanje);
		mntmKonajUrejanje.addActionListener(this);
		
		separator = new JSeparator();
		mnMeni.add(separator);
		
		mntmIzhod = new JMenuItem("Izhod");
		mntmIzhod.addActionListener(this);
		KeyStroke keyStrokeIzhod = KeyStroke.getKeyStroke("control X");
		mntmIzhod.setAccelerator(keyStrokeIzhod);
		mnMeni.add(mntmIzhod);
		
		contentPane = new JPanel();
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		setupUI();
	}
	
	/**
	 * Metoda narise zbirko, ki je shranjena v zbirkaZaRisanje. Ce je omogoceno urejanje, doda se JCheckBoxe.
	 */
	private void narisi(){
		
		JScrollPane scroll = new JScrollPane(contentPane);
		
		Map<Integer, Map<String, String>> zbirka = SqlManager.beriZbirkoPodatki(zbirkaZaRisanje);
		List<String> stolpci = SqlManager.beriZbirkoStolpci(zbirkaZaRisanje);
		id = new HashMap<Integer, Integer>();
		
		int columnCounter = 0;
		
		for (String stolpec : stolpci){
			JLabel napis = new JLabel(stolpec);
			GridBagConstraints gbc_napis = new GridBagConstraints();
			gbc_napis.insets = new Insets(0, 0, 5, 5);
			gbc_napis.gridx = columnCounter;
			gbc_napis.gridy = 0;
			contentPane.add(napis, gbc_napis);
			columnCounter++;
		}
		
		int rowCounter = 0;
		
		for (int i : zbirka.keySet()){
			Map<String, String> element = zbirka.get(i);
			rowCounter++;
			columnCounter = 0;
			
			for (int j = 0; j < stolpci.size(); j++){
				String lastnost = element.get(stolpci.get(j));
				JLabel napis = new JLabel(lastnost);
				GridBagConstraints gbc_napis = new GridBagConstraints();
				gbc_napis.insets = new Insets(0, 0, 5, 5);
				gbc_napis.gridx = columnCounter;
				gbc_napis.gridy = rowCounter;
				contentPane.add(napis, gbc_napis);
				columnCounter++;
			}
			
			
			id.put(rowCounter, i);
		}
		
		if (urejanjeOmogoceno){
			int column = SqlManager.beriZbirkoStolpci(zbirkaZaRisanje).size();
			slovarCheckBoxElement = new HashMap<JCheckBox, Integer>();
			
			JLabel izbrisi = new JLabel("Izbriši");
			GridBagConstraints gbc_izbrisi = new GridBagConstraints();
			gbc_izbrisi.insets = new Insets(0, 0, 5, 5);
			gbc_izbrisi.gridx = column;
			gbc_izbrisi.gridy = 1;
			contentPane.add(izbrisi, gbc_izbrisi);
			
			for (rowCounter = 0; rowCounter < zbirka.size(); rowCounter++){
				JCheckBox box = new JCheckBox();
				box.addActionListener(this);
				GridBagConstraints gbc_box = new GridBagConstraints();
				gbc_box.insets = new Insets(0, 0, 5, 5);
				gbc_box.gridx = column;
				gbc_box.gridy = rowCounter + 2;
				slovarCheckBoxElement.put(box, rowCounter + 2);
				contentPane.add(box, gbc_box);
			}
			
			buttonShraniSpremembe = new JButton("Shrani");
			buttonShraniSpremembe.addActionListener(this);
			GridBagConstraints gbc_buttonShraniSpremembe = new GridBagConstraints();
			gbc_buttonShraniSpremembe.insets = new Insets(0, 0, 5, 5);
			gbc_buttonShraniSpremembe.gridx = column;
			gbc_buttonShraniSpremembe.gridy = rowCounter+2;
			contentPane.add(buttonShraniSpremembe, gbc_buttonShraniSpremembe);
		}
		
		getContentPane().add(scroll);
		
		revalidate();
		repaint();
		pack();
	}

	/**
	 * Metoda, ki narise UI. To stori tako, da najprej pogleda, ali lahko 
	 * urejamo ter se potem na podlagi teh podatkov odloci, kaj naj narise.
	 */
	public void setupUI(){
		getContentPane().removeAll();
		contentPane.removeAll();
		pack();
		
		if (urejanjeOmogoceno){
			mntmOmogociUrejanje.setEnabled(false);
			mntmKonajUrejanje.setEnabled(true);
		}
		else {
			mntmOmogociUrejanje.setEnabled(true);
			mntmKonajUrejanje.setEnabled(false);
		}
		
		narisi();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object vir = arg0.getSource();
		if (vir == buttonShraniSpremembe){
			for (JCheckBox kljuc : slovarCheckBoxElement.keySet()){
				if (kljuc.isSelected()){
					int izbrisiElement = id.get(slovarCheckBoxElement.get(kljuc));
					SqlManager.izbrisiElement(izbrisiElement);
				}
			}
			setupUI();
		}
		else if (vir == mntmOmogociUrejanje){
			urejanjeOmogoceno = true;
			setupUI();
		}
		else if(vir == mntmKonajUrejanje){
			urejanjeOmogoceno = false;
			setupUI();
		}
		else if (vir == mntmIzhod){
			dispose();
		}
	}
}
