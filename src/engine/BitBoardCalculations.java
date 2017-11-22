package src.engine;

import java.util.Arrays;

import src.entities.ChessBoard;

public class BitBoardCalculations {
	
	// Flips bitboard upside down compared to array visual
	public static long convertStringToBitboard(String binary) {
        if (binary.charAt(0)=='0') { // check if it is a negative number
            return Long.parseLong(binary, 2);
        } else { // if binary number is negative
            return Long.parseLong("1"+binary.substring(2), 2)*2;
        }
    }
	
	public static ChessBoard arrayToChessBoard(String[][] chessBoard) {
		ChessBoard bitboards = new ChessBoard();
		String binary;
		for (int i = 0; i < 64; i++) {
			binary = "0000000000000000000000000000000000000000000000000000000000000000";
			binary=binary.substring(i+1)+"1"+binary.substring(0, i);
			switch (chessBoard[i/8][i%8]) {
	            case "P": bitboards.WP+=convertStringToBitboard(binary);
	                break;
	            case "N": bitboards.WN+=convertStringToBitboard(binary);
	                break;
	            case "B": bitboards.WB+=convertStringToBitboard(binary);
	                break;
	            case "R": bitboards.WR+=convertStringToBitboard(binary);
	                break;
	            case "Q": bitboards.WQ+=convertStringToBitboard(binary);
	                break;
	            case "K": bitboards.WK+=convertStringToBitboard(binary);
	                break;
	            case "p": bitboards.BP+=convertStringToBitboard(binary);
	                break;
	            case "n": bitboards.BN+=convertStringToBitboard(binary);
	                break;
	            case "b": bitboards.BB+=convertStringToBitboard(binary);
	                break;
	            case "r": bitboards.BR+=convertStringToBitboard(binary);
	                break;
	            case "q": bitboards.BQ+=convertStringToBitboard(binary);
	                break;
	            case "k": bitboards.BK+=convertStringToBitboard(binary);
	                break;
			}
		}
		return bitboards;
	}

	public static ChessBoard stringToChessBoard(String chessboardString) {
        ChessBoard bitboards = new ChessBoard();
        String binary;
        for (int i = 0; i < 64; i++) {
            binary = "0000000000000000000000000000000000000000000000000000000000000000";
            binary = binary.substring(i+1)+"1"+binary.substring(0, i);
            switch (chessboardString.charAt(i)) {
                case 'P':
                    bitboards.WP += convertStringToBitboard(binary);
                    break;
                case 'N':
                    bitboards.WN += convertStringToBitboard(binary);
                    break;
                case 'B':
                    bitboards.WB += convertStringToBitboard(binary);
                    break;
                case 'R':
                    bitboards.WR += convertStringToBitboard(binary);
                    break;
                case 'Q':
                    bitboards.WQ += convertStringToBitboard(binary);
                    break;
                case 'K':
                    bitboards.WK += convertStringToBitboard(binary);
                    break;
                case 'p':
                    bitboards.BP += convertStringToBitboard(binary);
                    break;
                case 'n':
                    bitboards.BN += convertStringToBitboard(binary);
                    break;
                case 'b':
                    bitboards.BB += convertStringToBitboard(binary);
                    break;
                case 'r':
                    bitboards.BR += convertStringToBitboard(binary);
                    break;
                case 'q':
                    bitboards.BQ += convertStringToBitboard(binary);
                    break;
                case 'k':
                    bitboards.BK += convertStringToBitboard(binary);
                    break;
            }
        }
        return bitboards;
    }

