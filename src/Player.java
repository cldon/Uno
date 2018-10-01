/** 
 * Player Class
 * Each player has a name and their hand of cards, and the player "next to them"
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player {
	
	String name;
	Map<String, ArrayList<Card>> cards;
	
	Player next;
	
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
		next = null;
	}
	
	// adds a card to player's hand
	public void dealCard(Card c) {
		cards.get(c.color).add(c);
		numCards++;
	}
	
	//helper function for checking wild cards, same checks done for wild and wild4
	private Card checkWild(String wild, String color) {
		if (cards.get(wild).size() == 0) {
			System.out.println("You don't have that card! Try again.");
			return null;
		}		
		numCards --;
		return cards.get(color).remove(0);
	}
	
	// checks if player's deck contains the card, if so removes the card, otherwise returns null
	public Card playCard(String color, int number) {

		// checks if wild/wild4 was played, just has to check if player has that card
		if (color.equals("wild")) {	
			return checkWild("wild", color);		
		}	
		if (color.equals("wild4")) {	
			System.out.println("Inside check wild4");
			return checkWild("wild4", color);	
		}
	
		// checks all other cards by cross checking color with number in hand
		for (int i = 0; i < cards.get(color).size(); i ++) {
			Card c = cards.get(color).get(i);
			if (c.number == number) {
				numCards --;
				System.out.println("Card played!");
				return cards.get(color).remove(i);	
			}
		}

		System.out.println("You don't have that card! Try again.");
		return null;
	}
	
	// prints out entire hand of cards in readable format, sorted by color
	public void printCards() {
		System.out.println(name + ", you have " + numCards + " cards:");
		for (String color: cardOrder) {
			String output = color + ": ";
			
			for (Card c : cards.get(color.toLowerCase())) {
				output += c.number + " ";
			}		
			System.out.println(output);
		}
		
		System.out.println("# Wild Cards: " + cards.get("wild").size());
		System.out.println("# Wild+4 Cards: " + cards.get("wild4").size());
	}
}
