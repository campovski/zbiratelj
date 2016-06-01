package gui;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import baza.PripravljalecPodatkov;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JSeparator;



/**
 * @author nina
 * GlavnoOkno je razred, ki skrbi za glavni uporabniski vmesnik. V njem se
 * izpisujejo zbirke, ki jih potem lahko izberemo za ogled, v njem dolocamo,
 * katere zbirke ali elemente zbirk zelimo izbrisati.
 * 
 * Mozna stanja:
 * stanje = 0: mojeZbirke
 * stanje = 1: narisiZbirko
 */
@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener{
	//TODO alwaysOnTop
	
	private JPanel contentPane;
	
	private JMenuItem mntmMojeZbirke;
	private JMenuItem mntmUstvariNovoZbirko;
	private JMenuItem mntmOmogociUrejanje;
	private JMenuItem mntmKonajUrejanje;
	private JMenuItem mntmIzhod;
	private JMenuItem mntmUvoziCsv;
	private JMenuItem mntmIzvoziCsv;
	private JMenuItem mntmNovElement;
	
	private Map<JButton, String> slovarGumbov;
	private JButton buttonShraniSpremembe;
	protected static Map<String, List<List<String>>> slovarSS;
	private static Map<JCheckBox, String> slovarCheckBoxZbirka;
	private static Map<JCheckBox, List<String>> slovarCheckBoxElement;

	private boolean urejanjeOmogoceno = false;
	private int stanje;

	private JButton btnPotrdi;
	private JButton btnPreklici;
	private ArrayList<String> seznamZbirkZaIzbris;
	private JDialog potrdiIzbris;
	private static String zbirkaZaRisanje = null;
	private JSeparator separator;
	private JSeparator separator_1;

	static PripravljalecPodatkov podatki;

	/**
	 * Zacni program.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GlavnoOkno frame = new GlavnoOkno();
					frame.setLayout(new GridBagLayout());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Naredi frame.
	 */
	public GlavnoOkno() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMeni = new JMenu("Meni");
		menuBar.add(mnMeni);
		
		mntmMojeZbirke = new JMenuItem("Moje zbirke");
		mntmMojeZbirke.addActionListener(this);
		KeyStroke keyStrokeMojeZbirke = KeyStroke.getKeyStroke("control H");
		mntmMojeZbirke.setAccelerator(keyStrokeMojeZbirke);
		mnMeni.add(mntmMojeZbirke);
		
		JMenu mnCsv = new JMenu("CSV");
		mnMeni.add(mnCsv);
		
		mntmUvoziCsv = new JMenuItem("Uvozi");
		mntmUvoziCsv.addActionListener(this);
		mnCsv.add(mntmUvoziCsv);
		
		mntmIzvoziCsv = new JMenuItem("Izvozi");
		mntmIzvoziCsv.addActionListener(this);
		mnCsv.add(mntmIzvoziCsv);
		
		mntmIzhod = new JMenuItem("Izhod");
		mntmIzhod.addActionListener(this);
		KeyStroke keyStrokeIzhod = KeyStroke.getKeyStroke("control X");
		
		separator = new JSeparator();
		mnMeni.add(separator);
		mntmIzhod.setAccelerator(keyStrokeIzhod);
		mnMeni.add(mntmIzhod);
		
		JMenu mnUredi = new JMenu("Uredi");
		menuBar.add(mnUredi);
		
		JMenu mnDodaj = new JMenu("Dodaj...");
		mnUredi.add(mnDodaj);
		
		mntmUstvariNovoZbirko = new JMenuItem("Nova zbirka");
		mnDodaj.add(mntmUstvariNovoZbirko);
		KeyStroke keyStrokeUstvariNovoZbirko = KeyStroke.getKeyStroke("control shift N");
		mntmUstvariNovoZbirko.setAccelerator(keyStrokeUstvariNovoZbirko);
		mntmUstvariNovoZbirko.addActionListener(this);
		
		mntmNovElement = new JMenuItem("Nov element");
		mnDodaj.add(mntmNovElement);
		KeyStroke keyStrokeNovElement = KeyStroke.getKeyStroke("control N");
		mntmNovElement.setAccelerator(keyStrokeNovElement);
		mntmNovElement.addActionListener(this);
		
		separator_1 = new JSeparator();
		mnUredi.add(separator_1);
		
		mntmOmogociUrejanje = new JMenuItem("Omogoči urejanje");
		mnUredi.add(mntmOmogociUrejanje);
		KeyStroke keyStrokeOmogociUrejanje = KeyStroke.getKeyStroke("control E");
		mntmOmogociUrejanje.setAccelerator(keyStrokeOmogociUrejanje);
		mntmOmogociUrejanje.addActionListener(this);
		
		mntmKonajUrejanje = new JMenuItem("Končaj urejanje");
		mnUredi.add(mntmKonajUrejanje);
		KeyStroke keyStrokeKonajUrejanje = KeyStroke.getKeyStroke("control D");
		mntmKonajUrejanje.setAccelerator(keyStrokeKonajUrejanje);
		mntmKonajUrejanje.addActionListener(this);
		
		contentPane = new JPanel();
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JScrollPane scroll = new JScrollPane(contentPane);
		getContentPane().add(scroll);
		stanje = 0;
		setupUI();
		pack();
	}
	
	/**
	 * Metoda, ki nas vprasa, ali smo prepricani, da zelimo izbrisati izbrane zbirke.
	 */
	private void shraniUrejanje(){
		if (stanje == 0){
			seznamZbirkZaIzbris = new ArrayList<String>();
			for (JCheckBox kljuc : slovarCheckBoxZbirka.keySet()){
				if (kljuc.isSelected()){
					seznamZbirkZaIzbris.add(slovarCheckBoxZbirka.get(kljuc));
				}
			}
			
			potrdiIzbris = new JDialog();
			potrdiIzbris.setTitle("Opozorilo!");
			
			JPanel contentPanel = new JPanel();
			GridBagLayout gbl_contentPanel = new GridBagLayout();
			gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
			gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
			gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
			gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			contentPanel.setLayout(gbl_contentPanel);
			potrdiIzbris.getContentPane().add(contentPanel);
			
			String napis = "<html>Ali ste prepričani, da želite odstraniti naslednje zbirke:<br>";
			for (String zbirka : seznamZbirkZaIzbris){
				napis += " - " + zbirka + "<br>";
			}
			napis += "</html>";
			
			JLabel lbl = new JLabel(napis);
			GridBagConstraints gbc_lbl = new GridBagConstraints();
			gbc_lbl.insets = new Insets(0, 0, 5, 5);
			gbc_lbl.gridx = 0;
			gbc_lbl.gridy = 0;
			gbc_lbl.gridwidth = 2;
			contentPanel.add(lbl, gbc_lbl);
			
			btnPotrdi = new JButton("Potrdi");
			btnPotrdi.addActionListener(this);
			GridBagConstraints gbc_btnPotrdi = new GridBagConstraints();
			gbc_btnPotrdi.insets = new Insets(0, 0, 5, 5);
			gbc_btnPotrdi.gridx = 0;
			gbc_btnPotrdi.gridy = 1;
			gbc_btnPotrdi.anchor = GridBagConstraints.EAST;
			contentPanel.add(btnPotrdi, gbc_btnPotrdi);
			
			btnPreklici = new JButton("Prekliči");
			btnPreklici.addActionListener(this);
			GridBagConstraints gbc_btnPreklici = new GridBagConstraints();
			gbc_btnPreklici.insets = new Insets(0, 0, 5, 5);
			gbc_btnPreklici.gridx = 1;
			gbc_btnPreklici.gridy = 1;
			gbc_btnPreklici.anchor = GridBagConstraints.EAST;
			contentPanel.add(btnPreklici, gbc_btnPreklici);
			
			potrdiIzbris.pack();
			potrdiIzbris.setVisible(true);
		}
		else {
			List<List<String>> seznamElementovZaIzbris = new ArrayList<List<String>>();
			for (JCheckBox kljuc : slovarCheckBoxElement.keySet()){
				if (kljuc.isSelected()){
					seznamElementovZaIzbris.add(slovarCheckBoxElement.get(kljuc));
				}
			}
			
			podatki.izbrisiElemente(seznamElementovZaIzbris);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object vir = arg0.getSource();
		if (vir == mntmMojeZbirke){
			stanje = 0;
			zbirkaZaRisanje = null;
			setupUI();
		}
		else if (vir == mntmUvoziCsv){
			UvoziCsvWindow dialog = new UvoziCsvWindow();
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
		}
		else if (vir == mntmIzvoziCsv){
			IzvoziCsvWindow dialog = new IzvoziCsvWindow();
			dialog.setVisible(true);
		}
		else if (vir == mntmIzhod){
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		else if (vir == mntmUstvariNovoZbirko){
			NovaZbirkaWindow dialog = new NovaZbirkaWindow();
			dialog.setVisible(true);
		}
		else if (vir == mntmNovElement){
			NovElementWindow dialog = new NovElementWindow();
			dialog.setVisible(true);
		}
		else if (vir == mntmOmogociUrejanje){
			urejanjeOmogoceno = true;
			setupUI();
		}
		else if(vir == mntmKonajUrejanje){
			urejanjeOmogoceno = false;
			setupUI();
		}
		else if (vir == buttonShraniSpremembe){
			shraniUrejanje();
			setupUI();
		}
		else if (vir == btnPotrdi){
			podatki.izbrisiZbirke(seznamZbirkZaIzbris);
			potrdiIzbris.dispose();
		}
		else if (vir == btnPreklici){
			potrdiIzbris.dispose();
		}
		else if (slovarGumbov.keySet().contains(vir)){
			for (JButton gumb : slovarGumbov.keySet()){
				if (vir == gumb){
					zbirkaZaRisanje = slovarGumbov.get(gumb);
					stanje = 1;
					setupUI();
					break;
				}
			}
		}
	}
	
	/**
	 * Metoda, ki narise UI. To stori tako, da najprej pogleda, v katerem stanju smo
	 * in ali lahko urejamo ter se potem na podlagi teh podatkov odloci, kaj naj narise.
	 */
	public void setupUI(){
		contentPane.removeAll();
		
		if (urejanjeOmogoceno){
			mntmOmogociUrejanje.setEnabled(false);
			mntmKonajUrejanje.setEnabled(true);
		}
		else {
			mntmOmogociUrejanje.setEnabled(true);
			mntmKonajUrejanje.setEnabled(false);
		}
		
		if (stanje == 0){
			mojeZbirke();
		}
		else if (stanje == 1){
			narisiZbirko();
		}
		contentPane.updateUI();
		pack();
	}
	
	/**
	 * Metoda, ki narise moje zbirke, predstavljene z gumbi, ki potem odprejo posamezno zbirko.
	 * Ce je omogoceno urejanje, dodamo se JCheckBox-e, ki omogocajo izbiro zbirk za izbris.
	 */
	public void mojeZbirke(){
		podatki = new PripravljalecPodatkov();
		slovarSS = podatki.getSlovar();
		List<String> seznamZbirk = new ArrayList<String>(slovarSS.keySet());
		Collections.sort(seznamZbirk);
		slovarGumbov = new HashMap<JButton, String>();

		JLabel lblMojeZbirke = new JLabel("Moje zbirke");
		GridBagConstraints gbc_lblMojeZbirke = new GridBagConstraints();
		gbc_lblMojeZbirke.gridx = 0;
		gbc_lblMojeZbirke.gridy = 0;
		contentPane.add(lblMojeZbirke, gbc_lblMojeZbirke);
		
		int rowCounter = 0;
		for (String zbirka : seznamZbirk){
			rowCounter++;
			
			JButton button = new JButton(zbirka);
			button.addActionListener(this);
			GridBagConstraints gbc_button = new GridBagConstraints();
			gbc_button.insets = new Insets(0, 0, 5, 5);
			gbc_button.gridx = 0;
			gbc_button.gridy = rowCounter;
			gbc_button.fill = GridBagConstraints.HORIZONTAL;
			slovarGumbov.put(button, zbirka);
			contentPane.add(button, gbc_button);
		}
		
		if (urejanjeOmogoceno){
			slovarCheckBoxZbirka = new HashMap<JCheckBox, String>();
			
			JLabel izbrisi = new JLabel("Izbriši");
			GridBagConstraints gbc_izbrisi = new GridBagConstraints();
			gbc_izbrisi.insets = new Insets(0, 0, 5, 5);
			gbc_izbrisi.gridx = 1;
			gbc_izbrisi.gridy = 0;
			contentPane.add(izbrisi, gbc_izbrisi);
			
			for (rowCounter = 1; rowCounter <= seznamZbirk.size(); rowCounter++){
				JCheckBox box = new JCheckBox();
				GridBagConstraints gbc_box = new GridBagConstraints();
				gbc_box.insets = new Insets(0, 0, 5, 5);
				gbc_box.gridx = 1;
				gbc_box.gridy = rowCounter;
				slovarCheckBoxZbirka.put(box, seznamZbirk.get(rowCounter-1));
				contentPane.add(box, gbc_box);
			}
			
			buttonShraniSpremembe = new JButton("Shrani");
			buttonShraniSpremembe.addActionListener(this);
			GridBagConstraints gbc_buttonShraniSpremembe = new GridBagConstraints();
			gbc_buttonShraniSpremembe.insets = new Insets(0, 0, 5, 5);
			gbc_buttonShraniSpremembe.gridx = 1;
			gbc_buttonShraniSpremembe.gridy = rowCounter;
			contentPane.add(buttonShraniSpremembe, gbc_buttonShraniSpremembe);
		}
	}
	
	/**
	 * Metoda narise zbirko, ki je shranjena v zbirkaZaRisanje. Ce je omogoceno urejanje, doda se JCheckBoxe.
	 */
	public void narisiZbirko(){
		// TODO ENHANCEMENT Narisi zbirko s pomocjo JTable.
		assert zbirkaZaRisanje != null;
		
		List<List<String>> zbirka = slovarSS.get(zbirkaZaRisanje);
		
		JLabel naslovZbirke = new JLabel(zbirkaZaRisanje);
		GridBagConstraints gbc_naslovZbirke = new GridBagConstraints();
		gbc_naslovZbirke.anchor = GridBagConstraints.CENTER;
		gbc_naslovZbirke.insets = new Insets(0, 0, 5, 5);
		gbc_naslovZbirke.gridx = 1;
		gbc_naslovZbirke.gridy = 0;
		contentPane.add(naslovZbirke, gbc_naslovZbirke);
		
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
				gbc_napis.gridy = row+1;
				contentPane.add(napis, gbc_napis);
			}
		}
		
		if (urejanjeOmogoceno){
			int rowCounter;
			int column = zbirka.get(0).size();
			slovarCheckBoxElement = new HashMap<JCheckBox, List<String>>();
			
			JLabel izbrisi = new JLabel("Izbriši");
			GridBagConstraints gbc_izbrisi = new GridBagConstraints();
			gbc_izbrisi.insets = new Insets(0, 0, 5, 5);
			gbc_izbrisi.gridx = column;
			gbc_izbrisi.gridy = 1;
			contentPane.add(izbrisi, gbc_izbrisi);
			
			for (rowCounter = 2; rowCounter <= zbirka.size(); rowCounter++){
				JCheckBox box = new JCheckBox();
				box.addActionListener(this);
				GridBagConstraints gbc_box = new GridBagConstraints();
				gbc_box.insets = new Insets(0, 0, 5, 5);
				gbc_box.gridx = column;
				gbc_box.gridy = rowCounter;
				slovarCheckBoxElement.put(box, zbirka.get(rowCounter-1));
				contentPane.add(box, gbc_box);
			}
			
			buttonShraniSpremembe = new JButton("Shrani");
			buttonShraniSpremembe.addActionListener(this);
			GridBagConstraints gbc_buttonShraniSpremembe = new GridBagConstraints();
			gbc_buttonShraniSpremembe.insets = new Insets(0, 0, 5, 5);
			gbc_buttonShraniSpremembe.gridx = column;
			gbc_buttonShraniSpremembe.gridy = rowCounter+1;
			contentPane.add(buttonShraniSpremembe, gbc_buttonShraniSpremembe);
		}
	}
	
	/**
	 * @return zbirkaZaRisanje
	 */
	public static String getZbirkaZaRisanje() {
		return zbirkaZaRisanje;
	}

}