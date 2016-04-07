package P2PSim;

import java.awt.EventQueue;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;

public class DataLoader {

	void Dataloader(){
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				for(String str: args)
					loadAccess(new File(str));
			}
		});
	}
	
	
	// TEMPORARY ONLY
	static ConcurrentHashMap<Integer, Peer> peers = new ConcurrentHashMap<Integer, Peer>();
	static ConcurrentHashMap<Integer, TorrentInstance> torrentInstances = new ConcurrentHashMap<Integer, TorrentInstance>();
	static ConcurrentHashMap<Integer, TorrentType> torrentTypes = new ConcurrentHashMap<Integer, TorrentType>();
	
	public static void loadAccess(File file) {
		Connection dbCon = null;
		try {
			if(file.getName() == "P2P.accdb")
				dbCon = DriverManager.getConnection("jdbc:ucanaccess://P2P.accdb");
			else if(file.exists())
				dbCon = DriverManager.getConnection("jdbc:ucanaccess://" + file.getPath());
			// Columns shown for reference
			String addPeers = "SELECT ID, maxIn, maxOut, trafficIn, trafficOut FROM Peers";
			String addTypes = "SELECT ID, numSections, sectionSize, torrentName FROM TorrentTypes";
			String addInstances = "SELECT ID, type, peer, complete FROM Torrents";
			Peer peer;
			PreparedStatement preparedStatement;
			KeyTranslator peerKeys = new KeyTranslator();
			KeyTranslator typeKeys = new KeyTranslator();
			KeyTranslator instanceKeys = new KeyTranslator();
			System.out.println("Connection Established");
			
			// PEERS
			preparedStatement = dbCon.prepareStatement(addPeers);
			ResultSet peerRS = preparedStatement.executeQuery();
			System.out.println("Loading Peers");
			while (peerRS.next()) {
				peer = new Peer();
				System.out.println("Loading Peer " + peerRS.getRow());
				peerKeys.add(peerRS.getInt("ID"), peer.ID);
				peer.maxIn = peerRS.getInt("maxIn");
				peer.maxOut = peerRS.getInt("maxOut");
				peer.trafficIn = peerRS.getInt("trafficIn");
				peer.trafficOut = peerRS.getInt("trafficOut");
				
				peers.put(peer.ID, peer);
			}
			System.out.println(peerKeys);
			System.out.println("Loading Types");
			
			//TORRENT TYPES - one by one adds type to temp Hash, while noting key translation
			preparedStatement = dbCon.prepareStatement(addTypes);
			ResultSet typeRS = preparedStatement.executeQuery();
			while (typeRS.next()) {
				System.out.println("Loading Type " + typeRS.getRow());
				TorrentType type = new TorrentType(typeRS.getString("torrentName"), typeRS.getInt("numSections"), typeRS.getInt("sectionSize"));
				typeKeys.add(typeRS.getInt("ID"), type.ID);
				torrentTypes.put(type.ID, type);
			}
			
			System.out.println("Loading Instances");
			//TORRENT INSTANCES
			preparedStatement = dbCon.prepareStatement(addInstances);
			ResultSet instanceRS = preparedStatement.executeQuery();
			while (instanceRS.next()) {
				System.out.println("Loading Instance " + instanceRS.getRow());
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
			System.out.println(instanceKeys);
			
			System.out.println("No conflicts within added DB: adding to main data...");
			
			Data.peers = peers;
			Data.torrentInstances = torrentInstances;
			Data.torrentTypes = torrentTypes;
			
			
			dbCon.close();
		} catch (SQLException e) {
			try {
				dbCon.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
