package TicTacToe;

import java.util.ArrayList;
import java.util.Random;

public class AIMove {
    public int row, col;

    // 简单 AI
    public static void moveEasy(TicTacToeBoard tboard) {
        int[] move = {-1, -1};
        // 生成随机数
        Random random = new Random();
        move[0] = random.nextInt(3);
        move[1] = random.nextInt(3);
        tboard.makeMove(move[0], move[1]);
    }

    // 检查有无线差一个棋子胜利
    private static boolean checkLine(TicTacToeBoard tboard, int row, int col, int addRow, int addCol, char player) {
        int nextRow = (row + addRow) % 3;
        int nextCol = (col + addCol) % 3;
        if (tboard.getBoardValue(nextRow, nextCol) == player &&
                tboard.getBoardValue((nextRow + addRow) % 3, (nextCol + addCol) % 3) == '-') {
            tboard.makeMove((nextRow + addRow) % 3, (nextCol + addCol) % 3);
            return true;
        }
        return false;
    }

    // 检查是否有任何行和列或对角线差一个格子就赢
    private static boolean canWin(char player, ArrayList<int[]> list, TicTacToeBoard tboard) {
        for (int[] cell : list) {
            int row = cell[0];
            int col = cell[1];

            // 判断所在行
            for (int i = 1; i < 3; i++)  // i 为所判断的两格间的相对距离
                if (checkLine(tboard, row, col, i, 0, player))
                    return true;

            // 判断所在列
            for (int i = 1; i < 3; i++)  // i 为所判断的两格间的相对距离
                if (checkLine(tboard, row, col, 0, i, player))
                    return true;

            // 判断所在对角线
            if (row == col)
                for (int i = 1; i < 3; i++)  // i 为所判断的两格间的相对距离
                    if (checkLine(tboard, row, col, i, i, player))
                        return true;
        }
        return false;
    }

    // 中级 AI
    public static void moveMiddle(TicTacToeBoard tboard) {
        ArrayList<int[]> listO = new ArrayList<>();
        ArrayList<int[]> listX = new ArrayList<>();

        // 遍历获取当前棋子的位置
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                char cellValue = tboard.getBoardValue(i, j);
                int[] cell = {i, j};

                if (cellValue == 'O')
                    listO.add(cell);
                else if (cellValue == 'X')
                    listX.add(cell);
            }

        // 若 AI 差一格能赢，则下
        if (canWin('O', listO, tboard))
            return;

        // 若玩家差一格能赢，则堵住
        if (canWin('X', listX, tboard))
            return;

        // 若双方都没达到胜利的条件，优先占据中心
        if (tboard.getBoardValue(1, 1) == '-') {
            tboard.makeMove(1, 1);
            return;
        }

        // 最后再选择随机下棋
        moveEasy(tboard);
    }

    // minimax 算法评估下在各点的分数
    public static int minimax(TicTacToeBoard tboard, int depth, boolean isMaximizing, int alpha, int beta) {
        char result = tboard.checkWinner();
        if (result != '-')
            return result == 'O' ? 10 : -10;

        if (tboard.isBoardFull())
            return 0;

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tboard.board[i][j] == '-') {
                        tboard.board[i][j] = 'O';
                        int eval = minimax(tboard, depth + 1, false, alpha, beta);
                        tboard.board[i][j] = '-';  // 恢复棋盘，撤销移动
                        maxEval = Math.max(maxEval, eval);
                        alpha = Math.max(alpha, eval);
                        if (beta <= alpha)
                            break; // Alpha-beta 剪枝
                    }
                }
                if (beta <= alpha)
                    break; // Alpha-beta 剪枝
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tboard.board[i][j] == '-') {
                        tboard.board[i][j] = 'X';
                        int eval = minimax(tboard, depth + 1, true, alpha, beta);
                        tboard.board[i][j] = '-';  // 恢复棋盘，撤销移动
                        minEval = Math.min(minEval, eval);
                        beta = Math.min(beta, eval);
                        if (beta <= alpha)
                            break;  // Alpha-beta 剪枝
                    }
                }
                if (beta <= alpha)
                    break;  // Alpha-beta 剪枝
            }
            return minEval;
        }
    }

    // 困难 AI
    public static void moveHard(TicTacToeBoard tboard) {
        int bestScore = Integer.MIN_VALUE;
        int[] move = {-1, -1};
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (tboard.board[i][j] == '-') {
                    // 模拟下棋，并递归模拟下一手
                    tboard.board[i][j] = 'O';
                    int score = minimax(tboard, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    tboard.board[i][j] = '-';  // 撤销移动

                    if (score > bestScore) {
                        bestScore = score;
                        move[0] = i;
                        move[1] = j;
                    }
                }
        tboard.makeMove(move[0], move[1]);
    }
}
