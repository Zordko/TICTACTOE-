

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
// This thread class is used by the server in order to use the MinMax function.
public class FindNextMove extends Thread {
    private String[] board;
    private MinMax min;
    private ArrayList<Node> moves;
    private int highestNum;
    private int random;
    private ArrayList<Node> bestMoves;
    private GameInfo gameInfo;
    private ObjectInputStream in;
    private TicTacToe ticTacToe;
    private int  clientNumber;
    private Server.ClientThread server;
    FindNextMove(String[] updatedBoard, GameInfo gameInfo, ObjectInputStream in, TicTacToe ticTacToe, int clientNumber, Server.ClientThread server){
        this.gameInfo = gameInfo;
        this.board = updatedBoard;
        min = new MinMax(board);
        moves = min.findMoves();
        bestMoves = new ArrayList<>();
        this.in = in;
        this.ticTacToe = ticTacToe;
        this.clientNumber = clientNumber;
        this.server = server;
    }
    /**
    This method will be used by the thread when it is running to generate a best move or just a random move for
    the server to play against the player. First it gets the difficulty of the game to see either should the next move use
    the MinMax function or just use a random selected move.
     */

    public synchronized void algorithm(){
        switch (gameInfo.getDifficulty()) {
            case EASY:
                System.out.println("In easy mode");
                random = (int) Math.round(Math.random() * (moves.size()-1));
                server.moveIndex =  moves.get(random).getMovedTo()-1;

                break;
            case MEDIUM:
                random = (int)Math.round(Math.random()*1);
                System.out.println("In medium mode");
                switch(random){
                    case 0:
                        random = (int) Math.round(Math.random() * (moves.size()-1));
                        server.moveIndex =  moves.get(random).getMovedTo()-1;
                        break;
                    case 1:
                        highestNum = moves.get(0).getMinMax();
                        for(Node e: moves) {
                            if (e.getMinMax() > highestNum){
                                highestNum = e.getMinMax();
                            }
                        }
                        for(Node e: moves){
                            if (e.getMinMax() == highestNum){
                                bestMoves.add(e);
                            }
                        }
                        random = (int) Math.round(Math.random() * (bestMoves.size() - 1));
                        server.moveIndex = bestMoves.get(random).getMovedTo() - 1;
                        break;
                }
                break;
            case HARD:
                highestNum = moves.get(0).getMinMax();
                System.out.println("In hard mode");
                for(Node e: moves) {
                    if (e.getMinMax() > highestNum){
                        highestNum = e.getMinMax();
                    }
                }

                for(Node e: moves){
                    if (e.getMinMax() == highestNum){
                        bestMoves.add(e);
                    }
                }
                random = (int) Math.round(Math.random() * (bestMoves.size() - 1));
                server.moveIndex = bestMoves.get(random).getMovedTo() - 1;
                break;
        }
        // end of switch for difficultly
        /**
         * After the server determines what kind of state the game is in and what it should do next.
         * Such as if the game is over that it should set the game status to GAMEOVER
         */
        if (!server.didPlayerWin() && !server.isGameTied()) {
            // Determine the best move based on the selected difficulty
            // Change gameInfo board
            gameInfo.getBoard()[server.moveIndex] = "X";
            ticTacToe.updateServerBoard(gameInfo.getBoard(), clientNumber);

            //Display the move
            gameInfo.setStatus(GameInfo.DISPLAY_MOVE);
            server.updateClient();

            // If the server has now won
            if (server.didServerWin()) {
                // Set the status to game over
                gameInfo.setStatus(GameInfo.GAME_OVER);
            }

        } else {
            //If the game isn't tied
            if(!server.isGameTied()) {
                //The player won
                gameInfo.setPlayerScore(gameInfo.getPlayerScore() + 1);
                server.calculateTopThreeClients(); // Client won - Check if they're top 3
                gameInfo.setIsPlayerWinner(true);
            }

            gameInfo.setStatus(GameInfo.GAME_OVER);
        }

        // Send updated GameInfo to client
        server.updateClient();

        //If the game is over
        if(gameInfo.getStatus() == GameInfo.GAME_OVER) {
            //Reset the board
            String[] blankBoard = {"b", "b", "b", "b", "b", "b", "b", "b", "b"};
            gameInfo.setBoard(blankBoard);
        }
    }
// The run method here keeps using the algorithm() method for the
    public void run() {
        algorithm();
    }
}
