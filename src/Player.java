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
	
	public Card playCard(String color, int number) {
		
		Card ret;
		
		if (color.equals("wild") && cards.get("wild").size() != 0 || color.equals("wild4") && cards.get("wild4").size() != 0) {
			numCards --;
			System.out.println("Card played! What color would you like to change the top card color to?");
			
			return cards.get(color).remove(0);
		}
		else if (color.equals("wild") || color.equals("wild4")) {
			System.out.println("You don't have that card! Try again.");
			return null;	
		}
		
		for (int i = 0; i < cards.get(color).size(); i ++) {
			Card c = cards.get(color).get(i);
			if (c.number == number) {
				numCards --;
				System.out.println("Card played! Next player's turn.");
				return cards.get(color).remove(i);	
			}
		}

		System.out.println("You don't have that card! Try again.");
		return null;
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
		
		System.out.println("Num Wild Cards: " + cards.get("wild").size());
		System.out.println("Num Wild+4 Cards: " + cards.get("wild4").size());
	}


}
