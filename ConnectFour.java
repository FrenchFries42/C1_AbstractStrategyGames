import java.util.*;

// Jeffrey Tso
// 10/16/2024
// CSE 123
// Creative Project 1: Abstract Strategy Games
// Sean Eglip

// This class extends the abstract class AbstractStrategyGame
// to simulate a game of Connect Four.

public class ConnectFour extends AbstractStrategyGame{
    private char[][] board;
    private boolean isPlayerOneTurn;

    // This constructor initializes the board with a 7x6 2D char array that contains '-' to 
    // represent the empty spaces. It also initializes the game with player one going first.
    public ConnectFour () {
        board = new char[][] {{'-', '-', '-', '-', '-', '-', '-'}, 
                              {'-', '-', '-', '-', '-', '-', '-'},
                              {'-', '-', '-', '-', '-', '-', '-'},
                              {'-', '-', '-', '-', '-', '-', '-'},
                              {'-', '-', '-', '-', '-', '-', '-'},
                              {'-', '-', '-', '-', '-', '-', '-'}};
        isPlayerOneTurn = true;
    }

    // Returns a String of instructions on how to play the game.
    public String instructions() {
        String result = "";
        result += "Two players takes turns placing tokens of their color into 1 of 7 columns.\n";
        result += "The token is placed in the bottom most available space in the column.\n";
        result += "If the column is full and the bottom token is of their color, the player \n";
        result += "can choose to remove that token from the column instead.\n";
        result += "The first player to get four tokens of their color in a row, whether that \n";
        result += "be horizontally, vertically, or diagonally, wins the game.";
        result += "Player 1 has black tokens, represented by 'B' on the board; player 2 has \n";
        result += "white tokens represented by 'W'. Empty squares are represented by '-'.";
        return result;
    }

    // Behavior:
    //      - Gets the winner (if any) at any certain state of the game.
    // Return:
    //      - Returns 1 if player one won, 2 if player two won, or -1 if the game isn't over yet.
    public int getWinner() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != '-' && checkToken(j, i)) {
                    if (board[i][j] == 'W') {
                        return 1;
                    } else {
                        return 2;
                    }
                    
                }
            }
        }
        return -1;
    }

    // Behavior:
    //      - Private helper method to check around a certain token for connect fours.
    // Return:
    //      - Returns true if there is a connect four for the given token, false if not.
    // Parameters:
    //      - Takes in an x and y int to mark the current token's board position.
    private boolean checkToken(int x, int y) {
        char currentToken = board[y][x];

        boolean iterating = true;
        int i = 1;
        // Check rightwards
        if (x <= 3) {
            while (iterating && i < 4) {
                if (currentToken != board[y][x + i]) {
                    iterating = false;
                } else {
                    i++;
                }
            }
            if (i == 4) {
                return true;
            } else {
                i = 1;
                iterating = true;
            }
        }
        // Check leftwards
        if (x >= 3) {
            while (iterating && i < 4) {
                if (currentToken != board[y][x - i]) {
                    iterating = false;
                } else {
                    i++;
                }
            }
            if (i == 4) {
                return true;
            } else {
                i = 1;
                iterating = true;
            }
        }
        // Check downwards
        if (y <= 2) {
            while (iterating && i < 4) {
                if (currentToken != board[y + i][x]) {
                    iterating = false;
                } else {
                    i++;
                }
            }
            if (i == 4) {
                return true;
            } else {
                i = 1;
                iterating = true;
            }
        }
        // Check upwards
        if (y >= 3) {
            while (iterating && i < 4) {
                if (currentToken != board[y - i][x]) {
                    iterating = false;
                } else {
                    i++;
                }
            }
            if (i == 4) {
                return true;
            } else {
                i = 1;
                iterating = true;
            }
        }
        // Check bottom rightwards
        if (y <= 2 && x <= 3) {
            while (iterating && i < 4) {
                if (currentToken != board[y + i][x + i]) {
                    iterating = false;
                } else {
                    i++;
                }
            }
            if (i == 4) {
                return true;
            } else {
                i = 1;
                iterating = true;
            }
        }
        // Check bottom leftwards
        if (y <= 2 && x >= 3) {
            while (iterating && i < 4) {
                if (currentToken != board[y + i][x - i]) {
                    iterating = false;
                } else {
                    i++;
                }
            }
            if (i == 4) {
                return true;
            } else {
                i = 1;
                iterating = true;
            }
        }
        // Check top rightwards
        if (y >= 3 && x <= 3) {
            while (iterating && i < 4) {
                if (currentToken != board[y - i][x + i]) {
                    iterating = false;
                } else {
                    i++;
                }
            }
            if (i == 4) {
                return true;
            } else {
                i = 1;
                iterating = true;
            }
        }
        // Check top leftwards
        if (y >= 3 && x >= 3) {
            while (iterating && i < 4) {
                if (currentToken != board[y - i][x - i]) {
                    iterating = false;
                } else {
                    i++;
                }
            }
            if (i == 4) {
                return true;
            } else {
                i = 1;
                iterating = true;
            }
        }

        return false;
    }

    // Behavior:
    //      - Gets the next player's turn.
    // Return:
    //      - Returns 1 if its player one's turn, 2 if player two, and -1 if the game is over.
    public int getNextPlayer() {
        if (isGameOver()) {
            return -1;
        }
        return isPlayerOneTurn ? 1 : 2;
    }

    // Behavior:
    //      - Makes a move on the board given a column input. Adds a token of the
    //      - current player's color in the bottommost empty row of the column. If the column is
    //      - full and the very bottom row contains a token of their color, the bottom token is 
    //      - removed instead.
    // Exceptions:
    //      - Throws an IllegalArgumentException if the column input is any of the following:
    //      - not between 1 and 7, is a non-integer, or the column is full AND the
    //      - bottom row token is not of their color.
    // Parameter:
    //      - Takes in a scanner to read the players column input.
    public void makeMove(Scanner input) {
        char currentPlayerColor;
        if (isPlayerOneTurn) {
            currentPlayerColor = 'W';
        } else {
            currentPlayerColor = 'B';
        }
        System.out.print("Column: ");
        if (!input.hasNextInt()) {
            input.next();
            throw new IllegalArgumentException("Please enter a single integer.");
        }
        int columnIndex = input.nextInt() - 1;
        if (columnIndex < 0 || columnIndex > 6) {
            throw new IllegalArgumentException("Please enter a number between 1-7.");
        } else if (board[0][columnIndex] != '-' && 
                board[5][columnIndex] != currentPlayerColor) {
            throw new IllegalArgumentException("Cannot choose this column. " + 
                                                    "Please select another column.");
        }

        if (board[0][columnIndex] != '-') {
            for (int i = 5; i > 0; i--) {
                board[i][columnIndex] = board[i-1][columnIndex];
            }
            board[0][columnIndex] = '-';
        } else {
            int rowIndex = 0;
            while (rowIndex != 5 && board[rowIndex + 1][columnIndex] == '-') {
                rowIndex++;
            }

            board[rowIndex][columnIndex] = currentPlayerColor;
        }

        isPlayerOneTurn = !isPlayerOneTurn;

    }

    // Returns a string representation of the current board's state. The string consists of
    // a 7x6 2D char array where '-' represents empty spaces, 'W' represents
    // player one's tokens, and 'B' represents player two's tokens.
    public String toString() {
        String result = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                result += board[i][j] + " ";
            }
            result += "\n";
        }
        return result;
    }
}
