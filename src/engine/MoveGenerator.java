package src.engine;

import src.entities.ChessBoard;

import java.util.Arrays;

public class MoveGenerator {
	private static long NOT_MY_PIECES;
    private static long MY_PIECES;
    private static long OCCUPIED;
    private static long EMPTY;
	
	public static String possibleMovesWhite(ChessBoard chessBoard) {
		NOT_MY_PIECES =~ (chessBoard.WP|chessBoard.WN|chessBoard.WB|chessBoard.WR|chessBoard.WQ|chessBoard.WK|chessBoard.BK); // BK to avoid illegal capture
        MY_PIECES = chessBoard.WP|chessBoard.WN|chessBoard.WB|chessBoard.WR|chessBoard.WQ; //omitted WK to avoid illegal capture
        OCCUPIED = chessBoard.WP|chessBoard.WN|chessBoard.WB|chessBoard.WR|chessBoard.WQ|chessBoard.WK|chessBoard.BP|chessBoard.BN|chessBoard.BB|chessBoard.BR|chessBoard.BQ|chessBoard.BK;
        EMPTY =~ OCCUPIED;
        String list= possibleMovesRooks(chessBoard.WR)+
				possibleMovesBishops(chessBoard.WB)+
				possibleMovesQueen(chessBoard.WQ);
//        String list = possibleMovesRooks(chessBoard.WR);
        return list;
	}
//	public static String possibleMovesBlack() {}
//	public static String possibleMovesWhitePawns() {}
//	public static String possibleMovesBlackPawns() {}
//	public static String possibleMovesKnights() {}
	public static String possibleMovesBishops(long B) {
		String list="";
		long i=B&~(B-1);
		long possibility;
		while(i != 0)
		{
			int iLocation=Long.numberOfTrailingZeros(i);
			possibility=DiagonalMoves(iLocation)&NOT_MY_PIECES;
			long j=possibility&~(possibility-1);
			while (j != 0)
			{
				int index=Long.numberOfTrailingZeros(j);
				list+="-"+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
				possibility&=~j;
				j=possibility&~(possibility-1);
			}
			B&=~i;
			i=B&~(B-1);
		}
		return list;
	}
	public static String possibleMovesRooks(long R) {
		String list="";
		long i=R&~(R-1);
		long possibility;
		while(i != 0)
		{
			int iLocation=Long.numberOfTrailingZeros(i);
			possibility=HorizontalVerticalMoves(iLocation)&NOT_MY_PIECES;
			long j=possibility&~(possibility-1);
			while (j != 0)
			{
				int index=Long.numberOfTrailingZeros(j);
				list+="-"+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
				possibility&=~j;
				j=possibility&~(possibility-1);
			}
			R&=~i;
			i=R&~(R-1);
        }
        return list;
	}
	public static String possibleMovesQueen(long Q) {
		String list="";
		long i=Q&~(Q-1);
		long possibility;
		while(i != 0)
		{
			int iLocation=Long.numberOfTrailingZeros(i);
			possibility=(HorizontalVerticalMoves(iLocation)|DiagonalMoves(iLocation))&NOT_MY_PIECES;
			long j=possibility&~(possibility-1);
			while (j != 0)
			{
				int index=Long.numberOfTrailingZeros(j);
				list+="-"+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
				possibility&=~j;
				j=possibility&~(possibility-1);
			}
			Q&=~i;
			i=Q&~(Q-1);
		}
		return list;
	}
//	public static String possibleMovesKing() {}
	
	private static long HorizontalVerticalMoves(int s) {
        long binaryS=1L<<s;
        long possibilitiesHorizontal = (OCCUPIED - 2 * binaryS) ^ Long.reverse(Long.reverse(OCCUPIED) - 2 * Long.reverse(binaryS));
        long possibilitiesVertical = ((OCCUPIED&BitmapFactory.COLUMN_MASKS[s % 8]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(OCCUPIED&BitmapFactory.COLUMN_MASKS[s % 8]) - (2 * Long.reverse(binaryS)));
        return (possibilitiesHorizontal&BitmapFactory.ROW_MASKS[s / 8]) | (possibilitiesVertical&BitmapFactory.COLUMN_MASKS[s % 8]);
    }

	private static long DiagonalMoves(int s) {
		long binaryS=1L<<s;
		long possibilitiesDiagonal = ((OCCUPIED&BitmapFactory.DIAGONAL_MASKS[(s / 8) + (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(OCCUPIED&BitmapFactory.DIAGONAL_MASKS[(s / 8) + (s % 8)]) - (2 * Long.reverse(binaryS)));
		long possibilitiesAntiDiagonal = ((OCCUPIED&BitmapFactory.DIAGONAL2_MASKS[(s / 8) + 7 - (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(OCCUPIED&BitmapFactory.DIAGONAL2_MASKS[(s / 8) + 7 - (s % 8)]) - (2 * Long.reverse(binaryS)));
		return (possibilitiesDiagonal&BitmapFactory.DIAGONAL_MASKS[(s / 8) + (s % 8)]) | (possibilitiesAntiDiagonal&BitmapFactory.DIAGONAL2_MASKS[(s / 8) + 7 - (s % 8)]);
	}

	public static void drawBitboard(long bitBoard) {
		String chessBoard[][]=new String[8][8];
		for (int i=0;i<64;i++) {
			chessBoard[i/8][i%8]="";
		}
		for (int i=0;i<64;i++) {
			if (((bitBoard>>>i)&1)==1) {chessBoard[i/8][i%8]="P";}
			if ("".equals(chessBoard[i/8][i%8])) {chessBoard[i/8][i%8]=" ";}
		}
		for (int i=0;i<8;i++) {
			System.out.println(Arrays.toString(chessBoard[i]));
		}
	}

}
