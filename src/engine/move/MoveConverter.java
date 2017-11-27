package src.engine.move;

import src.engine.bitmap.ChessBoardFactory;
import src.exceptions.InvalidMoveException;

public class MoveConverter {

    public static String toCoordinateMove(String arrayMove) throws InvalidMoveException {
        if (arrayMove.length() != 4) throw new InvalidMoveException();
        String move = "" + ChessBoardFactory.convertToVerticalChar(arrayMove.charAt(1)) + ChessBoardFactory.convertToHorizontalChar(arrayMove.charAt(0)) +
                ChessBoardFactory.convertToVerticalChar(arrayMove.charAt(3)) + ChessBoardFactory.convertToHorizontalChar(arrayMove.charAt(2));
        return move;
    }

    public static String toArrayMove(String coordinateMove) throws InvalidMoveException {
        if (coordinateMove.length() != 4) throw new InvalidMoveException();
        int moveFrom_vertical = (coordinateMove.charAt(0)-'a');
        int moveFrom_horizontal = ('8'-coordinateMove.charAt(1));
        int moveTo_vertical = coordinateMove.charAt(2)-'a';
        int moveTo_horizontal = '8'-coordinateMove.charAt(3);
        String move = Integer.toString(moveFrom_horizontal) + Integer.toString(moveFrom_vertical) + Integer.toString(moveTo_horizontal) + Integer.toString(moveTo_vertical);
        return move;
    }

    public static String getPieceCoordinates(String chessboard, char c) {
        chessboard = ChessBoardFactory.flipChessboard(chessboard);
        int i = chessboard.indexOf(c);
        String coordinates = "" + Integer.toString(i/8) + Integer.toString(i%8);
        return coordinates;
    }
}
