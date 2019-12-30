
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicTacToeTest {

	//String[] moves = {"b","b", "b", "b","b","b","b","b","b"};
	//MinMax board;

	// @BeforeEach
	//void init() {
	//	board = new MinMax(moves);
	//}

	@Test
	void test() {
		String[] moves = {"b","b", "b", "b","b","b","b","b","b"};
		MinMax board = new MinMax(moves);
		assertEquals("class MinMax",board.getClass().toString());
	}

	@Test
	void EmptyboardFindMovesSizeTest1() {
		String[] moves = {"b","b", "b", "b","b","b","b","b","b"};
		MinMax board = new MinMax(moves);

		ArrayList<Node> temp = board.findMoves();

		assertEquals(9, temp.size());
	}

	@Test
	void EmptyboardFindMovesTest1() {
		String[] moves = {"b","b", "b", "b","b","b","b","b","b"};
		MinMax board = new MinMax(moves);

		ArrayList<Node> temp = board.findMoves();


		assertEquals(0, temp.get(0).getMinMax());
		assertEquals(0, temp.get(1).getMinMax());
		assertEquals(0, temp.get(2).getMinMax());
	}

	@Test
	void SinglePlayFindMovesTest() {
		String[] moves = {"O","b", "b", "b","b","b","b","b","b"};
		MinMax board = new MinMax(moves);

		ArrayList<Node> temp = board.findMoves();

		assertEquals(-10, temp.get(0).getMinMax());
	}

	@Test
	void SinglePlayFindMovesTest2() {
		String[] moves = {"O","b", "b", "b","b","b","b","b","b"};
		MinMax board = new MinMax(moves);

		ArrayList<Node> temp = board.findMoves();


		assertEquals(0, temp.get(3).getMinMax());
	}

	@Test
	void twoPlaysFindMovesTest2() {
		String[] moves = {"O","b", "X", "b","b","b","b","b","b"};
		MinMax board = new MinMax(moves);

		ArrayList<Node> temp = board.findMoves();


		assertEquals(-10, temp.get(0).getMinMax());

	}

	@Test
	void WinningFindMovesTest() {
		String[] moves = {"X","X", "b", "b","b","b","b","b","b"};
		MinMax board = new MinMax(moves);

		ArrayList<Node> temp = board.findMoves();


		assertEquals(10, temp.get(0).getMinMax());

	}
	@Test
	void WinningFindMovesTest2() {
		String[] moves = {"X","O", "O", "O","X","O","O","O","b"};
		MinMax board = new MinMax(moves);

		ArrayList<Node> temp = board.findMoves();


		assertEquals(10, temp.get(0).getMinMax());

	}

	@Test
	void WinningFindMovesTest3() {
		String[] moves = {"X","O", "O", "O","X","O","O","b","b"};
		MinMax board = new MinMax(moves);

		ArrayList<Node> temp = board.findMoves();

		assertEquals(10, temp.get(1).getMinMax());

	}

	@Test
	void WinningFindMovesTest4() {
		String[] moves = {"O","b", "b", "O","X","X","X","O","X"};
		MinMax board = new MinMax(moves);

		ArrayList<Node> temp = board.findMoves();

		assertEquals(10, temp.get(1).getMinMax());


	}

	@Test
	void TieFindMovesTest() {
		String[] moves = {"X","O", "O", "O","X","O","O","b","O"};
		MinMax board = new MinMax(moves);

		ArrayList<Node> temp = board.findMoves();


		assertEquals(0, temp.get(0).getMinMax());

	}

	@Test
	void TieFindMovesTest2() {
		String[] moves = {"O","X", "X", "X","O","O","O","X","b"};
		MinMax board = new MinMax(moves);

		ArrayList<Node> temp = board.findMoves();

		assertEquals(0, temp.get(0).getMinMax());

	}

	@Test
	void LosingFindMovesTest() {
		String[] moves = {"X","O", "O", "O","X","O","O","b","b"};
		MinMax board = new MinMax(moves);

		ArrayList<Node> temp = board.findMoves();

		assertEquals(-10, temp.get(0).getMinMax());


	}

	@Test
	void LosingFindMovesTest2() {
		String[] moves = {"O","O", "b", "O","X","X","O","X","X"};
		MinMax board = new MinMax(moves);

		ArrayList<Node> temp = board.findMoves();

		assertEquals(10, temp.get(0).getMinMax());


	}

	@Test
	void EmptyboardMaxTest1() {
		String[] moves = {"b","b", "b", "b","b","b","b","b","b"};
		MinMax board = new MinMax(moves);
		//ArrayList<Node> temp = board.findMoves();

		Node temp = new Node(moves, 0);
		//Node temp;

		assertEquals(0, board.Max(temp));
	}


	@Test
	void MaxTest() {
		String[] moves = {"X","O", "O", "O","X","O","O","b","O"};

		MinMax board = new MinMax(moves);
		//ArrayList<Node> temp = board.findMoves();

		Node temp = new Node(moves, 1);
		//Node temp;

		assertEquals(0, board.Max(temp));
	}

	@Test
	void MaxTest2() {
		String[] moves = {"O","b", "b", "O","X","X","X","O","X"};

		MinMax board = new MinMax(moves);
		//ArrayList<Node> temp = board.findMoves();

		Node temp = new Node(moves, 1);
		//Node temp;

		assertEquals(10, board.Max(temp));
	}

	@Test
	void EmptyboardMinTest1() {
		String[] moves = {"b","b", "b", "b","b","b","b","b","b"};
		MinMax board = new MinMax(moves);
		//ArrayList<Node> temp = board.findMoves();

		Node temp = new Node(moves, 0);
		//Node temp;

		assertEquals(0, board.Min(temp));
	}


	@Test
	void MinTest() {
		String[] moves = {"X","O", "O", "O","X","O","O","b","O"};

		MinMax board = new MinMax(moves);
		//ArrayList<Node> temp = board.findMoves();

		Node temp = new Node(moves, 0);
		//Node temp;

		assertEquals(-10, board.Min(temp));
	}

	@Test
	void MinTest2() {
		String[] moves = {"O","b", "b", "O","X","X","X","O","X"};

		MinMax board = new MinMax(moves);
		//ArrayList<Node> temp = board.findMoves();

		Node temp = new Node(moves, 0);
		//Node temp;

		assertEquals(0, board.Min(temp));
	}


}

