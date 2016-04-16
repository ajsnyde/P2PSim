package P2PSim;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Dialog.ModalityType;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class DataLoader {
	static LoaderGUI loader = new LoaderGUI();;
	private static int numImports = 1;
	
	void Dataloader(){
		
	}
	
	public static void main(File[] args) {	// note that this main takes Files, not Strings
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				numImports = args.length;
				// Prevent playing with other windows while loading... It works, but doesn't prevent clicks.
				// i.e. - while the concurrent actions are prevented, a clicked button will click itself after loading.
				// This is funny because other JDialogs I've used modally have had other windows flashed on attempts to focus
				loader.setModal(true);
				loader.setAlwaysOnTop(true);
				loader.setModalityType (ModalityType.APPLICATION_MODAL);
				 
				loader.setVisible(true);
				loader.addMsg("Starting Imports...");
				loader.progressBar.setValue(1);
				for(int i = 0; i < args.length; ++i){
					loadAccess(args[i]);
					loader.setTitle("Loading Progress - " + i + "/" + args.length + " Complete");
				}

				loader.setTitle("Loading Progress - " + args.length + "/" + args.length + " Complete");
			}
		});
	}
	
	
	// TEMPORARY ONLY
	static ConcurrentHashMap<Integer, Peer> peers = new ConcurrentHashMap<Integer, Peer>();
	static ConcurrentHashMap<Integer, TorrentInstance> torrentInstances = new ConcurrentHashMap<Integer, TorrentInstance>();
	static ConcurrentHashMap<Integer, TorrentType> torrentTypes = new ConcurrentHashMap<Integer, TorrentType>();
	
	public static void loadAccess(File file) {
		loader.textPane.setBackground(new Color(255,255,255));
		Connection dbCon = null;
		try {
			if(file.getName() == "P2P.accdb")
				dbCon = DriverManager.getConnection("jdbc:ucanaccess://P2P.accdb");
			else if(file.exists())
				dbCon = DriverManager.getConnection("jdbc:ucanaccess://" + file.getPath());
			// Columns shown for reference
			String addPeers = "SELECT ID, maxIn, maxOut, trafficIn, trafficOut, peerName FROM Peers";
			String addTypes = "SELECT ID, numSections, sectionSize, torrentName FROM TorrentTypes";
			String addInstances = "SELECT ID, type, peer, complete FROM Torrents";
			int interval = 333;
			Peer peer;
			PreparedStatement preparedStatement;
			KeyTranslator peerKeys = new KeyTranslator();
			KeyTranslator typeKeys = new KeyTranslator();
			KeyTranslator instanceKeys = new KeyTranslator();
			loader.addMsg("Connection Established");
			
			// PEERS
			preparedStatement = dbCon.prepareStatement(addPeers);
			ResultSet peerRS = preparedStatement.executeQuery();
			loader.addMsg("Loading Peers");
			while (peerRS.next()) {
				peer = new Peer();
				loader.addMsg("Loading Peer " + peerRS.getRow());
				System.out.println();
				peerKeys.add(peerRS.getInt("ID"), peer.ID);
				peer.maxIn = peerRS.getInt("maxIn");
				peer.maxOut = peerRS.getInt("maxOut");
				peer.trafficIn = peerRS.getInt("trafficIn");
				peer.trafficOut = peerRS.getInt("trafficOut");
				if(peerRS.getString("peerName") != null)
					peer.name = peerRS.getString("peerName");
				peers.put(peer.ID, peer);
			}
			loader.progressBar.setValue(loader.progressBar.getValue()+interval);
			loader.addMsg(peerKeys + "");
			loader.addMsg("Loading Types");
			
			//TORRENT TYPES - one by one adds type to temp Hash, while noting key translation
			preparedStatement = dbCon.prepareStatement(addTypes);
			ResultSet typeRS = preparedStatement.executeQuery();
			while (typeRS.next()) {
				loader.addMsg("Loading Type " + typeRS.getRow());
				System.out.println();
				TorrentType type = new TorrentType(typeRS.getString("torrentName"), typeRS.getInt("numSections"), typeRS.getInt("sectionSize"));
				typeKeys.add(typeRS.getInt("ID"), type.ID);
				torrentTypes.put(type.ID, type);
				
			}
			loader.progressBar.setValue(loader.progressBar.getValue()+interval);
			loader.addMsg("Loading Instances");
			//TORRENT INSTANCES
			preparedStatement = dbCon.prepareStatement(addInstances);
			ResultSet instanceRS = preparedStatement.executeQuery();
			while (instanceRS.next()) {
				loader.addMsg("Loading Instance " + instanceRS.getRow());
				//To make the instance, this statement grabs type using processed input type ID, also sets complete or not
				
				TorrentInstance instance = new TorrentInstance(torrentTypes.get(typeKeys.preToPost(instanceRS.getInt("type"))), instanceRS.getBoolean("complete"));
				instanceKeys.add(instanceRS.getInt("ID"), instance.ID);	// adds pre/post keys to translator
				torrentInstances.put(instance.ID, instance);
				// now to associate the peer with the instance...
				
				//grabs peer from 
				peer = peers.get(peerKeys.preToPost(instanceRS.getInt("peer")));
				
				peer.torrents.add(instance.ID);
				// and with the type...
				peer.torrentTypes.add(instance.TYPE);
				
			}
			loader.progressBar.setValue(loader.progressBar.getValue()+interval);
			loader.addMsg(instanceKeys + "");
			loader.addMsg("No conflicts within added DB: adding to main data...");
			
			Data.peers = peers;
			Data.torrentInstances = torrentInstances;
			Data.torrentTypes = torrentTypes;
			dbCon.close();
			loader.addMsg("Data import complete. Updating open windows to reflect changes in data...");
			P2PGUI.updateAllLists();
			loader.addMsg("All operations complete - Proceed to close window");
			loader.btnCancel.setText("Close");
			
		} catch (SQLException e) {
			try {
				dbCon.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			loader.textPane.setBackground(new Color(255,204,204));
			loader.addMsg("ERROR: " + e.getMessage());
		}
	}
}
