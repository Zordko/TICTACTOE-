import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

public class Server implements Subject {

	// List of clients and the client numbers
	private volatile ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	private ArrayList<Integer> occupiedClientNumbers = new ArrayList<Integer>();
	private volatile ArrayList<String> topThreeClients = new ArrayList<String>();

	private TheServer server;
	private Consumer<Serializable> activityCallback;
	private String port;
	private TicTacToe ticTacToeStage;
	private FindNextMove findNextMove;

	Server(Consumer<Serializable> activityCallback, String port, TicTacToe ticTacToeStage) {
		this.port = port;
		this.activityCallback = activityCallback;
		this.ticTacToeStage = ticTacToeStage;
		server = new TheServer();
		server.start();
	}

	// Responsible for accepting new clients
	public class TheServer extends Thread {

		private ServerSocket mysocket;


		// Closes all the ClientThread Sockets
		public void shutdown() {
			try {
				// For each client
				for (ClientThread c : clients) {
					// Close their connections
					c.closeConnections();
				}

				// Close the server socket
				mysocket.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}

		public void run() {

			try {
				mysocket = new ServerSocket(Integer.valueOf(port));

				while (true) {
					// Wait for a new client to connect to the socket
					Socket acceptClientSocket = mysocket.accept();

					// If the server was shutdown for some reason
					if (mysocket.isClosed()) {
						// Break out of the loop completely, ending the server thread
						break;
					} else {
						// Otherwise, add the player to the game
						ClientThread c = new ClientThread(acceptClientSocket, generateClientNumber());

						// Update server activity ListView
						activityCallback.accept("Client " + c.clientNumber + " has connected to server.");

						// Add Client
						clients.add(c);
						ticTacToeStage.updateClientList(getListOfClients());

						c.start(); // Start the client thread
					}
				}
			} // end of try
			catch (Exception e) {
				try {
					mysocket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
				}
				activityCallback.accept("Server socket did not launch");
			}
		}// end of while

		// Returns the first unused client number
		private int generateClientNumber() {
			int clientNumber = 1;
			// While the current client number is occupied
			while (occupiedClientNumbers.contains(clientNumber)) {
				// Increment Client Number
				clientNumber += 1;
			}
			// Add the client number to occupied client number list
			occupiedClientNumbers.add(clientNumber);
			return clientNumber;
		}
	}

	// Reads objects in and updates client ListViews
	class ClientThread extends Thread implements Observer {

		Socket connection;
		ObjectInputStream in;
		ObjectOutputStream out;

		private GameInfo gameInfo;
		int clientNumber;
		int moveIndex;

		ClientThread(Socket s, int clientNumber) {
			this.connection = s;
			this.clientNumber = clientNumber;
			gameInfo = new GameInfo();
		}

