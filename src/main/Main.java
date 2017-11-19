package src.main;

import src.engine.Bitmaps;
import src.engine.ChessBoardFactory;
import src.engine.MoveGenerator;

public class Main {

	public static void main(String[] args) {
		ChessBoardFactory.initiateChessBoard();
		String result = MoveGenerator.possibleMovesWhite(ChessBoardFactory.chessBoard);
		System.out.println(result);
	}

}
