package src.engine.move;

import src.engine.bitmap.Bitmaps;
import src.engine.bitmap.ChessBoardFactory;
import src.entities.Chessboard;

import java.util.Arrays;

public class MoveGenerator {
	private static long ENEMY_PIECES;
    private static long MY_PIECES;
    private static long OCCUPIED;
    private static long EMPTY;

    public static String possibleMovesWhiteWithKing(Chessboard chessboard) {
        ENEMY_PIECES =~ (chessboard.WP| chessboard.WN| chessboard.WB| chessboard.WR| chessboard.WQ| chessboard.WK);
        MY_PIECES = chessboard.WP| chessboard.WN| chessboard.WB| chessboard.WR| chessboard.WQ| chessboard.WK;
        OCCUPIED = chessboard.WP| chessboard.WN| chessboard.WB| chessboard.WR| chessboard.WQ| chessboard.WK| chessboard.BP| chessboard.BN| chessboard.BB| chessboard.BR| chessboard.BQ| chessboard.BK;
        EMPTY =~ OCCUPIED;
        String list= possibleMovesWhitePawns(chessboard.WP, chessboard.BP)+
                possibleMovesRooks(chessboard.WR)+
                possibleMovesKnights(chessboard.WN)+
                possibleMovesBishops(chessboard.WB)+
                possibleMovesQueen(chessboard.WQ)+
                possibleMovesKing(chessboard.WK);
        return list;
    }

    public static String possibleMovesBlackWithKing(Chessboard chessboard) {
        ENEMY_PIECES = ~(chessboard.BP | chessboard.BN | chessboard.BB | chessboard.BR | chessboard.BQ | chessboard.BK);
        MY_PIECES = chessboard.BP | chessboard.BN | chessboard.BB | chessboard.BR | chessboard.BQ| chessboard.BK;
        OCCUPIED = chessboard.WP | chessboard.WN | chessboard.WB | chessboard.WR | chessboard.WQ | chessboard.WK | chessboard.BP | chessboard.BN | chessboard.BB | chessboard.BR | chessboard.BQ | chessboard.BK;
        EMPTY = ~OCCUPIED;
        String list = possibleMovesBlackPawns(chessboard.BP, chessboard.WP) +
                possibleMovesRooks(chessboard.BR) +
                possibleMovesKnights(chessboard.BN) +
                possibleMovesBishops(chessboard.BB)+
                possibleMovesQueen(chessboard.BQ)+
                possibleMovesKing(chessboard.BK);
        return list;
    }
	
	public static String possibleMovesWhite(Chessboard chessboard) {
		ENEMY_PIECES =~ (chessboard.WP| chessboard.WN| chessboard.WB| chessboard.WR| chessboard.WQ| chessboard.WK| chessboard.BK); // BK to avoid illegal capture
        MY_PIECES = chessboard.WP| chessboard.WN| chessboard.WB| chessboard.WR| chessboard.WQ; //omitted WK to avoid illegal capture
        OCCUPIED = chessboard.WP| chessboard.WN| chessboard.WB| chessboard.WR| chessboard.WQ| chessboard.WK| chessboard.BP| chessboard.BN| chessboard.BB| chessboard.BR| chessboard.BQ| chessboard.BK;
        EMPTY =~ OCCUPIED;
        String list= possibleMovesWhitePawns(chessboard.WP, chessboard.BP)+
                possibleMovesRooks(chessboard.WR)+
                possibleMovesKnights(chessboard.WN)+
				possibleMovesBishops(chessboard.WB)+
				possibleMovesQueen(chessboard.WQ)+
                possibleMovesKing(chessboard.WK);
        list = IllegalMoveFilterWhite(list.split("-"));
        return list;
	}

	public static String possibleMovesBlack(Chessboard chessboard) {
        ENEMY_PIECES = ~(chessboard.BP | chessboard.BN | chessboard.BB | chessboard.BR | chessboard.BQ | chessboard.BK | chessboard.WK);// added WK to avoid illegal capture
        MY_PIECES = chessboard.BP | chessboard.BN | chessboard.BB | chessboard.BR | chessboard.BQ;// omitted BK to avoid illegal capture
        OCCUPIED = chessboard.WP | chessboard.WN | chessboard.WB | chessboard.WR | chessboard.WQ | chessboard.WK | chessboard.BP | chessboard.BN | chessboard.BB | chessboard.BR | chessboard.BQ | chessboard.BK;
        EMPTY = ~OCCUPIED;
        String list = possibleMovesBlackPawns(chessboard.BP, chessboard.WP) +
                possibleMovesRooks(chessboard.BR) +
                possibleMovesKnights(chessboard.BN) +
                possibleMovesBishops(chessboard.BB)+
                possibleMovesQueen(chessboard.BQ)+
                possibleMovesKing(chessboard.BK);
        list = IllegalMoveFilterBlack(list.split("-"));
        return list;
    }

