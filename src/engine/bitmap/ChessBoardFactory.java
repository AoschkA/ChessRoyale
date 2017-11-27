package src.engine.bitmap;

import src.entities.Chessboard;

/*
 * PIECE=WHITE/black
 * pawn = P/p
 * knight (horse) = N/n
 * bishop = B/b
 * rook (castle) = R/r
 * Queen = Q/q
 * King = K/k
 * 
 */

public class ChessBoardFactory {
	public static Chessboard chessboard;
	public static String chessBoardString;
	private static String[][] initialChessBoard = {

			//0	  1   2   3   4   5   6   7
            {"r","n","b","q","k","b","n","r"}, // 0
            {"p","p","p","p","p","p","p","p"}, // 1
            {" "," "," "," "," "," "," "," "}, // 2
            {" "," "," "," "," "," "," "," "}, // 3
            {" "," "," "," "," "," "," "," "}, // 4
            {" "," "," "," "," "," "," "," "}, // 5
            {"P","P","P","P","P","P","P","P"}, // 6
            {"R","N","B","Q","K","B","N","R"}};// 7
	
	public static void initiateChessBoard() {
		chessboard = BitBoardCalculations.arrayToChessBoard(initialChessBoard);
		updateChessBoardString();
	}

	public static void updateChessBoardString() {
		chessBoardString = BitBoardCalculations.chessBoardToString(chessboard);
	}

	public static String simulateMove(String move, String chessBoardString) {
		String board = flipChessboard(chessBoardString);
		int moveFrom1 = Integer.parseInt(move.charAt(0)+"");
		int moveFrom2 = Integer.parseInt(move.charAt(1)+"");
		int moveTo1 = Integer.parseInt(move.charAt(2)+"");
		int moveTo2 = Integer.parseInt(move.charAt(3)+"");
        char moveChar;

		try { moveChar = board.charAt(moveFrom1*8+moveFrom2); } catch (StringIndexOutOfBoundsException e) {
		    return "BBBB";
        }
		String newChessboard = "";
		for (int i = 0; i < board.length(); i++) {
			if (i == moveFrom1*8+moveFrom2) {
				newChessboard += "0";
			} else if (i == moveTo1*8+moveTo2) {
				newChessboard += moveChar;
			} else  {
				newChessboard += board.charAt(i);
			}
		}
		newChessboard =  flipChessboard(newChessboard);
		return newChessboard;
	}

	public static String flipChessboard(String chessBoardString) {
		String board = new StringBuilder(chessBoardString).reverse().toString();
		return board;
	}

	public static Chessboard generateChessBoardFromString(String chessBoardString) {
        chessBoardString = flipChessboard(chessBoardString);
		Chessboard result = BitBoardCalculations.stringToChessBoard(chessBoardString);
		return result;
	}

	public static void importFEN(String fen) {
        Chessboard chessboard = new Chessboard();
	    int charIndex = 0;
        int boardIndex = 0;
        while (fen.charAt(charIndex) != ' ') {
            switch (fen.charAt(charIndex++)) {
                case 'P': chessboard.WP |= (1L << boardIndex++);
                    break;
                case 'p': chessboard.BP |= (1L << boardIndex++);
                    break;
                case 'N': chessboard.WN |= (1L << boardIndex++);
                    break;
                case 'n': chessboard.BN |= (1L << boardIndex++);
                    break;
                case 'B': chessboard.WB |= (1L << boardIndex++);
                    break;
                case 'b': chessboard.BB |= (1L << boardIndex++);
                    break;
                case 'R': chessboard.WR |= (1L << boardIndex++);
                    break;
                case 'r': chessboard.BR |= (1L << boardIndex++);
                    break;
                case 'Q': chessboard.WQ |= (1L << boardIndex++);
                    break;
                case 'q': chessboard.BQ |= (1L << boardIndex++);
                    break;
                case 'K': chessboard.WK |= (1L << boardIndex++);
                    break;
                case 'k': chessboard.BK |= (1L << boardIndex++);
                    break;
                case '/':
                    break;
                case '1': boardIndex++;
                    break;
                case '2': boardIndex += 2;
                    break;
                case '3': boardIndex += 3;
                    break;
                case '4': boardIndex += 4;
                    break;
                case '5': boardIndex += 5;
                    break;
                case '6': boardIndex += 6;
                    break;
                case '7': boardIndex += 7;
                    break;
                case '8': boardIndex += 8;
                    break;
            }
        }
        BitBoardCalculations.drawChessboard(chessboard);
        ChessBoardFactory.chessboard = chessboard;
        updateChessBoardString();
    }

    public static void movePiece(String move) {
	    System.out.println(chessBoardString);
	    String movedChessboard = simulateMove(move, chessBoardString);
        System.out.println(movedChessboard);
        chessboard = generateChessBoardFromString(movedChessboard);
        updateChessBoardString();
        BitBoardCalculations.drawChessboard(chessboard);
    }

    public static char convertToVerticalChar(char i) {
	    switch (i) {
            case '0': return 'a';
            case '1': return 'b';
            case '2': return 'c';
            case '3': return 'd';
            case '4': return 'e';
            case '5': return 'f';
            case '6': return 'g';
            case '7': return 'h';
            default: return 'E';
        }
    }

    public static char convertToHorizontalChar(char i) {
        switch (i) {
            case '0': return '8';
            case '1': return '7';
            case '2': return '6';
            case '3': return '5';
            case '4': return '4';
            case '5': return '3';
            case '6': return '2';
            case '7': return '1';
            default: return 'E';
        }
    }
}
