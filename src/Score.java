import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
//Jason Hanna
//300266085
public class Score extends HBox {
	private int num;
	private ImageView digit1;
	private ImageView digit2;
	private ImageView digit3;
	
	//Score obj telling how many mines are left to be flagged
	//l = level 
	//0: beginner 1: intermediate 2: expert
	//set the 3 digits in the hbox appropriately
	public Score(int lvl) {
		digit1 = new ImageView("digits/0.png");
		digit3 = new ImageView("digits/0.png");
		if(lvl == 0) {
			num = 10;
			digit2 = new ImageView("digits/1.png");
			getChildren().addAll(digit1, digit2, digit3);
		}else if(lvl == 1) {
			num = 40;
			digit2 = new ImageView("digits/4.png");
			getChildren().addAll(digit1, digit2, digit3);
		}else {
			num = 99;
			digit2 = new ImageView("digits/9.png");
			digit3 = new ImageView("digits/9.png");
			getChildren().addAll(digit1, digit2, digit3);
		}
	}
	
	//when a flag is removed add to the mines left score
	public void addScore() {
		num++;
		if(num >= 0) {
			digit1 = new ImageView("digits/0.png");
			digit2 = getDigit(num / 10);
			digit3 = getDigit(num % 10);
			
		}else {
			digit1 = new ImageView("digits/digit-.png");
			digit2 = getDigit(Math.abs((int)(num / 10)));
			digit3 = getDigit(Math.abs(num % 10));
		}
		getChildren().clear();
		getChildren().addAll(digit1, digit2, digit3);
	}
	
	
	//when a tile is flagged take away from the mines left score
	public void subScore() {
		num--;
		if(num >= 0) {
			digit2 = getDigit(num / 10);
			digit3 = getDigit(num % 10);
		}else {
			digit1 = new ImageView("digits/digit-.png");
			digit2 = getDigit(Math.abs((int)(num / 10)));
			digit3 = getDigit(Math.abs(num % 10));
		}
		getChildren().clear();
		getChildren().addAll(digit1, digit2, digit3);
	}
	
	//get the appropriate image for each digit
	public ImageView getDigit(int n) {
		switch(n) {
			case 0:
				return new ImageView("digits/0.png");
			case 1:
				return new ImageView("digits/1.png");
			case 2:
				return new ImageView("digits/2.png");
			case 3:
				return new ImageView("digits/3.png");
			case 4:
				return new ImageView("digits/4.png");
			case 5:
				return new ImageView("digits/5.png");
			case 6:
				return new ImageView("digits/6.png");
			case 7:
				return new ImageView("digits/7.png");
			case 8:
				return new ImageView("digits/8.png");
			case 9:
				return new ImageView("digits/9.png");
			default:
				return new ImageView("digits/0.png");
				
		}
	}
}

