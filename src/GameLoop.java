/** simple linked list of players
 * 
 * 
 *
 */

public class GameLoop {
	
	
	private Player head;
	private Player curr;
	
	public GameLoop(){
		head = null;
		curr = null;
	}
	
	public Player getFirst() {
		return head;
	}
	
	public void add(String name) {	
		
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
	
	// swap values of prev and next for each player node
	public void reverse() {
		
		Player currentEl = head;
		Player prev = curr;
		Player next = null;
		
		while(curr.next != head) {
			next = currentEl.next;
			curr.next = prev;
			
			prev = currentEl;
			currentEl = next;
			
		}
		
		
	}
	
	
}
