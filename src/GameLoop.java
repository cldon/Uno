/** simple doubly linked list of player
 * 
 * 
 *
 */

public class GameLoop {
	
	
	private Player head;
	private Player curr;
	private Player tail;
	
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
			p.prev = curr;
			p.next = head;
			curr = p;
			
			head.prev = curr;
		} else {
			head = p;
			curr = p;	
		}
	}
	
	public void reverse() {
		Player prev = null;
		Player curr = head;
		Player next = null; 
		
	}
	
	
}
