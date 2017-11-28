package src.engine.move;

import src.engine.bitmap.BitBoardCalculations;
import src.engine.bitmap.ChessBoardFactory;
import src.entities.Chessboard;

import java.util.Arrays;
import java.util.Scanner;

public class MoveIterator {
    public static int PLAYER;
    public static int PLAYER_WHITE = 1;
    public static int PLAYER_BLACK = 0;
    public static int VERIFIED_DEPTH = 2;
//    public static String[] moveStack;

    /*
            Player parameter represents 1 or 0.
                        1. White
                        0. Black

            Alpha Beta pruning source: https://chessprogramming.wikispaces.com/Alpha-Beta
                alpha: -infinite
                beta: infinite
     */

    public static String alphaBetaMax(int depth, int alpha, int beta, Chessboard chessboard, int player, String previousMove) {
        /*
        if ( depthleft == 0 ) return evaluate();
        for ( all moves) {
            score = alphaBetaMin( alpha, beta, depthleft - 1 );
            if( score >= beta )
                return beta;   // fail hard beta-cutoff
            if( score > alpha )
                alpha = score; // alpha acts like max in MiniMax
         }
         return alpha;
         */
//        if (depth == VERIFIED_DEPTH) moveStack = new String[VERIFIED_DEPTH+1];
        if (depth == 0){ return previousMove + "b" + MoveEvaluator.evaluateChessboard(chessboard, player, depth);
        }

        String[] moveList;
        if  (player == 1) moveList = MoveGenerator.possibleMovesWhite(chessboard).split("-");
        else moveList = MoveGenerator.possibleMovesBlack(chessboard).split("-");
        String desiredMove = "BBBB";
        if (moveList.length != 0) {
            player = 1 - player;
            if (depth == VERIFIED_DEPTH) System.out.println(Arrays.toString(moveList));
            for (int i = 0; i < moveList.length; i++) {
                if (moveList[i].length() == 4) {
                    String movedBoard = ChessBoardFactory.simulateMove(moveList[i], BitBoardCalculations.chessBoardToString(chessboard));
                    if (!movedBoard.equals("BBBB")) {
                        String result = alphaBetaMin(depth - 1, alpha, beta, ChessBoardFactory.generateChessBoardFromString(movedBoard), player, moveList[i]);
                        if (!result.substring(0, 4).equals("BBBB")) {
                            int value = Integer.valueOf(result.substring(5));
                            if (value >= beta) {
                                return "BBBBb" + beta;
                            }
                            if (value > alpha) {
                                alpha = value;
                                desiredMove = moveList[i];
                                if (depth == VERIFIED_DEPTH) {
                                    System.out.println(desiredMove + ", a:"+alpha+", b:"+beta);
                                }
//                                moveStack[depth] = previousMove;
                            }
                        }
                    }
                }
            }
        }
//        if (depth == VERIFIED_DEPTH) {
//            System.out.println(Arrays.toString(moveStack));
//            System.out.println(desiredMove);
//            //return moveStack[VERIFIED_DEPTH-1] + "b" + alpha;
//        }
        return desiredMove + "b" + alpha;
    }

    public static String alphaBetaMin(int depth, int alpha, int beta, Chessboard chessboard, int player, String previousMove) {
        /*
        if ( depthleft == 0 ) return -evaluate();
        for ( all moves) {
            score = alphaBetaMax( alpha, beta, depthleft - 1 );
        if( score <= alpha )
            return alpha; // fail hard alpha-cutoff
        if( score < beta )
         beta = score; // beta acts like min in MiniMax
        }
        return beta;
         */


        if (depth == 0){ return previousMove + "b" + -MoveEvaluator.evaluateChessboard(chessboard, player, depth);
        }

        String[] moveList;
        if  (player == 1) moveList = MoveGenerator.possibleMovesWhite(chessboard).split("-");
        else moveList = MoveGenerator.possibleMovesBlack(chessboard).split("-");
        player = 1 - player;
        String desiredMove = "BBBB";
        if (moveList.length != 0) {
            for (int i=0;i<moveList.length;i++) {
                if (moveList[i].length() == 4) {
                    String movedBoard = ChessBoardFactory.simulateMove(moveList[i], BitBoardCalculations.chessBoardToString(chessboard));
                    if (!movedBoard.equals("BBBB")) {
                        String result = alphaBetaMax(depth - 1, alpha, beta, ChessBoardFactory.generateChessBoardFromString(movedBoard), player, moveList[i]);
                        if (!result.substring(0, 4).equals("BBBB")) {
                            int value = Integer.valueOf(result.substring(5));
                            if (value <= alpha) {
                                return "BBBBb" + alpha;
                            }
                            if (value < beta) {
                                beta = value;
                                desiredMove = result.substring(0, 4);
                                System.out.println("    "+desiredMove + ", a:"+alpha+", b:"+beta);
//                                moveStack[depth] = previousMove;
                            }
                        }
                    }
                }
            }
        }
        return desiredMove + "b" + beta;
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

    public static String alphaBetaMax2(int depth, int alpha, int beta, int player, Chessboard chessboard) {
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
                    String min = alphaBetaMin2(depth - 1, alpha, beta, player, ChessBoardFactory.generateChessBoardFromString(movedBoard));
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

    public static String alphaBetaMin2(int depth, int alpha, int beta, int player, Chessboard chessboard) {
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
                    String max = alphaBetaMax2(depth - 1, alpha, beta, player, ChessBoardFactory.generateChessBoardFromString(movedBoard));
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

}
