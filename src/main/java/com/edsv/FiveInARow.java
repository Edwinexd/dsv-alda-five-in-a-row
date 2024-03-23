/* 
dsv-alda-five-in-a-row: A Java implementation of the game "Five in a row" (Gomoku) with an AI using the Minimax algorithm and alpha-beta pruning.
Copyright (C) 2024 Edwin Sundberg

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
package com.edsv;

import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.Map.Entry;

// TODO: This game is called gomoku
public class FiveInARow {
    private static final int BOARD_SIZE = 15;
    private static final int WINNING_COUNT = 5;
    private static final int MAX_DEPTH = 3;
    private Placement[][] board = new Placement[BOARD_SIZE][BOARD_SIZE];
    private boolean playerTurn;

    public FiveInARow(boolean playerStarts) {
        playerTurn = playerStarts;
    }

    public boolean isEmpty(int x, int y) {
        return board[x][y] == null;
    }

    public void place(int x, int y, Placement placement) {
        if (!isEmpty(x, y)) {
            throw new IllegalArgumentException("The position is already taken");
        }
        if (playerTurn && placement != Placement.PLAYER) {
            throw new IllegalArgumentException("It's the player's turn");
        }
        if (!playerTurn && placement != Placement.COMPUTER) {
            throw new IllegalArgumentException("It's the computer's turn");
        }
        board[x][y] = placement;
        playerTurn = !playerTurn;
    }

    public void unplace(int x, int y) {
        if (isEmpty(x, y)) {
            throw new IllegalArgumentException("The position is already empty");
        }
        Placement placement = board[x][y];
        if (placement == Placement.PLAYER && playerTurn) {
            throw new IllegalArgumentException("It's the computer's turn");
        }
        if (placement == Placement.COMPUTER && !playerTurn) {
            throw new IllegalArgumentException("It's the player's turn");
        }
        board[x][y] = null;
        playerTurn = !playerTurn;
    }

    public boolean isVictoryMove(int x, int y) {
        Placement placement = board[x][y];
        if (placement == null) {
            return false;
        }
        if (getSegmentsAt(x, y, placement).stream().anyMatch(segment -> segment.length() >= WINNING_COUNT)) {
            return true;
        }
        return false;
    }

    public boolean isFull() {
        for (Placement[] placements : board) {
            for (Placement placement : placements) {
                if (placement == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private Collection<Segment> getSegmentsAt(int x, int y, Placement player) {
        LinkedList<Segment> segments = new LinkedList<>();
        if (isEmpty(x, y)) {
            return segments;
        }
        // Check horizontal
        int count = 1;
        int openEnds = 0;
        for (int i = x - 1; i >= 0; i--) {
            if (board[i][y] == player) {
                count++;
            } else if (board[i][y] == null) {
                openEnds++;
                break;
            } else {
                break;
            }
        }
        for (int i = x + 1; i < BOARD_SIZE; i++) {
            if (board[i][y] == player) {
                count++;
            } else if (board[i][y] == null) {
                openEnds++;
                break;
            } else {
                break;
            }
        }
        if (openEnds > 0 && count > 1 || count == WINNING_COUNT) {
            segments.add(new Segment(count, openEnds));
        }

        // Check vertical
        count = 1;
        openEnds = 0;
        for (int i = y - 1; i >= 0; i--) {
            if (board[x][i] == player) {
                count++;
            } else if (board[x][i] == null) {
                openEnds++;
                break;
            } else {
                break;
            }
        }
        for (int i = y + 1; i < BOARD_SIZE; i++) {
            if (board[x][i] == player) {
                count++;
            } else if (board[x][i] == null) {
                openEnds++;
                break;
            } else {
                break;
            }
        }
        if (openEnds > 0 && count > 1 || count == WINNING_COUNT) {
            segments.add(new Segment(count, openEnds));
        }
        
        // Check diagonal bottom left to top right
        count = 1;
        openEnds = 0;
        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == player) {
                count++;
            } else if (board[i][j] == null) {
                openEnds++;
                break;
            } else {
                break;
            }
        }
        for (int i = x + 1, j = y + 1; i < BOARD_SIZE && j < BOARD_SIZE; i++, j++) {
            if (board[i][j] == player) {
                count++;
            } else if (board[i][j] == null) {
                openEnds++;
                break;
            } else {
                break;
            }
        }
        if (openEnds > 0 && count > 1 || count == WINNING_COUNT) {
            segments.add(new Segment(count, openEnds));
        }

        // Check other diagonal
        count = 1;
        openEnds = 0;
        for (int i = x - 1, j = y + 1; i >= 0 && j < BOARD_SIZE; i--, j++) {
            if (board[i][j] == player) {
                count++;
            } else if (board[i][j] == null) {
                openEnds++;
                break;
            } else {
                break;
            }
        }
        for (int i = x + 1, j = y - 1; i < BOARD_SIZE && j >= 0; i++, j--) {
            if (board[i][j] == player) {
                count++;
            } else if (board[i][j] == null) {
                openEnds++;
                break;
            } else {
                break;
            }
        }
        if (openEnds > 0 && count > 1 || count == WINNING_COUNT) {
            segments.add(new Segment(count, openEnds));
        }

        return segments;
    }

    public TreeMap<Segment, Integer> getSegmentsCount(Placement player) {
        TreeMap<Segment, Integer> segments = new TreeMap<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != player) {
                    continue;
                }
                Collection<Segment> squareSegments = getSegmentsAt(i, j, player);
                for (Segment segment : squareSegments) {
                    segments.put(segment, segments.getOrDefault(segment, 0) + 1);
                }
            }
        }
        // Since segments of size n appear n to many times in the map we divide by n for each key before returning
        // e.x. XXXX has 4 segments of size 4, so we divide by 4
        for (Segment segment : segments.keySet()) {
            segments.put(segment, segments.get(segment) / segment.length());
        }
        return segments;
    }

    public int evaluateUsingCounts() {
        TreeMap<Segment, Integer> playerSegments = getSegmentsCount(Placement.PLAYER);
        TreeMap<Segment, Integer> computerSegments = getSegmentsCount(Placement.COMPUTER);

        while(!playerSegments.isEmpty() && !computerSegments.isEmpty()) {
            Entry<Segment, Integer> playerEntry = playerSegments.pollLastEntry();
            Entry<Segment, Integer> computerEntry = computerSegments.pollLastEntry();
            if (computerEntry.getKey().compareTo(playerEntry.getKey()) > 0) {
                return calculateSegmentValue(computerEntry);
            } else if (computerEntry.getKey().compareTo(playerEntry.getKey()) < 0) {
                return -calculateSegmentValue(playerEntry);
            }
            if (computerEntry.getValue() != playerEntry.getValue()) {
                return calculateSegmentValue(computerEntry) - calculateSegmentValue(playerEntry);
            }
        }
        if (playerSegments.isEmpty() && computerSegments.isEmpty()) {
            return 0;
        }
        if (playerSegments.isEmpty()) {
            return calculateSegmentValue(computerSegments.pollLastEntry());
        }
        return -calculateSegmentValue(playerSegments.pollLastEntry());
    }

    private int calculateSegmentValue(Entry<Segment, Integer> entry) {
        int responseValue = 1;
        for (int i = 0; i < entry.getKey().length(); i++) {
            responseValue *= 10;
        }
        responseValue *= entry.getKey().openEnds();
        responseValue *= Math.min(entry.getValue(), 4); // may seem arbitrary but it's to ensure that a 
                                                          // higher length evaluates higher
        return responseValue;
    }

    public MoveInfo immediateWin(Placement playerType) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (isEmpty(i, j)) {
                    place(i, j, playerType);
                    if (isVictoryMove(i, j)) {
                        unplace(i, j);
                        if (playerType == Placement.COMPUTER) {
                            return new MoveInfo(i, j, Integer.MAX_VALUE - 1);
                        } else {
                            return new MoveInfo(i, j, Integer.MIN_VALUE + 1);
                        }
                    }
                    unplace(i, j);
                }
            }
        }
        return null;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public boolean isComputerTurn() {
        return !playerTurn;
    }

    public MoveInfo findCompMove() {
        return findCompMove(MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private MoveInfo findCompMove(int remainingDepth, int alpha, int beta) {
        MoveInfo bestMove = new MoveInfo(-1, -1, Integer.MIN_VALUE);

        if (isFull()) {
            return new MoveInfo(-1, -1, evaluateUsingCounts());
        }
        MoveInfo quickWin = immediateWin(Placement.COMPUTER);
        if (quickWin != null) {
            return quickWin;
        }

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!isEmpty(i, j)) {
                    continue;
                }
                place(i, j, Placement.COMPUTER);
                int responseValue;
                if (remainingDepth == 0) {
                    responseValue = evaluateUsingCounts();
                } else {
                    responseValue = findPlayerMove(remainingDepth - 1, alpha, beta).result();
                }
                unplace(i, j);
                if (responseValue > alpha) {
                    alpha = responseValue;
                }
                if (alpha >= beta) {
                    return new MoveInfo(i, j, responseValue);
                }
                if (responseValue > bestMove.result()) {
                    bestMove = new MoveInfo(i, j, responseValue);
                }
            }
        }
        return bestMove;
    }

    public MoveInfo findPlayerMove() {
        return findPlayerMove(MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private MoveInfo findPlayerMove(int remainingDepth, int alpha, int beta) {
        MoveInfo bestMove = new MoveInfo(-1, -1, Integer.MAX_VALUE);

        if (isFull()) {
            return new MoveInfo(-1, -1, evaluateUsingCounts());
        }
        MoveInfo quickWin = immediateWin(Placement.PLAYER);
        if (quickWin != null) {
            return quickWin;
        }
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!isEmpty(i, j)) {
                    continue;
                }
                place(i, j, Placement.PLAYER);
                int responseValue;
                if (remainingDepth == 0) {
                    responseValue = evaluateUsingCounts();
                } else {
                    responseValue = findCompMove(remainingDepth - 1, alpha, beta).result();
                }
                unplace(i, j);
                if (responseValue < beta) {
                    beta = responseValue;
                }
                if (beta <= alpha) {
                    return new MoveInfo(i, j, responseValue);
                }
                if (responseValue < bestMove.result()) {
                    bestMove = new MoveInfo(i, j, responseValue);
                }
            }
        }
        return bestMove;
    }

    public void printBoard() {
        for (int i = 0; i < BOARD_SIZE * 4 + 1; i++) {
            System.out.print("-");
        }
        System.out.println();
        System.out.print("|");
        for (int i = 0; i < BOARD_SIZE; i++) {
            // space so it takes exactly 2 characters
            System.out.printf("%2d |", i);
        }
        System.out.println();
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print("|");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(" ");
                if (board[j][i] == null) {
                    System.out.print(" ");
                } else if (board[j][i] == Placement.PLAYER) {
                    System.out.print("X");
                } else {
                    System.out.print("O");
                }
                System.out.print(" |");
            }
            System.out.println();
        }
        for (int i = 0; i < BOARD_SIZE * 4 + 1; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
