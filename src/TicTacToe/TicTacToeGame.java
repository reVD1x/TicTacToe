package TicTacToe;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("井字棋");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            addModeSelectionPanel(frame);
            frame.setVisible(true);
        });
    }

    private static void addModeSelectionPanel(JFrame frame) {
        JPanel modePanel = new JPanel();
        frame.add(modePanel);

        JLabel modeLabel = new JLabel("选择模式");
        modePanel.add(modeLabel);

        JRadioButton singlePlayer = new JRadioButton("单人模式");
        JRadioButton multiPlayer = new JRadioButton("双人模式");
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(singlePlayer);
        modeGroup.add(multiPlayer);

        modePanel.add(singlePlayer);
        modePanel.add(multiPlayer);

        JButton nextButton = new JButton("下一步");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (singlePlayer.isSelected()) {
                    showDifficultySelection(frame);
                } else if (multiPlayer.isSelected()) {
                    startGame(2, 0); // 双人模式，难度为0
                    frame.dispose(); // 关闭选择窗口
                }
            }
        });
        modePanel.add(nextButton);

        JButton statsButton = new JButton("数据统计");
        statsButton.addActionListener(e -> showStatisticsPage(frame));
        modePanel.add(statsButton);

        JButton exitButton = new JButton("退出");
        exitButton.addActionListener(e -> System.exit(0));
        modePanel.add(exitButton);
    }

    private static void showStatisticsPage(JFrame frame) {
        frame.getContentPane().removeAll();

        JPanel statsPanel = new JPanel();
        frame.add(statsPanel);

        JLabel statsLabel = new JLabel("数据统计");
        statsPanel.add(statsLabel);

        // 假设的数据统计
        JLabel gamesPlayedLabel = new JLabel("游戏次数: 10");
        JLabel winsLabel = new JLabel("胜利次数: 5");
        JLabel lossesLabel = new JLabel("失败次数: 3");
        JLabel drawsLabel = new JLabel("平局次数: 2");

        statsPanel.add(gamesPlayedLabel);
        statsPanel.add(winsLabel);
        statsPanel.add(lossesLabel);
        statsPanel.add(drawsLabel);

        JButton backButton = new JButton("返回");
        backButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            addModeSelectionPanel(frame);
            frame.revalidate();
            frame.repaint();
        });
        statsPanel.add(backButton);

        JButton exitButton = new JButton("退出");
        exitButton.addActionListener(e -> System.exit(0));
        statsPanel.add(exitButton);

        frame.revalidate();
        frame.repaint();
    }

    private static void showDifficultySelection(JFrame frame) {
        frame.getContentPane().removeAll();

        JPanel difficultyPanel = new JPanel();
        frame.add(difficultyPanel);

        JLabel difficultyLabel = new JLabel("选择难度");
        difficultyPanel.add(difficultyLabel);

        JRadioButton easy = new JRadioButton("简单");
        JRadioButton medium = new JRadioButton("中等");
        JRadioButton hard = new JRadioButton("困难");
        ButtonGroup difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easy);
        difficultyGroup.add(medium);
        difficultyGroup.add(hard);

        difficultyPanel.add(easy);
        difficultyPanel.add(medium);
        difficultyPanel.add(hard);

        JButton startButton = new JButton("开始游戏");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int difficulty = 0;
                if (easy.isSelected()) {
                    difficulty = 1;
                } else if (medium.isSelected()) {
                    difficulty = 2;
                } else if (hard.isSelected()) {
                    difficulty = 3;
                }

                startGame(1, difficulty); // 单人模式
                frame.dispose(); // 关闭选择窗口
            }
        });

        difficultyPanel.add(startButton);

        frame.revalidate();
        frame.repaint();
    }

    private static void startGame(int mode, int difficulty) {
        TicTacToeGUI gui= TicTacToeGUI.getInstance(mode, difficulty);
        gui.showGame();
    }
}
