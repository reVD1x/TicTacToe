import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TicTacToeGUI extends JFrame {
    private final TicTacToeBoard board;
    private final JButton[][] buttons = new JButton[3][3];
    private char currentPlayer;
    private final int gameType; //游戏模式(0:PVE 1:PVP)
    private final int gameLevel; //游戏难度(0:PVP 1:easy 2:middle 3:hard)
    private AIMove ai;

    public TicTacToeGUI(int gameType, int gameLevel) {
        this.gameType = gameType;
        this.gameLevel = gameLevel;
        board = new TicTacToeBoard();
        currentPlayer = 'X';//最开始的玩家为X

        setTitle("井字棋");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));
        setVisible(true);

        //初始化按钮
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 50));
                button.addActionListener(new ButtonClickListener(i, j));
                add(button);
                buttons[i][j] = button;
            }
        }
        displayBoard();
    }

    void displayBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //将board棋盘上的每个元素对应到相应位置上的button
                char value = board.getBoardValue(i, j);
                if (value == 'X' || value == 'O') {
                    buttons[i][j].setText(String.valueOf(value));
                    buttons[i][j].setEnabled(false);
                } else {
                    buttons[i][j].setText("");
                }
            }
        }
    }

    private class ButtonClickListener implements ActionListener {
        private final int row;
        private final int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {//点击事件
            if (board.makeMove(row, col)) {//玩家向棋盘中下棋
                buttons[row][col].setText(String.valueOf(currentPlayer));
                buttons[row][col].setEnabled(false);
                if (!hasWinner() && !isDraw()) {
                    //交换玩家
                    if (gameType == 1)
                        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                    if (gameType == 0) {//如果是PVE 则电脑下棋
                        AIMove.makeBestMove(board);
                        displayBoard();
                        //判断游戏是否结束
                        //将当前玩家切换为 O 判断是否胜利
                        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                        isDraw();
                        hasWinner();
                        //判断完毕，将当前玩家切换回 X
                        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                    }
                }
            }
        }
    }

    private boolean hasWinner() {//判断是否胜利
        if (board.checkWinner() != '-') {
            int option = JOptionPane.showOptionDialog(this, currentPlayer + " wins!", "选择下一步选项",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, new Object[]{"退出", "再玩一次"}, "再玩一次");
            if (option == JOptionPane.YES_NO_OPTION)
                System.exit(0);//退出程序
            else resetGame();//重来
            return true;
        }
        return false;
    }

    private boolean isDraw() {//判断是否平局
        if (board.isBoardFull()) {
            int option = JOptionPane.showOptionDialog(this, "平局!", "选择下一步选项",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, new Object[]{"退出", "再玩一次"}, "再玩一次");
            if (option == JOptionPane.YES_NO_OPTION)
                System.exit(0);//退出程序
            else resetGame();//重来
            return true;
        }
        return false;
    }

    private void checkWinnerOrDraw() {
        if (hasWinner() || isDraw()) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setEnabled(false);
                }
            }
        }
    }

    private void resetGame() {
        //重新开始游戏
        //清空棋盘
        board.clearBoard();
        dispose();//关闭当前窗口
        new StartFrame();
    }
}


