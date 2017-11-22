package src.engine;

import src.entities.ChessBoard;

/*
 * PIECE=WHITE/black
 * pawn = P/p
 * knight (horse) = N/n
 * bishop = B/b
 * rook (castle) = R/r
 * Queen = Q/q
 * King = K/k
 * 
 */

public class ChessBoardFactory {
	public static ChessBoard chessBoard;
	public static String chessBoardString;
	private static String[][] initialChessBoard = {

			//0	  1   2   3   4   5   6   7
            {"r","n","b","q","k","b","n","r"}, // 0
            {"p","p","p","p","p","p","p","p"}, // 1
            {" "," "," "," "," "," "," "," "}, // 2
            {" "," "," "," "," "," "," "," "}, // 3
            {" "," "," "," "," "," "," "," "}, // 4
            {" "," "," "," "," "," "," "," "}, // 5
            {"P","P","P","P","P","P","P","P"}, // 6
            {"R","N","B","Q","K","B","N","R"}};// 7
	
	public static void initiateChessBoard() {
		chessBoard = BitBoardCalculations.arrayToChessBoard(initialChessBoard);
		updateChessBoardString();
	}

	public static void updateChessBoardString() {
		chessBoardString = BitBoardCalculations.chessBoardToString(chessBoard);
	}

	public static String simulateMove(String move, String chessBoardString) {
		String board = flipChessboard(chessBoardString);
		int moveFrom1 = Integer.parseInt(move.charAt(0)+"");
		int moveFrom2 = Integer.parseInt(move.charAt(1)+"");
		int moveTo1 = Integer.parseInt(move.charAt(2)+"");
		int moveTo2 = Integer.parseInt(move.charAt(3)+"");
        char moveChar;

		try { moveChar = board.charAt(moveFrom1*8+moveFrom2); } catch (StringIndexOutOfBoundsException e) {
		    return "BBBB";
        }
		String newChessboard = "";
		for (int i = 0; i < board.length(); i++) {
			if (i == moveFrom1*8+moveFrom2) {
				newChessboard += "0";
			} else if (i == moveTo1*8+moveTo2) {
				newChessboard += moveChar;
			} else  {
				newChessboard += board.charAt(i);
			}
		}
		newChessboard =  flipChessboard(newChessboard);
		return newChessboard;
	}

	public static String flipChessboard(String chessBoardString) {
		String board = new StringBuilder(chessBoardString).reverse().toString();
		return board;
	}

	public static ChessBoard generateChessBoardFromString(String chessBoardString) {
        chessBoardString = flipChessboard(chessBoardString);
		ChessBoard result = BitBoardCalculations.stringToChessBoard(chessBoardString);
		return result;
	}
}
