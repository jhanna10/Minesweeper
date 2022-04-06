import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
//Jason Hanna
//300266085
public class Header extends BorderPane {
	public Score score;
	public static Time time;
	private ImageView smiley;
		
	
	//Create the header for the game
	public Header() {
		score = new Score(MainStage.lvl);
		time = new Time();
		
		smiley = new ImageView("face-smile.png");
		smiley.setOnMouseClicked(e -> resetGame());
		
		//keep the score and time obj in the middle
		setMargin(score, new Insets(15, 0, 0, 0));
		setMargin(time, new Insets(15, 0, 0, 0));
		
		setLeft(score);
		setRight(time);
		setCenter(smiley);
		
	}
	
	//Set the smiley to sad when mine hit
	public void setSad() {
		smiley = new ImageView("face-dead.png");
		setCenter(smiley);
		
		Board.checkFlags();
		
		smiley.setOnMouseClicked(e -> {
			resetGame();
		});
	}
	
	
	//method for setting the sunglasses smiley for a win.
	public void setWin() {
		smiley = new ImageView("face-win.png");
		setCenter(smiley);
		
		smiley.setOnMouseClicked(e -> {
			resetGame();
		});
	}
	
	//O face every time mouse is pressed down
	public void setO() {
		smiley = new ImageView("face-O.png");
		setCenter(smiley);
		
		smiley.setOnMouseClicked(e -> {
			resetGame();
		});
	}
	
	public void setSmile() {
		smiley = new ImageView("face-smile.png");
		setCenter(smiley);
		
		smiley.setOnMouseClicked(e -> {
			resetGame();
		});
	}
	
	//Method to reset the game.
	public void resetGame() {
		//make sure the isFirst variable resets for new game
		Tile.isFirst = true;
		Tile.gameOver = false;
		
		//reset smiley face
		smiley = new ImageView("face-smile.png");
		setCenter(smiley);
		
		//reset score and time with new obj
		score = new Score(MainStage.lvl);
		time = new Time();
		
		//make sure new obj are in middle
		setMargin(score, new Insets(15, 0, 0, 0));
		setMargin(time, new Insets(15, 0, 0, 0));
		
		setLeft(score);
		setRight(time);
		
		//reset the board 
		Board.showInitial();
		
		smiley.setOnMouseClicked(e -> {
			resetGame();
		});
	}
	
	public Time getTime() {
		return time;
	}
}

