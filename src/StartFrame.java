import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame extends JFrame {
    StartFrame() {
        setTitle("井字棋 - 开始界面");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1)); // 垂直布局
        this.setVisible(true);

        // 添加游戏模式选择
        JLabel modeLabel = new JLabel("选择游戏模式:");
        add(modeLabel);

        ButtonGroup modeGroup = new ButtonGroup();
        JRadioButton playerVsPlayer = new JRadioButton("玩家对战", true);
        JRadioButton playerVsComputer = new JRadioButton("玩家对战电脑");
        modeGroup.add(playerVsPlayer);
        modeGroup.add(playerVsComputer);
        add(playerVsPlayer);
        add(playerVsComputer);

        //添加开始游戏按钮
        JButton startButton = new JButton("开始游戏");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 根据选择的游戏模式启动游戏
                if (playerVsComputer.isSelected()) {
                    // 启动玩家对战电脑模式的游戏
                    dispose();
//                    new TicTacToeGUI(0);
                    new LevelFrame();
                } else {
                    // 启动玩家对战玩家模式的游戏
                    dispose();//隐藏开始界面
                    new TicTacToeGUI(1,0);
                }
            }
        });
        add(startButton);
    }
}
