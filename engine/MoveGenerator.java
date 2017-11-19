package engine;

import entities.ChessBoard;

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
//        String list=possibleWP(chessBoard.WP,chessBoard.BP, chessBoard.EP)+
//                possibleN(OCCUPIED,chessBoard.WN)+
//                possibleB(OCCUPIED,chessBoard.WB)+
//                possibleR(chessBoard.WR)+
//                possibleQ(OCCUPIED,chessBoard.WQ)+
//                possibleK(OCCUPIED,chessBoard.WK)+
//                possibleCW(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK,CWK,CWQ);
        String list = possibleMovesRooks(chessBoard.WR);
        return list;
	}
//	public static String possibleMovesBlack() {}
//	public static String possibleMovesWhitePawns() {}
//	public static String possibleMovesBlackPawns() {}
//	public static String possibleMovesKnights() {}
//	public static String possibleMovesBishops() {}
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
				list+=""+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
				possibility&=~j;
				j=possibility&~(possibility-1);
			}
			R&=~i;
			i=R&~(R-1);
        }
        return list;
	}
//	public static String possibleMovesQueen() {}
//	public static String possibleMovesKing() {}
	
	private static long HorizontalVerticalMoves(int s) {
        //REMINDER: requires OCCUPIED to be up to date
        long binaryS=1L<<s;
        long possibilitiesHorizontal = (OCCUPIED - 2 * binaryS) ^ Long.reverse(Long.reverse(OCCUPIED) - 2 * Long.reverse(binaryS));
        long possibilitiesVertical = ((OCCUPIED&BitmapFactory.COLUMN_MASKS[s % 8]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(OCCUPIED&BitmapFactory.COLUMN_MASKS[s % 8]) - (2 * Long.reverse(binaryS)));
        return (possibilitiesHorizontal&BitmapFactory.ROW_MASKS[s / 8]) | (possibilitiesVertical&BitmapFactory.ROW_MASKS[s % 8]);
    }

}
