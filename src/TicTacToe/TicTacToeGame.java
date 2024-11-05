package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGame {
    public static int xWins = 0;
    public static int oWins = 0;
    public static int draws = 0;

    public static int easyWins = 0;
    public static int easyLosses = 0;
    public static int easyDraws = 0;

    public static int mediumWins = 0;
    public static int mediumLosses = 0;
    public static int mediumDraws = 0;

    public static int hardWins = 0;
    public static int hardLosses = 0;
    public static int hardDraws = 0;

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
        JPanel modePanel = new JPanel(new GridLayout(4, 1, 5, 5));
        frame.add(modePanel);

        JLabel modeLabel = new JLabel("选择模式", SwingConstants.CENTER);
        modeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        modePanel.add(modeLabel);

        JPanel radioPanel = new JPanel(new GridLayout(1, 2, 5, 5)); // 1行2列，组件之间有5像素的间距
        JRadioButton singlePlayer = new JRadioButton("单人模式");
        JRadioButton multiPlayer = new JRadioButton("双人模式");
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(singlePlayer);
        modeGroup.add(multiPlayer);
        radioPanel.add(singlePlayer);
        radioPanel.add(multiPlayer);
        modePanel.add(radioPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5)); // 1行3列的网格布局
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
        buttonPanel.add(nextButton);

        JButton statsButton = new JButton("数据统计");
        statsButton.addActionListener(e -> showStatisticsPage(frame));
        buttonPanel.add(statsButton);

        JButton exitButton = new JButton("退出");
        exitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitButton);

        modePanel.add(buttonPanel);
    }

    private static void showStatisticsPage(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setSize(300, 400);

        JPanel statsPanel = new JPanel(new GridLayout(11, 1, 5, 5));
        frame.add(statsPanel);

        JLabel statsLabel = new JLabel("数据统计", SwingConstants.CENTER);
        statsLabel.setFont(new Font("Arial", Font.BOLD, 18)); // 加大加粗标题
        statsPanel.add(statsLabel);

        // 假设的数据统计
        JLabel multiPlayerLabel = new JLabel("双人对战:");
        statsPanel.add(multiPlayerLabel);
        JPanel multiPlayerStatsPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        JLabel xWinsLabel = new JLabel("X 胜利次数: " + xWins);
        multiPlayerStatsPanel.add(xWinsLabel);
        JLabel oWinsLabel = new JLabel("O 胜利次数: " + oWins);
        multiPlayerStatsPanel.add(oWinsLabel);
        JLabel drawsLabel = new JLabel("平局次数: " + draws);
        multiPlayerStatsPanel.add(drawsLabel);
        statsPanel.add(multiPlayerStatsPanel);

        JLabel easyLabel = new JLabel("单人对战 - 简单: ");
        singlePlayerStatsDisplay(statsPanel, easyLabel, easyWins, easyLosses, easyDraws);

        JLabel mediumLabel = new JLabel("单人对战 - 中等: ");
        singlePlayerStatsDisplay(statsPanel, mediumLabel, mediumWins, mediumLosses, mediumDraws);

        JLabel hardLabel = new JLabel("单人对战 - 困难: ");
        singlePlayerStatsDisplay(statsPanel, hardLabel, hardWins, hardLosses, hardDraws);

        JButton backButton = new JButton("返回");
        backButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.setSize(300, 200);
            addModeSelectionPanel(frame);
            frame.revalidate();
            frame.repaint();
        });
        statsPanel.add(backButton);

        frame.revalidate();
        frame.repaint();
    }

    private static void singlePlayerStatsDisplay(JPanel statsPanel, JLabel easyLabel, int easyWins, int easyLosses, int easyDraws) {
        statsPanel.add(easyLabel);
        JPanel easyStatsPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        JLabel easyWinsLabel = new JLabel("胜利次数: " + easyWins);
        easyStatsPanel.add(easyWinsLabel);
        JLabel easyLossesLabel = new JLabel("失败次数: " + easyLosses);
        easyStatsPanel.add(easyLossesLabel);
        JLabel easyDrawsLabel = new JLabel("平局次数: " + easyDraws);
        easyStatsPanel.add(easyDrawsLabel);
        statsPanel.add(easyStatsPanel);
    }

    private static void showDifficultySelection(JFrame frame) {
        frame.getContentPane().removeAll();

        JPanel difficultyPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        frame.add(difficultyPanel);

        JLabel difficultyLabel = new JLabel("选择难度", SwingConstants.CENTER);
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 18));
        difficultyPanel.add(difficultyLabel);

        JPanel radioPanel = new JPanel(new GridLayout(1, 3, 5, 5)); // 1行3列，组件之间有5像素的间距
        JRadioButton easy = new JRadioButton("简单");
        JRadioButton medium = new JRadioButton("中等");
        JRadioButton hard = new JRadioButton("困难");
        ButtonGroup difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easy);
        difficultyGroup.add(medium);
        difficultyGroup.add(hard);
        radioPanel.add(easy);
        radioPanel.add(medium);
        radioPanel.add(hard);
        difficultyPanel.add(radioPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5)); // 1行2列，组件之间有5像素的间距
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

        buttonPanel.add(startButton);

        JButton backButton = new JButton("返回");
        backButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            addModeSelectionPanel(frame);
            frame.revalidate();
            frame.repaint();
        });
        buttonPanel.add(backButton);

        difficultyPanel.add(buttonPanel);

        frame.revalidate();
        frame.repaint();
    }

    private static void startGame(int mode, int difficulty) {
        TicTacToeGUI gui = TicTacToeGUI.getInstance(mode, difficulty);
        gui.showGame();
    }
}
