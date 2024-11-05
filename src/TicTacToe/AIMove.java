package TicTacToe;

import java.util.ArrayList;
import java.util.Random;

public class AIMove {
    public int row, col;
    public TicTacToeBoard board;
    public int gameLevel;

    public AIMove(TicTacToeBoard board, int gameLevel) {
        this.board = board;
        this.gameLevel = gameLevel;
    }

    public void move() {
        if (gameLevel == 1)
            moveEasy(board);
        else if (gameLevel == 2)
            moveMiddle(board);
        else if (gameLevel == 3)
            moveHard(board);
    }

    // 简单 AI
    public void moveEasy(TicTacToeBoard board) {
        do{
            Random random = new Random();  // 生成随机数
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!board.makeMove(row, col));
    }

    // 检查有无线差一个棋子胜利
    private boolean checkLine(TicTacToeBoard board, int row, int col, int addRow, int addCol, char player) {
        int nextRow = (row + addRow) % 3;
        int nextCol = (col + addCol) % 3;
        if (board.getBoardValue(nextRow, nextCol) == player &&
                board.getBoardValue((nextRow + addRow) % 3, (nextCol + addCol) % 3) == '-') {
            row = (nextRow + addRow) % 3;
            col = (nextCol + addCol) % 3;
            board.makeMove(row, col);
            return true;
        }
        return false;
    }

    // 检查是否有任何行和列或对角线差一个格子就赢
    private boolean canWin(char player, ArrayList<int[]> list, TicTacToeBoard board) {
        for (int[] cell : list) {
            int row = cell[0];
            int col = cell[1];

            // 判断所在行
            for (int i = 1; i < 3; i++)  // i 为所判断的两格间的相对距离
                if (checkLine(board, row, col, i, 0, player))
                    return true;

            // 判断所在列
            for (int i = 1; i < 3; i++)  // i 为所判断的两格间的相对距离
                if (checkLine(board, row, col, 0, i, player))
                    return true;

            // 判断所在对角线
            if (row == col)
                for (int i = 1; i < 3; i++)  // i 为所判断的两格间的相对距离
                    if (checkLine(board, row, col, i, i, player))
                        return true;
        }
        return false;
    }

    // 中级 AI
    public void moveMiddle(TicTacToeBoard board) {
        ArrayList<int[]> listO = new ArrayList<>();
        ArrayList<int[]> listX = new ArrayList<>();

        // 遍历获取当前棋子的位置
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                char cellValue = board.getBoardValue(i, j);
                int[] cell = {i, j};

                if (cellValue == 'O')
                    listO.add(cell);
                else if (cellValue == 'X')
                    listX.add(cell);
            }

        // 若 AI 差一格能赢，则下
        if (canWin('O', listO, board))
            return;

        // 若玩家差一格能赢，则堵住
        if (canWin('X', listX, board))
            return;

        // 若双方都没达到胜利的条件，优先占据中心
        if (board.getBoardValue(1, 1) == '-') {
            row = 1;
            col = 1;
            board.makeMove(row, col);
            return;
        }

        // 最后再选择随机下棋
        moveEasy(board);
    }

    // minimax 算法评估下在各点的分数
    public int minimax(TicTacToeBoard board, int depth, boolean isMaximizing, int alpha, int beta) {
        char result = board.checkWinner();
        if (result != '-')
            return result == 'O' ? 10 : -10;

        if (board.isBoardFull())
            return 0;

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.board[i][j] == '-') {
                        board.board[i][j] = 'O';
                        int eval = minimax(board, depth + 1, false, alpha, beta);
                        board.board[i][j] = '-';  // 恢复棋盘，撤销移动
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
                    if (board.board[i][j] == '-') {
                        board.board[i][j] = 'X';
                        int eval = minimax(board, depth + 1, true, alpha, beta);
                        board.board[i][j] = '-';  // 恢复棋盘，撤销移动
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
    public void moveHard(TicTacToeBoard board) {
        int bestScore = Integer.MIN_VALUE;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board.board[i][j] == '-') {
                    // 模拟下棋，并递归模拟下一手
                    board.board[i][j] = 'O';
                    int score = minimax(board, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board.board[i][j] = '-';  // 撤销移动

                    if (score > bestScore) {
                        bestScore = score;
                        row = i;
                        col = j;
                    }
                }
        board.makeMove(row, col);
    }
}