	public static String chessBoardToString(ChessBoard board) {
		String chessboardString = "";
		// White pieces
		String WK = convertBitboardToString(board.WK);
		String WQ = convertBitboardToString(board.WQ);
		String WB = convertBitboardToString(board.WB);
		String WN = convertBitboardToString(board.WN);
		String WR = convertBitboardToString(board.WR);
		String WP = convertBitboardToString(board.WP);
		// Black pieces
        String BK = convertBitboardToString(board.BK);
        String BQ = convertBitboardToString(board.BQ);
        String BB = convertBitboardToString(board.BB);
        String BN = convertBitboardToString(board.BN);
        String BR = convertBitboardToString(board.BR);
        String BP = convertBitboardToString(board.BP);

		for (int i=0; i<64; i++) {
		    if (WK.charAt(i) == 1) chessboardString += "K";
            else if (WQ.charAt(i) == '1') chessboardString += "Q";
            else if (WB.charAt(i) == '1') chessboardString += "B";
            else if (WN.charAt(i) == '1') chessboardString += "N";
            else if (WR.charAt(i) == '1') chessboardString += "R";
            else if (WP.charAt(i) == '1') chessboardString += "P";
            else if (BK.charAt(i) == '1') chessboardString += "k";
            else if (BQ.charAt(i) == '1') chessboardString += "q";
            else if (BB.charAt(i) == '1') chessboardString += "b";
            else if (BN.charAt(i) == '1') chessboardString += "n";
            else if (BR.charAt(i) == '1') chessboardString += "r";
            else if (BP.charAt(i) == '1') chessboardString += "p";
            else chessboardString += "0";
		}
		return chessboardString;
	}

	public static String convertBitboardToString(long bitboard) {
		String value = Long.toBinaryString(bitboard);
		String zeros = "0000000000000000000000000000000000000000000000000000000000000000"; //String of 64 zeros
		return zeros.substring(value.length()) + value;
	}
	
	public static long bitArrayToBitBoard(String[][] bitArray) {
		String arrayString = bitArrayToBitString(bitArray);
		long bitboard = convertStringToBitboard(arrayString);
		return bitboard;
	}
	
	public static String bitArrayToBitString(String[][] bitArray) {
		String s = "";
	    for (int i=0; i < bitArray.length; i++) {
	        for (int j=0; j < bitArray[i].length; j++) {
	            s += bitArray[i][j];
	        }
	    }
	    return s;
	}
	
	public static void drawChessboard(ChessBoard bitboards) {
        String chessBoardArray[][] = new String[8][8];
        for (int i=0;i<64;i++) {
        	chessBoardArray[i/8][i%8]=" ";
        }
        for (int i=0;i<64;i++) {
            if (((bitboards.WP>>i)&1)==1) {chessBoardArray[i/8][i%8]="P";}
            if (((bitboards.WN>>i)&1)==1) {chessBoardArray[i/8][i%8]="N";}
            if (((bitboards.WB>>i)&1)==1) {chessBoardArray[i/8][i%8]="B";}
            if (((bitboards.WR>>i)&1)==1) {chessBoardArray[i/8][i%8]="R";}
            if (((bitboards.WQ>>i)&1)==1) {chessBoardArray[i/8][i%8]="Q";}
            if (((bitboards.WK>>i)&1)==1) {chessBoardArray[i/8][i%8]="K";}
            if (((bitboards.BP>>i)&1)==1) {chessBoardArray[i/8][i%8]="p";}
            if (((bitboards.BN>>i)&1)==1) {chessBoardArray[i/8][i%8]="n";}
            if (((bitboards.BB>>i)&1)==1) {chessBoardArray[i/8][i%8]="b";}
            if (((bitboards.BR>>i)&1)==1) {chessBoardArray[i/8][i%8]="r";}
            if (((bitboards.BQ>>i)&1)==1) {chessBoardArray[i/8][i%8]="q";}
            if (((bitboards.BK>>i)&1)==1) {chessBoardArray[i/8][i%8]="k";}
        }
        for (int i=0;i<8;i++) {
            System.out.println(Arrays.toString(chessBoardArray[i]));
        }
    }
	
	public static void drawBitmapString(String bitmap) {
		String bitmapArray[][] = new String[8][8];
        for (int i=0;i<64;i++) {
        	bitmapArray[i/8][i%8]="0";
        }
        for (int i=0;i<64;i++) {
            if (bitmap.charAt(i)=='1') {bitmapArray[i/8][i%8]="1";}
        }
        for (int i=0;i<8;i++) {
            System.out.println(Arrays.toString(bitmapArray[i]));
        }
	}

}
