package gui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import baza.SqlManager;
import io.CsvManager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class IzvoziCsvWindow extends JDialog implements ActionListener{

	private JTextField textFieldDatoteka;
	private JPanel contentPanel;
	private Map<JCheckBox, String> slovarCheckBoxZbirka;
	private JButton izvozi;

	/**
	 * Naredi JDialog, v katerem izberemo s pomocjo JCheckBox-ov zbirke, ki jih zelimo izvoziti.
	 */
	public IzvoziCsvWindow() {
		contentPanel = new JPanel();
		getContentPane().add(contentPanel);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE};
		contentPanel.setLayout(gridBagLayout);
		
		JLabel lblShraniV = new JLabel("Shrani v");
		GridBagConstraints gbc_lblShraniV = new GridBagConstraints();
		gbc_lblShraniV.anchor = GridBagConstraints.EAST;
		gbc_lblShraniV.insets = new Insets(0, 0, 5, 5);
		gbc_lblShraniV.gridx = 0;
		gbc_lblShraniV.gridy = 0;
		gbc_lblShraniV.weighty = 1;
		contentPanel.add(lblShraniV, gbc_lblShraniV);
		
		textFieldDatoteka = new JTextField();
		textFieldDatoteka.setColumns(30);
		GridBagConstraints gbc_textFieldDatoteka = new GridBagConstraints();
		gbc_textFieldDatoteka.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldDatoteka.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDatoteka.gridx = 1;
		gbc_textFieldDatoteka.gridy = 0;
		gbc_textFieldDatoteka.gridwidth = 2;
		textFieldDatoteka.addMouseListener(new MouseAdapter(){
			private File datoteka;

			@Override
			public void mouseClicked(MouseEvent e){
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setAcceptAllFileFilterUsed(false);
				int result = fileChooser.showOpenDialog(contentPanel);
				if (result == JFileChooser.APPROVE_OPTION){
					datoteka = fileChooser.getSelectedFile();
					textFieldDatoteka.setText(datoteka.getAbsolutePath());
				}
			}
		});
		contentPanel.add(textFieldDatoteka, gbc_textFieldDatoteka);

		slovarCheckBoxZbirka = new HashMap<JCheckBox, String>();
		int rowCounter = 0;
		
		for (String zbirka : SqlManager.beriBazo()){
			rowCounter++;
			
			JLabel lblZbirka = new JLabel(zbirka);
			GridBagConstraints gbc_lblZbirka = new GridBagConstraints();
			gbc_lblZbirka.anchor = GridBagConstraints.EAST;
			gbc_lblZbirka.insets = new Insets(0, 0, 5, 5);
			gbc_lblZbirka.gridx = 0;
			gbc_lblZbirka.gridy = rowCounter;
			gbc_lblZbirka.weighty = 1;
			contentPanel.add(lblZbirka, gbc_lblZbirka);
			
			JCheckBox box = new JCheckBox();
			GridBagConstraints gbc_box = new GridBagConstraints();
			gbc_box.insets = new Insets(0, 0, 5, 5);
			gbc_box.gridx = 1;
			gbc_box.gridy = rowCounter;
			slovarCheckBoxZbirka.put(box, zbirka);
			contentPanel.add(box, gbc_box);
		}
		
		izvozi = new JButton("Izvozi");
		izvozi.addActionListener(this);
		GridBagConstraints gbc_izvozi = new GridBagConstraints();
		gbc_izvozi.insets = new Insets(0, 0, 5, 5);
		gbc_izvozi.gridx = 1;
		gbc_izvozi.gridy = rowCounter + 1;
		gbc_izvozi.fill = GridBagConstraints.HORIZONTAL;
		contentPanel.add(izvozi, gbc_izvozi);
		
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object vir = arg0.getSource();
		if (vir == izvozi){
			CsvManager csv = new CsvManager();
			List<String> zbirke = new ArrayList<String>();
			for (JCheckBox gumb : slovarCheckBoxZbirka.keySet()){
				if (gumb.isSelected()){
					zbirke.add(slovarCheckBoxZbirka.get(gumb));
				}
			}
			String naslovDirektorija = textFieldDatoteka.getText();
			
			try {
				csv.narediCsv(naslovDirektorija, zbirke);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		dispose();
	}

}
