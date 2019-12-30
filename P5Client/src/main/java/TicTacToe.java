import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TicTacToe extends Application {

	private Stage primaryStage;

	// Scenes
	private ConnectionScene connectionScene;
	private MainMenuScene mainMenuScene;
	private GameScene gameScene;

	// Client
	private Client client;

	// feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		setStageDetails();

		createComponents();
		setEventHandlers();
		addComponents();
	}

	private void addComponents() {
		displayScene(connectionScene.getScene());

	}

	private void setEventHandlers() {
		//Connection Scene EventHandlers
		connectionScene.getConnectButton().setOnMouseClicked(e -> {
			if (isPortValid(connectionScene.getPort())) {
				displayScene(mainMenuScene.getScene());
				client = new Client(connectionScene.getIP(), connectionScene.getPort(), this);
				client.start();
			}
		});

		// Main Menu Scene EventHandlers
		mainMenuScene.getEasyButton().setOnMouseClicked(e -> {
			displayScene(gameScene.getScene());
			gameScene.setGameDifficulty(Difficulty.EASY);
			gameScene.getPlayAgainButton().setDisable(true);
		});

		mainMenuScene.getMediumButton().setOnMouseClicked(e -> {
			displayScene(gameScene.getScene());
			gameScene.setGameDifficulty(Difficulty.MEDIUM);
			gameScene.getPlayAgainButton().setDisable(true);
		});

		mainMenuScene.getHardButton().setOnMouseClicked(e -> {
			displayScene(gameScene.getScene());
			gameScene.setGameDifficulty(Difficulty.HARD);
			gameScene.getPlayAgainButton().setDisable(true);
		});

		//Game Scene EventHandlers
		gameScene.getMoveButton(0).setOnMouseClicked(e ->client.send(gameScene.declareNewMove(0)));
		gameScene.getMoveButton(1).setOnMouseClicked(e ->client.send(gameScene.declareNewMove(1)));
		gameScene.getMoveButton(2).setOnMouseClicked(e ->client.send(gameScene.declareNewMove(2)));
		gameScene.getMoveButton(3).setOnMouseClicked(e ->client.send(gameScene.declareNewMove(3)));
		gameScene.getMoveButton(4).setOnMouseClicked(e ->client.send(gameScene.declareNewMove(4)));
		gameScene.getMoveButton(5).setOnMouseClicked(e ->client.send(gameScene.declareNewMove(5)));
		gameScene.getMoveButton(6).setOnMouseClicked(e ->client.send(gameScene.declareNewMove(6)));
		gameScene.getMoveButton(7).setOnMouseClicked(e ->client.send(gameScene.declareNewMove(7)));
		gameScene.getMoveButton(8).setOnMouseClicked(e ->client.send(gameScene.declareNewMove(8)));
		gameScene.getQuitButton().setOnMouseClicked(e->System.exit(0));
		gameScene.getPlayAgainButton().setOnMouseClicked(e->{
			displayScene(mainMenuScene.getScene());
			gameScene.reset();
		});

	}

	private void createComponents() {
		connectionScene = new ConnectionScene(270, 140);
		mainMenuScene = new MainMenuScene(305, 415);
		gameScene = new GameScene(370, 400);
	}

	// Returns true if the port is valid
	private boolean isPortValid(String port) {
		try {
			Integer.valueOf(port);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private void displayScene(Scene scene) {
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void setStageDetails() {
		primaryStage.setTitle("Client - Tic Tac Toe");

		primaryStage.setOnCloseRequest(e -> {
			if (client != null) {
				client.shutdown();
			}
			Platform.exit();
		});

	}

	//Updates the GameBoard when the server makes a move
	public void updateBoard(GameInfo gameInfo) {
		gameScene.updateBoard(gameInfo);
	}

	//Disables the game board and enables the play again button
	public void gameIsOver(boolean playerWon) {
		gameScene.gameIsOver();

		if(playerWon) {
			int newScore = mainMenuScene.incrementPoint();
			gameScene.updateScore(newScore); //Update the GameInfo player score variable
		}

	}

	//Updates the top three clients in the main menu
	public void updateTopClients(ArrayList<String> topThreeClients) {
		mainMenuScene.updateTopClients(topThreeClients);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
}
