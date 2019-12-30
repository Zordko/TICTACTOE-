import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Thread {

	private Socket socketClient;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	private String ip;
	private String port;

	private GameInfo gameInfo = new GameInfo();
	private TicTacToe gameBoard;
	
	public Client(String ip, String port, TicTacToe gameBoard) {
		this.ip = ip;
		this.port = port;
		this.gameBoard = gameBoard;
	}

	private void closeConnections() {
		try {
			if (in != null) {
				in.close();
			}

			if (out != null) {
				out.close();
			}

			if (socketClient != null) {
				socketClient.close();
			}
			

		} catch (IOException e) {
			// TODO Auto-generated catch block

		}
	}

	public void run() {

		try {
			socketClient = new Socket(ip, Integer.valueOf(port));
			out = new ObjectOutputStream(socketClient.getOutputStream());
			in = new ObjectInputStream(socketClient.getInputStream());
			socketClient.setTcpNoDelay(true);
		} catch (Exception e) {
			closeConnections();
			return;
		}

		// Read in objects
		while (true) {

			try {
				gameInfo = (GameInfo) in.readObject();
				
				switch(gameInfo.getStatus()) {
					case GameInfo.DISPLAY_MOVE:
						gameBoard.updateBoard(gameInfo);
						break;
						
					case GameInfo.GAME_OVER:
						gameBoard.gameIsOver(gameInfo.isPlayerWinner());
						break;
					
					case GameInfo.UPDATE_TOP_CLIENTS:
						gameBoard.updateTopClients(gameInfo.getTopThreeClients());
				}
				
				
				
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
				closeConnections();
				break;
			}
		}
	}

	//Send a GameInfo to the server when the client clicks on a button
	public void send(GameInfo gameInfo) {
		try {
			out.writeObject(gameInfo);
			out.flush();
			out.reset();
		} catch (IOException e) {
			closeConnections();
		}
	}

	public void shutdown() {
		closeConnections();

	}

}
