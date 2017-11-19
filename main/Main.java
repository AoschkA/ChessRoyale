package main;

import engine.BitmapFactory;
import engine.ChessBoardFactory;
import engine.MoveGenerator;

public class Main {

	public static void main(String[] args) {
		ChessBoardFactory.initiateChessBoard();
		BitmapFactory.initiateStaticBitboards();
		String result = MoveGenerator.possibleMovesWhite(ChessBoardFactory.chessBoard);
		System.out.println(result);
	}

}
