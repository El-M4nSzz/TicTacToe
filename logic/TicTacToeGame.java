package logic;

public class TicTacToeGame extends GameBase { 

    public boolean placeMove(int row, int col) {
        if (board[row][col] == '\u0000') {
            board[row][col] = currentPlayer;
            return true;
        }
        return false;
    }

    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '\u0000')
                    return false;
        return true;
    }    

    public String checkWinner() {
        for (int i = 0; i < 3; i++)
            if (board[i][0] != '\u0000' && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                return String.valueOf(board[i][0]);

        for (int i = 0; i < 3; i++)
            if (board[0][i] != '\u0000' && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                return String.valueOf(board[0][i]);

        if (board[0][0] != '\u0000' && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            return String.valueOf(board[0][0]);
        if (board[0][2] != '\u0000' && board[0][2] == board[1][1] && board[1][1] == board[2][0])
            return String.valueOf(board[0][2]);

        return null;
    }
}

