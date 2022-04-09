import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderImage;
import javafx.scene.layout.BorderRepeat;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Game extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override 
	public void start(Stage primaryStage) {
		//start with difficulty menu
		new DiffMenu();
	}
}


class MainStage extends Stage {
	public static int lvl;
	
	MainStage(int dif) {
		lvl = dif;
		
	
		Header header = new Header();
		header.setBorder(new Border(new BorderImage(new Image("cover.png"), 
													new BorderWidths(10, 10, 0, 10), 
													new Insets(0,0,0,0), 
													new BorderWidths(10), 
													false, 
													BorderRepeat.REPEAT, 
													BorderRepeat.REPEAT)
													));		
		
		//initialize board with header and lvl
		//and populate it with empty cover tiles
		new Board(lvl, header);
		Board.showInitial();
		
		//Retrieve the grid of Tiles
		GridPane squares = Board.getSquares();
		
		squares.setAlignment(Pos.CENTER);
		squares.setHgap(1);
		squares.setVgap(1);
		squares.setPadding(new Insets(1,1,1,1));
		squares.setBorder(new Border(new BorderImage(new Image("cover.png"), 
														new BorderWidths(10), 
														new Insets(0,0,0,0), 
														new BorderWidths(14), 
														false, 
														BorderRepeat.STRETCH, 
														BorderRepeat.STRETCH)
														));		
		
		
		Menu menu = new Menu("Difficulty");
		MenuItem easy = new MenuItem("Beginner");
		MenuItem medium = new MenuItem("Intermediate");
		MenuItem hard = new MenuItem("Expert");
		
		menu.getItems().addAll(easy, medium, hard);
		
		Menu highScore = new Menu("High Scores");
		MenuItem hs = new MenuItem("High Scores");
		
		highScore.getItems().add(hs);
		
		//menu actions
		easy.setOnAction(e -> {
			new MainStage(0);
			close();
		});
		medium.setOnAction(e -> {
			new MainStage(1);
			close();
		});
		hard.setOnAction(e -> {
			new MainStage(2);
			close();
		});
		hs.setOnAction(e -> new HSDisplay(Board.getHighScores()));
		
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(menu, highScore);
		
		//Vbox at highest level containing the menu, then the header 
		//and then the tiles
		VBox vbox = new VBox();
		vbox.getChildren().addAll(menuBar, header, squares);
				
				
		Scene scene = new Scene(vbox);
		setTitle("MineSweeper");
		setScene(scene);
		show();
		
		
	}
}

//Difficulty menu when first start game
class DiffMenu extends Stage {
	VBox vbox;
	Label label;
	
	Button beginner;
	Button intermediate;
	Button expert;
	
	
	
	DiffMenu() {
		
		beginner = new Button("Beginner");
		intermediate = new Button("Intermediate");
		expert = new Button("Expert");
		label = new Label("Pick your difficulty");
		
		vbox = new VBox();
		vbox.setPadding(new Insets(10, 0, 10, 0));
		vbox.getChildren().addAll(label, beginner, intermediate, expert);
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(5);
		
		setTitle("Difficulty");
		setScene(new Scene(vbox));
		show();
		
		beginner.setOnAction(e -> { 
				new MainStage(0);
				close();
		});
		
		intermediate.setOnAction(e -> { 
				new MainStage(1); 
				close();
		});
		
		expert.setOnAction(e -> { 
				new MainStage(2);
				close();
		});
	}
}

//name prompt when you get a high score
class HSName extends Stage {
	VBox vbox;
	Label prompt;
	TextField nameGetter;
	Button ok;
	String name;
	
	HSName(int lvl, ArrayList<HighScore> scores) {
		String s;
		
		if(lvl == 0) {
			s = "You have the fastest time for beginner "
					+ "level.";
		}else if(lvl == 1) {
			s = "You have the fastest time for intermediate "
					+ "level.";
		}else {
			s = "You have the fastest time for expert "
					+ "level.";
		}
		
		prompt = new Label(s);
		
		ok = new Button("OK");
		
		ok.setOnAction(e -> {
			name = nameGetter.getText();
			scores.get(lvl).setName(name);
			scores.get(lvl).setTime(Header.time.getTime());
			new HSDisplay(scores);
			
			try	{ 
				PrintWriter pw = new PrintWriter(new File("highScores.txt"));
				pw.print("");
				for(int i = 0; i < scores.size(); i ++) {
					pw.println(scores.get(i).toString());
				}
				pw.close();
			}catch(FileNotFoundException f) {
				
			}
			
			
			close();
		});
		
		nameGetter = new TextField();
		
		vbox = new VBox();
		vbox.getChildren().addAll(prompt, nameGetter, ok);
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(5);
		
		setTitle("HIGH SCORE!");
		setScene(new Scene(vbox));
		show();
	}
}


//High scores display
class HSDisplay extends Stage {
	VBox vbox;
	
	HBox beginner;
	HBox intermediate;
	HBox expert;
	
	Label beg;
	Label begTime;
	Label begName;
	
	Label inter;
	Label interTime;
	Label interName;
	
	Label exp;
	Label expTime;
	Label expName;
	
	Button ok;
	
	
	HSDisplay(ArrayList<HighScore> scores) {
		
		
		beginner = new HBox();	
		intermediate = new HBox();
		expert = new HBox();
		
		beg = new Label("Beginner:");
		inter = new Label("Intermediate:");
		exp = new Label("Epert:");
		
		beg.setPrefWidth(80);
		inter.setPrefWidth(80);
		exp.setPrefWidth(80);

		begTime = new Label(scores.get(0).getTime() + " seconds");
		interTime = new Label(scores.get(1).getTime() + " seconds");
		expTime = new Label(scores.get(2).getTime() + " seconds");

		begTime.setPrefWidth(70);
		interTime.setPrefWidth(70);
		expTime.setPrefWidth(70);
		
		begName = new Label(scores.get(0).getName());
		interName = new Label(scores.get(1).getName());
		expName = new Label(scores.get(2).getName());

		begName.setMaxWidth(80);
		interName.setMaxWidth(80);
		expName.setMaxWidth(80);
		
		ok = new Button("OK");
		ok.setOnAction(e -> close());
		
		
		beginner.getChildren().addAll(beg, begTime, begName);
		intermediate.getChildren().addAll(inter, interTime, interName);
		expert.getChildren().addAll(exp, expTime, expName);

		vbox = new VBox();
		vbox.getChildren().addAll(beginner, intermediate, expert, ok);
		
		vbox.setSpacing(3);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(20));
		
		VBox.setMargin(ok, new Insets(10, 0, 0, 0));
		
		setTitle("High Scores");
		setScene(new Scene(vbox));
		show();
	}
	
}




















