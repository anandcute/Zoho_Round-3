// package Sudako;

public class SudakoBoard {
    private int[][] board;
    final int INNER_DIMENSION = 3;

    SudakoBoard(int[][] board){
        this.board = board;
    }
    public void printBoard(){
        for(int i = 0; i < board.length; i++){
            if(i % INNER_DIMENSION == 0 ) System.out.println();
            for(int j = 0; j < board.length; j++){
                if(j % INNER_DIMENSION == 0) System.out.print(" ");
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean solve (int row , int column){
        if(row == board.length ){
            column++;
            if(column == board.length){
                return true;
            }
            else 
                row = 0;
        }
        if(board[row][column] != 0) return solve(row +1, column);

        for(int num = 1 ; num <= board.length ; num++){
            
            if(isValid(row,column,num))
            {
                board[row][column] = num;
                if(solve(row + 1 , column)) return true;
                board[row][column] = 0;
            }
        }
        return false;
    }
    public boolean isValid(int row , int col , int num){
        //colum check
        for(int i = 0 ; i < board.length; i++){
            if(board[i][col] == num) return false;
        }
        //chceck row
        for(int j = 0 ; j < board.length ; j++){
            if(board[row][j] == num) return false;
        }

        //check if num in 3x3 matrix
        int startRow = (row / INNER_DIMENSION) * INNER_DIMENSION;
        int startcol = (col / INNER_DIMENSION) * INNER_DIMENSION;
        for(int i = startRow ; i < startRow + INNER_DIMENSION ; i++ ){
            for(int j = startcol ; j < startcol + INNER_DIMENSION ; j++){
                if(board[i][j] == num) return false;
            }    
        }
        return true;
        
    }
}
