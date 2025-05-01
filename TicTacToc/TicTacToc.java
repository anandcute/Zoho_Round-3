// package TicTacToc;

import java.util.Scanner;

public class TicTacToc {
    static char[][] board = {
        {' ' , ' ', ' '},
        {' ' , ' ', ' '},
        {' ' , ' ', ' '}
    };

    static char currentPlayer = 'X'; 

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean gameEnd = false;
        System.out.println("Welcome to the TIC TAC TOC......!");
        printBoard();

        
        while (!gameEnd) {
            System.out.println("print the move on the board row and col (0 1) : ");
            int row = sc.nextInt();
            int col = sc.nextInt();

            if(isValidate(row, col)){
                board[row][col] = currentPlayer;
                printBoard();
                if (checkWin()) {
                    System.out.println("Player " + currentPlayer + " wins!");
                    gameEnd = true;
                } else if (isBoardFull()) {
                    System.out.println("It's a draw!");
                    gameEnd = true;
                } else {
                    switchPlayer();
                }
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
        sc.close();

    }
    public static void printBoard(){
        System.out.println("----------------");

        for(int i = 0 ; i < 3 ; i++){
            System.out.print(" | ");
            for(int j = 0 ; j < 3 ; j++){
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n---------------");
        }
    }
    public static boolean isValidate(int row , int col){
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ';
    }
    
    static void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    static boolean checkWin() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer &&
                board[i][1] == currentPlayer &&
                board[i][2] == currentPlayer) return true;

            if (board[0][i] == currentPlayer &&
                board[1][i] == currentPlayer &&
                board[2][i] == currentPlayer) return true;
        }

        // Check diagonals
        if (board[0][0] == currentPlayer &&
            board[1][1] == currentPlayer &&
            board[2][2] == currentPlayer) return true;

        if (board[0][2] == currentPlayer &&
            board[1][1] == currentPlayer &&
            board[2][0] == currentPlayer) return true;

        return false;
    }

    static boolean isBoardFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ') return false;
        return true;
    }
}
