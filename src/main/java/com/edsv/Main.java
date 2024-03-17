package com.edsv;

import java.util.Scanner;
import java.util.Set;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static int[] getUserInput() {
        int[] input = new int[2];
        System.out.print("Enter the row (1-15): ");
        input[1] = scanner.nextInt() - 1;
        System.out.print("Enter the column (1-15): ");
        input[0] = scanner.nextInt() - 1;
        return input;
    }

    public static void testCompMove() {
        FiveInARow game = new FiveInARow(true);
        game.place(0, 0, Placement.PLAYER);
        MoveInfo p = game.findCompMove();
        game.place(p.x(), p.y(), Placement.COMPUTER);
        System.out.println(p.x() + " " + p.y() + " " + p.result());
        game.printBoard();

        game.place(1, 0, Placement.PLAYER);
        p = game.findCompMove();
        game.place(p.x(), p.y(), Placement.COMPUTER);
        System.out.println(p.x() + " " + p.y() + " " + p.result());
        game.printBoard();

        game.place(2, 0, Placement.PLAYER);
        p = game.findCompMove();
        game.place(p.x(), p.y(), Placement.COMPUTER);
        System.out.println(p.x() + " " + p.y() + " " + p.result());
        game.printBoard();

        game.place(3, 0, Placement.PLAYER);
        p = game.findCompMove();
        game.place(p.x(), p.y(), Placement.COMPUTER);
        System.out.println(p.x() + " " + p.y() + " " + p.result());
        game.printBoard();

        game.place(5, 0, Placement.PLAYER);
        p = game.findCompMove();
        game.place(p.x(), p.y(), Placement.COMPUTER);
        game.printBoard();
    }

    public static void testCompVsComp() {
        FiveInARow game = new FiveInARow(true);
        while (true) {
            System.out.println(game.getSegmentsCount(Placement.PLAYER));
            MoveInfo p = game.findPlayerMove();
            System.out.println(p + ", " + Placement.PLAYER);
            game.place(p.x(), p.y(), Placement.PLAYER);
            p = game.findCompMove();
            System.out.println(p + ", " + Placement.COMPUTER);
            game.place(p.x(), p.y(), Placement.COMPUTER);
            game.printBoard();
            // Check if victory has been achieved
            // get segments for player
            Set<Segment> segments = game.getSegmentsCount(Placement.PLAYER).keySet();
            for (Segment s : segments) {
                if (s.length() == 5) {
                    System.out.println("Player wins");
                    return;
                }
            }
            // get segments for computer
            segments = game.getSegmentsCount(Placement.COMPUTER).keySet();
            for (Segment s : segments) {
                if (s.length() == 5) {
                    System.out.println("Computer wins");
                    return;
                }
            }
            System.out.println(game.evaluateUsingCounts());
        }
    }

    public static void main(String[] args) {
        if (true) {
            testCompVsComp();
            testCompMove();
            return;
        }
        FiveInARow game = new FiveInARow(true);
        while (true) {
            game.printBoard();
            int[] input = getUserInput();
            game.place(input[0], input[1], Placement.PLAYER);
            MoveInfo compMove = game.findCompMove();
            game.place(compMove.x(), compMove.y(), Placement.COMPUTER);
            System.out.println(game.evaluateUsingCounts());
        }
    }
}
