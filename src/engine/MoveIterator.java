package src.engine;

import src.entities.ChessBoard;

public class MoveIterator {
    public static int PLAYER_WHITE = 1;
    public static int PLAYER_BLACK = 0;
    public static int VERIFIED_DEPTH = 5;
    public static String[] moveStack;

    /*
            Player parameter represents 1 or 0.
                        1. White
                        0. Black

     */

    public static String alphaBetaMax3(int depth, int alpha, int beta, ChessBoard chessboard, int player, String previousMove) {
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
        if (depth == VERIFIED_DEPTH) moveStack = new String[VERIFIED_DEPTH+1];
        if (depth == 0){ return previousMove + "b" + MoveEvaluator.evaluateChessboard(chessboard, player);
        }

        String[] moveList;
        if  (player == 1) moveList = MoveGenerator.possibleMovesWhite(chessboard).split("-");
        else moveList = MoveGenerator.possibleMovesBlack(chessboard).split("-");
        String desiredMove = "BBBB";
        if (moveList.length != 0) {
            player = 1 - player;
            for (int i = 0; i < moveList.length; i++) {
                if (moveList[i].length() == 4) {
                    String movedBoard = ChessBoardFactory.simulateMove(moveList[i], BitBoardCalculations.chessBoardToString(chessboard));
                    if (!movedBoard.equals("BBBB")) {
                        String result = alphaBetaMin3(depth - 1, alpha, beta, ChessBoardFactory.generateChessBoardFromString(movedBoard), player, moveList[i]);
                        if (!result.substring(0, 4).equals("BBBB")) {
                            int value = Integer.valueOf(result.substring(5));
                            if (value >= beta) {
                                return "BBBBb" + beta;
                            }
                            if (value > alpha) {
                                alpha = value;
                                desiredMove = result.substring(0, 4);
                                moveStack[depth] = previousMove;
                            }
                        }
                    }
                }
            }
        }
        if (depth == VERIFIED_DEPTH) return moveStack[4] + "b" + alpha;
        return desiredMove + "b" + alpha;
    }

    public static String alphaBetaMin3(int depth, int alpha, int beta, ChessBoard chessboard, int player, String previousMove) {
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


        if (depth == 0){ return previousMove + "b" + -MoveEvaluator.evaluateChessboard(chessboard, player);
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
                        String result = alphaBetaMax3(depth - 1, alpha, beta, ChessBoardFactory.generateChessBoardFromString(movedBoard), player, moveList[i]);
                        if (!result.substring(0, 4).equals("BBBB")) {
                            int value = Integer.valueOf(result.substring(5));
                            if (value <= alpha) {
                                return "BBBBb" + alpha;
                            }
                            if (value < beta) {
                                beta = value;
                                desiredMove = result.substring(0, 4);
                                moveStack[depth] = previousMove;
                            }
                        }
                    }
                }
            }
        }
        return desiredMove + "b" + beta;
    }

//    public static String alphaBeta2(int depth, int beta, int alpha, String move, ChessBoard chessboard, int player) {
//        //return in the form of 1234b##########
//        String moves = MoveGenerator.possibleMovesWhite(chessboard);
//        String[] list = moves.split("-");
//        if (depth == 0 || list.length == 0) { return move+(MoveEvaluator.evaluateMove(list.length, depth, player)*(player*2-1));}
//        // Sort moves
//        player = 1 - player;
//        for (int i=0;i<list.length;i++) {
//            String movedBoard = ChessBoardFactory.simulateMove(list[i], BitBoardCalculations.chessBoardToString(chessboard));
//            String returnString = alphaBeta2(depth-1, beta, alpha, list[i], ChessBoardFactory.generateChessBoardFromString(movedBoard), player);
//            int value = Integer.valueOf(returnString.substring(5));
//            if (player==0) {
//                if (value<=beta) {beta=value; if (depth==globalDepth) {move=returnString.substring(0,5);}}
//            } else {
//                if (value>alpha) {alpha=value; if (depth==globalDepth) {move=returnString.substring(0,5);}}
//            }
//            if (alpha>=beta) {
//                if (player==0) {return move+beta;} else {return move+alpha;}
//            }
//        }
//        if (player==0) {return move+beta;} else {return move+alpha;}
//    }

//    public static int alphaBetaPruning(int depth, int player) {
//        int alphaBeta = alphaBetaMax(Integer.MIN_VALUE, Integer.MAX_VALUE, depth, player);
//        return alphaBeta;
//    }
//
//    private static int alphaBetaMax(int alpha, int beta, int depthleft, int player) {
//        /*
//        if ( depthleft == 0 ) return evaluate();
//        for ( all moves) {
//            score = alphaBetaMin( alpha, beta, depthleft - 1 );
//            if( score >= beta )
//                return beta;   // fail hard beta-cutoff
//            if( score > alpha )
//                alpha = score; // alpha acts like max in MiniMax
//         }
//         return alpha;
//         */
//        String moves;
//        String moveToMake = "";
//        int nextPlayer;
//        moves = MoveGenerator.possibleMovesWhite(ChessBoardFactory.chessBoard);
//        nextPlayer = 0;
//
//        String[] moveList = moves.split("-");
//        if (depthleft == 0) return MoveEvaluator.evaluateMove(moveList.length, depthleft, player);
//
//        for (String move : moveList) {
//            int score = alphaBetaMin(alpha, beta, depthleft-1, nextPlayer);
//            if (score >= beta)
//                return beta;
//            if (score > alpha) {
//                alpha = score;
//            }
//        }
//        return alpha;
//    }
//
//    private static int alphaBetaMin(int alpha, int beta, int depthleft, int player) {
//        //if (depthleft == 0) return evaluate
//        /*
//        for ( all moves) {
//            score = alphaBetaMax( alpha, beta, depthleft - 1 );
//            if( score <= alpha )
//                return alpha; // fail hard alpha-cutoff
//            if( score < beta )
//                beta = score; // beta acts like min in MiniMax
//        }
//        return beta;
//         */
//        String moves;
//        String moveToMake = "";
//        int nextPlayer;
//        moves = MoveGenerator.possibleMovesWhite(ChessBoardFactory.chessBoard);
//        nextPlayer = 0;
//
//        String[] moveList = moves.split("-");
//        if (depthleft == 0) return -MoveEvaluator.evaluateMove(moveList.length, depthleft, player);
//        for (String move : moveList) {
//            int score = alphaBetaMax(alpha, beta, depthleft, nextPlayer);
//            if (score <= alpha)
//                return alpha;
//            if (score > beta) {
//                beta = score;
//            }
//        }
//        return beta;
//    }

    // Format: move+|+value e.g 6050|300
    public static int extractValue(String s) {
        String[] split = s.split("|");
        int result = Integer.parseInt(split[1]);
        return result;
    }

    public static String extractMove(String s) {
        String[] split = s.split("|");
        return split[0];
    }


}
