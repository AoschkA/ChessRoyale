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
	}
}
