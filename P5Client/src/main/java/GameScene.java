import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class GameScene {

	private int sceneWidth;
	private int sceneHeight;

	// Components
	private Button[] buttons;
	private Button playAgainButton;
	private Button quitButton;

	// Layouts
	private GridPane gameButtonGrid;
	private HBox bottomButtonsHBox;
	private VBox sceneVBox;
	private Scene scene;
	
	//Game Info
	private GameInfo gameInfo;

	public GameScene(int sceneWidth, int sceneHeight) {
		this.sceneWidth = sceneWidth;
		this.sceneHeight = sceneHeight;

		createComponents();
		editComponents();
		addComponents();
	}

	private void addComponents() {
		gameButtonGrid.getChildren().addAll(buttons);
		bottomButtonsHBox.getChildren().addAll(playAgainButton, quitButton);
		sceneVBox.getChildren().addAll(gameButtonGrid, bottomButtonsHBox);
		scene = new Scene(sceneVBox, sceneWidth, sceneHeight);


	}

	private void editComponents() {
		Font buttonFont = new Font("Times New Roman", 45);
		for (int i = 0; i < 9; i++) {
			buttons[i].setPrefSize(100, 100);
			buttons[i].setFont(buttonFont);
			buttons[i].setStyle("-fx-background-color:LightSlateGray;");
			buttons[i].setBlendMode(BlendMode.DIFFERENCE);
		}

		playAgainButton.setPrefWidth(100);
		playAgainButton.setDisable(true);
		quitButton.setPrefWidth(100);
		
		gameButtonGrid.setAlignment(Pos.CENTER);
		gameButtonGrid.setPadding(new Insets(10));
		gameButtonGrid.setHgap(7);
		gameButtonGrid.setVgap(7);
		
		bottomButtonsHBox.setAlignment(Pos.CENTER);
		bottomButtonsHBox.setPadding(new Insets(10));
		bottomButtonsHBox.setSpacing(10);
		
		sceneVBox.setAlignment(Pos.CENTER);
		sceneVBox.setPadding(new Insets(10));
		
		
		GridPane.setConstraints(buttons[0], 0, 0);
		GridPane.setConstraints(buttons[1], 1, 0);
		GridPane.setConstraints(buttons[2], 2, 0);
		GridPane.setConstraints(buttons[3], 0, 1);
		GridPane.setConstraints(buttons[4], 1, 1);
		GridPane.setConstraints(buttons[5], 2, 1);
		GridPane.setConstraints(buttons[6], 0, 2);
		GridPane.setConstraints(buttons[7], 1, 2);
		GridPane.setConstraints(buttons[8], 2, 2);

	}

	private void createComponents() {
		// Initialize the 9 buttons used for GamePlay
		buttons = new Button[9];
		for (int i = 0; i < 9; i++) {
			buttons[i] = new Button();
		}

		// Initialize components
		playAgainButton = new Button("Play Again");
		quitButton = new Button("Quit");

		// Initialize layouts
		gameButtonGrid = new GridPane();
		gameButtonGrid.setStyle("-fx-background-color:Aquamarine;");
		bottomButtonsHBox = new HBox();
		bottomButtonsHBox.setStyle("-fx-background-color:LightSlateGray;");
		sceneVBox = new VBox();
		
		//Game components
		gameInfo = new GameInfo();
	}

	public Scene getScene() {
		return scene;
	}

	public Button getPlayAgainButton() {
		return playAgainButton;
	}

	public Button getQuitButton() {
		return quitButton;
	}
	
	public Button getMoveButton(int index) {
		return buttons[index];
	}
	
	
	//Changes the board array in GameInfo. Returns the updated GameInfo
	public GameInfo declareNewMove(int index) {
		//Disable the button
		buttons[index].setDisable(true);
		
		//Change the text of the button
		buttons[index].setText("O");
		
		//Change the GameInfo board
		gameInfo.getBoard()[index] = "O";
		
		//Disable the board
		setBoardDisabled(true);
		
		return gameInfo;
	}
	
	//Enables or disables the button moves
	private void setBoardDisabled(boolean disabled) {
		for(int i = 0; i < 9; i++) {
			//If we're enabling the buttons and the button has no text on it (Only enable the unclicked buttons)
			//OR if we're disabling the buttons
			if(!disabled && buttons[i].getText().equals("") || disabled) {
				buttons[i].setDisable(disabled);
			}
			
		}
	}
	
	//Sets the difficulty in the GameInfo object
	public void setGameDifficulty(Difficulty selectedDifficulty) {
		gameInfo.setDifficulty(selectedDifficulty);
	}

	public void updateBoard(GameInfo gameInfo) {
		this.gameInfo = gameInfo;
		
		String[] board = gameInfo.getBoard();
		
		Platform.runLater(()->{
			updateBoardText(board);
			setBoardDisabled(false);
		});
		
	}

	private void updateBoardText(String[] board) {
		for(int i = 0; i < 9; i++) {
			if(!board[i].equals("b")) {
				buttons[i].setText(board[i]);
			}
		}
		
	}

	public void gameIsOver() {
		Platform.runLater(()->{
			setBoardDisabled(true);
			playAgainButton.setDisable(false);
		});
	}

	public void reset() {
		for(int i = 0; i < 9; i++) {
			//Put the text back to normal
			buttons[i].setText("");
			
			//Reset the game info
			gameInfo.reset();
		}
		
		//Enable the board
		setBoardDisabled(false);
		
	}

	public void updateScore(int newScore) {
		gameInfo.setPlayerScore(newScore);
		
	}

}
