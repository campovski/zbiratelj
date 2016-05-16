package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

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
		mnMeni.add(mntmMojeZbirke);
		
		mntmUstvariNovoZbirko = new JMenuItem("Ustvari novo zbirko");
		mnMeni.add(mntmUstvariNovoZbirko);
		
		mntmIzhod = new JMenuItem("Izhod");
		mntmIzhod.addActionListener(this);
		mnMeni.add(mntmIzhod);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
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
		if (vir == mntmIzhod){
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		else if (vir == mntmUstvariNovoZbirko){
		}
		
	}

}
