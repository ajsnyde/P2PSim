package P2PSim;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Data {
	public static ConcurrentHashMap<Integer, Peer> peers = new ConcurrentHashMap<Integer, Peer>();
	public static ConcurrentHashMap<Integer, TorrentInstance> torrentInstances = new ConcurrentHashMap<Integer, TorrentInstance>();
	public static ConcurrentHashMap<Integer, TorrentType> torrentTypes = new ConcurrentHashMap<Integer, TorrentType>();
	
	static ArrayList<Integer> TypeFromPeer(int ID) {
		return getPeer(ID).torrentTypes;
	}
	static int TypeFromInstance(int ID) {
		return getInstance(ID).TYPE;
	}
	
	// Get by its own ID
	static TorrentInstance getInstance(int ID) {
		return torrentInstances.get(ID);
	}
	static TorrentType getType(int ID) {
		return torrentTypes.get(ID);
	}
	static Peer getPeer(int ID) {
		return peers.get(ID);
	}
	
	// Get by TorrentInstance
	static Peer getPeer(TorrentInstance in) {
		for(Peer peer : peers.values()) {
			if(peer.torrents.contains(in.ID))
				return peer;
		}
		return null;
	}
	
	static void add(Peer in) {
		peers.put(in.ID, in);
	}
	static void add(TorrentInstance in) {
		torrentInstances.put(in.ID, in);
	}
	static void add(TorrentType in) {
		torrentTypes.put(in.ID, in);
	}
}
