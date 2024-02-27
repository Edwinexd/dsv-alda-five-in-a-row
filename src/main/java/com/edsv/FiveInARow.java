package com.edsv;

public class FiveInARow {
    private Placement[][] board = new Placement[15][15];
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
        // Check horizontal
        int count = 1;
        for (int i = x - 1; i >= 0; i--) {
            if (board[i][y] == placement) {
                count++;
            } else {
                break;
            }
        }
        for (int i = x + 1; i < 15; i++) {
            if (board[i][y] == placement) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
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
        for (int i = y + 1; i < 15; i++) {
            if (board[x][i] == placement) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
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
        for (int i = x + 1, j = y + 1; i < 15 && j < 15; i++, j++) {
            if (board[i][j] == placement) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) {
            return true;
        }

        // Check other diagonal
        count = 1;
        for (int i = x - 1, j = y + 1; i >= 0 && j < 15; i--, j++) {
            if (board[i][j] == placement) {
                count++;
            } else {
                break;
            }
        }
        for (int i = x + 1, j = y - 1; i < 15 && j >= 0; i++, j--) {
            if (board[i][j] == placement) {
                count++;
            } else {
                break;
            }
        }
        return count >= 5;
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

    public MoveInfo immediateCompWin() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
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
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
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
}