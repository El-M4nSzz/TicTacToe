package logic;

public abstract class GameBase implements GameRules {//modul 6
    protected char[][] board;
    protected char currentPlayer = 'X';

    public GameBase() {
        board = new char[3][3];
    }

    public void switchTurn() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public char getCurrentPlayerSymbol() {
        return currentPlayer;
    }

    public char[][] getBoard() {
        return board;
    }
}
