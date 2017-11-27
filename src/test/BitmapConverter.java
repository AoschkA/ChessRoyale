package src.test;

import src.engine.bitmap.ChessBoardFactory;

public class BitmapConverter {
	public static void main(String[] args) throws Exception{
		computeAndPrint();
		while(true) {}
    }
    public static void computeAndPrint() throws Exception{
    	ChessBoardFactory.initiateChessBoard();
    	
    	String zeros = "0000000000000000000000000000000000000000000000000000000000000000";
    	String value = Long.toBinaryString(72340172838076673L);
    	System.out.println("COLUMN_A: "+zeros.substring(value.length()) + value);
    	value = Long.toBinaryString(-9187201950435737472L);
    	System.out.println("COLUMN_H: "+zeros.substring(value.length()) + value);
    	value = Long.toBinaryString(217020518514230019L);
    	System.out.println("FILE_AB: "+zeros.substring(value.length()) + value);
    	value = Long.toBinaryString(-4557430888798830400L);
    	System.out.println("FILE_GH: "+zeros.substring(value.length()) + value);
    	value = Long.toBinaryString(-72057594037927936L);
    	System.out.println("RANK_1: "+zeros.substring(value.length()) + value);
    	value = Long.toBinaryString(1095216660480L);
    	System.out.println("RANK_4: "+zeros.substring(value.length()) + value);
    	value = Long.toBinaryString(4278190080L);
    	System.out.println("RANK_5: "+zeros.substring(value.length()) + value);
    	value = Long.toBinaryString(255L);
    	System.out.println("RANK_8: "+zeros.substring(value.length()) + value);
    	value = Long.toBinaryString(103481868288L);
    	System.out.println("CENTER: "+zeros.substring(value.length()) + value);
    	value = Long.toBinaryString(66229406269440L);
    	System.out.println("EXTENDED_CENTER: "+zeros.substring(value.length()) + value);
    	value = Long.toBinaryString(-1085102592571150096L);
    	System.out.println("KING_SIDE: "+zeros.substring(value.length()) + value);
    	value = Long.toBinaryString(1085102592571150095L);
    	System.out.println("QUEEN_SIDE: "+zeros.substring(value.length()) + value);
    	value = Long.toBinaryString(460039L);
    	System.out.println("KING_SPAN: "+zeros.substring(value.length()) + value);
    	value = Long.toBinaryString(43234889994L);
    	System.out.println("KNIGHT_SPAN: "+zeros.substring(value.length()) + value);
    	value = Long.toBinaryString(562675075481600L);
    	System.out.println("possibilitiesHorizontal	562675075481600: "+zeros.substring(value.length()) + value);
    	value = Long.toBinaryString(36028522141040640L);
    	System.out.println("possibilitiesVertical	36028522141040640: "+zeros.substring(value.length()) + value);
    	System.out.println("KNIGHT_SPAN: "+zeros.substring(value.length()) + value);
    	value = Long.toBinaryString(71776119061217280L);
    	System.out.println("WP: "+zeros.substring(value.length()) + value);
    }

}
