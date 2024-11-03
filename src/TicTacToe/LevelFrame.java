package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LevelFrame extends JFrame {
    LevelFrame(){
        setTitle("井字棋 - 难度选择");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1)); // 垂直布局
        this.setVisible(true);

        JLabel modeLabel = new JLabel("选择游戏难度:");
        add(modeLabel);

        ButtonGroup modeGroup = new ButtonGroup();
        JRadioButton easy = new JRadioButton("简单", true);
        JRadioButton middle = new JRadioButton("中等");
        JRadioButton hard = new JRadioButton("高级");
        modeGroup.add(easy);
        modeGroup.add(middle);
        modeGroup.add(hard);
        add(easy);
        add(middle);
        add(hard);

        //添加开始游戏按钮
        JButton startButton = new JButton("开始游戏");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 根据选择的游戏模式启动游戏
                if (easy.isSelected()) {
                    // 启动简单模式
                    dispose();
                    new TicTacToeGUI(0,1);
                } else if (middle.isSelected()){
                    // 启动中等模式
                    dispose();//隐藏开始界面
                    new TicTacToeGUI(0,2);
                } else {
                    //启动高级模式
                    dispose();
                    new TicTacToeGUI(0,3);
                }
            }
        });
        add(startButton);
    }
}
