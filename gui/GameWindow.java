package gui; //menandakan file ini berada dalam package gui (struktur modular Java)

//import package dan class yang digunakan (Modul 1)
import java.awt.*;
import javax.swing.*;
import logic.TicTacToeGame;
import players.ComputerPlayer;
import players.HumanPlayer;  //komponen GUI Swing (Modul 1)
import players.Player;     //layout dan komponen GUI (Modul 1)

//gameWindow adalah class utama yang menangani GUI permainan (Modul 2)
public class GameWindow extends JFrame {

    // ===== Deklarasi Variabel GUI & Game State =====
    private JButton[][] buttons = new JButton[3][3]; //tombol papan Tic Tac Toe (Modul 1)
    private JLabel statusLabel; //label untuk giliran pemain
    private JLabel player1Label, player2Label; //label nama pemain
    private JLabel player1Stats, player2Stats; //label skor menang/kalah
    private JButton newGameBtn, resetBtn, exitBtn; //tombol kontrol permainan
    private TicTacToeGame game; //objek logika permainan (Modul 2)
    private Player p1, p2; //pemain 1 dan 2 (Modul 2, 5)
    private boolean vsComputer = false; //menyimpan mode permainan
    private int p1Wins = 0, p2Wins = 0, p1Losses = 0, p2Losses = 0; //statistik skor

    //konstruktor utama - memanggil setupUI (Modul 2)
    public GameWindow() {
        setupUI();
    }

    // ===== Membuat Tampilan GUI Permainan ===== (Modul 1: GUI dengan Swing)
    private void setupUI() {
        game = new TicTacToeGame(); //membuat ulang papan permainan

        setTitle("Tic Tac Toe Kelompok 6");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //panel atas untuk info pemain
        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        player1Label = new JLabel("Player 1 (X): ");
        player2Label = new JLabel("Player 2 (O): ");
        player1Stats = new JLabel("Wins: 0 | Losses: 0");
        player2Stats = new JLabel("Wins: 0 | Losses: 0");
        topPanel.add(player1Label);
        topPanel.add(player2Label);
        topPanel.add(player1Stats);
        topPanel.add(player2Stats);
        add(topPanel, BorderLayout.NORTH);

        //panel tengah: papan permainan 3x3
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton(""); //membuat setiap sel
                final int row = i, col = j; //variabel final untuk lambda (event)
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                buttons[i][j].addActionListener(e -> handleMove(row, col)); //menangani klik (Modul 7)
                boardPanel.add(buttons[i][j]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        //panel bawah: tombol kontrol + status
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        newGameBtn = new JButton("New Game");
        resetBtn = new JButton("Reset");
        exitBtn = new JButton("Exit");
        buttonPanel.add(newGameBtn);
        buttonPanel.add(resetBtn);
        buttonPanel.add(exitBtn);

        statusLabel = new JLabel("Player X's turn", SwingConstants.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        //event Listener tombol kontrol (Modul 1 & 2)
        newGameBtn.addActionListener(e -> newGame());
        resetBtn.addActionListener(e -> resetBoard());
        exitBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
        newGame();
    }

    
    // ===== Menangani Klik Tombol pada Papan =====
    private void handleMove(int row, int col) {
        try {
            if (game.placeMove(row, col)) { //menaruh simbol pada papan (Modul 2)
                buttons[row][col].setText(String.valueOf(game.getCurrentPlayerSymbol()));
                String winner = game.checkWinner(); //mengecek pemenang (Modul 2)
                if (winner != null) {
                    endGame(winner);
                    return;
                } else if (game.isBoardFull()) {
                    endDraw(); 
                    return;
                }
                game.switchTurn(); //modul 2
                statusLabel.setText("Player " + game.getCurrentPlayerSymbol() + "'s turn");
                if (vsComputer && game.getCurrentPlayerSymbol() == 'O') {
                    aiMove(); //jika komputer, jalankan langkah AI (Modul 2, 5)
                }
            } else {
                throw new Exception("Invalid move! Cell already occupied."); //modul 7
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); //modul 7
        }
    }

    // ===== Langkah AI Otomatis ===== (Modul 5 & 4: inheritance + polymorphism)
    private void aiMove() {
        int[] move = ((ComputerPlayer) p2).getMove(game.getBoard()); //memanggil AI sederhana
        if (move != null) {
            handleMove(move[0], move[1]); //lakukan langkah AI
        }
    }

    // ===== Selesaikan Game & Update Statistik =====
    private void endGame(String winner) {
        String winnerName = "Unknown";
        if (p1 != null && p2 != null) {
            winnerName = winner.equals("X") ? p1.getName() : p2.getName();
        }
        JOptionPane.showMessageDialog(
            this,
            "Pemenang: " + winnerName + " (" + winner + ")",
            "Game Over",
            JOptionPane.INFORMATION_MESSAGE
        );
        statusLabel.setText("Winner: " + winner);
        if (winner.equals("X")) {
            p1Wins++; p2Losses++;
        } else {
            p2Wins++; p1Losses++;
        }
        updateStats(); //tampilkan skor
        disableButtons(); //nonaktifkan papan
    }

    private void endDraw() { //fungsi jika permainan draw
        JOptionPane.showMessageDialog(this, "Permainan seri! Tidak ada pemenang.", "Hasil", JOptionPane.INFORMATION_MESSAGE);
        statusLabel.setText("Draw! No winner.");
        disableButtons();
    }

    // ===== Nonaktifkan Semua Tombol =====
    private void disableButtons() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                buttons[i][j].setEnabled(false);
    }

    // ===== Reset Papan (tanpa ubah nama/skor) =====
    private void resetBoard() {
        game = new TicTacToeGame(); //buat ulang papan kosong
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        statusLabel.setText("Player X's turn");
    }

    // ===== Game Baru: Pilih Mode, Masukkan Nama =====
    private void newGame() {
        Object[] options = {"Player vs Player", "Player vs Computer"};
        int mode = JOptionPane.showOptionDialog(this, "Pilih mode permainan:", "Mode",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        vsComputer = (mode == 1); //true jika mode komputer

        //input nama pemain
        String name1 = JOptionPane.showInputDialog(this, "Masukkan nama Player 1 (X):");
        p1 = new HumanPlayer(name1 != null ? name1 : "Player 1", 'X');

        if (vsComputer) {
            p2 = new ComputerPlayer("Komputer", 'O');
        } else {
            String name2 = JOptionPane.showInputDialog(this, "Masukkan nama Player 2 (O):");
            p2 = new HumanPlayer(name2 != null ? name2 : "Player 2", 'O');
        }

        //reset skor dan update label
        p1Wins = p2Wins = p1Losses = p2Losses = 0;
        player1Label.setText("Player 1 (X): " + p1.getName());
        player2Label.setText("Player 2 (O): " + p2.getName());
        updateStats();
        resetBoard();
    }

    // ===== Update Statistik Menang/Kalah =====
    private void updateStats() {
        player1Stats.setText("Wins: " + p1Wins + " | Losses: " + p1Losses);
        player2Stats.setText("Wins: " + p2Wins + " | Losses: " + p2Losses);
    }
}
