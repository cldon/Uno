/** 
 * Card class
 * Represents an Uno card, has a color and a numerical value 0 - 14
 * 0 - 9 represents numbers
 * 10 = Skip Card, 11 = +2 Card, 12 = Reverse Card
 * 13 = Wild Card, 14 = Wild +4 Card, but numbers are unimportant as wild cards can match with any
 * 	other card number/color.
 */ 

import java.util.Arrays;
import java.util.List;

public class Card {

	String color;
	int number;
	
	static final List<String> ALL_COLORS = 
			Arrays.asList("red", "blue", "green", "yellow", "wild", "wild4");
	
	public Card(String color, int number) {
		this.color = color;
		this.number = number;
	}
	
	@Override
	public String toString() {
		if (color.equals("wild") || color.equals("wild 4")) {
			return color;
		}
		
		return color + " "+ number;	
	}
}
