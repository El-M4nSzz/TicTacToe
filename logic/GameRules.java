package logic;

public interface GameRules {
    boolean placeMove(int row, int col);
    String checkWinner();
    void switchTurn();
    char getCurrentPlayerSymbol();
}
