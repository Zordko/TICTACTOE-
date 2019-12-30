import java.io.Serializable;
import java.util.ArrayList;

public class GameInfo implements Serializable {

	private static final long serialVersionUID = 5867735837149809485L;

	private String[] board = { "b", "b", "b", "b", "b", "b", "b", "b", "b" };
	private Difficulty selectedDifficulty;
	private int status = -1;
	private boolean playerWon = false;
	private ArrayList<String> topThreeClients = new ArrayList<String>();
	private int playerScore = 0;

	//Statuses
	public static final int DISPLAY_MOVE = 0;
	public static final int GAME_OVER = 1;
	public static final int UPDATE_TOP_CLIENTS = 2;
	
	// Getters
	public String[] getBoard() {
		return board;
	}

	public Difficulty getDifficulty() {
		return selectedDifficulty;
	}
	
	public int getStatus() {
		return status;
	}
	
	public boolean isPlayerWinner() {
		return playerWon;
	}
	
	public ArrayList<String> getTopThreeClients() {
		return topThreeClients;
	}

	public int getPlayerScore() {
		return playerScore;
	}
	
	// Setters
	public void setBoard(String[] board) {
		this.board = board;
	}

	public void setDifficulty(Difficulty selectedDifficulty) {
		this.selectedDifficulty = selectedDifficulty;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public void setIsPlayerWinner(boolean playerWon) {
		this.playerWon = playerWon;
	}
	
	public void setTopThreeClients(ArrayList<String> topThreeClients) {
		this.topThreeClients = topThreeClients;
	}
	
	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}
	
	public void reset() {
		// Set the board to all be blanks
		for (int i = 0; i < 9; i++) {
			board[i] = "b";
		}
		
		topThreeClients.clear();
		selectedDifficulty = null;
		status = -1;
		playerWon = false;
		
	}

	public void printBoard() {
		System.out.println(board[0] + " " + board[1] + " " + board[2]);
		System.out.println(board[3] + " " + board[4] + " " + board[5]);
		System.out.println(board[6] + " " + board[7] + " " + board[8]);

	}

}
