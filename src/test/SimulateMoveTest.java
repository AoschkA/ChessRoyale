package src.test;

import src.engine.BitBoardCalculations;
import src.engine.ChessBoardFactory;
import src.engine.MoveGenerator;
import src.entities.ChessBoard;

public class SimulateMoveTest {

    public static void main(String[] args) {
        ChessBoardFactory.initiateChessBoard();
        String chessboard = ChessBoardFactory.simulateMove("6050", ChessBoardFactory.chessBoardString);
        String result = MoveGenerator.possibleMovesWhite(ChessBoardFactory.chessBoard);
        System.out.println(result);
        System.out.println(chessboard);

        ChessBoard chessboard2 = ChessBoardFactory.generateChessBoardFromString(chessboard);
        String chessboard3 = BitBoardCalculations.chessBoardToString(chessboard2);
        System.out.println(chessboard3);

    }
}
