package P2PSim;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Insets;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ImportWizardGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtFilepathHere;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImportWizardGUI frame = new ImportWizardGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ImportWizardGUI() {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		setTitle("Import Wizard");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JButton btnSelectFile = new JButton("Select File..");

		GridBagConstraints gbc_btnSelectFile = new GridBagConstraints();
		gbc_btnSelectFile.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelectFile.gridx = 1;
		gbc_btnSelectFile.gridy = 1;
		panel.add(btnSelectFile, gbc_btnSelectFile);

		txtFilepathHere = new JTextField();
		txtFilepathHere.setText("Filepath Here");
		GridBagConstraints gbc_txtFilepathHere = new GridBagConstraints();
		gbc_txtFilepathHere.gridwidth = 2;
		gbc_txtFilepathHere.insets = new Insets(0, 0, 5, 5);
		gbc_txtFilepathHere.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFilepathHere.gridx = 2;
		gbc_txtFilepathHere.gridy = 1;
		panel.add(txtFilepathHere, gbc_txtFilepathHere);
		txtFilepathHere.setColumns(10);

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_1.gridwidth = 3;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 2;
		panel.add(panel_1, gbc_panel_1);

		JCheckBox chckbxMergeTorrents = new JCheckBox("Merge Torrents");
		panel_1.add(chckbxMergeTorrents);

		JRadioButton rdbtnById = new JRadioButton("By ID");
		panel_1.add(rdbtnById);
		rdbtnById.setSelected(true);
		rdbtnById.setEnabled(false);

		JRadioButton rdbtnByName = new JRadioButton("By Name");
		panel_1.add(rdbtnByName);
		rdbtnByName.setEnabled(false);

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_2.gridwidth = 3;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 3;
		panel.add(panel_2, gbc_panel_2);

		JCheckBox chckbxMergePeers = new JCheckBox("Merge Peers");
		panel_2.add(chckbxMergePeers);

		JRadioButton rdbtnById_1 = new JRadioButton("By ID");
		rdbtnById_1.setEnabled(false);
		rdbtnById_1.setSelected(true);
		panel_2.add(rdbtnById_1);
		
		JButton btnImport = new JButton("Import..");
		btnImport.setEnabled(false);
		GridBagConstraints gbc_btnImport = new GridBagConstraints();
		gbc_btnImport.insets = new Insets(0, 0, 0, 5);
		gbc_btnImport.gridx = 2;
		gbc_btnImport.gridy = 7;
		panel.add(btnImport, gbc_btnImport);

		chckbxMergeTorrents.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				rdbtnById.setEnabled(chckbxMergeTorrents.isSelected());
				rdbtnByName.setEnabled(chckbxMergeTorrents.isSelected());
			}
		});
		chckbxMergePeers.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				rdbtnById_1.setEnabled(chckbxMergePeers.isSelected());
			}
		});
		rdbtnByName.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				rdbtnById.setSelected(!rdbtnByName.isSelected());
			}
		});
		rdbtnById.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				rdbtnByName.setSelected(!rdbtnById.isSelected());
			}
		});
		btnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Access Databases", "accdb");
				chooser.addChoosableFileFilter(filter);
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					txtFilepathHere.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
	}
}
