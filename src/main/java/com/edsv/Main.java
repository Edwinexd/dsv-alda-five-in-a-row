package com.edsv;

public class Main {
    public static void testCompMove() {
        FiveInARow game = new FiveInARow(true);
        game.place(0, 0, Placement.PLAYER);
        MoveInfo p = game.findCompMove(true);
        game.place(p.x(), p.y(), Placement.COMPUTER);
        System.out.println(p.x() + " " + p.y() + " " + p.result());
        game.printBoard();

        game.place(0, 1, Placement.PLAYER);
        p = game.findCompMove(true);
        game.place(p.x(), p.y(), Placement.COMPUTER);
        System.out.println(p.x() + " " + p.y() + " " + p.result());
        game.printBoard();

        game.place(2, 0, Placement.PLAYER);
        p = game.findCompMove(true);
        game.place(p.x(), p.y(), Placement.COMPUTER);
        System.out.println(p.x() + " " + p.y() + " " + p.result());
        game.printBoard();

        game.place(1, 2, Placement.PLAYER);
        p = game.findCompMove(true);
        game.place(p.x(), p.y(), Placement.COMPUTER);
        System.out.println(p.x() + " " + p.y() + " " + p.result());
        game.printBoard();
    }

    public static void main(String[] args) {
        testCompMove();
    }
}