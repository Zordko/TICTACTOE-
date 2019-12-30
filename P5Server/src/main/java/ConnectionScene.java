import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ConnectionScene {
	
	private int sceneWidth;
	private int sceneHeight;
	
	//Components
	private Text portText;
	private TextField portTextField;
	private Button connectButton;
	
	//Layouts
	private HBox portHBox;
	private VBox sceneVBox;
	private Scene scene;
	
	public ConnectionScene(int sceneWidth, int sceneHeight) {
		this.sceneWidth = sceneWidth;
		this.sceneHeight = sceneHeight;
		
		createComponents();
		editComponents();
		addComponents();
		
	}

	private void addComponents() {
		portHBox.getChildren().addAll(portText, portTextField);
		sceneVBox.getChildren().addAll(portHBox, connectButton);
		
		scene = new Scene(sceneVBox, sceneWidth, sceneHeight);
	}

	private void editComponents() {
		portTextField.setPrefWidth(200);

		
		portHBox.setAlignment(Pos.CENTER);
		portHBox.setPadding(new Insets(10));
		
		sceneVBox.setAlignment(Pos.CENTER);
		sceneVBox.setPadding(new Insets(10));
	}

	private void createComponents() {
		portText = new Text("Port: ");
		portTextField = new TextField();
		connectButton = new Button("Connect");
		
		portHBox = new HBox();
		sceneVBox = new VBox();
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public Button getConnectButton() {
		return connectButton;
	}
	
	public String getPort() {
		return portTextField.getText();
	}
}
