package src.main;

import src.engine.*;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		ChessBoardFactory.initiateChessBoard();
		String result = MoveGenerator.possibleMovesWhite(ChessBoardFactory.chessBoard);
		ChessBoardFactory.updateChessBoardString();
		System.out.println(result);
        System.out.println("------");
        String moveToMake = MoveIterator.alphaBetaMax3(MoveIterator.VERIFIED_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, ChessBoardFactory.chessBoard, 1, "BBBB");
        System.out.println("alphaBetaResult: "+moveToMake);
		System.out.println(Arrays.toString(MoveIterator.moveStack));
	}

}
