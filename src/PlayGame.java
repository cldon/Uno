import java.util.Collections;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;


public class PlayGame {
	
	private Scanner scan = new Scanner(System.in);
	
	int numPlayers;
	GameLoop gp;
	LinkedList<Card> deck;
	
	Card topCard;		// top card in discard pile
	String topColor;	// top color in discard pile
	
	Player currPlayer;
	
	public PlayGame() {
		
		numPlayers = getNumPlayers();
		
		gp = makePlayerLoop();
		
		deck = makeDeck();
		
		dealCards();
		
		topCard = deck.removeFirst();
		if (topCard.color.equals("wild") || topCard.color.equals("wild4")) {
			topColor = "red";
		}
		
		run();
	}

	// runs until correct input given for number of players
	private int getNumPlayers() {
		
		System.out.print("Welcome to Uno! How many players? (2 - 10)\n>> ");
		
		int numPlayers = -1;
		while (numPlayers < 0) {
			try {
				numPlayers = scan.nextInt();
				scan.nextLine();
				
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
	
	// collects player names to make player nodes for game loop
	private GameLoop makePlayerLoop() {
		
		GameLoop g = new GameLoop();
		
		for (int i = 1; i <= numPlayers; i++) {
			System.out.print("Player " + i + ", enter your name:\n>> ");
			g.add(scan.next());
		}
		return g;
		
	}
	
	// adds all cards to linkedlist "deck," and shuffles collection before returning
	private LinkedList<Card> makeDeck() {
		
		LinkedList<Card> ret = new LinkedList<Card>();
		
		for (String color:Player.cardOrder) {
			for (int i = 0; i < 13; i ++) {
				ret.add(new Card(color.toLowerCase(), i));
				
				if (i != 0) {
					ret.add(new Card(color.toLowerCase(), i));
				}
			}	
		}
		for (int i = 0; i < 4; i ++) {
			ret.add(new Card("wild", 13));
			ret.add(new Card("wild4", 14));
		}

		Collections.shuffle(ret);
		
		return ret;
	}
	
	// deals each player 7 cards, in proper dealing fashion (i.e. not given in chunks of 7)
	private void dealCards() {
		Player currPlayer = gp.getFirst();
		
		for (int i = 1; i <= numPlayers * 7; i++) {
			Card c = deck.removeFirst();

			currPlayer.dealCard(c);
			currPlayer = currPlayer.next;
		}
		
	}
	
	// will attempt to add card to player's hand, unless out of cards in deck
	public void tryToDraw(Player p, int numCards) {
		for (int i = 0; i < numCards; i++) {
			try {
				p.dealCard(deck.removeFirst());
			} catch (Exception e) {
				System.out.println("There are no more cards to draw in the deck. Keep playing!");
				return;
			}
		}	
	}
	
	// return True if player wants to draw card, return false if wants to play card
	private boolean chooseToDraw() {
		System.out.print("Would you like to draw a card or play a card?(d/p)\n>> ");
		String answer = scan.next();
		while (!answer.equals("d") && !answer.equals("p")) {
			System.out.print("Invalid response, try again!\n>> ");
			answer = scan.next();
		}
		return answer.equals("d");
	}
	
	// if top card is wild card, will also state the top color chosen by prev player
	private void displayTopCard() {
		System.out.println("");
		System.out.print("Card on top: ");
		System.out.println(topCard);
		if (topCard.color == "wild" || topCard.color =="wild4") {
			System.out.println("Wild Card color chosen to be " + topColor);
		}	
		System.out.println("");
	}
	
	
	// function to let player select card to play
	private Card playTurn(Player p){
		String color;
		int number;
		boolean success = false;
		Card ret = null;
		
		while (!success) {
			number = -1;
			color = "";
			
			System.out.println("Play your card! First enter color, then number:");
			System.out.print("Color (blue, red, green, yellow, wild, wild4):\n>> ");
			
			color = scan.next().toLowerCase();		
			
			//first enter a color
			while (!Card.ALL_COLORS.contains(color)) {
				System.out.print("Invalid color, try again!\n>> ");
				color = scan.next().toLowerCase();
			}
			
			// if the color entered isn't a wild card, player must enter number as well
			if (!color.equals("wild") && !color.equals("wild4")) {
				
				System.out.print("Number (0-9, 10 = Skip, 11 = +2, 12 = Reverse):\n>> ");

				while (number < 0 || number > 12) {
					try {		
						number = scan.nextInt();
						scan.nextLine();
						if (number < 0 || number > 12) {
							System.out.print("Invalid number, try again!\n>> ");
							number = scan.nextInt();
							scan.nextLine();	
						}
					}
					catch (InputMismatchException e){
						System.out.print("Invalid number, try again!\n>> ");
						scan.nextLine();	
					}
				}			
			}
			// checks to make sure card is playable given current topCard
			//TODO: uncomment out
//			if ( (!color.equals(topColor) || number != topCard.number) 
//					&& color.equals("wild") && color.equals("wild4")) {
////				System.out.println("You can't play that card! Color or number must match.");
//				continue;
//			}
			
			// as long as playCard hasn't returned a card (aka returned non-null val), keep cycling
			ret = p.playCard(color, number);
			success = (ret != null);
		}	
		return ret;
	}
	
	// given the player has successfully chosen a card, execute any action the value might have
	// (i.e. if Reverse played, reverse the game loop)
	// returns true if needs to skip a player later
	private boolean handlePlay() {
		topColor = topCard.color;
		if (topCard.number <= 9) {
			return false;
		}
		// Skip card: add an extra skip to currPlayer
		else if (topCard.number == 10) {
			System.out.println("SKIP!");
//			System.out.println("Old player: " + currPlayer.name);
//			currPlayer = currPlayer.next;
//			System.out.println("New player: " + currPlayer.name);
			return true;
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
		return false;
	}
	
	private void run() {
		boolean winner = false;
		
		currPlayer = gp.getFirst();
		
		Player currPlayer = gp.getFirst();
		
		while (!winner) {			
			
			currPlayer.printCards();
			
			displayTopCard();
			
			
			boolean mustSkip = false;
			// if the player wants to draw a card, draw one/update deck, otherwise let play a turn
			if (chooseToDraw()) {
				tryToDraw(currPlayer, 1);
			} else {
				topCard = playTurn(currPlayer);
				mustSkip = handlePlay();
			}

			if(currPlayer.numCards == 1) {
				System.out.println(currPlayer.name + " has UNO!");
			} else if (currPlayer.numCards == 0) {
				winner = true;
				System.out.println(currPlayer.name + " WINS!!!!!");
				break;
			}
	
			currPlayer = currPlayer.next;	
			if (mustSkip) {
				currPlayer = currPlayer.next;
			}
			System.out.println("Your turn is over! Press any key + Enter then pass "
					+ "the computer to " + currPlayer.name);
			
			scan.next();
			
			System.out.print("\033[H\033[2J");  
		    System.out.flush();
		}	
	}
}