    private static String possibleMovesWhiteNoFilter(Chessboard chessboard) {
        ENEMY_PIECES =~ (chessboard.WP| chessboard.WN| chessboard.WB| chessboard.WR| chessboard.WQ| chessboard.WK| chessboard.BK); // BK to avoid illegal capture
        MY_PIECES = chessboard.WP| chessboard.WN| chessboard.WB| chessboard.WR| chessboard.WQ; //omitted WK to avoid illegal capture
        OCCUPIED = chessboard.WP| chessboard.WN| chessboard.WB| chessboard.WR| chessboard.WQ| chessboard.WK| chessboard.BP| chessboard.BN| chessboard.BB| chessboard.BR| chessboard.BQ| chessboard.BK;
        EMPTY =~ OCCUPIED;
        String list= possibleMovesWhitePawns(chessboard.WP, chessboard.BP)+
                possibleMovesRooks(chessboard.WR)+
                possibleMovesKnights(chessboard.WN)+
                possibleMovesBishops(chessboard.WB)+
                possibleMovesQueen(chessboard.WQ)+
                possibleMovesKing(chessboard.WK);
        return list;
    }

    private static String possibleMovesBlackNoFilter(Chessboard chessboard) {
        ENEMY_PIECES = ~(chessboard.BP | chessboard.BN | chessboard.BB | chessboard.BR | chessboard.BQ | chessboard.BK | chessboard.WK);// added WK to avoid illegal capture
        MY_PIECES = chessboard.BP | chessboard.BN | chessboard.BB | chessboard.BR | chessboard.BQ;// omitted BK to avoid illegal capture
        OCCUPIED = chessboard.WP | chessboard.WN | chessboard.WB | chessboard.WR | chessboard.WQ | chessboard.WK | chessboard.BP | chessboard.BN | chessboard.BB | chessboard.BR | chessboard.BQ | chessboard.BK;
        EMPTY = ~OCCUPIED;
        String list = possibleMovesBlackPawns(chessboard.BP, chessboard.WP) +
                possibleMovesRooks(chessboard.BR) +
                possibleMovesKnights(chessboard.BN) +
                possibleMovesBishops(chessboard.BB)+
                possibleMovesQueen(chessboard.BQ)+
                possibleMovesKing(chessboard.BK);
        return list;
    }

    private static String possibleMovesWhitePawns(long WP, long BP) {
        String list="";
        //x1,y1,x2,y2
        long PAWN_MOVES=(WP>>7)& ENEMY_PIECES &OCCUPIED&~Bitmaps.ROW_8&~Bitmaps.COLUMN_A;//capture right
        long possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            list+="-"+(index/8+1)+(index%8-1)+(index/8)+(index%8);
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        PAWN_MOVES=(WP>>9)& ENEMY_PIECES &OCCUPIED&~Bitmaps.ROW_8&~Bitmaps.COLUMN_H;//capture left
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
        return list;
    }

    private static String possibleMovesBlackPawns(long BP, long WP) {
        String list = "";
        // x1,y1,x2,y2
        long PAWN_MOVES = (BP << 7) & ENEMY_PIECES & OCCUPIED & ~Bitmaps.ROW_1 & ~Bitmaps.COLUMN_H;// capture right
        long possibility = PAWN_MOVES & ~(PAWN_MOVES - 1);
        while (possibility != 0) {
            int index = Long.numberOfTrailingZeros(possibility);
            list += "-" + (index / 8 - 1) + (index % 8 + 1) + (index / 8) + (index % 8);
            PAWN_MOVES &= ~possibility;
            possibility = PAWN_MOVES & ~(PAWN_MOVES - 1);
        }
        PAWN_MOVES = (BP << 9) & ENEMY_PIECES & OCCUPIED & ~Bitmaps.ROW_1 & ~Bitmaps.COLUMN_A;// capture left
        possibility = PAWN_MOVES & ~(PAWN_MOVES - 1);
        while (possibility != 0) {
            int index = Long.numberOfTrailingZeros(possibility);
            list += "-" + (index / 8 - 1) + (index % 8 - 1) + (index / 8) + (index % 8);
            PAWN_MOVES &= ~possibility;
            possibility = PAWN_MOVES & ~(PAWN_MOVES - 1);
        }
        PAWN_MOVES = (BP << 8) & EMPTY & ~Bitmaps.ROW_1;// move 1 forward
        possibility = PAWN_MOVES & ~(PAWN_MOVES - 1);
        while (possibility != 0) {
            int index = Long.numberOfTrailingZeros(possibility);
            list += "-" + (index / 8 - 1) + (index % 8) + (index / 8) + (index % 8);
            PAWN_MOVES &= ~possibility;
            possibility = PAWN_MOVES & ~(PAWN_MOVES - 1);
        }
        PAWN_MOVES = (BP << 16) & EMPTY & (EMPTY << 8) & Bitmaps.ROW_5;// move 2 forward
        possibility = PAWN_MOVES & ~(PAWN_MOVES - 1);
        while (possibility != 0) {
            int index = Long.numberOfTrailingZeros(possibility);
            list += "-" + (index / 8 - 2) + (index % 8) + (index / 8) + (index % 8);
            PAWN_MOVES &= ~possibility;
            possibility = PAWN_MOVES & ~(PAWN_MOVES - 1);
        }

        return list;
    }

