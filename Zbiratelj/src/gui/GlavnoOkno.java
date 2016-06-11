package gui;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import baza.SqlManager;

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
 */

@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener{
	// TODO stolpci so vedno urejeni po abecednem vrstnem redu (optional)
	// TODO utf8 (optional)
	// TODO Search (optional)
	
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
	private static Map<JCheckBox, String> slovarCheckBoxZbirka;

	private boolean urejanjeOmogoceno = false;

	private JButton btnPotrdi;
	private JButton btnPreklici;
	private ArrayList<String> seznamZbirkZaIzbris;
	private JDialog potrdiIzbris;
	private static String zbirkaZaRisanje = null;
	private JSeparator separator;
	private JSeparator separator_1;

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
		
		setTitle("Zbiratelj");
		
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
		add(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		setupUI();
		pack();
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
		
		mojeZbirke();
		pack();
	}
	
	/**
	 * Metoda, ki nas vprasa, ali smo prepricani, da zelimo izbrisati izbrane zbirke.
	 */
	private void shraniUrejanje(){
		seznamZbirkZaIzbris = new ArrayList<String>();
		for (JCheckBox kljuc : slovarCheckBoxZbirka.keySet()){
			if (kljuc.isSelected()){
				seznamZbirkZaIzbris.add(slovarCheckBoxZbirka.get(kljuc));
			}
		}
		
		potrdiIzbris = new JDialog(SwingUtilities.windowForComponent(this));
		potrdiIzbris.setModal(true);
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
	
	/**
	 * Metoda, ki narise moje zbirke, predstavljene z gumbi, ki potem odprejo posamezno zbirko.
	 * Ce je omogoceno urejanje, dodamo se JCheckBox-e, ki omogocajo izbiro zbirk za izbris.
	 */
	public void mojeZbirke(){
		List<String> seznamZbirk = SqlManager.beriBazo();
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
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object vir = arg0.getSource();
		if (vir == mntmMojeZbirke){
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
			for (String zbirka : seznamZbirkZaIzbris){
				SqlManager.izbrisiZbirko(zbirka);
			}
			potrdiIzbris.dispose();
			setupUI();
		}
		else if (vir == btnPreklici){
			potrdiIzbris.dispose();
		}
		else if (slovarGumbov.keySet().contains(vir)){
			for (JButton gumb : slovarGumbov.keySet()){
				if (vir == gumb){
					zbirkaZaRisanje = slovarGumbov.get(gumb);
					NarisiZbirkoWindow dialog = new NarisiZbirkoWindow(zbirkaZaRisanje);
					dialog.setVisible(true);
					
					setupUI();
					break;
				}
			}
		}
	}
}