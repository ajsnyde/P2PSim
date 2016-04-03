package P2PSim;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Peer {
	public static int keyCounter = -1;
	public final int ID;
	public ArrayList<Integer> torrents = new ArrayList<Integer>();
	public ArrayList<Integer> torrentTypes = new ArrayList<Integer>();
	public ArrayList<Integer> connections = new ArrayList<Integer>();
	public static int MAX_CONNECTIONS = 5;
	public int maxIn;
	public int maxOut;
	public int trafficIn;
	public int trafficOut;

	Peer() {
		ID = ++keyCounter;
	}

	void tick() {
		getPossiblePeers();
	}

	void getPossiblePeers() {	// checks each peer, then each type.. Rules out impossible connections
		Iterator<Peer> it = Data.peers.values().iterator();
		ArrayList<String> choices = new ArrayList<String>();
		while (it.hasNext()) {
			Peer peer = it.next();

			if (peer.ID != ID && Helper.intersection(peer.connections, connections).isEmpty()) {
				// checks for identical peer and lack of connections
				//System.out.println("Comparing peers " + ID + " and " + peer.ID);
				List<Integer> torrentChoices = Helper.intersection(peer.torrentTypes,torrentTypes);
				List<Integer> finalChoices = new ArrayList<Integer>();
				//System.out.println("Choices before checking sections "+torrentChoices);
				for(int i: torrentChoices) {
					if(Helper.intersection(Data.getInstance(peer.getInstance(i)).sections, Data.getInstance(this.getInstance(i)).sections).isEmpty()) {
						finalChoices.add(i);
						//System.out.println("Added " + i + " to shortlist");
						choices.add("Peer " + ID + "&" + peer.ID + "-" + i);
					}
				}
			}
		}
		System.out.println("FINAL PEER CHOICES FOR PEER " + ID + ": \n" + choices);
	}

	int getInstance(int type) {
		for (Integer i : torrents) {
			if (Data.torrentInstances.get(i).TYPE == type)
				return i;
		}
		return -1;
	}

	void add(TorrentInstance in) {
		torrents.add(in.ID);
		torrentTypes.add(in.TYPE);
	}

	boolean hasType(int torrentType) {
		if (torrentTypes.contains(torrentTypes))
			return true;
		return false;
	}
	public String toString() {
		return "Peer - ID:"+ID;	
	}
}