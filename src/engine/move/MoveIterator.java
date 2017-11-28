package src.engine.move;

import src.engine.bitmap.BitBoardCalculations;
import src.engine.bitmap.ChessBoardFactory;
import src.entities.Chessboard;

import java.util.Scanner;

public class MoveIterator {
    public static int PLAYER;
    public static int PLAYER_WHITE = 1;
    public static int PLAYER_BLACK = 0;
    public static int VERIFIED_DEPTH = 2;

    /*
            Player parameter represents 1 or 0.
                        1. White
                        0. Black

            Alpha Beta pruning source: https://chessprogramming.wikispaces.com/Alpha-Beta
                alpha: -infinite
                beta: infinite
     */

    public static String alphaBetaMax(int depth, int alpha, int beta, int player, Chessboard chessboard) {
        if (depth == 0) return MoveEvaluator.evaluateChessboard(chessboard, player, depth) + "|EEEE";
        String desiredMove = "|EEEE";
        String[] movelist;

        if  (player == 1) movelist = MoveGenerator.possibleMovesWhite(chessboard).split("-");
        else movelist = MoveGenerator.possibleMovesBlack(chessboard).split("-");

        if (movelist.length == 0) return -200000 + "|EEEE";

        for (String move : movelist) {
            if (move.length() == 4) {
                String movedBoard = ChessBoardFactory.simulateMove(move, BitBoardCalculations.chessBoardToString(chessboard));
                if (!movedBoard.equals("BBBB")) {
                    String min = alphaBetaMin(depth - 1, alpha, beta, player, ChessBoardFactory.generateChessBoardFromString(movedBoard));
                    int score = Integer.parseInt(min.substring(0, min.indexOf("|")));
                    if (score >= beta) return beta + "|EEEE";
                    if (score > alpha) {
                        alpha = score;
                        desiredMove = "|" + move;
                    }
                }
            }
        }
        return alpha + desiredMove;
    }

    public static String alphaBetaMin(int depth, int alpha, int beta, int player, Chessboard chessboard) {
        if (depth == 0) return -MoveEvaluator.evaluateChessboard(chessboard, player, depth) + "|EEEE";
        String desiredMove = "|EEEE";
        String[] movelist;

        player = 1 - player;
        if  (player == 1) movelist = MoveGenerator.possibleMovesWhite(chessboard).split("-");
        else movelist = MoveGenerator.possibleMovesBlack(chessboard).split("-");
        player = 1 - player;

        for (String move : movelist) {
            if (move.length() == 4) {
                String movedBoard = ChessBoardFactory.simulateMove(move, BitBoardCalculations.chessBoardToString(chessboard));
                if (!movedBoard.equals("BBBB")) {
                    String max = alphaBetaMax(depth - 1, alpha, beta, player, ChessBoardFactory.generateChessBoardFromString(movedBoard));
                    int score = Integer.parseInt(max.substring(0, max.indexOf("|")));
                    if (score <= alpha) return alpha + "|EEEE";
                    if (score < beta) {
                        beta = score;
                        desiredMove = "|" + move;
                    }
                }
            }
        }
        return beta + desiredMove;
    }

    public static int alphaBetaMaxForTest(int depth, int alpha, int beta, Scanner scanner) {
        System.out.println(depth+" max: alpha: "+alpha+" beta: "+beta);
        if (depth == 0) return Integer.parseInt(scanner.nextLine());

        for (int i=0; i < 3; i++) {
            int score = alphaBetaMinForTest(depth-1, alpha, beta, scanner);
            System.out.println(depth+" max: alpha: "+alpha+" beta: "+beta);
            if( score >= beta ) return beta;
            if (score > alpha) alpha = score;
        }
        return alpha;
    }

    public static int alphaBetaMinForTest(int depth, int alpha, int beta, Scanner scanner) {
        System.out.println(depth+" min: alpha: "+alpha+" beta: "+beta);
        if (depth == 0) return Integer.parseInt(scanner.nextLine());

        for (int i=0; i < 3; i++) {
            int score = alphaBetaMaxForTest(depth-1, alpha, beta, scanner);
            System.out.println(depth+" min: alpha: "+alpha+" beta: "+beta);
            if( score <= alpha ) return alpha;
            if (score < beta) beta = score;
        }
        return beta;
    }

}
