package P2PSim;

import java.awt.EventQueue;

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
			    chooser.addChoosableFileFilter(filter);
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       System.out.println("You chose to open this file: " +
			            chooser.getSelectedFile().getName());
			       DataLoader.loadAccess(chooser.getSelectedFile());
			    }
			    
			}
		});
		mnFile.add(mntmImportAccessDb);
		
		JMenuItem mntmImportDefaultDb = new JMenuItem("Import Default DB");
		mntmImportDefaultDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataLoader.loadAccess(new File("P2P.accdb"));
			}
		});
		mnFile.add(mntmImportDefaultDb);
		
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
	}

}
