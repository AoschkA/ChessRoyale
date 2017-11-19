package src.test;

import java.util.Scanner;

public class BitmapToNumber {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception{
        String binary;
        Scanner consoleinput = new Scanner(System.in);
        System.out.println("Please enter bitmap: ");
        while(true)
        {
        	binary=consoleinput.nextLine();
            String answer = convertStringToBitboard(binary);
            System.out.println("    "+answer);
        } 
    }
	
	public static String convertStringToBitboard(String binary) {
		String value = Long.toBinaryString(Long.parseLong(binary));
		String zeros = "0000000000000000000000000000000000000000000000000000000000000000"; //String of 64 zeros
		return zeros.substring(value.length()) + value;
    }

}
