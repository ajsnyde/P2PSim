package P2PSim;

import java.io.File;

public class Tester {
	public static void main(String[] args) {	
		
		System.out.println("Loading Access File");
		DataLoader.loadAccess(new File("P2P.accdb"));
		
		// Add test types
		TorrentType t1 = new TorrentType("T1", 512, 16);
		Data.add(t1);
		TorrentType t2 = new TorrentType("T2", 512, 16);
		Data.add(t2);
		TorrentInstance tInstance;
		Peer peer;
		
		// new complete instance
		tInstance = new TorrentInstance(t1, true);
		Data.add(tInstance);
		
		// new seed
		peer = new Peer();
		peer.add(tInstance);
		Data.add(peer);
		// new complete instance2
		tInstance = new TorrentInstance(t2, true);
		Data.add(tInstance);
		peer.add(tInstance);
		
		tInstance = new TorrentInstance(t1, true);
		Data.add(tInstance);
		
		// new seed
		peer = new Peer();
		peer.add(tInstance);
		Data.add(peer);
		
		// leech
		tInstance = new TorrentInstance(t1);
		peer = new Peer();
		peer.add(tInstance);
		Data.add(tInstance);
		Data.add(peer);
		// new instance
		tInstance = new TorrentInstance(t2);
		Data.add(tInstance);
		peer.add(tInstance);
		
		
		tInstance = new TorrentInstance(t1);
		peer = new Peer();
		peer.add(tInstance);
		Data.add(tInstance);
		Data.add(peer);
		// new instance
		tInstance = new TorrentInstance(t2);
		Data.add(tInstance);
		peer.add(tInstance);
		
		
		tInstance = new TorrentInstance(t1);
		peer = new Peer();
		peer.add(tInstance);
		Data.add(tInstance);
		Data.add(peer);
		// new instance
		tInstance = new TorrentInstance(t2);
		Data.add(tInstance);
		peer.add(tInstance);
		
		System.out.println(Data.peers.values());
		System.out.println(Data.torrentTypes.values());
		System.out.println(Data.torrentInstances.values());
		
		for(Peer b : Data.peers.values()) {
			b.printFull();
			b.getPossiblePeers();
		}
		System.out.println("Done!");
		
	}
}
