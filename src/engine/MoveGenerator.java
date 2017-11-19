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
        String list= possibleMovesWhitePawns(chessBoard.WP, chessBoard.BP)+
                possibleMovesRooks(chessBoard.WR)+
				possibleMovesBishops(chessBoard.WB)+
                possibleMovesKnights(chessBoard.WN)+
				possibleMovesQueen(chessBoard.WQ)+
                possibleMovesKing(chessBoard.WK);
        return list;
	}
//	public static String possibleMovesBlack() {}
	public static String possibleMovesWhitePawns(long WP, long BP) {
        String list="";
        //x1,y1,x2,y2
        long PAWN_MOVES=(WP>>7)&NOT_MY_PIECES&OCCUPIED&~Bitmaps.ROW_8&~Bitmaps.COLUMN_A;//capture right
        long possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            list+="-"+(index/8+1)+(index%8-1)+(index/8)+(index%8);
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        PAWN_MOVES=(WP>>9)&NOT_MY_PIECES&OCCUPIED&~Bitmaps.ROW_8&~Bitmaps.COLUMN_H;//capture left
        possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            list+="-"+(index/8+1)+(index%8+1)+(index/8)+(index%8);
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        PAWN_MOVES=(WP>>8)&EMPTY&~Bitmaps.ROW_8;//move 1 forward
        possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            list+="-"+(index/8+1)+(index%8)+(index/8)+(index%8);
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        PAWN_MOVES=(WP>>16)&EMPTY&(EMPTY>>8)&Bitmaps.ROW_4;//move 2 forward
        possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            list+="-"+(index/8+2)+(index%8)+(index/8)+(index%8);
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        //y1,y2,Promotion Type,"P"
        PAWN_MOVES=(WP>>7)&NOT_MY_PIECES&OCCUPIED&Bitmaps.ROW_8&~Bitmaps.COLUMN_A;//pawn promotion by capture right
        possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            list+="-"+(index%8-1)+(index%8)+"QP"+(index%8-1)+(index%8)+"RP"+(index%8-1)+(index%8)+"BP"+(index%8-1)+(index%8)+"NP";
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        PAWN_MOVES=(WP>>9)&NOT_MY_PIECES&OCCUPIED&Bitmaps.ROW_8&~Bitmaps.COLUMN_H; //pawn promotion by capture left
        possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            list+="-"+(index%8+1)+(index%8)+"QP"+(index%8+1)+(index%8)+"RP"+(index%8+1)+(index%8)+"BP"+(index%8+1)+(index%8)+"NP";
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        PAWN_MOVES=(WP>>8)&EMPTY&Bitmaps.ROW_8;//pawn promotion by move 1 forward
        possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            list+="-"+(index%8)+(index%8)+"QP"+(index%8)+(index%8)+"RP"+(index%8)+(index%8)+"BP"+(index%8)+(index%8)+"NP";
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        //y1,y2,"WE"
        //en passant right
        possibility = (WP << 1)&BP&Bitmaps.ROW_5&~Bitmaps.COLUMN_A&Bitmaps.EP;//shows piece to remove, not the destination
        if (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            list+="-"+(index%8-1)+(index%8)+"WE";
        }
        //en passant left
        possibility = (WP >> 1)&BP&Bitmaps.ROW_5&~Bitmaps.COLUMN_H&Bitmaps.EP;//shows piece to remove, not the destination
        if (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            list+="-"+(index%8+1)+(index%8)+"WE";
        }
        return list;
    }

//	public static String possibleMovesBlackPawns() {}
	public static String possibleMovesKnights(long N) {
        String list="";
        long i=N&~(N-1);
        long possibility;
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            if (iLocation>18)
            {
                possibility= Bitmaps.KNIGHT_SPAN<<(iLocation-18);
            }
            else {
                possibility= Bitmaps.KNIGHT_SPAN>>(18-iLocation);
            }
            if (iLocation%8<4)
            {
                possibility &=~Bitmaps.COLUMN_GH&NOT_MY_PIECES;
            }
            else {
                possibility &=~Bitmaps.COLUMN_AB&NOT_MY_PIECES;
            }
            long j=possibility&~(possibility-1);
            while (j != 0)
            {
                int index=Long.numberOfTrailingZeros(j);
                list+="-"+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
                possibility&=~j;
                j=possibility&~(possibility-1);
            }
            N&=~i;
            i=N&~(N-1);
        }
        return list;
    }
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
	public static String possibleMovesKing(long K) {
        String list = "";
        long possibility;
        int iLocation = Long.numberOfTrailingZeros(K);
        if (iLocation > 9) {
            possibility = Bitmaps.KING_SPAN << (iLocation - 9);
        } else {
            possibility = Bitmaps.KING_SPAN >> (9 - iLocation);
        }
        if (iLocation % 8 < 4) {
            possibility &= ~Bitmaps.COLUMN_GH & NOT_MY_PIECES;
        } else {
            possibility &= ~Bitmaps.COLUMN_AB & NOT_MY_PIECES;
        }
        long j = possibility & ~(possibility - 1);
        while (j != 0) {
            int index = Long.numberOfTrailingZeros(j);
            list += "-" + (iLocation / 8) + (iLocation % 8) + (index / 8) + (index % 8);
            possibility &= ~j;
            j = possibility & ~(possibility - 1);
        }
        return list;
    }
	
	private static long HorizontalVerticalMoves(int s) {
        long binaryS=1L<<s;
        long possibilitiesHorizontal = (OCCUPIED - 2 * binaryS) ^ Long.reverse(Long.reverse(OCCUPIED) - 2 * Long.reverse(binaryS));
        long possibilitiesVertical = ((OCCUPIED& Bitmaps.COLUMN_MASKS[s % 8]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(OCCUPIED& Bitmaps.COLUMN_MASKS[s % 8]) - (2 * Long.reverse(binaryS)));
        return (possibilitiesHorizontal& Bitmaps.ROW_MASKS[s / 8]) | (possibilitiesVertical& Bitmaps.COLUMN_MASKS[s % 8]);
    }

	private static long DiagonalMoves(int s) {
		long binaryS=1L<<s;
		long possibilitiesDiagonal = ((OCCUPIED& Bitmaps.DIAGONAL_MASKS[(s / 8) + (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(OCCUPIED& Bitmaps.DIAGONAL_MASKS[(s / 8) + (s % 8)]) - (2 * Long.reverse(binaryS)));
		long possibilitiesAntiDiagonal = ((OCCUPIED& Bitmaps.DIAGONAL2_MASKS[(s / 8) + 7 - (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(OCCUPIED& Bitmaps.DIAGONAL2_MASKS[(s / 8) + 7 - (s % 8)]) - (2 * Long.reverse(binaryS)));
		return (possibilitiesDiagonal& Bitmaps.DIAGONAL_MASKS[(s / 8) + (s % 8)]) | (possibilitiesAntiDiagonal& Bitmaps.DIAGONAL2_MASKS[(s / 8) + 7 - (s % 8)]);
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
