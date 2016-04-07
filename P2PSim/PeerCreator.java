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

public class PeerCreator extends JFrame {

	private JPanel contentPane;
	private JList<TorrentInstance> instanceList = new JList<TorrentInstance>();
	private DefaultListModel<Peer> peerModel;
	private JList<Peer> peerList = new JList<Peer>();
	private JList<TorrentType> typeList = new JList<TorrentType>();
	private DefaultListModel<TorrentType> typeModel = new DefaultListModel<TorrentType>();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PeerCreator frame = new PeerCreator();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PeerCreator() {

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

		setTitle("Peer Creator");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 606, 372);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 147, 147, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 64, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		panel.add(scrollPane, gbc_scrollPane);

		peerList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				updateInstances();
			}
		});
		scrollPane.setViewportView(peerList);

		JLabel lblPeers_1 = new JLabel("Peers");
		lblPeers_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		scrollPane.setColumnHeaderView(lblPeers_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 2;
		gbc_scrollPane_1.gridy = 1;
		panel.add(scrollPane_1, gbc_scrollPane_1);

		peerModel = new DefaultListModel<Peer>();
		for (Peer peer : Data.peers.values()) {
			peerModel.addElement(peer);
		}

		peerList.setModel(peerModel);
		scrollPane_1.setViewportView(instanceList);

		JLabel lblTorrentInstances_1 = new JLabel("Torrent Instances (within selected peer/s)");
		lblTorrentInstances_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		scrollPane_1.setColumnHeaderView(lblTorrentInstances_1);

		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 4;
		gbc_scrollPane_2.gridy = 1;
		panel.add(scrollPane_2, gbc_scrollPane_2);

		scrollPane_2.setViewportView(typeList);

		JLabel lblTorrentTypes = new JLabel("Global Torrent Types");
		lblTorrentTypes.setFont(new Font("Tahoma", Font.BOLD, 11));
		scrollPane_2.setColumnHeaderView(lblTorrentTypes);

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.gridwidth = 4;
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 3;
		panel.add(panel_1, gbc_panel_1);

		JButton btnNewPeer = new JButton("New Peer");
		btnNewPeer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Peer peer = new Peer();
				Data.peers.put(peer.ID, peer);
				peerModel.addElement(peer);
			}
		});
		panel_1.add(btnNewPeer);

		JButton btnDeletePeer = new JButton("Delete Peer");
		btnDeletePeer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Peer> killList = peerList.getSelectedValuesList();
				peerList.setSelectedIndex(-1);
				for (Peer kill : killList) {
					for(TorrentInstance instance: Data.torrentInstances.values()){
						if(kill.torrents.contains(instance.ID))
							Data.torrentInstances.remove(instance.ID);
					}
					
					Data.peers.remove(kill.ID);
					peerModel.removeElement(kill);
				}
				updateInstances();
			}
		});
		panel_1.add(btnDeletePeer);

		JButton btnRemoveSelected = new JButton("Remove Selected Instance");
		panel_1.add(btnRemoveSelected);

		JCheckBox chckbxTorrentComplete = new JCheckBox("Torrent Complete");
		panel_1.add(chckbxTorrentComplete);
		
		JButton btnAddSelectedTorrent = new JButton("Add Selected Torrent Type/s");
		btnAddSelectedTorrent.addActionListener(new ActionListener() {
			TorrentInstance out;
			public void actionPerformed(ActionEvent e) {
				for(Peer peer: peerList.getSelectedValuesList()){
					for(TorrentType type: typeList.getSelectedValuesList())
						out = new TorrentInstance(type, chckbxTorrentComplete.isSelected());
						Data.add(out);
						peer.add(out);
				}
				updateInstances();
			}
		});
		panel_1.add(btnAddSelectedTorrent);

		updateTypes();
		updateInstances();
		setVisible(true);

		peerList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_DELETE)
					btnDeletePeer.doClick();
			}
		});

	}

	void updateAll(){
		updatePeers();
		updateInstances();
		updateTypes();
		
	}
	
	void updateInstances() {
		DefaultListModel<TorrentInstance> instances = new DefaultListModel<TorrentInstance>();
		if (peerList.getSelectedValuesList().isEmpty())
			for (TorrentInstance instance : Data.torrentInstances.values()) {
				instances.addElement(instance);
			}
		else
			for (TorrentInstance instance : Data.torrentInstances.values()) {
				for (Peer peer : peerList.getSelectedValuesList())
					if (peer.torrents.contains(instance.ID))
						instances.addElement(instance);
			}
		instanceList.setModel(instances);
	}

	void updatePeers() {
		peerModel = new DefaultListModel<Peer>();
		for (Peer peer : Data.peers.values()) {
			peerModel.addElement(peer);
		}
		peerList.setModel(peerModel);
	}

	void updateTypes() {
		typeModel.removeAllElements();
		for (TorrentType type : Data.torrentTypes.values())
			typeModel.addElement(type);
		typeList.setModel(typeModel);
	}
}
