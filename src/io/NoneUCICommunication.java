package src.io;

import src.engine.bitmap.ChessBoardFactory;
import src.engine.move.MoveConverter;
import src.engine.move.MoveGenerator;
import src.engine.move.MoveIterator;
import src.engine.move.ThreadHandler;
import src.exceptions.InvalidMoveException;

public class NoneUCICommunication {

    public static void noneUCICommunication(String input) {
        if (input.startsWith("possiblemoves")) possibleMoves(input);
        else if (input.startsWith("startgame")) startGame();
        else if (input.startsWith("pickside")) pickSide(input);
        else if (input.startsWith("move")) move(input);
    }

    private static void possibleMoves(String input) {
        if (input.contains("white") || input.contains(" w")) possibleMovesWhite();
        else if (input.contains("black") || input.contains(" b")) possibleMovesBlack();
    }

    private static void possibleMovesWhite() {
        String moves = MoveGenerator.possibleMovesWhite(ChessBoardFactory.chessboard);
        System.out.println(moves);
    }

    private static void possibleMovesBlack() {
        String moves = MoveGenerator.possibleMovesBlack(ChessBoardFactory.chessboard);
        System.out.println(moves);
    }

    private static void startGame() {
        ChessBoardFactory.initiateChessBoard();
        System.out.println("Welcome to "+UCI.ENGINENAME+" by "+UCI.AUTHOR);
        System.out.println("'pickside w' to play as white, 'pickside b' to play as black");
        System.out.println("To make a move use 'move f2f4' for example to move a piece from f2 to f4");
    }

    private static void pickSide(String input) {
        if (input.contains("b") || input.contains("black")) {
            MoveIterator.PLAYER = MoveIterator.PLAYER_WHITE;
            System.out.println("Now playing as black, the engine will play as white");
            String result = ThreadHandler.calculateBestMove();
            engineMove(result);
        }
        else if (input.contains("w") || input.contains("white")) {
            MoveIterator.PLAYER = MoveIterator.PLAYER_BLACK;
            System.out.println("Now playing as white, the engine will play as black");
        }
    }

    private static void move(String input) {
        input = input.substring(5);
        try {
            String move;
            move = MoveConverter.toArrayMove(input);
            ChessBoardFactory.movePiece(move);
            String result = ThreadHandler.calculateBestMove();
            engineMove(result);
        } catch (InvalidMoveException e) {
            System.out.println("INVALID MOVE");
        }
    }

    private static void engineMove(String move) {
        try {
            move = MoveConverter.toArrayMove(move);
            ChessBoardFactory.movePiece(move);
        } catch (InvalidMoveException e) {
            System.out.println("INVALID MOVE");
        }
    }
}
