import java.util.ArrayList;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainMenuScene {

	private int sceneWidth;
	private int sceneHeight;
	
	//Components
	private Text scoreText;
	private Text topClientsText;
	
	private TextField scoreTextField;
	private ListView<String> topClientsListView;
	
	private Button easyButton;
	private Button mediumButton;
	private Button hardButton;
	
	//Layouts
	private HBox scoreHBox;
	private VBox topClientsVBox;
	private HBox difficultyHBox;
	private VBox sceneVBox;
	private Scene scene;
	
	
	public MainMenuScene(int sceneWidth, int sceneHeight) {
		this.sceneWidth = sceneWidth;
		this.sceneHeight = sceneHeight;
		
		createComponents();
		editComponents();
		addComponents();
	}


	private void addComponents() {
		scoreHBox.getChildren().addAll(scoreText, scoreTextField);
		topClientsVBox.getChildren().addAll(topClientsText, topClientsListView);
		difficultyHBox.getChildren().addAll(easyButton, mediumButton, hardButton);
		sceneVBox.getChildren().addAll(scoreHBox, topClientsVBox, difficultyHBox);
		
		scene = new Scene(sceneVBox, sceneWidth, sceneHeight);
	}


	private void editComponents() {
		scoreTextField.setPrefSize(25, 25);
		scoreTextField.setEditable(false);
		scoreTextField.setText("0");
		
		topClientsListView.setPrefSize(200, 270);
		
		easyButton.setPrefWidth(75);
		mediumButton.setPrefWidth(75);
		hardButton.setPrefWidth(75);
		

		
		scoreHBox.setAlignment(Pos.CENTER);
		scoreHBox.setPadding(new Insets(10));
		scoreHBox.setSpacing(10);
		
		topClientsVBox.setAlignment(Pos.CENTER);
		topClientsVBox.setPadding(new Insets(10));
		topClientsVBox.setSpacing(10);
		
		difficultyHBox.setAlignment(Pos.CENTER);
		difficultyHBox.setPadding(new Insets(10));
		difficultyHBox.setSpacing(20);
		
		sceneVBox.setAlignment(Pos.CENTER);
		sceneVBox.setPadding(new Insets(10));
		
		
	}


	private void createComponents() {
		scoreText = new Text("Your Score:");
		topClientsText = new Text("Top 3 Clients");
		
		scoreTextField = new TextField();
		topClientsListView = new ListView<String>();

		easyButton = new Button("Easy");
		mediumButton = new Button("Medium");
		hardButton = new Button("Hard");
		
		scoreHBox = new HBox();
		topClientsVBox = new VBox();
		difficultyHBox = new HBox();
		sceneVBox = new VBox();
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public Button getEasyButton() {
		return easyButton;
	}
	
	public Button getMediumButton() {
		return mediumButton;
	}
	
	public Button getHardButton() {
		return hardButton;
	}
	
	public ListView<String> getTopClientsListView() {
		return topClientsListView;
	}


	public int incrementPoint() {
		int score = Integer.valueOf(scoreTextField.getText()) + 1;
		Platform.runLater(()->scoreTextField.setText(String.valueOf(score)));
		
		return score;
	}


	public void updateTopClients(ArrayList<String> topThreeClients) {
		Platform.runLater(()->{
			topClientsListView.getItems().clear();
			topClientsListView.getItems().addAll(topThreeClients);
			
		});
	}
}
