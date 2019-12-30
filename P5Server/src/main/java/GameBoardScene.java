import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class GameBoardScene {

	private int sceneWidth;
	private int sceneHeight;

	// Components
	private Button[] buttons;
	private Button backButton;

	// Layouts
	private GridPane buttonGrid;
	private VBox sceneVBox;
	private Scene scene;

	// Client Info
	private int clientNumber;

	public GameBoardScene(int sceneWidth, int sceneHeight) {
		this.sceneWidth = sceneWidth;
		this.sceneHeight = sceneHeight;

		createComponents();
		editComponents();
		addComponents();
	}

	private void addComponents() {
		buttonGrid.getChildren().addAll(buttons);
		sceneVBox.getChildren().addAll(buttonGrid, backButton);
		scene = new Scene(sceneVBox, sceneWidth, sceneHeight);

	}

	private void editComponents() {
		Font buttonFont = new Font("Times New Roman", 45);
		for (int i = 0; i < 9; i++) {
			buttons[i].setPrefSize(100, 100);
			buttons[i].setFont(buttonFont);
			buttons[i].setDisable(true);
		}

		buttonGrid.setAlignment(Pos.CENTER);
		buttonGrid.setPadding(new Insets(10));
		buttonGrid.setHgap(7);
		buttonGrid.setVgap(7);
		GridPane.setConstraints(buttons[0], 0, 0);
		GridPane.setConstraints(buttons[1], 1, 0);
		GridPane.setConstraints(buttons[2], 2, 0);
		GridPane.setConstraints(buttons[3], 0, 1);
		GridPane.setConstraints(buttons[4], 1, 1);
		GridPane.setConstraints(buttons[5], 2, 1);
		GridPane.setConstraints(buttons[6], 0, 2);
		GridPane.setConstraints(buttons[7], 1, 2);
		GridPane.setConstraints(buttons[8], 2, 2);

		backButton.setPrefWidth(100);

		sceneVBox.setAlignment(Pos.CENTER);
	}

	private void createComponents() {
		// Initialize the 9 buttons used for GamePlay
		buttons = new Button[9];
		for (int i = 0; i < 9; i++) {
			buttons[i] = new Button();
		}

		backButton = new Button("Back");
		buttonGrid = new GridPane();
		sceneVBox = new VBox();
	}

	public Scene getScene() {
		return scene;
	}

	public void setClientNumber(int clientNumber) {
		this.clientNumber = clientNumber;
	}
	
	public void reset() {
		clientNumber = -1;
		
		for(int i = 0; i < 9; i++) {
			buttons[i].setText("");
		}
	}

	public Button getBackButton() {
		return backButton;
	}

	public void updateBoard(String[] board, int clientNumber) {
		Platform.runLater(() -> updateButtons(board, clientNumber));
	}

	private void updateButtons(String[] board, int clientNumber) {
		// If the client number matches
		if (this.clientNumber == clientNumber) {
			for (int i = 0; i < buttons.length; i++) {
				if (!board[i].equals("b")) {
					buttons[i].setText(board[i]);
				}
			}
		}
	}
}
