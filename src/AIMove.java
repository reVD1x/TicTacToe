import java.util.Random;

public class AIMove {
    public int row, col;

    public static void moveEazy(TicTacToeBoard tboard) {
        int[] move = {-1, -1};
        // 生成随机数
        Random random = new Random();
        move[0] = random.nextInt(3);
        move[1] = random.nextInt(3);
        tboard.makeMove(move[0], move[1]);
    }


    public static int minimax(TicTacToeBoard tboard, int depth, boolean isMaximizing, int alpha, int beta) {
        char result = tboard.checkWinner();
        if (result != '-') {
            return result == 'O' ? 10 : -10;
        }
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
                            break; // Alpha-beta pruning
                    }
                }
                if (beta <= alpha)
                    break; // Alpha-beta pruning
            }
            return minEval;
        }
    }

    public static void moveHard(TicTacToeBoard tboard) {
        int bestScore = Integer.MIN_VALUE;
        int[] move = {-1, -1};
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (tboard.board[i][j] == '-') {
                    tboard.board[i][j] = 'O';
                    int score = minimax(tboard, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    tboard.board[i][j] = '-'; // Undo the move
                    if (score > bestScore) {
                        bestScore = score;
                        move[0] = i;
                        move[1] = j;
                    }
                }
        tboard.makeMove(move[0], move[1]);
    }

}
