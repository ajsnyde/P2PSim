package P2PSim;

import java.util.ArrayList;

public class KeyTranslator {
	int keyCounter = 0; // saves time by storing position of last free slot
	ArrayList<Integer> preSet;
	ArrayList<Integer> postSet;
	
	KeyTranslator(){
		preSet = new ArrayList<Integer>();
		postSet = new ArrayList<Integer>();
		
	}
	
	void add(int pre, int post) {
		if (!preSet.contains(pre)) {
			preSet.add(pre);
			while (postSet.contains(keyCounter))
				keyCounter++;// Increment until free - should not need to be done often
			postSet.add(keyCounter);
		}
	}

	int preToPost(int pre) {
		return postSet.get(preSet.indexOf(pre));
	}

	int preTop() {
		return preSet.get(preSet.size() - 1);
	}

	int postToPre(int post) {
		return preSet.get(postSet.indexOf(post));
	}

	int postTop() {
		return preSet.get(postSet.size() - 1);
	}
	public String toString() {
		String out = "";
		for(Integer i: preSet)
			out += "Pre:" + i + "Post:" + preToPost(i) + "\n";
		return out;
	}
}
