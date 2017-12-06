package src.io;

import src.engine.bitmap.BitBoardCalculations;
import src.engine.bitmap.ChessBoardFactory;
import src.engine.move.MoveConverter;
import src.engine.move.MoveIterator;
import src.engine.move.ThreadHandler;
import src.exceptions.InvalidMoveException;

import java.sql.Time;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.*;

public class UCI {
    public static final String ENGINENAME = "Tordenskiold v0.9.6";
    public static final String AUTHOR = "Jonas Praem";

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
            else NoneUCICommunication.noneUCICommunication(inputString);
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
//        if (input.contains("b ")) {
//            MoveIterator.PLAYER = MoveIterator.PLAYER_BLACK;
//        }
//        else if (input.contains("w ")) {
//            MoveIterator.PLAYER = MoveIterator.PLAYER_WHITE;
//        }
        if (input.contains("moves")) {
            input = input.substring(input.indexOf("moves") + 6);
            while (input.length() > 0) {
                makeMove(input);
                input=input.substring(input.indexOf(' ')+1);
            }
        }
    }

    private static void go() {
        ThreadHandler.calculateBestMove();
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
        int moveFrom_vertical = (input.charAt(0)-'a');
        int moveFrom_horizontal = ('8'-input.charAt(1));
        int moveTo_vertical = input.charAt(2)-'a';
        int moveTo_horizontal = '8'-input.charAt(3);
        String move = Integer.toString(moveFrom_horizontal) + Integer.toString(moveFrom_vertical) + Integer.toString(moveTo_horizontal) + Integer.toString(moveTo_vertical);
        ChessBoardFactory.movePiece(move);
    }
}
