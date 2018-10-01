/** 
 * Game Loop is a circular linked list made up of nodes of players
 * Has functionality to be reversed in the case that a player plays the "Reverse" card
 */

public class GameLoop {
	
	private Player head;
	private Player curr;
	int size;
	
	public GameLoop(){
		head = null;
		curr = null; 
		size = 0;
	}
	
	public Player getFirst() {
		return head;
	}
	
	public void add(String name) {	
		size ++;
		Player p = new Player(name);
		
		if (head != null) {	
			curr.next = p;
			p.next = head;
			curr = p;
		} else {
			head = p;
			curr = p;	
		}
	}
	
	// reverses game flow direction
	public void reverse() {
		Player prev = null;
		Player currentEl = head;
		Player next;
		
		for (int i = 0; i < size; i ++) {
			next = currentEl.next;
			currentEl.next = prev;
			prev = currentEl;
			currentEl = next;
		}
		
		head.next = prev;
		
		
			
	}
}
