package P2PSim;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JList;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JEditorPane;

public class TypeCreator extends JFrame {

	private JPanel contentPane;
	private DefaultListModel<Peer> peerModel;
	private JList<TorrentType> typeList = new JList<TorrentType>();
	private DefaultListModel<TorrentType> typeModel = new DefaultListModel<TorrentType>();
	private JTextField textField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TypeCreator frame = new TypeCreator();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TypeCreator() {

		setTitle("Type Creator");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 606, 372);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 147, 72, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 64, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		peerModel = new DefaultListModel<Peer>();
		for (Peer peer : Data.peers.values()) {
			peerModel.addElement(peer);
		}

		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.gridheight = 4;
		gbc_scrollPane_2.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 1;
		gbc_scrollPane_2.gridy = 1;
		panel.add(scrollPane_2, gbc_scrollPane_2);

		scrollPane_2.setViewportView(typeList);

		JLabel lblTorrentTypes = new JLabel("Global Torrent Types");
		lblTorrentTypes.setFont(new Font("Tahoma", Font.BOLD, 11));
		scrollPane_2.setColumnHeaderView(lblTorrentTypes);
		
		JLabel lblName = new JLabel("Name:");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 2;
		gbc_lblName.gridy = 1;
		panel.add(lblName, gbc_lblName);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 1;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblSizekb = new JLabel("Size (KB):");
		GridBagConstraints gbc_lblSizekb = new GridBagConstraints();
		gbc_lblSizekb.anchor = GridBagConstraints.EAST;
		gbc_lblSizekb.insets = new Insets(0, 0, 5, 5);
		gbc_lblSizekb.gridx = 2;
		gbc_lblSizekb.gridy = 2;
		panel.add(lblSizekb, gbc_lblSizekb);
		
		JSpinner spinner = new JSpinner();
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 3;
		gbc_spinner.gridy = 2;
		panel.add(spinner, gbc_spinner);
		
		JLabel lblSectionSizekb = new JLabel("Section Size(KB):");
		GridBagConstraints gbc_lblSectionSizekb = new GridBagConstraints();
		gbc_lblSectionSizekb.anchor = GridBagConstraints.EAST;
		gbc_lblSectionSizekb.insets = new Insets(0, 0, 5, 5);
		gbc_lblSectionSizekb.gridx = 2;
		gbc_lblSectionSizekb.gridy = 3;
		panel.add(lblSectionSizekb, gbc_lblSectionSizekb);
		
		JSpinner spinner_1 = new JSpinner();
		GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
		gbc_spinner_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_1.insets = new Insets(0, 0, 5, 5);
		gbc_spinner_1.gridx = 3;
		gbc_spinner_1.gridy = 3;
		panel.add(spinner_1, gbc_spinner_1);
		
		JLabel lblDescription = new JLabel("Description:");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 2;
		gbc_lblDescription.gridy = 4;
		panel.add(lblDescription, gbc_lblDescription);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 3;
		gbc_scrollPane.gridy = 4;
		panel.add(scrollPane, gbc_scrollPane);
		
		JEditorPane editorPane = new JEditorPane();
		scrollPane.setViewportView(editorPane);
		editorPane.setContentType("text/rtd");
		editorPane.setDragEnabled(true);

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.gridwidth = 3;
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 6;
		panel.add(panel_1, gbc_panel_1);

		updateTypes();
		setVisible(true);

	}
	
	void updateTypes() {
		typeModel.removeAllElements();
		for (TorrentType type : Data.torrentTypes.values())
			typeModel.addElement(type);
		typeList.setModel(typeModel);
	}
}
