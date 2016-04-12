package P2PSim;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

		/*
		 * Index: 1. Most Objects + Properties 2. ActionListener Creation 3.
		 * Adding ActionListeners to Objects 4. Methods
		 */

		setTitle("Peer Creator");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 606, 372);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel mainPanel = new JPanel();
		contentPane.add(mainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[] { 0, 147, 147, 0, 0, 0, 0 };
		gbl_mainPanel.rowHeights = new int[] { 0, 0, 0, 64, 0, 0 };
		gbl_mainPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_mainPanel.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		mainPanel.setLayout(gbl_mainPanel);

		JScrollPane scrollPanePeers = new JScrollPane();
		GridBagConstraints gbc_scrollPanePeers = new GridBagConstraints();
		gbc_scrollPanePeers.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPanePeers.fill = GridBagConstraints.BOTH;
		gbc_scrollPanePeers.gridx = 1;
		gbc_scrollPanePeers.gridy = 1;
		mainPanel.add(scrollPanePeers, gbc_scrollPanePeers);

		JPanel buttonPanel = new JPanel();
		GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
		gbc_buttonPanel.insets = new Insets(0, 0, 5, 5);
		gbc_buttonPanel.gridwidth = 4;
		gbc_buttonPanel.fill = GridBagConstraints.BOTH;
		gbc_buttonPanel.gridx = 1;
		gbc_buttonPanel.gridy = 3;
		mainPanel.add(buttonPanel, gbc_buttonPanel);

		JCheckBox chckbxTorrentComplete = new JCheckBox("Torrent Complete");
		buttonPanel.add(chckbxTorrentComplete);

		scrollPanePeers.setViewportView(peerList);

		// PEER MENU

		JMenuItem removePeerMenu = new JMenuItem("Remove");
		JMenuItem addTypePeerMenu = new JMenuItem("Add Selected Types");
		JMenuItem renamePeerMenu = new JMenuItem("Rename");
		JMenuItem modifyPeerMenu = new JMenuItem("Modify");
		JMenuItem newPeerMenu = new JMenuItem("New Peer");
		modifyPeerMenu.setEnabled(false);
		JMenuItem propertiesPeerMenu = new JMenuItem("Properties..");
		propertiesPeerMenu.setEnabled(false);

		JPopupMenu peerPopup = new JPopupMenu();
		peerPopup.add(newPeerMenu);
		peerPopup.add(removePeerMenu);
		peerPopup.add(addTypePeerMenu);
		peerPopup.add(modifyPeerMenu);
		peerPopup.add(renamePeerMenu);
		peerPopup.add(propertiesPeerMenu);

		peerList.setComponentPopupMenu(peerPopup);

		// INSTANCE MENU

		JMenuItem removeInstanceMenu = new JMenuItem("Remove");
		JMenuItem editInstanceMenu = new JMenuItem("Edit");
		editInstanceMenu.setEnabled(false);

		JPopupMenu instancePopup = new JPopupMenu();
		instancePopup.add(removeInstanceMenu);
		instancePopup.add(editInstanceMenu);
		instanceList.setComponentPopupMenu(instancePopup);

		// TYPE MENU

		JMenuItem removeTypeMenu = new JMenuItem("Remove");
		JMenuItem createTypeMenu = new JMenuItem("New..");
		removeTypeMenu.setEnabled(false);

		JPopupMenu typePopup = new JPopupMenu();
		typePopup.add(removeTypeMenu);
		typePopup.add(createTypeMenu);
		typeList.setComponentPopupMenu(typePopup);

		JLabel lblPeers_1 = new JLabel("Peers");
		lblPeers_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		scrollPanePeers.setColumnHeaderView(lblPeers_1);

		JScrollPane scrollPaneInstances = new JScrollPane();
		GridBagConstraints gbc_scrollPaneInstances = new GridBagConstraints();
		gbc_scrollPaneInstances.gridwidth = 2;
		gbc_scrollPaneInstances.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneInstances.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneInstances.gridx = 2;
		gbc_scrollPaneInstances.gridy = 1;
		mainPanel.add(scrollPaneInstances, gbc_scrollPaneInstances);

		peerModel = new DefaultListModel<Peer>();
		peerList.setModel(peerModel);
		scrollPaneInstances.setViewportView(instanceList);

		JLabel lblTorrentInstances_1 = new JLabel("Torrent Instances (within selected peer/s)");
		lblTorrentInstances_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		scrollPaneInstances.setColumnHeaderView(lblTorrentInstances_1);

		JScrollPane scrollPaneTypes = new JScrollPane();
		GridBagConstraints gbc_scrollPaneTypes = new GridBagConstraints();
		gbc_scrollPaneTypes.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneTypes.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneTypes.gridx = 4;
		gbc_scrollPaneTypes.gridy = 1;
		mainPanel.add(scrollPaneTypes, gbc_scrollPaneTypes);
		scrollPaneTypes.setViewportView(typeList);

		JLabel lblTorrentTypes = new JLabel("Global Torrent Types");
		lblTorrentTypes.setFont(new Font("Tahoma", Font.BOLD, 11));
		scrollPaneTypes.setColumnHeaderView(lblTorrentTypes);

		JButton btnNewPeer = new JButton("New Peer");
		buttonPanel.add(btnNewPeer);

		JButton btnDeletePeer = new JButton("Delete Peer");
		buttonPanel.add(btnDeletePeer);

		JButton btnRemoveSelected = new JButton("Remove Selected Instance");
		buttonPanel.add(btnRemoveSelected);

		JButton btnAddSelectedTorrent = new JButton("Add Selected Torrent Type/s");
		buttonPanel.add(btnAddSelectedTorrent);

		peerList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				updateInstances();
			}
		});

		ActionListener createPeer = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Peer peer = new Peer();
				Data.peers.put(peer.ID, peer);
				peerModel.addElement(peer);
			}
		};

		ActionListener removePeer = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Peer> killList = peerList.getSelectedValuesList();
				peerList.setSelectedIndex(-1);
				for (Peer kill : killList) {
					for (TorrentInstance instance : Data.torrentInstances.values()) {
						if (kill.torrents.contains(instance.ID))
							Data.torrentInstances.remove(instance.ID);
					}

					Data.peers.remove(kill.ID);
					peerModel.removeElement(kill);
				}
				updateInstances();
			}
		};

		ActionListener renamePeer = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Peer> renameList = peerList.getSelectedValuesList();
				String name = JOptionPane.showInputDialog("Please input name: ");
				for (Peer peer : renameList)
					peer.name = name;
				updatePeers();
			}
		};

		ActionListener removeInstance = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<TorrentInstance> instances = (List<TorrentInstance>) instanceList.getSelectedValuesList();
				instanceList.setSelectedIndex(-1);

				for (TorrentInstance instance : instances) {
					peerModel.removeElement(instance);
					Data.getPeer(instance).torrents.remove((Integer) instance.ID); // remove
																					// instance
																					// from
																					// its
																					// peer

					// something on this line kills the program, related to
					// toString for Instance
					// Data.getPeer(instance).torrentTypes.remove((Integer)instance.TYPE);

					Data.torrentInstances.remove(instance.ID);
				}
				updateInstances();
			}
		};

		ActionListener addTorrent = new ActionListener() {
			TorrentInstance out;

			public void actionPerformed(ActionEvent e) {
				for (Peer peer : peerList.getSelectedValuesList()) {
					for (TorrentType type : typeList.getSelectedValuesList()) {
						if (!peer.torrentTypes.contains(type.ID)) {
							out = new TorrentInstance(type, chckbxTorrentComplete.isSelected());
							Data.add(out);
							peer.add(out);
						}
					}
				}
				updateInstances();
			}
		};

		ActionListener createType = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TypeCreator();
			}
		};

		// Allows right-click to select and create popup menu
		MouseListener select = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e) && ((JList<?>) e.getSource()).getSelectedIndices().length < 2) {
					JList<?> list = (JList<?>) e.getSource();
					int row = list.locationToIndex(e.getPoint());
					list.setSelectedIndex(row);
				}
			}
		};

		// Adding all listeners
		btnNewPeer.addActionListener(createPeer);
		btnDeletePeer.addActionListener(removePeer);
		btnAddSelectedTorrent.addActionListener(addTorrent);
		renamePeerMenu.addActionListener(renamePeer);
		removePeerMenu.addActionListener(removePeer);
		addTypePeerMenu.addActionListener(addTorrent);
		removeInstanceMenu.addActionListener(removeInstance);
		newPeerMenu.addActionListener(createPeer);
		createTypeMenu.addActionListener(createType);
		peerList.addMouseListener(select);
		instanceList.addMouseListener(select);
		typeList.addMouseListener(select);

		KeyAdapter deleteKey = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_DELETE){
					if (arg0.getSource() == peerList)
						removePeer.actionPerformed(null);
					if (arg0.getSource() == instanceList)
						removeInstance.actionPerformed(null);	
				}
			}
		};
		
		peerList.addKeyListener(deleteKey);
		instanceList.addKeyListener(deleteKey);

		updateAll();
		setVisible(true);
	}

	void updateAll() {
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
