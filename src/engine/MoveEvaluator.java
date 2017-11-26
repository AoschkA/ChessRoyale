package src.engine;

import src.engine.evaluation.PositionalEvaluations;
import src.entities.ChessBoard;

public class MoveEvaluator {

    public static int evaluateChessboard(ChessBoard chessboard, int player) {
        int counter = 0;
        String chessboardString = BitBoardCalculations.chessBoardToString(chessboard);
        if (player == 1) {
            counter += rateMaterialWhite(chessboardString);
            counter -= rateMaterialBlack(chessboardString);
            counter += rateAttackWhite(chessboardString);
            counter -= rateAttackBlack(chessboardString);
            counter += ratePositionalWhite(chessboardString);
            chessboardString = ChessBoardFactory.flipChessboard(chessboardString); // Flip board for positional evaluation
            counter -= ratePositionalBlack(chessboardString);
        } else {
            counter += rateMaterialBlack(chessboardString);
            counter -= rateMaterialWhite(chessboardString);
            counter += rateAttackBlack(chessboardString);
            counter -= rateAttackWhite(chessboardString);
            counter -= ratePositionalWhite(chessboardString);
            chessboardString = ChessBoardFactory.flipChessboard(chessboardString); // Flip board for positional evaluation
            counter += ratePositionalBlack(chessboardString);
        }
        return counter;
    }

    private static int rateAttackWhite(String chessboard) {
        int counter = 0;
        String[] moves = MoveGenerator.possibleMovesBlack(ChessBoardFactory.generateChessBoardFromString(chessboard)).split("-");
        if (moves.length != 0) {
            for (String move : moves) {
                if (move.length() == 4) {
                    switch (occopiedBy(chessboard, move)) {
                        case 'P':
                            counter -= 64;
                            break;
                        case 'R':
                            counter -= 500;
                            break;
                        case 'N':
                            counter -= 300;
                            break;
                        case 'B':
                            counter -= 300;
                            break;
                        case 'Q':
                            counter -= 900;
                            break;
                        case 'K':
                            counter -= 1500;
                            break;
                    }
                }
            }
        }
        return counter;
    }

    private static int rateAttackBlack(String chessboard) {
        int counter = 0;
        String[] moves = MoveGenerator.possibleMovesWhite(ChessBoardFactory.generateChessBoardFromString(chessboard)).split("-");
        if (moves.length != 0) {
            for (String move : moves) {
                if (move.length() == 4) {
                    switch (occopiedBy(chessboard, move)) {
                        case 'p':
                            counter -= 64;
                            break;
                        case 'r':
                            counter -= 500;
                            break;
                        case 'n':
                            counter -= 300;
                            break;
                        case 'b':
                            counter -= 300;
                            break;
                        case 'q':
                            counter -= 900;
                            break;
                        case 'k':
                            counter -= 1500;
                            break;
                    }
                }
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

    private static char occopiedBy(String chessboard, String move) {
        int row_counter = 0;
        int column_counter = 0;
        for (int i = 0; i < Integer.parseInt(move.charAt(2)+""); i++) {
            row_counter+=8;
        }
        for (int j = 0; j < Integer.parseInt(move.charAt(3)+""); j++) {
            column_counter++;
        }
        int counter = row_counter + column_counter;
        return chessboard.charAt(counter);
    }
}
