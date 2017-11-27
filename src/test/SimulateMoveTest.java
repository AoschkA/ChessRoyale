package src.test;

import src.engine.bitmap.BitBoardCalculations;
import src.engine.bitmap.ChessBoardFactory;
import src.engine.move.MoveGenerator;
import src.entities.Chessboard;

public class SimulateMoveTest {

    public static void main(String[] args) {
        ChessBoardFactory.initiateChessBoard();
        String chessboard = ChessBoardFactory.simulateMove("6050", ChessBoardFactory.getChessBoardString());
        String result = MoveGenerator.possibleMovesWhite(ChessBoardFactory.chessboard);
        System.out.println(result);
        System.out.println(chessboard);

        Chessboard chessboard2 = ChessBoardFactory.generateChessBoardFromString(chessboard);
        String chessboard3 = BitBoardCalculations.chessBoardToString(chessboard2);
        System.out.println(chessboard3);

    }
}
