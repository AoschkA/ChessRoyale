package src.test;

import src.engine.move.MoveIterator;

import java.util.Scanner;

public class AlphaBetaTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int result = MoveIterator.alphaBetaMaxForTest(3, Integer.MIN_VALUE, Integer.MAX_VALUE, scanner);
        System.out.println(result);
    }
}
