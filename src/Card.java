
public class Card {

	String color;
	int number;
	
	public Card(String color, int number) {
		this.color = color;
		this.number = number;
	}
	
	@Override
	public String toString() {
		return "CARD: " + color + number;
		
	}
}
