import java.util.Arrays;
import java.util.List;

public class Card {

	String color;
	int number;
	
	static final List<String> ALL_COLORS = Arrays.asList("red", "blue", "green", "yellow", "wild", "wild4");
	
	public Card(String color, int number) {
		this.color = color;
		this.number = number;
	}
	
	@Override
	public String toString() {
		return "CARD: " + color + number;
		
	}
}