		private void closeConnections() {
			try {
				activityCallback.accept("Client " + clientNumber + " has disconnected!...");
				if (in != null) {
					in.close();
				}

				if (out != null) {
					out.close();
				}

				if (connection != null) {
					connection.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}

		// Sends a message to all the clients
		public synchronized void updateClient() {
			try {
				// Send GameInfo to Client
				out.writeObject(gameInfo);
				out.flush();
				out.reset();

			} catch (Exception e) {
			}

		}

		public void run() {
			try {
				in = new ObjectInputStream(connection.getInputStream());
				out = new ObjectOutputStream(connection.getOutputStream());
				connection.setTcpNoDelay(true);

				calculateTopThreeClients(); // Client joined - calculate top 3

			} catch (Exception e) {
				closeConnections();
			}

			while (true) {
				try {

					// Read in GameInfo sent by Client GUI
					gameInfo = (GameInfo) in.readObject();
					if (!isGameTied()) {
						// Store the new board
						String[] updatedBoard = gameInfo.getBoard();
						ticTacToeStage.updateServerBoard(updatedBoard, clientNumber);
						findNextMove = new FindNextMove(updatedBoard, gameInfo, in, ticTacToeStage, clientNumber, this);
						findNextMove.start();
					}
					else{
						gameInfo.setStatus(GameInfo.GAME_OVER);
						updateClient();
					}

				} catch (Exception e) {
					e.printStackTrace();
					clientQuitActions();
					break;
				}
			}
		}// end of run

		public boolean isGameTied() {
			for(String value : gameInfo.getBoard()) {
				//If there is still a blank square
				if(value.equals("b")) {
					//The game hasn't tied
					return false;
				}
			}
				
			return true;
		}

		public boolean didServerWin() {
			String move = "X";
			return isHorizontalWin(move) || isVerticalWin(move) || isDiagonalWin(move);
		}

		public boolean didPlayerWin() {
			String move = "O";
			return isHorizontalWin(move) || isVerticalWin(move) || isDiagonalWin(move);
		}

		public boolean isDiagonalWin(String move) {
			String[] board = gameInfo.getBoard();
			return (board[0].equals(move) && board[0].equals(board[4]) && board[4].equals(board[8]))
					|| (board[2].equals(move) && board[2].equals(board[4]) && board[4].equals(board[6]));
					
		}

		public boolean isVerticalWin(String move) {
			String[] board = gameInfo.getBoard();
			return (board[0].equals(move) && board[0].equals(board[3]) && board[3].equals(board[6]))
					|| (board[1].equals(move) && board[1].equals(board[4]) && board[4].equals(board[7]))
					|| (board[2].equals(move) && board[2].equals(board[5]) && board[5].equals(board[8]));
		}

		public boolean isHorizontalWin(String move) {
			String[] board = gameInfo.getBoard();
			return (board[0].equals(move) && board[0].equals(board[1]) && board[1].equals(board[2]))
					|| (board[3].equals(move) && board[3].equals(board[4]) && board[4].equals(board[5]))
					|| (board[6].equals(move) && board[6].equals(board[7]) && board[7].equals(board[8]));
		}




		// Actions to perform when a client quits
		private void clientQuitActions() {
			removeClient(this);
			clients.remove(this);
			calculateTopThreeClients(); // Client quit - calculate top three clients
			closeConnections();
			occupiedClientNumbers.remove((Object) clientNumber);
			ticTacToeStage.updateClientList(getListOfClients());

		}

		@Override
		public void updateList() {
			gameInfo.setStatus(GameInfo.UPDATE_TOP_CLIENTS);
			gameInfo.setTopThreeClients(topThreeClients);
			updateClient();

		}

		/*
		 * This function will only run when: 1) A client connects to the server 2) A
		 * client wins a game 3) A client disconnects from the server
		 */
		public void calculateTopThreeClients() {
			// Clear the top three clients
			topThreeClients.clear();

			// Store the client scores and the respective client index
			int[] unsortedScores = new int[clients.size()];
			int[] unsortedClientIndecies = new int[clients.size()];
			for (int i = 0; i < clients.size(); i++) {
				unsortedScores[i] = clients.get(i).gameInfo.getPlayerScore();
				unsortedClientIndecies[i] = i;
			}

			// Sort the clients by their score. Ordered by index
			int[] sortedClientIndeciesByScore = sortClients(unsortedScores, unsortedClientIndecies, clients.size());

			for (int i = 0; i < 3; i++) {
				if (sortedClientIndeciesByScore.length == i) {
					break;
				}

				addClient(clients.get(sortedClientIndeciesByScore[i]));
			}

			notifyClients();

		}

		/*
		 * Reference: https://www.geeksforgeeks.org/insertion-sort/ Insertion sort
		 * function used is from geeksforgeeks. Professor said we're allowed to use a
		 * sorting function found on the internet:
		 * https://piazza.com/class/jzsw60xpy722ja?cid=718
		 */
		public int[] sortClients(int[] scores, int[] clientIndecies, int size) {
			int i, key, keyIndex, j;
			for (i = 1; i < size; i++) {
				key = scores[i];
				keyIndex = clientIndecies[i];
				j = i - 1;

				/*
				 * Move elements of arr[0..i-1], that are greater than key, to one position
				 * ahead of their current position
				 */
				while (j >= 0 && scores[j] < key) {
					scores[j + 1] = scores[j];
					clientIndecies[j + 1] = clientIndecies[j];
					j = j - 1;
				}
				scores[j + 1] = key;
				clientIndecies[j + 1] = keyIndex;
			}
			return clientIndecies;
		}
	}

	// Returns a list of clients as a string (Format: "Client 3")
	public ArrayList<String> getListOfClients() {
		ArrayList<String> listOfClients = new ArrayList<String>();

		for (ClientThread c : clients) {
			listOfClients.add("Client " + c.clientNumber);
		}

		return listOfClients;
	}

	public void shutdown() {
		server.shutdown();
	}

	@Override
	public void addClient(Observer o) {
		ClientThread client = (ClientThread) o;
		topThreeClients.add("Client " + client.clientNumber + " - " + client.gameInfo.getPlayerScore() + " points");

	}

	@Override
	public void removeClient(Observer o) {
		ClientThread client = (ClientThread) o;
		topThreeClients.remove((Object) client.clientNumber);

	}

	@Override
	public void notifyClients() {
		for (ClientThread client : clients) {
			client.updateList();
		}

	}

	// Returns the board for a specific client number
	public String[] getBoard(int clientNumber) {
		for (ClientThread client : clients) {
			if (client.clientNumber == clientNumber) {
				return client.gameInfo.getBoard();
			}
		}
		String[] blankBoard = { "b", "b", "b", "b", "b", "b", "b", "b", "b" };
		return blankBoard;
	}
}