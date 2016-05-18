package gui;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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

@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener{
	
	private JPanel contentPane;
	private JMenuItem mntmMojeZbirke;
	private JMenuItem mntmUstvariNovoZbirko;
	private JMenuItem mntmIzhod;
	private Map<JButton, String> mnozicaGumbov;
	private Map<String, List<List<String>>> slovarSS;

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
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMeni = new JMenu("Meni");
		menuBar.add(mnMeni);
		
		mntmMojeZbirke = new JMenuItem("Moje zbirke");
		mntmMojeZbirke.addActionListener(this);
		mnMeni.add(mntmMojeZbirke);
		
		mntmUstvariNovoZbirko = new JMenuItem("Ustvari novo zbirko");
		mntmUstvariNovoZbirko.addActionListener(this);
		mnMeni.add(mntmUstvariNovoZbirko);
		
		mntmIzhod = new JMenuItem("Izhod");
		mntmIzhod.addActionListener(this);
		mnMeni.add(mntmIzhod);
		
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblMojeZbirke = new JLabel("Moje zbirke");
		GridBagConstraints gbc_lblMojeZbirke = new GridBagConstraints();
		gbc_lblMojeZbirke.gridx = 0;
		gbc_lblMojeZbirke.gridy = 0;
		contentPane.add(lblMojeZbirke, gbc_lblMojeZbirke);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object vir = arg0.getSource();
		if (vir == mntmMojeZbirke){
			mojeZbirke();
		}
		else if (vir == mntmUstvariNovoZbirko){
			NovaZbirkaWindow dialog = new NovaZbirkaWindow();
			dialog.setVisible(true);
		}
		else if (vir == mntmIzhod){
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		else {
			for (JButton gumb : mnozicaGumbov.keySet()){
				if (vir == gumb){
					narisiZbirko(mnozicaGumbov.get(gumb));
					break;
				}
			}
		}
	}
	
	public void mojeZbirke(){
		PripravljalecPodatkov podatki = new PripravljalecPodatkov();
		slovarSS = podatki.getSlovar();
		List<String> seznamZbirk = new ArrayList<String>(slovarSS.keySet());
		Collections.sort(seznamZbirk);
		mnozicaGumbov = new HashMap<JButton, String>();
		
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
			mnozicaGumbov.put(button, zbirka);
			contentPane.add(button, gbc_button);
			
			contentPane.updateUI();
		}
	}
	
	public void narisiZbirko(String imeZbirke){
		contentPane.removeAll();
		
		List<List<String>> zbirka = slovarSS.get(imeZbirke);
		
		JLabel naslovZbirke = new JLabel(imeZbirke);
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
		
		contentPane.updateUI();
	}

}