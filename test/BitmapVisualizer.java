package test;

import java.util.Scanner;

import engine.BitBoardCalculations;
import engine.ChessBoardFactory;

public class BitmapVisualizer {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception{
        String operation;
        Scanner consoleinput = new Scanner(System.in);
        ChessBoardFactory.initiateChessBoard();
		BitBoardCalculations.drawChessboard(ChessBoardFactory.chessBoard);
        System.out.println("Please enter bitmap: ");
        while(true)
        {
            operation=consoleinput.nextLine();
            if (operation.length() == 64) drawBitMap(operation);
            else System.out.println("Bitmap not the right size for a chess board");
        } 
    }
	
	public static void drawBitMap(String operation) throws Exception{
		BitBoardCalculations.drawBitmapString(operation);
    }

}
