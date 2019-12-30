import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ConnectionScene {

	private int sceneWidth;
	private int sceneHeight;
	
	//Components
	private Text ipText;
	private Text portText;
	private TextField ipTextField;
	private TextField portTextField;
	private Button connectButton;
	
	//Layouts
	private HBox ipHBox;
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
		ipHBox.getChildren().addAll(ipText, ipTextField);
		portHBox.getChildren().addAll(portText, portTextField);
		sceneVBox.getChildren().addAll(ipHBox, portHBox, connectButton);
		
		scene = new Scene(sceneVBox, sceneWidth, sceneHeight);
	}

	private void editComponents() {
		ipTextField.setPrefWidth(200);
		portTextField.setPrefWidth(200);

		
		ipHBox.setAlignment(Pos.CENTER);
		ipHBox.setPadding(new Insets(10));
		
		portHBox.setAlignment(Pos.CENTER);
		portHBox.setPadding(new Insets(10));
		
		sceneVBox.setAlignment(Pos.CENTER);
		sceneVBox.setPadding(new Insets(10));
		
	}

	private void createComponents() {
		ipText = new Text("IP:     ");
		portText = new Text("Port: ");
		ipTextField = new TextField();
		portTextField = new TextField();
		connectButton = new Button("Connect");
		
		ipHBox = new HBox();
		portHBox = new HBox();
		sceneVBox = new VBox();
		
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public Button getConnectButton() {
		return connectButton;
	}
	
	public String getIP() {
		return ipTextField.getText();
	}
	
	public String getPort() {
		return portTextField.getText();
	}
}
