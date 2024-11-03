package TicTacToe;

import java.util.Random;

public class TicTacToeBoard {
    public char[][] board; //棋盘
    private char currentPlayer; //

    public TicTacToeBoard() {
        board = new char[3][3];
        currentPlayer = 'X'; // X 玩家开始
        clearBoard();
    }

    public void clearBoard() {
        //清空棋盘
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = '-';
    }

    public boolean makeMove(int row, int col) {
        //下棋
        if (board[row][col] == '-') {
            board[row][col] = currentPlayer;
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            return true;//下到正确位置
        }
        return false;//未下到正确位置
    }

    public void AIMove(int level) {
        int row, col;
        switch (level) {
            case 1://简单
                do {
                    //生成随机数
                    Random random = new Random();
                    row = random.nextInt(3);
                    col = random.nextInt(3);
                } while (!makeMove(row, col));
                break;
            case 2://中等
                break;
            case 3://高级
                break;
        }

    }

    // 获取当前棋盘
    public char getBoardValue(int row, int col) {
        return board[row][col];
    }

    public char checkWinner() {
        // 判断是否胜利
        for (int i = 0; i < 3; i++) {// 判断行和列是否有全部相等
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '-')
                return board[i][0];
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '-')
                return board[0][i];
        }
        // 判断对角线
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-')
            return board[0][0];
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '-')
            return board[0][2];
        return '-';
    }

    // 判断棋盘是否已满
    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '-')
                    return false;

        return true;
    }
}
