package com.edsv;

import java.util.Collection;
import java.util.LinkedList;

// TODO: This game is called gomoku
public class FiveInARow {
    private static final int BOARD_SIZE = 15; // TODO: Change to 15
    private static final int WINNING_COUNT = 5; // TODO: Change to 5
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

    // TODO: This should return an int
    public boolean isVictoryMove(int x, int y) {
        Placement placement = board[x][y];
        if (placement == null) {
            return false;
        }
        // Check horizontal
        int count = 1;
        for (int i = x - 1; i >= 0; i--) {
            if (board[i][y] == placement) {
                count++;
            } else {
                break;
            }
        }
        for (int i = x + 1; i < BOARD_SIZE; i++) {
            if (board[i][y] == placement) {
                count++;
            } else {
                break;
            }
        }
        if (count >= WINNING_COUNT) {
            return true;
        }

        // Check vertical
        count = 1;
        for (int i = y - 1; i >= 0; i--) {
            if (board[x][i] == placement) {
                count++;
            } else {
                break;
            }
        }
        for (int i = y + 1; i < BOARD_SIZE; i++) {
            if (board[x][i] == placement) {
                count++;
            } else {
                break;
            }
        }
        if (count >= WINNING_COUNT) {
            return true;
        }

        // Check diagonal
        count = 1;
        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == placement) {
                count++;
            } else {
                break;
            }
        }
        for (int i = x + 1, j = y + 1; i < BOARD_SIZE && j < BOARD_SIZE; i++, j++) {
            if (board[i][j] == placement) {
                count++;
            } else {
                break;
            }
        }
        if (count >= WINNING_COUNT) {
            return true;
        }

        // Check other diagonal
        count = 1;
        for (int i = x - 1, j = y + 1; i >= 0 && j < BOARD_SIZE; i--, j++) {
            if (board[i][j] == placement) {
                count++;
            } else {
                break;
            }
        }
        for (int i = x + 1, j = y - 1; i < BOARD_SIZE && j >= 0; i++, j--) {
            if (board[i][j] == placement) {
                count++;
            } else {
                break;
            }
        }
        return count >= WINNING_COUNT;
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

    public Collection<Segment> getSegments(Placement player) {
        LinkedList<Segment> segments = new LinkedList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != player) {
                    continue;
                }
                // Check horizontal
                int count = 1;
                int openEnds = 0;
                for (int k = i - 1; k >= 0; k--) {
                    if (board[k][j] == player) {
                        count++;
                    } else if (board[k][j] == null) {
                        openEnds++;
                        break;
                    } else {
                        break;
                    }
                }
                for (int k = i + 1; k < BOARD_SIZE; k++) {
                    if (board[k][j] == player) {
                        count++;
                    } else if (board[k][j] == null) {
                        openEnds++;
                        break;
                    } else {
                        break;
                    }
                }
                if (openEnds > 0 && count > 1) {
                    segments.add(new Segment(count, openEnds));
                }

                // Check vertical
                count = 1;
                openEnds = 0;
                for (int k = j - 1; k >= 0; k--) {
                    if (board[i][k] == player) {
                        count++;
                    } else if (board[i][k] == null) {
                        openEnds++;
                        break;
                    } else {
                        break;
                    }
                }
                for (int k = j + 1; k < BOARD_SIZE; k++) {
                    if (board[i][k] == player) {
                        count++;
                    } else if (board[i][k] == null) {
                        openEnds++;
                        break;
                    } else {
                        break;
                    }
                }
                if (openEnds > 0 && count > 1) {
                    segments.add(new Segment(count, openEnds));
                }

                // Check diagonal
                count = 1;
                openEnds = 0;
                for (int k = i - 1, l = j - 1; k >= 0 && l >= 0; k--, l--) {
                    if (board[k][l] == player) {
                        count++;
                    } else if (board[k][l] == null) {
                        openEnds++;
                        break;
                    } else {
                        break;
                    }
                }
                for (int k = i + 1, l = j + 1; k < BOARD_SIZE && l < BOARD_SIZE; k++, l++) {
                    if (board[k][l] == player) {
                        count++;
                    } else if (board[k][l] == null) {
                        openEnds++;
                        break;
                    } else {
                        break;
                    }
                }
                if (openEnds > 0 && count > 1) {
                    segments.add(new Segment(count, openEnds));
                }

                // Check other diagonal
                count = 1;
                openEnds = 0;
                for (int k = i - 1, l = j + 1; k >= 0 && l < BOARD_SIZE; k--, l++) {
                    if (board[k][l] == player) {
                        count++;
                    } else if (board[k][l] == null) {
                        openEnds++;
                        break;
                    } else {
                        break;
                    }
                }
                for (int k = i + 1, l = j - 1; k < BOARD_SIZE && l >= 0; k++, l--) {
                    if (board[k][l] == player) {
                        count++;
                    } else if (board[k][l] == null) {
                        openEnds++;
                        break;
                    } else {
                        break;
                    }
                }
                if (openEnds > 0 && count > 1) {
                    segments.add(new Segment(count, openEnds));
                }
            }
        }
        return segments;
    }

    /*
     * Negative value => player is winning, positive value => computer is winning, 0
     * => draw
     */
    public int evaluate() {
        // Ideas:
        // * Maybe check how many "expandable" lines there are, one point for each dot
        // in each expandable line, if it can be expanded in both directions => double
        // points
        // *

        Collection<Segment> playerSegments = getSegments(Placement.PLAYER);

        // Find best player segment: most in a row and most open ends
        int maxLength = 0;
        int maxOpenEnds = 0;
        for (Segment segment : playerSegments) {
            if (segment.length() > maxLength) {
                maxLength = segment.length();
                maxOpenEnds = segment.openEnds();
            } else if (segment.length() == maxLength && segment.openEnds() > maxOpenEnds) {
                maxOpenEnds = segment.openEnds();
            }
        }

        Collection<Segment> computerSegments = getSegments(Placement.COMPUTER);

        // Find best computer segment: most in a row and most open ends
        int computerMaxLength = 0;
        int computerMaxOpenEnds = 0;
        for (Segment segment : computerSegments) {
            if (segment.length() > computerMaxLength) {
                computerMaxLength = segment.length();
                computerMaxOpenEnds = segment.openEnds();
            } else if (segment.length() == computerMaxLength && segment.openEnds() > computerMaxOpenEnds) {
                computerMaxOpenEnds = segment.openEnds();
            }
        }

        // TODO: Calculate a comparable value / indicator of who is winning
        return 0;
    }

    public MoveInfo immediateCompWin() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (isEmpty(i, j)) {
                    place(i, j, Placement.COMPUTER);
                    if (isVictoryMove(i, j)) {
                        unplace(i, j);
                        return new MoveInfo(i, j, MoveResult.COMPUTER_WIN);
                    }
                    unplace(i, j);
                }
            }
        }
        return null;
    }

    public MoveInfo immediatePlayerWin() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (isEmpty(i, j)) {
                    place(i, j, Placement.PLAYER);
                    if (isVictoryMove(i, j)) {
                        unplace(i, j);
                        return new MoveInfo(i, j, MoveResult.PLAYER_WIN);
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

    // TODO: Depth must be limited to N or it won't complete before the sun explodes
    // TODO: alpha/beta pruning
    public MoveInfo findCompMove(boolean firstCall) {
        MoveResult responseValue = MoveResult.PLAYER_WIN;
        int[] bestMove = { -1, -1 };

        if (isFull()) {
            return new MoveInfo(-1, -1, MoveResult.DRAW);
        }
        MoveInfo quickWin = immediateCompWin();
        if (quickWin != null) {
            return quickWin;
        }

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!isEmpty(i, j)) {
                    continue;
                }
                if (firstCall) {
                    System.out.println("Trying " + i + " " + j);
                }
                place(i, j, Placement.COMPUTER);
                // printBoard();
                responseValue = findPlayerMove().result();
                unplace(i, j);
                if (responseValue == MoveResult.COMPUTER_WIN) {
                    bestMove = new int[] { i, j };
                    responseValue = MoveResult.COMPUTER_WIN;
                    return new MoveInfo(bestMove[0], bestMove[1], responseValue);
                } else if (responseValue == MoveResult.DRAW) {
                    bestMove = new int[] { i, j };
                    responseValue = MoveResult.DRAW;
                } else if (bestMove[0] == -1) {
                    bestMove = new int[] { i, j };
                }
            }
        }
        return new MoveInfo(bestMove[0], bestMove[1], responseValue);
    }

    public MoveInfo findPlayerMove() {
        MoveResult responseValue = MoveResult.COMPUTER_WIN;
        int[] bestMove = { -1, -1 };

        if (isFull()) {
            return new MoveInfo(-1, -1, MoveResult.DRAW);
        }
        MoveInfo quickWin = immediatePlayerWin();
        if (quickWin != null) {
            return quickWin;
        }

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!isEmpty(i, j)) {
                    continue;
                }
                place(i, j, Placement.PLAYER);
                responseValue = findCompMove(false).result();
                unplace(i, j);
                if (responseValue == MoveResult.PLAYER_WIN) {
                    bestMove = new int[] { i, j };
                    responseValue = MoveResult.PLAYER_WIN;
                    return new MoveInfo(bestMove[0], bestMove[1], responseValue);
                } else if (responseValue == MoveResult.DRAW) {
                    bestMove = new int[] { i, j };
                    responseValue = MoveResult.DRAW;
                } else if (bestMove[0] == -1) {
                    bestMove = new int[] { i, j };
                }
            }
        }
        return new MoveInfo(bestMove[0], bestMove[1], responseValue);
    }

    public void printBoard() {
        System.out.println("---------------");
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[j][i] == null) {
                    System.out.print(" ");
                } else if (board[j][i] == Placement.PLAYER) {
                    System.out.print("X");
                } else {
                    System.out.print("O");
                }
            }
            System.out.println();
        }
        System.out.println("---------------");
    }
}