package TicTacToe;

public class TicTacToeBoard {
    public char[][] board; // 棋盘
    public char currentPlayer; // 当前玩家

    public TicTacToeBoard() {
        board = new char[3][3];
        currentPlayer = 'X';  // X 玩家开始
        clearBoard();
    }

    // 清空棋盘
    public void clearBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = '-';  // 使用 '-' 表示空位
    }

    // 下棋
    public boolean makeMove(int row, int col) {
        if (board[row][col] == '-') {
            board[row][col] = currentPlayer;
            return true;  // 下到正确位置
        }
        return false;  // 未下到正确位置
    }

    // 获取当前棋盘
    public char getBoardValue(int row, int col) {
        return board[row][col];
    }

    // 判断是否胜利
    public char checkWinner() {
        // 判断行和列是否有全部相等
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '-')
                return board[i][0];
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '-')
                return board[0][i];
        }

        // 判断对角线是否有全部相等
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-')
            return board[0][0];
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '-')
            return board[0][2];

        return '-';  // 没有胜者
    }

    // 判断棋盘是否已满
    public boolean isBoardFull() {
        // 遍历棋盘，如果有空位则返回 false
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '-')
                    return false;

        return true;
    }
}
