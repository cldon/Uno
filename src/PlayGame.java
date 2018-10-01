import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;


public class PlayGame {
	
	private Scanner scan = new Scanner(System.in);
	int numPlayers;
	GameLoop gp;
	LinkedList<Card> deck;
	
	Card topCard;
	String topColor;
	
	ArrayList<Card> discardDeck;
	Player currPlayer;
	
	public PlayGame() {
		
		numPlayers = getNumPlayers();
		
		gp = makePlayerLoop();
		
		discardDeck = new ArrayList<Card>();
		
		deck = makeDeck();
		
		dealCards();
		
		topCard = deck.removeFirst();
		
		run();
	}

	private int getNumPlayers() {
		
		System.out.print("Welcome to Uno! How many players? (2 - 10)\n>> ");
		
		int numPlayers = -1;
		while (numPlayers < 0) {
			try {
				numPlayers = scan.nextInt();
				if (numPlayers < 2 || numPlayers > 10) {
					numPlayers = -1;
					System.out.print("Invalid number of players, try again!\n>> ");
				}
			}
			catch (InputMismatchException e){
				scan.next();
				System.out.print("Invalid number of players, try again!!\n>> ");
			}
		}
		return numPlayers;
	}
	
	private GameLoop makePlayerLoop() {
		
		GameLoop g = new GameLoop();
		
		for (int i = 1; i <= numPlayers; i++) {
			System.out.print("Player " + i + ", enter your name:\n>> ");
			g.add(scan.next());
		}
		return g;
		
	}
	
	private LinkedList<Card> makeDeck() {
		
		LinkedList<Card> ret = new LinkedList<Card>();
		
		
		for (String color:Player.cardOrder) {
			for (int i = 0; i < 13; i ++) {
				ret.add(new Card(color.toLowerCase(), i));
				
				/** Uno deck includes 2 of every number except 0 per color
				 * 10 == Skip Card
				 * 11 = +2 Card
				 * 12 = Reverse Card
				 */
				if (i != 0) {
					ret.add(new Card(color.toLowerCase(), i));
				}
			}	
		}
		for (int i = 0; i < 4; i ++) {
			ret.add(new Card("wild", 13));
			ret.add(new Card("wild4", 14));
		}
		
//		Card currCard = ret.removeFirst();
//		while (!ret.isEmpty()) {
//			System.out.println(currCard);
//			currCard = ret.removeFirst();
//		}

		Collections.shuffle(ret);
		
		return ret;
	}
	
	private void dealCards() {
		Player currPlayer = gp.getFirst();
		
		for (int i = 1; i <= numPlayers * 7; i++) {
			Card c = deck.removeFirst();

			currPlayer.dealCard(c);
			currPlayer = currPlayer.next;
		}
		
	}
	
	private void displayTopCard() {
		System.out.print("Card on top: ");
		if (topCard.color == "wild" || topCard.color =="wild4") {
			System.out.println("Wild Card, color chosen as " + topColor);
		}
			
		System.out.println(topCard);
	}
	
	private Card playTurn(Player p){
		String color = "";
		int number = -1;
		boolean success = false;
		Card ret = null;
		
		while (!success) {
			System.out.println("What card would you like to play?");
			System.out.print("Color (blue, red, green, yellow, wild, wild4:\n>> ");
			color = scan.next().toLowerCase();
			
			
			while (!Card.ALL_COLORS.contains(color)) {
				System.out.print("Invalid color, try again!\n>> ");
				color = scan.next().toLowerCase();
			}
			if (!color.equals("wild") || !color.equals("wild4")) {
				System.out.print("Number (0-9, 10 = Skip, 11 = +2, 12 = Reverse):\n>> ");
				
				while (number < 0 || number > 12) {
					try {
						number = scan.nextInt();
						if (number < 0 || number > 12) {
							number = -1;
							System.out.print("Invalid number, try again!\n>> ");
						}
					}
					catch (InputMismatchException e){
						scan.next();
						System.out.print("Invalid number, try again!\n>> ");
					}
				}			
			}
			
			// as long as playCard hasn't returned a card, keep cycling
			// TODO: add error handling for entering a card that can't go on top
			ret = p.playCard(color, number);
			success = (ret != null);
		}	
		return ret;
	}
	
	public void tryToDraw(Player p, int numCards) {
		for (int i = 0; i < numCards; i++) {
			try {
				p.dealCard(deck.removeFirst());
			} catch (Exception e) {
				System.out.println("There are no more cards in the deck, keep playing with what you have!");
				return;
			}
		}	
	}
	
	private void handlePlay() {
		topColor = topCard.color;
		if (topCard.number <= 9) {
			return;
		}
		// Skip card: add an extra skip to currPlayer
		else if (topCard.number == 10) {
			System.out.println("SKIP!");
			currPlayer = currPlayer.next;
		} // +2 card: person coming next gains two cards
		else if (topCard.number == 11) {
			System.out.println(currPlayer.next.name + " just gained two cards!");
			tryToDraw(currPlayer.next, 2);
		} else if(topCard.number == 12) {
			System.out.println("REVERSE!");
			gp.reverse();
		}
		else if(topCard.number == 13 || topCard.number == 14) {
			
			System.out.print("Wild card! What would you like the color to be?\n>> ");
			String color = scan.next().toLowerCase();
			
			while (!Card.ALL_COLORS.contains(color) && !color.equals("wild") && !color.equals("wild4")) {
				System.out.println("Invalid color, try again!\n>> ");
				color = scan.next();
			}
			
			topColor = color;
			
			if (topCard.number == 14) {
				tryToDraw(currPlayer.next, 4);
			}
		}
		
	}
	private void run() {
		boolean winner = false;
		
		currPlayer = gp.getFirst();
		
		Player currPlayer = gp.getFirst();
		
		while (!winner) {			
			
			currPlayer.printCards();
			
			displayTopCard();
			
			topCard = playTurn(currPlayer);
			
			handlePlay();
			
			if(currPlayer.numCards == 1) {
				System.out.println(currPlayer.name + " has UNO!");
			} else if (currPlayer.numCards == 0) {
				winner = true;
				System.out.println(currPlayer.name + " WINS!!!!!");
			}		
			currPlayer = currPlayer.next;			
		}	
	}
}
