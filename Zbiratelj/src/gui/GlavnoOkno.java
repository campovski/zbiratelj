package gui;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

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



/**
 * @author nina
 * stanje 0: mojeZbirke
 * stanje 1: narisiZbirko
 */
@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener{
	
	private JPanel contentPane;
	
	private JMenuItem mntmMojeZbirke;
	private JMenu mnDodaj;
	private JMenuItem mntmUstvariNovoZbirko;
	private JMenuItem mntmOmogociUrejanje;
	private JMenuItem mntmKonajUrejanje;
	private JMenuItem mntmIzhod;
	
	private Map<JButton, String> slovarGumbov;
	private JButton buttonShraniSpremembe;
	private Map<String, List<List<String>>> slovarSS;
	private static Map<JCheckBox, String> slovarCheckBoxZbirka;
	private static Map<JCheckBox, List<String>> slovarCheckBoxElement;

	private boolean urejanjeOmogoceno = false;
	private int stanje;
	private String zbirkaZaRisanje = null;
	private JMenu mnUredi;
	private JMenuItem mntmNovElement;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GlavnoOkno frame = new GlavnoOkno();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GlavnoOkno() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMeni = new JMenu("Meni");
		menuBar.add(mnMeni);
		
		mntmMojeZbirke = new JMenuItem("Moje zbirke");
		mntmMojeZbirke.addActionListener(this);
		mnMeni.add(mntmMojeZbirke);
		
		mntmIzhod = new JMenuItem("Izhod");
		mntmIzhod.addActionListener(this);
		mnMeni.add(mntmIzhod);
		
		mnUredi = new JMenu("Uredi");
		menuBar.add(mnUredi);
		
		mnDodaj = new JMenu("Dodaj...");
		mnUredi.add(mnDodaj);
		
		mntmUstvariNovoZbirko = new JMenuItem("Nova zbirka");
		mnDodaj.add(mntmUstvariNovoZbirko);
		mntmUstvariNovoZbirko.addActionListener(this);
		
		mntmNovElement = new JMenuItem("Nov element");
		mnDodaj.add(mntmNovElement);
		mntmNovElement.addActionListener(this);
		//TODO metoda dodajNovElement in JDialog NovElementWindow
		
		mntmOmogociUrejanje = new JMenuItem("Omogoči urejanje");
		mnUredi.add(mntmOmogociUrejanje);
		mntmOmogociUrejanje.addActionListener(this);
		
		mntmKonajUrejanje = new JMenuItem("Končaj urejanje");
		mnUredi.add(mntmKonajUrejanje);
		mntmKonajUrejanje.addActionListener(this);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		stanje = 0;
		setupUI();
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object vir = arg0.getSource();
		if (vir == mntmMojeZbirke){
			stanje = 0;
			setupUI();
		}
		else if (vir == mntmUstvariNovoZbirko){
			NovaZbirkaWindow dialog = new NovaZbirkaWindow();
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
		else if (vir == mntmIzhod){
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		else if (vir == buttonShraniSpremembe){
			if (stanje == 0){
				JDialog potrdiIzbris = new JDialog();
				potrdiIzbris.setTitle("Opozorilo!");
				//TODO pozeni PripravljalecPodatkov.izbrisiZbirke() v novem vlaknu, da dobimo seznam zbirk, ki se izpisejo v opozorilu
				//ce smo v JDialog potrdili izbris, nadaljuj vlakno, drugace prekini
				
				JPanel contentPanel = new JPanel();
				GridBagLayout gbl_contentPanel = new GridBagLayout();
				gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
				gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
				gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
				gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				contentPanel.setLayout(gbl_contentPanel);
				potrdiIzbris.getContentPane().add(contentPanel);
				
				JLabel lbl = new JLabel("Ali ste prepričani, da želite odstraniti naslednje zbirke:");
				GridBagConstraints gbc_lbl = new GridBagConstraints();
				gbc_lbl.insets = new Insets(0, 0, 5, 5);
				gbc_lbl.gridx = 0;
				gbc_lbl.gridy = 0;
				contentPanel.add(lbl, gbc_lbl);
				
				potrdiIzbris.pack();
				potrdiIzbris.setVisible(true);
			}
			else {
				PripravljalecPodatkov.izbrisiElemente();
			}
			
			setupUI();
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
	
	public void mojeZbirke(){
		PripravljalecPodatkov podatki = new PripravljalecPodatkov();
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
			for (rowCounter = 1; rowCounter <= seznamZbirk.size(); rowCounter++){
				JCheckBox box = new JCheckBox();
				box.addActionListener(this);
				GridBagConstraints gbc_box = new GridBagConstraints();
				gbc_box.insets = new Insets(0, 0, 5, 5);
				gbc_box.gridx = 1;
				gbc_box.gridy = rowCounter;
				gbc_box.anchor = GridBagConstraints.WEST;
				slovarCheckBoxZbirka.put(box, seznamZbirk.get(rowCounter-1));
				contentPane.add(box, gbc_box);
			}
			
			buttonShraniSpremembe = new JButton("Shrani");
			buttonShraniSpremembe.addActionListener(this);
			GridBagConstraints gbc_buttonShraniSpremembe = new GridBagConstraints();
			gbc_buttonShraniSpremembe.insets = new Insets(0, 0, 5, 5);
			gbc_buttonShraniSpremembe.gridx = 1;
			gbc_buttonShraniSpremembe.gridy = rowCounter;
			gbc_buttonShraniSpremembe.anchor = GridBagConstraints.WEST;
			contentPane.add(buttonShraniSpremembe, gbc_buttonShraniSpremembe);
		}
	}
	
	public void narisiZbirko(){
		//TODO urejanje
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
			for (rowCounter = 2; rowCounter <= zbirka.size(); rowCounter++){
				JCheckBox box = new JCheckBox();
				box.addActionListener(this);
				GridBagConstraints gbc_box = new GridBagConstraints();
				gbc_box.insets = new Insets(0, 0, 5, 5);
				gbc_box.gridx = column;
				gbc_box.gridy = rowCounter;
				gbc_box.anchor = GridBagConstraints.WEST;
				slovarCheckBoxElement.put(box, zbirka.get(rowCounter-1));
				contentPane.add(box, gbc_box);
			}
			
			buttonShraniSpremembe = new JButton("Shrani");
			buttonShraniSpremembe.addActionListener(this);
			GridBagConstraints gbc_buttonShraniSpremembe = new GridBagConstraints();
			gbc_buttonShraniSpremembe.insets = new Insets(0, 0, 5, 5);
			gbc_buttonShraniSpremembe.gridx = column;
			gbc_buttonShraniSpremembe.gridy = rowCounter+1;
			gbc_buttonShraniSpremembe.anchor = GridBagConstraints.WEST;
			contentPane.add(buttonShraniSpremembe, gbc_buttonShraniSpremembe);
		}
	}


	public static Map<JCheckBox, String> getSlovarCheckBoxZbirka() {
		return slovarCheckBoxZbirka;
	}

	/**
	 * @return the slovarCheckBoxElement
	 */
	public static Map<JCheckBox, List<String>> getSlovarCheckBoxElement() {
		return slovarCheckBoxElement;
	}
}