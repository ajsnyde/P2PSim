package P2PSim;

import java.awt.EventQueue;
import java.awt.Frame;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;

public class P2PGUI {

	private JFrame frmPpsim;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					P2PGUI window = new P2PGUI();
					window.frmPpsim.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public P2PGUI() {
		initialize();
	}

	private void initialize() {
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		frmPpsim = new JFrame();
		frmPpsim.setTitle("P2PSim");
		frmPpsim.setBounds(100, 100, 669, 464);
		frmPpsim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPpsim.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frmPpsim.getContentPane().add(panel, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		frmPpsim.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmImportAccessDb = new JMenuItem("Import Access DB...");
		mntmImportAccessDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "Access Databases", "accdb");
			    chooser.setMultiSelectionEnabled(true);
			    chooser.addChoosableFileFilter(filter);
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       File[] files = chooser.getSelectedFiles();
			       DataLoader.loadAccess(chooser.getSelectedFile());
			       DataLoader.main(files);
			    }
			}
		});
		mnFile.add(mntmImportAccessDb);
		
		JMenuItem mntmImportDefaultDb = new JMenuItem("Import Default DB");
		mntmImportDefaultDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File[] in = new File[1];
				in[0] = new File("P2P.accdb");
				DataLoader.main(in);
				// allows for multiple access files to be added at once, while using a new thread - no waiting
			}
		});
		mnFile.add(mntmImportDefaultDb);
		
		JMenuItem mntmImportWizard = new JMenuItem("Import Wizard...");
		mntmImportWizard.setEnabled(false);
		mnFile.add(mntmImportWizard);
		
		JMenu mnCreate = new JMenu("Create");
		menuBar.add(mnCreate);
		
		JMenuItem mntmPeer = new JMenuItem("Peer/s");
		mntmPeer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new PeerCreator();
			}
		});
		mnCreate.add(mntmPeer);
		
		JMenuItem mntmSingleTorrent = new JMenuItem("Single Torrent");
		mnCreate.add(mntmSingleTorrent);
		
		JMenuItem mntmType = new JMenuItem("Type");
		mntmType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TypeCreator();
			}
		});
		mnCreate.add(mntmType);
		
		JMenu mnOutput = new JMenu("Output");
		menuBar.add(mnOutput);
		
		JMenuItem mntmPeersconsole = new JMenuItem("Peers (Console)");
		mntmPeersconsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Peer peer: Data.peers.values())
					peer.printFull();
			}
		});
		mnOutput.add(mntmPeersconsole);
		
		JMenuItem mntmTorrentInstancesconsole = new JMenuItem("Torrent Instances (Console)");
		mntmTorrentInstancesconsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(TorrentInstance i: Data.torrentInstances.values())
					System.out.println(i);
			}
		});
		mnOutput.add(mntmTorrentInstancesconsole);
		
		JMenuItem mntmTorrentTypesconsole = new JMenuItem("Torrent Types (Console)");
		mntmTorrentTypesconsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(TorrentType type: Data.torrentTypes.values())
					System.out.println(type);
			}
		});
		mnOutput.add(mntmTorrentTypesconsole);
		
		JMenuItem mntmPossibleConnectionsconsole = new JMenuItem("Possible Connections (Console)");
		mntmPossibleConnectionsconsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Peer b : Data.peers.values()) {
					b.getPossiblePeers();
				}
			}
		});
		mnOutput.add(mntmPossibleConnectionsconsole);
		
		JMenuItem mntmPeerstree = new JMenuItem("Peers (Tree)");
		mntmPeerstree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PeerView();
			}
		});
		mnOutput.add(mntmPeerstree);
	}
	
	public static void updateAllLists() {
		
		/*
		 * "Anytime you find yourself writing code of the form "if the object is of type T1, 
		 * then do something, but if it's of type T2, then do something else," slap yourself.
		 */
		
		List<Frame> f = Arrays.asList(JFrame.getFrames());
		//Filter down to PeerCreators from Frames
		
		for(Frame frame: f) {
			if(frame instanceof PeerCreator)
				((PeerCreator) frame).updateAll();
			else if(frame instanceof TypeCreator)
				((TypeCreator) frame).updateTypes();
			else if(frame instanceof PeerView)
				((PeerView) frame).updatePeers();
		}
	}
}
