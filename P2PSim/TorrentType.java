package P2PSim;

public class TorrentType {
	public static int keyCounter = -1;
	public final int ID;
	public final String name;
	public final int numSections;
	public final int sectionSize;
	
	TorrentType(String name, int numSections, int sectionSize){
		this.name = name;
		this.numSections = numSections;
		this.sectionSize = sectionSize;
		ID = ++keyCounter;
	}
	
	public String toString(){
		return name;
	}
}