    private static String possibleMovesKnights(long N) {
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
                possibility &=~Bitmaps.COLUMN_GH& ENEMY_PIECES;
            }
            else {
                possibility &=~Bitmaps.COLUMN_AB& ENEMY_PIECES;
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

    private static String possibleMovesBishops(long B) {
		String list="";
		long i=B&~(B-1);
		long possibility;
		while(i != 0)
		{
			int iLocation=Long.numberOfTrailingZeros(i);
			possibility=DiagonalMoves(iLocation)& ENEMY_PIECES;
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

    private static String possibleMovesRooks(long R) {
		String list="";
		long i=R&~(R-1);
		long possibility;
		while(i != 0)
		{
			int iLocation=Long.numberOfTrailingZeros(i);
			possibility=HorizontalVerticalMoves(iLocation)& ENEMY_PIECES;
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

    private static String possibleMovesQueen(long Q) {
		String list="";
		long i=Q&~(Q-1);
		long possibility;
		while(i != 0)
		{
			int iLocation=Long.numberOfTrailingZeros(i);
			possibility=(HorizontalVerticalMoves(iLocation)|DiagonalMoves(iLocation))& ENEMY_PIECES;
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

    private static String possibleMovesKing(long K) {
        String list = "";
        long possibility;
        int iLocation = Long.numberOfTrailingZeros(K);
        if (iLocation > 9) {
            possibility = Bitmaps.KING_SPAN << (iLocation - 9);
        } else {
            possibility = Bitmaps.KING_SPAN >> (9 - iLocation);
        }
        if (iLocation % 8 < 4) {
            possibility &= ~Bitmaps.COLUMN_GH & ENEMY_PIECES;
        } else {
            possibility &= ~Bitmaps.COLUMN_AB & ENEMY_PIECES;
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

    private static void drawBitboard(long bitBoard) {
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

	private static String IllegalMoveFilterWhite(String[] movelist) {
        String filtered_movelist = "";
        for (String move : movelist) {
            if (move.length() == 4) {
                String movedboard = ChessBoardFactory.simulateMove(move, ChessBoardFactory.getChessBoardString());
                if (!movedboard.equals("BBBB")) {
                    String kingPosition = MoveConverter.getPieceCoordinates(movedboard, 'K');
                    if (whiteKingIsSafe(movedboard, kingPosition)) filtered_movelist += move + "-";
                }
            }
        }
        return filtered_movelist;
    }

    private static String IllegalMoveFilterBlack(String[] movelist) {
        String filtered_movelist = "";
        for (String move : movelist) {
            if (move.length() == 4) {
                String movedboard = ChessBoardFactory.simulateMove(move, ChessBoardFactory.getChessBoardString());
                if (!movedboard.equals("BBBB")) {
                    String kingPosition = MoveConverter.getPieceCoordinates(movedboard, 'k');
                    if (blackKingIsSafe(movedboard, kingPosition)) filtered_movelist += move + "-";
                }
            }
        }
        return filtered_movelist;
    }

    public static boolean whiteKingIsSafe(String chessboard, String kingPosition) {
        String[] black_movelist = possibleMovesBlackWithKing(ChessBoardFactory.generateChessBoardFromString(chessboard)).split("-");
        for (String black_move : black_movelist) {
            if (black_move.length() == 4)
                if (black_move.charAt(2) == kingPosition.charAt(0) && black_move.charAt(3) == kingPosition.charAt(1))
                    return false;
        }
        return true;
    }

    public static boolean blackKingIsSafe(String chessboard, String kingPosition) {
        String[] white_movelist = possibleMovesWhiteWithKing(ChessBoardFactory.generateChessBoardFromString(chessboard)).split("-");
        for (String white_move : white_movelist) {
            if (white_move.length() == 4)
                if (white_move.charAt(2) == kingPosition.charAt(0) && white_move.charAt(3) == kingPosition.charAt(1))
                    return false;
        }
        return true;
    }

}
