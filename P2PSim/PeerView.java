package P2PSim;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.ScrollPane;

public class PeerView extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PeerView frame = new PeerView();
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
	public PeerView() {
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


		JTree tree = new JTree();
		tree.setEditable(true);
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Peers") {
			{
			}
		}));
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

		for (Peer peer : Data.peers.values()) {
			model.insertNodeInto(new DefaultMutableTreeNode(peer.toString()), (MutableTreeNode) model.getRoot(), 0);
			model.insertNodeInto(new DefaultMutableTreeNode("Torrent Instances"), (MutableTreeNode) model.getChild(model.getRoot(), 0), 0);
			for (TorrentInstance instance : Data.torrentInstances.values()) {
				if(peer.torrents.contains(instance.ID))
					model.insertNodeInto(new DefaultMutableTreeNode(instance), (MutableTreeNode) model.getChild(model.getChild(model.getRoot(), 0), 0), 0);
			}
		}
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		ScrollPane scrollPane = new ScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		scrollPane.add(tree, ScrollPane.CENTER_ALIGNMENT);
		
		setVisible(true);
	}

}
