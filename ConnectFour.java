import java.util.*;

public class ConnectFour extends AbstractStrategyGame{
    private char[][] board;
    private boolean isPlayerOneTurn;

    public ConnectFour () {
        board = new char[][] {{'-', '-', '-', '-', '-', '-', '-'}, 
                              {'-', '-', '-', '-', '-', '-', '-'},
                              {'-', '-', '-', '-', '-', '-', '-'},
                              {'-', '-', '-', '-', '-', '-', '-'},
                              {'-', '-', '-', '-', '-', '-', '-'},
                              {'-', '-', '-', '-', '-', '-', '-'}};
        isPlayerOneTurn = true;
    }

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

    public int getNextPlayer() {
        if (isGameOver()) {
            return -1;
        }
        return isPlayerOneTurn ? 1 : 2;
    }

    public void makeMove(Scanner input) {
        char currentPlayerColor;
        if (isPlayerOneTurn) {
            currentPlayerColor = 'W';
        } else {
            currentPlayerColor = 'B';
        }
        System.out.print("Column: ");
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
