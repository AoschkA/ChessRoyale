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
            {"r","n","b","q","k","b","n","r"},
            {"p","p","p","p","p","p","p","p"},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {"P","P","P","P","P","P","P","P"},
            {"R","N","B","Q","K","B","N","R"}};
	
	public static void initiateChessBoard() {
		chessBoard = BitBoardCalculations.arrayToChessBoard(initialChessBoard);
	}
}
