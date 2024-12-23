package TicTacToe;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGame {
    public static int xWins = 0;    // 双人模式 X 胜利次数
    public static int oWins = 0;    // 双人模式 O 胜利次数
    public static int draws = 0;    // 双人模式平局次数

    public static int easyWins = 0;     // 单人模式简单难度胜利次数
    public static int easyLosses = 0;   // 单人模式简单难度失败次数
    public static int easyDraws = 0;    // 单人模式简单难度平局次数

    public static int mediumWins = 0;   // 单人模式中等难度胜利次数
    public static int mediumLosses = 0; // 单人模式中等难度失败次数
    public static int mediumDraws = 0;  // 单人模式中等难度平局次数

    public static int hardWins = 0;     // 单人模式困难难度胜利次数
    public static int hardLosses = 0;   // 单人模式困难难度失败次数
    public static int hardDraws = 0;    // 单人模式困难难度平局次数

    private static Clip clickSound; // 点击音效
    private static Clip mainBGM;    // 主界面背景音乐

    // main 方法
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("井字棋");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            addModeSelectionPanel(frame);
            frame.setVisible(true);

            // 加载背景音乐
            try {
                AudioInputStream audioInputStream =
                        AudioSystem.getAudioInputStream(TicTacToeGame.class.getResource("resources/mainBGM.wav"));
                mainBGM = AudioSystem.getClip();
                mainBGM.open(audioInputStream);
                mainBGM.loop(Clip.LOOP_CONTINUOUSLY); // 循环播放
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 加载点击音效
            try {
                AudioInputStream audioInputStream =
                        AudioSystem.getAudioInputStream(TicTacToeGame.class.getResource("resources/Click.wav"));
                clickSound = AudioSystem.getClip();
                clickSound.open(audioInputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // 添加模式选择面板
    private static void addModeSelectionPanel(JFrame frame) {
        JPanel modePanel = new JPanel(new GridLayout(4, 1, 5, 5));
        frame.add(modePanel);

        JLabel modeLabel = new JLabel("选择模式", SwingConstants.CENTER);
        modeLabel.setFont(new Font("黑体", Font.BOLD, 18));
        modePanel.add(modeLabel);

        JPanel radioPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        JRadioButton singlePlayer = new JRadioButton("单人模式");
        JRadioButton multiPlayer = new JRadioButton("双人模式");
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(singlePlayer);
        modeGroup.add(multiPlayer);
        radioPanel.add(singlePlayer);
        radioPanel.add(multiPlayer);
        modePanel.add(radioPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        JButton nextButton = new JButton("下一步");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playClickSound();
                if (singlePlayer.isSelected()) {
                    showDifficultySelection(frame);
                } else if (multiPlayer.isSelected()) {
                    startGame(2, 0);    // 双人模式，难度为0
                    frame.dispose();    // 关闭选择窗口
                }
            }
        });
        buttonPanel.add(nextButton);

        JButton statsButton = new JButton("数据统计");
        statsButton.addActionListener(e -> {
            playClickSound();
            showStatisticsPage(frame);
        });
        buttonPanel.add(statsButton);

        JButton exitButton = new JButton("退出");
        exitButton.addActionListener(e -> {
            playClickSound();
            System.exit(0); // 退出程序
        });
        buttonPanel.add(exitButton);

        modePanel.add(buttonPanel);
    }

    // 显示数据统计页面
    private static void showStatisticsPage(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setSize(300, 400);

        JPanel statsPanel = new JPanel(new GridLayout(11, 1, 5, 5));
        frame.add(statsPanel);

        JLabel statsLabel = new JLabel("数据统计", SwingConstants.CENTER);
        statsLabel.setFont(new Font("黑体", Font.BOLD, 18)); // 加大加粗标题
        statsPanel.add(statsLabel);

        // 数据统计
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
            playClickSound();
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

    // 显示单人模式数据统计
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

    // 显示难度选择页面
    private static void showDifficultySelection(JFrame frame) {
        frame.getContentPane().removeAll();

        JPanel difficultyPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        frame.add(difficultyPanel);

        JLabel difficultyLabel = new JLabel("选择难度", SwingConstants.CENTER);
        difficultyLabel.setFont(new Font("黑体", Font.BOLD, 18));
        difficultyPanel.add(difficultyLabel);

        JPanel radioPanel = new JPanel(new GridLayout(1, 3, 5, 5));
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

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton startButton = new JButton("开始游戏");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playClickSound();
                int difficulty = 0;
                if (easy.isSelected()) {
                    difficulty = 1;
                } else if (medium.isSelected()) {
                    difficulty = 2;
                } else if (hard.isSelected()) {
                    difficulty = 3;
                }

                startGame(1, difficulty);   // 单人模式
                frame.dispose();    // 关闭选择窗口
            }
        });
        buttonPanel.add(startButton);

        JButton backButton = new JButton("返回");
        backButton.addActionListener(e -> {
            playClickSound();
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

    // 播放点击音效
    private static void playClickSound() {
        if (clickSound != null && clickSound.isRunning())
            clickSound.stop();  // 停止正在播放的音效

        if (clickSound != null) {
            clickSound.setFramePosition(0); // 重置音效位置
            clickSound.start(); // 播放音效
        }
    }

    // 开始游戏
    private static void startGame(int mode, int difficulty) {
        mainBGM.close();
        TicTacToeGUI gui = TicTacToeGUI.getInstance(mode, difficulty);
        gui.showGame();
    }
}
