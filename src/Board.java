import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.layout.GridPane;



public class Board {
	//Variables to access tile, gridpane, header, time and a Tile 2D array,
	//
	private static Tile[][] board;
	private static Header header;
	private static GridPane squares; 
	//private static Tile tile;
	//private static Time time;
	
	//Width, height, and a board of ints
	//mum mines and number of correct flags
	private static int width;
	private static int height;
	private static int[][] intBoard;
	private static int numMines;
	public static int correctFlags;
	public static int level;
	
	//Difficulty is based on an int: 0 for beginner 1 for intermediate and 2 for expert
	//pass in the header to access the score and pass in the time
	public Board(int level, Header header) {
		Board.level = level;
		Board.header = header;
		//Board.time = Header.time;
		
		if(level == 0) {
			width = 8;
			height = 8;
			numMines = 10;
		}else if(level == 1) {
			width = 16;
			height = 16;
			numMines = 40;
		}else {
			width = 32;
			height = 16;
			numMines = 99;
		}
		
		squares = new GridPane();
		
	}
	
	//create a blank board with cover tiles only. no mines or numbers
	public static void showInitial() {
		Tile cover;
		board = new Tile[height][width];
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				cover = new Tile(header, i, j);
				board[i][j] = cover;
				squares.add(cover, j, i);
			}
		}
	}
	
	//sets the board when the first tile is clicked, generates mines and numbers
	//making sure the first click is on a blank tile.
	public static void resetBoard() {
		
		//time = Header.time; //keeps the time variable up to date with current time obj
		intBoard = new int[height][width]; //initializes the board of ints
		int[][] mines = new int[numMines][2]; //an array for the row and col of the mines
		int firstRow = 0;
		int firstCol = 0;
		
		//get first tile clicked
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(board[i][j].getFirstTile()) {
					firstRow = i;
					firstCol = j;
				}
			}
		}
		
		//First clicked tile and surrounding tiles must not be a mine
		//get an array of indexes of tiles that cannot be a mine
		int[][] noMine = getNoMines(firstRow, firstCol);
		
		//populates the mines array checking that all mines are at different spots
		//on the game board
		int mineIndex = 0;
		boolean add;
		int row;
		int col;
		
		while(mineIndex < numMines) {
			add = true;
			row = (int)(Math.random() * height);
			col = (int)(Math.random() * width);
			
			for(int i = 0; i < noMine.length; i++) {
				if(row == noMine[i][0] && col == noMine[i][1])
					add = false;
			}
			
			if(add) {
				
				for(int j = 0; j < numMines; j++) {
					if(row == mines[j][0] && col == mines[j][1]) 
						add = false;
				}
			}
			
			if(add) {
				mines[mineIndex][0] = row;
				mines[mineIndex][1] = col;
				mineIndex++;
			}
			
		}
		
		//calculate numbers for each tile
		addNumbers(mines);
		
		
		//setImg for all tiles
		for(int i = 0; i < height; i ++) {
			for(int j = 0; j < width; j++) {
				
				board[i][j].setImg(intBoard[i][j]);
				
			}
		}
		
	}
	
	//iterates through all the mines and adds + 1 in all valid directions
			//to the tiles beside it. updating the board with the correct numbers
			//for each tile
	public static void addNumbers(int[][] mines) {
		int row;
		int col;
		for(int i = 0; i < numMines; i++) {
			
			row = mines[i][0];
			col = mines[i][1];
			intBoard[row][col] = 9;
			
			for(int j = -1; j <= 1; j++) {
				for(int k = -1; k <= 1; k++) {
					
					if(isValid((row + j), (col + k))) {
						
						if(intBoard[row + j][col + k] != 9) 
							intBoard[row + j][col + k]++;
					}
				}
			}
		}
	}

	//recursion method to uncover all blank tiles on first click and 
	//when you click a blank tile
	public static void recurse(int row, int col) {
		//loop to check all 8 surrounding squares
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				if(isValid((row + i), (col + j)) && !board[row + i][col + j].getShow()) {
										
					if(board[row + i][col + j].getImg() == 0 && !board[row + i][col + j].getShow()) {
						
						board[row + i][col + j].changeImage();
						recurse((row + i), (col + j));
						
					}else if(board[row + i][col + j].getImg() != 0 && !board[row + i][col + j].getShow()) { 
						
						if(!board[row + i][col + j].getFlag()) {
							board[row + i][col + j].changeImage();
						}
					}
				}
			}
		}
	}
	
	
	//takes in the row and col of the first clicked tile and returns an array
	//of tiles that cannot be mines. the tile clicked and surrounding tiles.
	private static int[][] getNoMines(int row, int col) {
		int[][] noMines = new int[9][2];
		int k = 0;
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				
				if(isValid((row + i), (col + j))) {
					noMines[k][0] = row + i;
					noMines[k][1] = col + j;
				}else {
					noMines[k][0] = -1;
					noMines[k][1] = -1;
				}
				
				k++;	
			}
		}
		return noMines;
	}
	
	//used in the recurse method to make sure the next tile index is in bounds
	private static boolean isValid(int row, int col) {
		if(row >= 0 && col >= 0) {
			
			if(row < height && col < width) {
				return true;
			}
		}
		return false;
	}
	
	//method to check if an uncovered tile has the correct number of
	//flags around it. used with the click uncovered number to show 
	//surroundings feature
	public static boolean correctFlags(int row, int col, int mines) {
		int count = 0;
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				
				if(isValid((row + i), (col + j))) {
					
					if(board[row + i][col + j].getFlag()) {
						count++;
					}
				}
			}
		}
		
		if(count == mines) {
			return true;
		}
		
		return false;
	}
	
	//if you click on an uncovered tile that has the correct number of flags
	//all surrounding tiles will show. if flagged incorrectly will 
	//uncover a mine and game over.
	public static void showSurrounding(int row, int col) {
		//loop to check all 8 surrounding squares
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
					
				if(isValid(row + i, col + j)) {
					if(!board[row + i][col + j].getFlag()) {
						
						//call the recursion method if 0 is uncovered
						if(board[row + i][col + j].getImg() == 0) {
							recurse((row + i), (col + j));
						}
						board[row + i][col + j].changeImage();
					}
				}
			}
		}
		checkWin();
	}
	
	//Check if game is won and set the sunglass smiley if its won
	public static void checkWin() {
		boolean win = true;
		//check all non mine tiles have been uncovered
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(!board[i][j].getMine() && !board[i][j].getShow()) {
					win = false;
				}
			}
		}
		
		if(win) {
			Header.time.stop();
			header.setWin();
			Tile.gameOver = true;
			handleHighScore();
		}
	}
	
	//check for misflagged tiles
	public static void checkFlags() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				
				if(board[i][j].getFlag() && !board[i][j].getMine()) {
					board[i][j].setMisflagged();
				}
			}
		}
	}
	
	//if a mine is clicked display all the other uncovered mines 
	public static void showMines() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(intBoard[i][j] == 9 && !board[i][j].getFlag()) {
					board[i][j].displayGrey();
				}
			}
		}
	}
	
	//check to see if the game was a high score or not
	//call new high score window or display high scores
	public static void handleHighScore() {
		ArrayList<HighScore> scores = getHighScores();
		
		boolean newScore = false;
		if(level == 0) {
			if(Header.time.getTime() < scores.get(0).getTime()) {
				newScore = true;
				new HSName(level, scores);
			}
		}else if(level == 1) {
			if(Header.time.getTime() < scores.get(1).getTime()) {
				newScore = true;
				new HSName(level, scores);
			}
		}else {
			if(Header.time.getTime() < scores.get(2).getTime()) {
				newScore = true;
				new HSName(level, scores);
			}
		}
		
		
		if(!newScore) {
			new HSDisplay(scores);
		}
		
	}
	
	//retrieve high scores
	//called when a game ends or when user clicks on the 
	//high score menu
	public static ArrayList<HighScore> getHighScores() {
		Scanner input;
		ArrayList<HighScore> scores = new ArrayList<>();
		
		int lvl;
		int HStime;
		
		int firstDelimiter;
		int secondDelimiter;
		int thirdDelimiter;
		
		String name = "";
		String inputLine;
		
		try { 
			input = new Scanner(new File("highScores.txt"));
			while(input.hasNextLine()) {
				inputLine = input.nextLine();
				
				lvl = getLevel(inputLine.charAt(0));
				
				firstDelimiter = inputLine.indexOf(" ");
				secondDelimiter = inputLine.indexOf(" ", inputLine.indexOf(" ") + 1);
				thirdDelimiter = inputLine.indexOf(" ", secondDelimiter + 1);
				
				HStime = Integer.parseInt(inputLine.substring(firstDelimiter + 1, secondDelimiter));
				
				name = inputLine.substring(thirdDelimiter + 1);
				
				scores.add(new HighScore(name, HStime, lvl));
			}
			input.close();
		}catch(FileNotFoundException e) {
		
		}
		
		if(scores.size() < 1) {
			scores.add(new HighScore(">:(", 999, 0));
			scores.add(new HighScore(">:(", 999, 1));
			scores.add(new HighScore(">:(", 999, 2));
		}
		
		return scores;
	}
	
	public static int getLevel(char c) {
		if(c == 'B') 
			return 0;
		if(c == 'I')
			return 1;
	
		return 2;
	}
	
	public static GridPane getSquares() {
		return squares;
	}
}



