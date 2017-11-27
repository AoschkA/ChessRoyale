package src.engine.move;

import src.engine.bitmap.BitBoardCalculations;
import src.engine.bitmap.ChessBoardFactory;
import src.engine.move.evaluation.PositionalEvaluations;
import src.entities.Chessboard;

public class MoveEvaluator {

    public static int evaluateChessboard(Chessboard chessboard, int player, int depth) {
        int counter = 0;
        String chessboardString = BitBoardCalculations.chessBoardToString(chessboard);
        if (player == 1) {
            counter += rateMaterialWhite(chessboardString);
            counter -= rateMaterialBlack(chessboardString);
            counter += rateAttackWhite(chessboardString);
            counter -= rateAttackBlack(chessboardString);
            counter += ratePositionalWhite(chessboardString);
            counter += rateMobilityWhite(chessboardString, depth);
            counter -= rateMobilityBlack(chessboardString, depth);
            chessboardString = ChessBoardFactory.flipChessboard(chessboardString); // Flip board for positional evaluation
            counter -= ratePositionalBlack(chessboardString);
        } else {
            counter += rateMaterialBlack(chessboardString);
            counter -= rateMaterialWhite(chessboardString);
            counter += rateAttackBlack(chessboardString);
            counter -= rateAttackWhite(chessboardString);
            counter -= ratePositionalWhite(chessboardString);
            counter += rateMobilityBlack(chessboardString, depth);
            counter -= rateMobilityWhite(chessboardString, depth);
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
                    switch (occupiedBy(chessboard, move)) {
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
                    switch (occupiedBy(chessboard, move)) {
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

    private static int rateMobilityWhite(String chessboard, int depth) {
        int counter = 0;
        String[] whiteMoves = MoveGenerator.possibleMovesWhite(ChessBoardFactory.generateChessBoardFromString(chessboard)).split("-");
        // 5 points for every move
        for (String move : whiteMoves) {
            if (move.length()==4) counter += 5;
        }
        if (counter==0) counter -= 200000; // check mate
        return counter;
    }

    private static int rateMobilityBlack(String chessboard, int depth) {
        int counter = 0;
        String[] blackMoves = MoveGenerator.possibleMovesBlack(ChessBoardFactory.generateChessBoardFromString(chessboard)).split("-");
        // 5 points for every move
        for (String move : blackMoves) {
            if (move.length()==4) counter += 5;
        }
        if (counter==0) counter -= 200000; // check mate
        return counter;
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

    private static char occupiedBy(String chessboard, String move) {
        chessboard = ChessBoardFactory.flipChessboard(chessboard);
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
