package src.main;

import src.engine.BitmapFactory;
import src.engine.ChessBoardFactory;
import src.engine.MoveGenerator;

public class Main {

	public static void main(String[] args) {
		ChessBoardFactory.initiateChessBoard();
		BitmapFactory.initiateStaticBitboards();
		String result = MoveGenerator.possibleMovesWhite(ChessBoardFactory.chessBoard);
		System.out.println(result);
	}

}
