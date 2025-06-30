package players;

public class ComputerPlayer extends Player {
    public ComputerPlayer(String name, char symbol) {
        super(name, symbol);
    }

    public int[] getMove(char[][] board) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '\u0000')
                    return new int[]{i, j};
        return null;
    }

    public int[] getMove() {
        return null; // Tidak digunakan
    }
}
