package P2PSim;

import java.util.ArrayList;
import java.util.Collections;

public class TorrentInstance {
	public static int keyCounter = -1;
	public final int ID;
	public final int TYPE;
	public ArrayList<Boolean> sections;
	
	TorrentInstance(TorrentType in){
		ID = ++keyCounter;
		TYPE = in.ID;
		sections = new ArrayList<Boolean>(Collections.nCopies(in.numSections, false));
	}
	TorrentInstance(TorrentType in, boolean complete){
		ID = ++keyCounter;
		TYPE = in.ID;
		sections = new ArrayList<Boolean>(Collections.nCopies(in.numSections, complete));
	}
	
	public String toString(){
		return ID + " - " + sections.get(0);
		//return Data.getType(TYPE).name + " - Type:" + TYPE + " - Peer:" + Data.getPeer(this).ID;
	}
}
