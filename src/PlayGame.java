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
	
	public PlayGame() {
		
		numPlayers = getNumPlayers();
		
		gp = makePlayerLoop();
		
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
		String[] colors = {"Red", "Blue", "Green", "Yellow"};
		
		for (String color:colors) {
			for (int i = 0; i < 13; i ++) {
				ret.add(new Card(color, i));
				
				/** Uno deck includes 2 of every number except 0 per color
				 * 10 == Skip Card
				 * 11 = +2 Card
				 * 12 = Reverse Card
				 */
				if (i != 0) {
					ret.add(new Card(color, i));
				}
			}	
		}
		for (int i = 0; i < 4; i ++) {
			ret.addFirst(new Card("Wild", 13));
			ret.addFirst(new Card("Wild+4", 14));
		}

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
		
//		System.out.println(deck.size() + " Cards Remain");
	}
	
	private void run() {
		boolean winner = false;
		boolean reverse = false;
		
		Player currPlayer = gp.getFirst();
		
		int count = 0;
		while (!winner) {			
//			System.out.println(curr.name + ", here are your cards:\n*all of the cards*");
//			System.out.println("Here is the top card in the discard pile:\n*top card*");
//			System.out.println("What would you like to play?");
//			scan.next();
			
			currPlayer.printCards();
			currPlayer = currPlayer.next;
				
			count ++;
		}
		
	}
	

}
