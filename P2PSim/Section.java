package P2PSim;

public class Section {
	public static int keyCounter = -1;
	public int ID;
	public boolean complete;
	public int senderID;
	public int receiverID;
	public final int size = 64;	//size kbits
	public int sent = 0;
	public int torrentID;
	
	Section(int torrentID, int senderID, int recieverID){
		ID = ++keyCounter;
		this.senderID = senderID;
		this.receiverID = recieverID;
		this.torrentID = torrentID;
	}
	Section(int torrentID, boolean complete){
		ID = ++keyCounter;
		this.complete = complete;
		this.torrentID = torrentID;
	}
}
