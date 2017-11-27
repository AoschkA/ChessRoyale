package src.io;

import src.engine.bitmap.ChessBoardFactory;
import src.engine.move.MoveGenerator;

public class NoneUCICommunication {

    public static void possibleMoves(String input) {
        if (input.contains("white") || input.contains(" w")) possibleMovesWhite();
        else if (input.contains("black") || input.contains(" b")) possibleMovesBlack();
    }

    private static void possibleMovesWhite() {
        String moves = MoveGenerator.possibleMovesWhite(ChessBoardFactory.chessboard);
        System.out.println(moves);
    }

    private static void possibleMovesBlack() {
        String moves = MoveGenerator.possibleMovesBlack(ChessBoardFactory.chessboard);
        System.out.println(moves);
    }
}
