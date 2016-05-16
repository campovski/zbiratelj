package gui;


import javax.swing.JDialog;
import javax.swing.JPanel;
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

@SuppressWarnings("serial")
public class NovaZbirkaWindow extends JDialog implements ActionListener{
	private JTextField textField;
	private List<JTextField> arrTextField;
	private JButton btnShrani;
	private JPanel contentPanel;
	private int rowCounter = 2;
	private JButton button;


	/**
	 * Create the dialog.
	 */
	public NovaZbirkaWindow() {
		setSize(500, 500);
		arrTextField = new ArrayList<JTextField>();
		
		contentPanel = new JPanel();
		getContentPane().add(contentPanel);
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
		gbc_lblImeZbirke.gridx = 1;
		gbc_lblImeZbirke.gridy = 0;
		contentPanel.add(lblImeZbirke, gbc_lblImeZbirke);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 0;
		contentPanel.add(textField, gbc_textField);
		textField.setColumns(10);
		
		button = new JButton("+");
		button.addActionListener(this);
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 0;
		gbc_button.gridy = rowCounter;
		contentPanel.add(button, gbc_button);
		
		JLabel lblStolpec = new JLabel("Stolpec 1");
		GridBagConstraints gbc_lblStolpec = new GridBagConstraints();
		gbc_lblStolpec.anchor = GridBagConstraints.EAST;
		gbc_lblStolpec.insets = new Insets(0, 0, 5, 5);
		gbc_lblStolpec.gridx = 1;
		gbc_lblStolpec.gridy = rowCounter;
		contentPanel.add(lblStolpec, gbc_lblStolpec);
		
		JTextField textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = rowCounter;
		contentPanel.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		btnShrani = new JButton("Shrani");
		GridBagConstraints gbc_btnShrani = new GridBagConstraints();
		gbc_btnShrani.gridx = 2;
		gbc_btnShrani.gridy = rowCounter+1;
		contentPanel.add(btnShrani, gbc_btnShrani);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object vir = e.getSource();
		if (vir == button){
			rowCounter++;
			
			contentPanel.remove(button);
			GridBagConstraints gbc_button = new GridBagConstraints();
			gbc_button.insets = new Insets(0, 0, 0, 5);
			gbc_button.gridx = 0;
			gbc_button.gridy = rowCounter;
			gbc_button.anchor = GridBagConstraints.NORTH;
			contentPanel.add(button, gbc_button);
			
			JLabel lblStolpec = new JLabel("Stolpec "+(rowCounter-1));
			GridBagConstraints gbc_lblStolpec = new GridBagConstraints();
			gbc_lblStolpec.insets = new Insets(0, 0, 0, 5);
			gbc_lblStolpec.gridx = 1;
			gbc_lblStolpec.gridy = rowCounter;
			gbc_lblStolpec.anchor = GridBagConstraints.NORTH;
			contentPanel.add(lblStolpec, gbc_lblStolpec);
			
			JTextField textField = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 2;
			gbc_textField.gridy = rowCounter;
			gbc_textField.anchor = GridBagConstraints.NORTH;
			contentPanel.add(textField, gbc_textField);
			textField.setColumns(4);
			arrTextField.add(textField);
			
			GridBagConstraints gbc_btnShrani = new GridBagConstraints();
			gbc_btnShrani.gridx = 2;
			gbc_btnShrani.gridy = rowCounter+1;
			gbc_btnShrani.anchor = GridBagConstraints.NORTH;
			contentPanel.add(btnShrani, gbc_btnShrani);
			
			contentPanel.revalidate();
			contentPanel.repaint();
			System.out.println(arrTextField);
		}
		else if (vir == btnShrani){
			
		}
	}

}
