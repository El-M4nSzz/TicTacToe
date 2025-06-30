package players;

public abstract class Player {
    protected String name;
    protected char symbol;

    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public abstract int[] getMove();

    public char getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }
}
