package src.engine;

import src.engine.evaluation.PositionalEvaluations;
import src.entities.ChessBoard;

public class MoveEvaluator {

    public static int evaluateChessboard(ChessBoard chessboard, int player) {
        int counter = 0;
        String chessboardString = BitBoardCalculations.chessBoardToString(chessboard);
        if (player == 1) {
            counter += rateMaterialWhite(chessboardString);
            counter += ratePositionalWhite(chessboardString);
        } else {
            chessboardString = ChessBoardFactory.flipChessboard(chessboardString);
            counter += rateMaterialBlack(chessboardString);
            counter += ratePositionalBlack(chessboardString);
        }
        return counter;
    }

    private static int rateAttack() {
        int counter = 0;
        String chessBoard = BitBoardCalculations.chessBoardToString(ChessBoardFactory.chessBoard);
        for (int i=0; i<64; i++) {
            switch (chessBoard.charAt(i)) {
                case 'P': // check if king would be safe
                    break;
                case 'R': counter += 500;
                    break;
                case 'N': counter += 300;
                    break;
                case 'B': counter += 300; // Consider being dynamic to number of bishops
                    break;
                case 'Q': counter += 900;
                    break;
            }
        }
        return counter;
    }

    private static int rateMaterialWhite(String chessboard) {
        int counter = 0;
        for (int i=0; i<64; i++) {
            switch (chessboard.charAt(i)) {
                case 'P': counter += 100;
                    break;
                case 'R': counter += 500;
                    break;
                case 'N': counter += 300;
                    break;
                case 'B': counter += 300; // Consider being dynamic to number of bishops
                    break;
                case 'Q': counter += 900;
                    break;
            }
        }
        return counter;
    }

    private static int rateMaterialBlack(String chessboard) {
        int counter = 0;
        for (int i=0; i<64; i++) {
            switch (chessboard.charAt(i)) {
                case 'p': counter += 100;
                    break;
                case 'r': counter += 500;
                    break;
                case 'n': counter += 300;
                    break;
                case 'b': counter += 300; // Consider being dynamic to number of bishops
                    break;
                case 'q': counter += 900;
                    break;
            }
        }
        return counter;
    }

    private static int rateMobility(int possibleMoves, int depth, int materialRating) {
        int counter = 0;
        counter += possibleMoves*5; // 5 points for every move
        // Checkmate or stalemate
        if (possibleMoves == 0) {
            // if !kingSafe / checkmate
            // counter +=-200000*depth
            // else / stalemate
            // counter +=-150000*depth
        }
        return 0;
    }

    private static int ratePositionalWhite(String chessboard) {
        int counter = 0;
        for (int i=0; i<64; i++) {
            switch (chessboard.charAt(i)) {
                case 'P': counter += PositionalEvaluations.PAWN[i/8][i%8];
                    break;
                case 'R': counter += PositionalEvaluations.ROOK[i/8][i%8];
                    break;
                case 'N': counter += PositionalEvaluations.KNIGHT[i/8][i%8];
                    break;
                case 'B': counter += PositionalEvaluations.BISHOP[i/8][i%8];
                    break;
                case 'Q': counter += PositionalEvaluations.QUEEN[i/8][i%8];
                    break;
                case 'K': counter += PositionalEvaluations.KING[i/8][i%8];
                    break;
            }
        }
        return counter;
    }

    private static int ratePositionalBlack(String chessboard) {
        int counter = 0;
        for (int i=0; i<64; i++) {
            switch (chessboard.charAt(i)) {
                case 'p': counter += PositionalEvaluations.PAWN[i/8][i%8];
                    break;
                case 'r': counter += PositionalEvaluations.ROOK[i/8][i%8];
                    break;
                case 'n': counter += PositionalEvaluations.KNIGHT[i/8][i%8];
                    break;
                case 'b': counter += PositionalEvaluations.BISHOP[i/8][i%8];
                    break;
                case 'q': counter += PositionalEvaluations.QUEEN[i/8][i%8];
                    break;
                case 'k': counter += PositionalEvaluations.KING[i/8][i%8];
                    break;
            }
        }
        return counter;
    }


}
