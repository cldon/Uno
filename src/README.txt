UNO CARD GAME!
Written by Claire Donovan

RUN INSTRUCTIONS: Compile with javac Uno.java and run with java Uno

GAME INSTRUCTIONS (copied and shortened from www.unorules.com): 

Setup: The game is for 2-10 players. Every player starts with seven cards, and the rest of the cards are placed in a Draw Pile face down. One card is placed face up.

Game Play: Every player views his/her cards and tries to match the card in the Discard Pile.

You have to match either by the number, color, or the symbol/Action. For instance, if the Discard Pile has a red card that is an 8 you have to place either a red card or a card with an 8 on it. You can also play a Wild card (which can alter current color in play).

If the player has no matches or they choose not to play any of their cards even though they might have a match, they must draw a card from the Draw pile. You can also play a Wild card, or a Wild Draw Four card on your turn.

If it is a Wild card, the player who played the Wild card can choose the new color to be on top. If the first card is a Wild Draw Four card, the player next to the player who dealt the card receives an additional four cards.

The game continues until a player has one card left. The moment a player has just one card they must yell “UNO!”. (In Claire's version the computer announces it)

Once a player has no cards remaining, the game round is over and this player wins.

Action Cards: Besides the number cards, there are several other cards that help mix up the game. These are called Action or Symbol cards:

Reverse – If going clockwise, switch to counterclockwise or vice versa.
Skip – When a player places this card, the next player has to skip their turn.
Draw Two – When a person places this card, the next player will have to pick up two cards and forfeit his/her turn.
Wild – This card represents all four colors, and can be placed on any card. The player has to state which color it will represent for the next player. It can be played regardless of whether another card is available.
Wild Draw Four – This acts just like the wild card except that the next player also has to draw four cards.


EXPLANATION OF DESIGN CHOICES
For this game I found the easiest way to control the flow (basically, whether the turns moved in a counterclockwise or clockwise motion, if the players were playing in real life) was by implementing a circular linked list that could easily be reversed in the case of a Reverse card being played. It was very simple to iterate through the players repeatedly. The nodes of this circular linked list(GameFlow) were Player objects that contained all the information pertaining to the player.

Each player has a dictionary of Cards that map from color to card type. I did this so that printing cards sorted by color could be done more easily and also so that searching through a player's hand to see if they have a certain card could be narrowed down by color to decrease the search time.

Creating a shuffled deck was easily implemented through a LinkedList because I could use Collection.shuffle to randomize the order and also use removeFirst() to pop a card off the deck. 

Lastly, I made a card class to easily group a card's two values, a color and number.


