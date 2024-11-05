package TicTacToe;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI {
    private static TicTacToeGUI instance;  // 单例模式实现，保证只有一个实例存在
    private final JFrame frame;  // 游戏窗口
    private final JButton[][] buttons = new JButton[3][3];
    private AIMove ai;
    private Clip clickSound;

    private final int gameType; //游戏模式
    private final TicTacToeBoard board;

    // 构造方法
    private TicTacToeGUI(int gameType, int gameLevel) {
        this.gameType = gameType;
        board = new TicTacToeBoard();
        board.currentPlayer = 'X';  // 初始玩家为 X

        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        // 游戏面板，包含 9 个格子
        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        frame.add(gamePanel);

        //游戏难度(0:PVP 1:easy 2:middle 3:hard)
        if (gameType == 1)
            ai = new AIMove(board, gameLevel);

        // 初始化按钮
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 50));
                button.addActionListener(new ButtonClickListener(i, j));
                gamePanel.add(button);
                buttons[i][j] = button;
            }
        }

        // 加载点击音效
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(getClass().getResource("resources/Click.wav"));
            clickSound = AudioSystem.getClip();
            clickSound.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateGamePanel(board);
        showGame();
    }

    // 获取实例
    public static TicTacToeGUI getInstance(int gameType, int gameLevel) {
        if (instance == null)
            instance = new TicTacToeGUI(gameType, gameLevel);
        return instance;
    }

    // 显示游戏窗口
    public void showGame() {
        frame.setVisible(true);
    }

    // 更新游戏面板
    public void updateGamePanel(TicTacToeBoard board) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                char value = board.getBoardValue(i, j);

                if (value == 'X' || value == 'O') {
                    buttons[i][j].setText(String.valueOf(value));
                    buttons[i][j].setEnabled(false);
                } else {
                    buttons[i][j].setText("");
                    buttons[i][j].setEnabled(true);
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
            playClickSound();

            if (board.makeMove(row, col)) {//玩家向棋盘中下棋
                updateGamePanel(board);

                if (hasWinner() || isDraw())
                    return;
                board.currentPlayer = (board.currentPlayer == 'X') ? 'O' : 'X';  // 交换玩家

                // 如果是 PVE 则电脑下棋
                if (gameType == 1) {
                    ai.move();
                    updateGamePanel(board);
                    if (hasWinner() || isDraw())  // 判断游戏是否结束
                        return;
                    board.currentPlayer = (board.currentPlayer == 'X') ? 'O' : 'X';  // 交换玩家
                }
            }
        }

        private boolean hasWinner() {//判断是否胜利
            char winner = board.checkWinner();
            if (winner != '-') {
                if (gameType == 2) {
                    if (winner == 'X')
                        TicTacToeGame.xWins++;
                    else if (winner == 'O')
                        TicTacToeGame.oWins++;
                } else if (gameType == 1) {
                    if (ai.gameLevel == 1) {
                        if (winner == 'X')
                            TicTacToeGame.easyWins++;
                        else if (winner == 'O')
                            TicTacToeGame.easyLosses++;
                    } else if (ai.gameLevel == 2) {
                        if (winner == 'X')
                            TicTacToeGame.mediumWins++;
                        else if (winner == 'O')
                            TicTacToeGame.mediumLosses++;
                    } else if (ai.gameLevel == 3) {
                        if (winner == 'X')
                            TicTacToeGame.hardWins++;
                        else if (winner == 'O')
                            TicTacToeGame.hardLosses++;
                    }
                }

                int option = JOptionPane.showOptionDialog(frame, winner + " wins!", "选择下一步选项",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, new Object[]{"返回", "再玩一次"}, "再玩一次");
                if (option == JOptionPane.YES_NO_OPTION) {
                    playClickSound();
                    returnToMainMenu();//返回主菜单
                } else{
                    playClickSound();
                    resetGame();//重来
                }
                return true;
            }
            return false;
        }

        private boolean isDraw() {//判断是否平局
            if (board.isBoardFull()) {
                if (gameType == 2)
                    TicTacToeGame.draws++;
                else if (gameType == 1) {
                    if (ai.gameLevel == 1)
                        TicTacToeGame.easyDraws++;
                    else if (ai.gameLevel == 2)
                        TicTacToeGame.mediumDraws++;
                    else if (ai.gameLevel == 3)
                        TicTacToeGame.hardDraws++;
                }

                int option = JOptionPane.showOptionDialog(frame, "平局!", "选择下一步选项",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, new Object[]{"返回", "再玩一次"}, "再玩一次");
                if (option == JOptionPane.YES_NO_OPTION)
                    returnToMainMenu();//返回主菜单
                else resetGame();//重来
                return true;
            }
            return false;
        }

        // 重新开始游戏
        private void resetGame() {
            board.clearBoard();  // 清空棋盘
            updateGamePanel(board);
            board.currentPlayer = 'X'; // 重置当前玩家
        }

        private void returnToMainMenu() {
            frame.dispose(); // 关闭当前游戏窗口
            instance=null;  // 释放实例
            TicTacToeGame.main(new String[]{}); // 重新启动主菜单
        }
    }

    private void playClickSound() {
        if (clickSound != null && clickSound.isRunning())
            clickSound.stop(); // 停止正在播放的音效
        if (clickSound != null) {
            clickSound.setFramePosition(0); // 重置音效位置
            clickSound.start(); // 播放音效
        }
    }
}

