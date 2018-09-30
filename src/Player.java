import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player {
	
	String name;
	Map<String, ArrayList<Card>> cards;
	
	Player next = null;
	Player prev = null;
	
	int numCards;
	
	String[] cardOrder = {"Red", "Blue", "Yellow", "Green"};
	
	public Player(String name) {
		this.name = name;
		
		cards = new HashMap<String, ArrayList<Card>>();
		cards.put("Red", new ArrayList<Card>());
		cards.put("Blue", new ArrayList<Card>());
		cards.put("Yellow", new ArrayList<Card>());
		cards.put("Green", new ArrayList<Card>());
		cards.put("Wild", new ArrayList<Card>());
		cards.put("Wild+4", new ArrayList<Card>());
		
		numCards = 0;
	}
	
	public void dealCard(Card c) {
		cards.get(c.color).add(c);
	}
	
	public void printCards() {
		System.out.println(name + ", here are your cards:");
		for (String color: cardOrder) {
			String output = color + ": ";
			
			for (Card c : cards.get(color)) {
				output += c.number + " ";
			}
			
			System.out.println(output);
		}
		
		System.out.println("Number of Wild Cards: " + cards.get("Wild").size());
		System.out.println("Number of Wild +4 Cards: " + cards.get("Wild+4").size());
	}


}
