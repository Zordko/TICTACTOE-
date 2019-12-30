import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ServerScene {

	private int sceneWidth;
	private int sceneHeight;
	
	//Components
	private Text clientText;
	private Text activityText;
	private ListView<String> clientListView;
	private ListView<String> activityListView;
	
	//Layouts
	private VBox clientVBox;
	private VBox activityVBox;
	private HBox sceneHBox;
	private Scene scene;
	
	public ServerScene(int sceneWidth, int sceneHeight) {
		this.sceneWidth = sceneWidth;
		this.sceneHeight = sceneHeight;
		
		createComponents();
		editComponents();
		addComponents();
	}

	private void addComponents() {
		clientVBox.getChildren().addAll(clientText, clientListView);
		activityVBox.getChildren().addAll(activityText, activityListView);
		sceneHBox.getChildren().addAll(clientVBox, activityVBox);
		
		scene = new Scene(sceneHBox, sceneWidth, sceneHeight);
		
	}

	private void editComponents() {
		clientListView.setPrefSize(200, 270);
		activityListView.setPrefSize(200, 270);

		clientVBox.setAlignment(Pos.CENTER);
		clientVBox.setPadding(new Insets(10));
		
		activityVBox.setAlignment(Pos.CENTER);
		activityVBox.setPadding(new Insets(10));
	}

	private void createComponents() {
		clientText = new Text("Clients");
		activityText = new Text("Activity");
		
		clientListView = new ListView<String>();
		activityListView = new ListView<String>();
		
		clientVBox = new VBox();
		activityVBox = new VBox();
		sceneHBox = new HBox();
		
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public ListView<String> getClientListView() {
		return clientListView;
	}
	
	public ListView<String> getActivityListView() {
		return activityListView;
	}
}
