import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
//Jason Hanna
//300266085
public class Time extends HBox {
	private int time;
	private Timeline ticker;
	private ImageView digit1;
	private ImageView digit2;
	private ImageView digit3;
	
	//Timer object to tick and change the red digits
	//to the next second timed for every second
	public Time() {
		
		time = 0;
		
		
		getChildren().addAll(
				new ImageView("digits/0.png"),
				new ImageView("digits/0.png"),
				new ImageView("digits/0.png")
				);
		
		ticker = new Timeline(new KeyFrame(Duration.seconds(1), e -> addTime()));
		ticker.setCycleCount(Timeline.INDEFINITE);
	}
	
	public void startTime() {
		ticker.play();
	}
	
	public void stop() {
		ticker.stop();
	}
	
	public void resetTime() {
		time = 0;
		getChildren().addAll(
				new ImageView("digits/0.png"),
				new ImageView("digits/0.png"),
				new ImageView("digits/0.png")
				);
	}
	
	//convert the time elapsed to each of the three digits
	//to set by the correct img
	private void addTime() {
		time++;
		digit1 = getDigit(time / 100);
		digit2 = getDigit(time / 10);
		digit3 = getDigit(time % 10);
		
		getChildren().clear();
		getChildren().addAll(digit1, digit2, digit3);
	}
	
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
	
	public int getTime() {
		return time;
	}
}
