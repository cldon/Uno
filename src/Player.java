import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player {
	
	String name;
	Map<String, ArrayList<Card>> cards;
	
	Player next = null;
	Player prev = null;
	
	int numCards;
	
	static String[] cardOrder = {"Red", "Blue", "Yellow", "Green"};
	
	public Player(String name) {
		this.name = name;
		
		cards = new HashMap<String, ArrayList<Card>>();
		cards.put("red", new ArrayList<Card>());
		cards.put("blue", new ArrayList<Card>());
		cards.put("yellow", new ArrayList<Card>());
		cards.put("green", new ArrayList<Card>());
		cards.put("wild", new ArrayList<Card>());
		cards.put("wild4", new ArrayList<Card>());
		
		numCards = 0;
	}
	
	public void dealCard(Card c) {
		cards.get(c.color).add(c);
		numCards++;
	}
	
	public boolean playCard(String color, int number) {
		
		for (Card c : cards.get(color)) {
			if (c.number == number) {
				numCards --;
				return cards.get(color).remove(c);
			}
		}
		System.out.println("You don't have that card! Try again.");
		return false;	
	}
	
	public void printCards() {
		System.out.println(name + ", here are your cards:");
		for (String color: cardOrder) {
			String output = color + ": ";
			
			for (Card c : cards.get(color.toLowerCase())) {
				output += c.number + " ";
			}
			
			System.out.println(output);
		}
		
		System.out.println("Number of Wild Cards: " + cards.get("Wild").size());
		System.out.println("Number of Wild +4 Cards: " + cards.get("Wild+4").size());
	}


}
