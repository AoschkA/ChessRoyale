package src.io;

import src.engine.bitmap.BitBoardCalculations;
import src.engine.bitmap.ChessBoardFactory;
import src.engine.move.MoveIterator;

import java.util.Scanner;

public class UCI {
    private static final String ENGINENAME = "ChessRoyale v0.9.3b1";
    private static final String AUTHOR = "Jonas Praem";

    public static void uciCommunication() {
        Scanner input = new Scanner(System.in);
        while(true) {
            String inputString = input.nextLine();
            if ("uci".equals(inputString)) inputUCI();
            else if (inputString.startsWith("setoption")) setOptions(inputString);
            else if ("isready".equals(inputString)) isReady();
            else if ("ucinewgame".equals(inputString)) newGame();
            else if (inputString.startsWith("position"))newPosition(inputString);
            else if (inputString.startsWith("go")) go();
            else if ("print".equals(inputString)) print();
            else if ("quit".equals(inputString)) {
                quit();
                break;
            }
            // None UCI communication
            else if (inputString.startsWith("possiblemoves")) NoneUCICommunication.possibleMoves(inputString);
        }
        input.close();
    }

    private static void inputUCI() {
        System.out.println("id name "+ENGINENAME);
        System.out.println("id author "+AUTHOR);
        // options if any
        ChessBoardFactory.initiateChessBoard();
        MoveIterator.PLAYER = MoveIterator.PLAYER_BLACK;
        System.out.println("uciok");
    }

    private static void setOptions(String input) {
        // set options
    }

    private static void isReady() {
        ChessBoardFactory.initiateChessBoard();
        System.out.println("readyok");
    }

    private static void newGame() {
        ChessBoardFactory.initiateChessBoard();

    }

    private static void newPosition(String input) {
        input=input.substring(9).concat(" ");
        if (input.contains("startpos ")) {
            input=input.substring(9);
            ChessBoardFactory.initiateChessBoard();
        }
        else if (input.contains("fen")) {
            input=input.substring(4);
            ChessBoardFactory.importFEN(input);
        }
        if (input.contains("b ")) {
            MoveIterator.PLAYER = MoveIterator.PLAYER_BLACK;
        }
        else if (input.contains("w ")) {
            MoveIterator.PLAYER = MoveIterator.PLAYER_WHITE;
        }
        if (input.contains("moves")) {
            input = input.substring(input.indexOf("moves") + 6);
            while (input.length() > 0) {
                makeMove(input);
                input=input.substring(input.indexOf(' ')+1);
            }
        }
    }

    private static void go() {
        String result = MoveIterator.alphaBetaMax(MoveIterator.VERIFIED_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, ChessBoardFactory.chessboard, MoveIterator.PLAYER, "BBBB");
        String nextMove = result.substring(0, 4);
        String output = "" + ChessBoardFactory.convertToVerticalChar(result.charAt(1)) + ChessBoardFactory.convertToHorizontalChar(result.charAt(0)) +
                ChessBoardFactory.convertToVerticalChar(result.charAt(3)) + ChessBoardFactory.convertToHorizontalChar(result.charAt(2));
        nextMove = "" + ChessBoardFactory.convertToVerticalChar(nextMove.charAt(1)) + ChessBoardFactory.convertToHorizontalChar(nextMove.charAt(0)) +
                ChessBoardFactory.convertToVerticalChar(nextMove.charAt(3)) + ChessBoardFactory.convertToHorizontalChar(nextMove.charAt(2));
        System.out.println("bestmove "+ output + " ponder " + nextMove);
    }

    private static void print() {
        String player;
        if (MoveIterator.PLAYER == 1) player = "white";
        else if (MoveIterator.PLAYER == 0) player = "black";
        else player = "NOONE";
        System.out.println("Currently playing as "+player);
        BitBoardCalculations.drawChessboard(ChessBoardFactory.chessboard);
    }

    private static void quit() {
        System.out.println("Good game");
    }

    private static void makeMove(String input) {
        BitBoardCalculations.drawChessboard(ChessBoardFactory.chessboard);
        int moveFrom_vertical = (input.charAt(0)-'a');
        int moveFrom_horizontal = ('8'-input.charAt(1));
        int moveTo_vertical = input.charAt(2)-'a';
        int moveTo_horizontal = '8'-input.charAt(3);
        String move = Integer.toString(moveFrom_horizontal) + Integer.toString(moveFrom_vertical) + Integer.toString(moveTo_horizontal) + Integer.toString(moveTo_vertical);
        System.out.println(move);
        ChessBoardFactory.movePiece(move);
    }
}
