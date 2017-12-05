package src.engine.move;

import src.engine.bitmap.BitBoardCalculations;
import src.engine.bitmap.ChessBoardFactory;
import src.entities.*;

import java.util.Scanner;
import java.util.concurrent.Callable;

public class MoveIterator implements Callable<String> {
    public static int PLAYER;
    public static int PLAYER_WHITE = 1;
    public static int PLAYER_BLACK = 0;
    public static int VERIFIED_DEPTH = 4;
    public static String result;
    public static int REACHED_DEPTH;

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
        String chessboardString = BitBoardCalculations.chessBoardToString(chessboard);
//        if (TranspositionTable.entryExistsMax(chessboardString)) {
//            TranspositionWrapper w = TranspositionTable.getItemMax(chessboardString);
//            return w.evaluation + "|" + w.bestmove;
//        }

        String desiredMove = "EEEE";
        String movedBoard;

        //Iterative deepening
        if (IterativeDeepeningTable.entryExists(depth)) {
            movedBoard = ChessBoardFactory.simulateMove(IterativeDeepeningTable.getItem(depth), chessboardString);
            if (!movedBoard.equals("BBBB")) {
                String min = alphaBetaMin(depth - 1, alpha, beta, player, ChessBoardFactory.generateChessBoardFromString(movedBoard));
                int score = Integer.parseInt(min.substring(0, min.indexOf("|")));
                if (score >= beta) return beta + "|EEEE";
                if (score > alpha) {
                    alpha = score;
                    desiredMove = "|" + IterativeDeepeningTable.getItem(depth);
                }
            }
        }


        String[] movelist;

        if  (player == 1) movelist = MoveGenerator.possibleMovesWhite(chessboard).split("-");
        else movelist = MoveGenerator.possibleMovesBlack(chessboard).split("-");

        for (String move : movelist) {
            if (move.length() == 4) {
                movedBoard = ChessBoardFactory.simulateMove(move, BitBoardCalculations.chessBoardToString(chessboard));
                if (!movedBoard.equals("BBBB")) {
                    String min = alphaBetaMin(depth - 1, alpha, beta, player, ChessBoardFactory.generateChessBoardFromString(movedBoard));
                    int score = Integer.parseInt(min.substring(0, min.indexOf("|")));
                    if (score >= beta) return beta + "|EEEE";
                    if (score > alpha) {
                        alpha = score;
                        desiredMove = move;
                        IterativeDeepeningMoveSaver.addEntry(depth-1, min.substring(min.indexOf("|")+1));
                    }
                }
            }
        }
        TranspositionTable.addEntryMax(BitBoardCalculations.chessBoardToString(chessboard), new TranspositionWrapper(desiredMove, alpha));
        return alpha + "|" + desiredMove;
    }

    public static String alphaBetaMin(int depth, int alpha, int beta, int player, Chessboard chessboard) {
        if (depth == 0) return -MoveEvaluator.evaluateChessboard(chessboard, player, depth) + "|EEEE";
        String chessboardString = BitBoardCalculations.chessBoardToString(chessboard);
//        if (TranspositionTable.entryExistsMin(chessboardString)) {
//            TranspositionWrapper w = TranspositionTable.getItemMin(chessboardString);
//            return w.evaluation + "|" + w.bestmove;
//        }
        String desiredMove = "EEEE";
        String movedBoard;

        // Iterative deepening
        if (IterativeDeepeningTable.entryExists(depth)) {
            String item = IterativeDeepeningTable.getItem(depth);
            if (item.contains("|")) item = item.substring(1);
            movedBoard = ChessBoardFactory.simulateMove(item, BitBoardCalculations.chessBoardToString(chessboard));
            if (!movedBoard.equals("BBBB")) {
                String max = alphaBetaMax(depth - 1, alpha, beta, player, ChessBoardFactory.generateChessBoardFromString(movedBoard));
                int score = Integer.parseInt(max.substring(0, max.indexOf("|")));
                if (score <= alpha) return alpha + "|EEEE";
                if (score < beta) {
                    beta = score;
                    desiredMove = "|" + IterativeDeepeningTable.getItem(depth);
                }
            }
        }

        String[] movelist;

        player = 1 - player;
        if  (player == 1) movelist = MoveGenerator.possibleMovesWhite(chessboard).split("-");
        else movelist = MoveGenerator.possibleMovesBlack(chessboard).split("-");
        player = 1 - player;

        for (String move : movelist) {
            if (move.length() == 4) {
                movedBoard = ChessBoardFactory.simulateMove(move, BitBoardCalculations.chessBoardToString(chessboard));
                if (!movedBoard.equals("BBBB")) {
                    String max = alphaBetaMax(depth - 1, alpha, beta, player, ChessBoardFactory.generateChessBoardFromString(movedBoard));
                    int score = Integer.parseInt(max.substring(0, max.indexOf("|")));
                    if (score <= alpha) return alpha + "|EEEE";
                    if (score < beta) {
                        beta = score;
                        desiredMove = move;
                        IterativeDeepeningMoveSaver.addEntry(depth-1, max.substring(max.indexOf("|")+1));
                    }
                }
            }
        }
        TranspositionTable.addEntryMin(chessboardString, new TranspositionWrapper(desiredMove, beta));
        return beta + "|" +  desiredMove;
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

    public static String iterativeDeepening() {
        IterativeDeepeningMoveSaver.reset();
        IterativeDeepeningTable.reset();
        String result_depth1 = alphaBetaMax(1, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, ChessBoardFactory.chessboard);
        result = result_depth1.substring(result_depth1.length()-4, result_depth1.length());
        REACHED_DEPTH = 1;
        IterativeDeepeningTable.reset();
        IterativeDeepeningTable.addEntry(2, result);
        IterativeDeepeningMoveSaver.reset();
        String result_depth2 = alphaBetaMax(2, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, ChessBoardFactory.chessboard);
        result = result_depth2.substring(result_depth2.length()-4, result_depth2.length());
        REACHED_DEPTH = 2;
        IterativeDeepeningMoveSaver.print();
        IterativeDeepeningTable.reset();
        IterativeDeepeningTable.addEntry(3, result);
        if (IterativeDeepeningMoveSaver.entryExists(1)) IterativeDeepeningTable.addEntry(2, IterativeDeepeningMoveSaver.getItem(1));
        IterativeDeepeningMoveSaver.reset();
        String result_depth3 = alphaBetaMax(3, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, ChessBoardFactory.chessboard);
        result = result_depth3.substring(result_depth3.length()-4, result_depth3.length());
        REACHED_DEPTH = 3;
        IterativeDeepeningTable.reset();
        IterativeDeepeningTable.addEntry(4, result);
        if (IterativeDeepeningMoveSaver.entryExists(2)) IterativeDeepeningTable.addEntry(3, IterativeDeepeningMoveSaver.getItem(2));
        if (IterativeDeepeningMoveSaver.entryExists(1)) IterativeDeepeningTable.addEntry(2, IterativeDeepeningMoveSaver.getItem(1));
        IterativeDeepeningMoveSaver.reset();
        String result_depth4 = alphaBetaMax(4, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, ChessBoardFactory.chessboard);
        result = result_depth4.substring(result_depth4.length()-4, result_depth4.length());
        REACHED_DEPTH = 4;
        return result;
    }

    @Override
    public String call() throws Exception {
        return iterativeDeepening();
    }
}
