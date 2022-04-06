import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
//Jason Hanna
//300266085
public class Tile extends ImageView {
	//keep a reference to the header and time
	private Header header;
	
	private boolean show; //tile showing or not
	private int img; //what the number is
	private boolean mine; //mine or not
	private boolean flag; //if its been flagged
	private boolean firstTile;
	
	public static boolean isFirst; // first click to start time
	public static boolean gameOver;;
	
	//constructor for creating tiles 
	public Tile(Header header, int row, int col) {
		isFirst = true;
		gameOver = false;
		this.header = header;
		firstTile = false;
		flag = false;
		show = false;
		mine = false;
		
		
		setImage(new Image("cover.png"));
		
		//O face when mouse pressed down 
		//and back to normal when released
		setOnMousePressed(e -> {
			if(!gameOver) {
				header.setO();
			}
		});
		setOnMouseReleased(e -> {
			if(!gameOver) {
				header.setSmile();
			}
		});
		
		//mouse clicks for all the tiles and different scenarios
		setOnMouseClicked(e -> {
			if(e.getButton() == MouseButton.PRIMARY && flag == false && !gameOver) {
				
				//First tile clicked generates new board with mines and numbers
				if(isFirst) {
					firstTile = true;
					setImage(new Image("0.png"));
					Board.resetBoard();
					Header.time.startTime();
					Board.recurse(row, col);
					isFirst = false;
				
				//if tile is uncovered and correct number of flags show surroundings	
				}else if(show){
					
					if(Board.correctFlags(row, col, img)) {
						Board.showSurrounding(row, col);
					}
					
				//normal gameplay click	
				}else {
					changeImage();
					Board.checkWin();
					
					if(img == 0) {
						Board.recurse(row, col);
					}
					
					if(mine) {
						flag = true;
						header.setSad();
						Header.time.stop();
						Board.showMines();
						gameOver = true;
					}
				}
				
			//right click handlers	
			}else if(e.getButton() == MouseButton.SECONDARY && !gameOver && !show) {
				
				if(flag) {
					setImage(new Image("cover.png"));
					flag = false;
					header.score.addScore();
					if(mine)
						Board.correctFlags --;
				}else {
					setImage(new Image("flag.png"));
					flag = true;
					header.score.subScore();
					if(mine)
						Board.correctFlags ++;
				}
			}
			
		});
	}
	
	
	public void setImg(int img) {
		this.img = img;
		if(img == 9)
			mine = true;
	}
	
	//Change Image depending on what the tile number is
	public void changeImage() {
		//System.out.println("changeImage row: " + row + " col: " + col);
		show = true;
		if(img == 0) {
			setImage(new Image("0.png"));
		}else if(img == 1) {
			setImage(new Image("1.png"));
		}else if(img == 2) {
			setImage(new Image("2.png"));
		}else if(img == 3) {
			setImage(new Image("3.png"));
		}else if(img == 4) {
			setImage(new Image("4.png"));
		}else if(img == 5) {
			setImage(new Image("5.png"));
		}else if(img == 6) {
			setImage(new Image("6.png"));
		}else if(img == 7) {
			setImage(new Image("7.png")); 
		}else if(img == 8) {
			setImage(new Image("8.png"));
		}else if(img == 9) {
			setImage(new Image("mine-red.png"));
			flag = true;
			header.setSad();
			Header.time.stop();
			Board.showMines();
			gameOver = true;
		}
	}
	
	
	public void setMisflagged() {
		setImage(new Image("mine-misflagged.png"));
	}
	
	public int getImg() {
		return img;
	}
	
	//Check to see if the tile is showing or not
	public boolean getShow() {
		return show;
	}
	
	//check to see if the tile is a mine or not.
	public boolean getMine() {
		return mine;
	}
	
	public boolean getFlag() {
		return flag;
	}
	
	public void displayGrey() {
		setImage(new Image("mine-grey.png"));
	}
	
	public boolean getFirstTile() {
		return firstTile;
	}
}
