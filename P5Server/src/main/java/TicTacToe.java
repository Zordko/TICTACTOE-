import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TicTacToe extends Application {

	private Stage primaryStage;

	// Scenes
	private ConnectionScene connectionScene;
	private ServerScene serverScene;
	private GameBoardScene gameBoardScene;

	// Server
	private Server server;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		setStageDetails();

		createComponents();
		setEventHandlers();
		addComponents();
	}

	private void addComponents() {
		// Display Initial Scene
		displayScene(connectionScene.getScene());
	}

	private void setEventHandlers() {
		// Add EventHandler for when the user presses the "Connect" button
		connectionScene.getConnectButton().setOnMouseClicked(e -> {
			String port = connectionScene.getPort();

			if (isPortValid(port)) {
				displayScene(serverScene.getScene());

				server = new Server(data -> {
					Platform.runLater(() -> {
						serverScene.getActivityListView().getItems().add(data.toString());
					});
				}, connectionScene.getPort(), this);
			}
		});

		//Add EventHandler for the ServerScene
		serverScene.getClientListView().setOnMouseClicked(e -> {
			String clientPressed = serverScene.getClientListView().getSelectionModel().getSelectedItem();
			
			if(clientPressed != null) {
				int clientNumber = Integer.valueOf(clientPressed.substring(clientPressed.indexOf(" ") + 1));
				gameBoardScene.setClientNumber(clientNumber);
				updateServerBoard(server.getBoard(clientNumber), clientNumber);			
				displayScene(gameBoardScene.getScene());
				
			}
		});
		
		//Add EventHandler for the gameBoardScene
		gameBoardScene.getBackButton().setOnMouseClicked(e->{
			gameBoardScene.reset();
			displayScene(serverScene.getScene());
		});

	}

	private void createComponents() {
		connectionScene = new ConnectionScene(270, 100);
		serverScene = new ServerScene(440, 320);
		gameBoardScene = new GameBoardScene(350, 380);
	}

	
	//Updates the game board that's on the server (assuming we're showing a specific client game)
	public void updateServerBoard(String[] board, int clientNumber) {
		gameBoardScene.updateBoard(board, clientNumber);
	}
	
	// Updates the list of clients ListView
	public void updateClientList(ArrayList<String> listOfClients) {
		Platform.runLater(() -> {
			serverScene.getClientListView().getItems().clear();
			serverScene.getClientListView().getItems().addAll(listOfClients);
		});
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
		primaryStage.setTitle("Server - Tic Tac Toe");

		primaryStage.setOnCloseRequest(e -> {
			if (server != null) {
				server.shutdown();
			}
			Platform.exit();
		});
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
}
