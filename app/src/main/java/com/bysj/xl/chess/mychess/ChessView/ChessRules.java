package com.bysj.xl.chess.mychess.ChessView;

import android.util.Log;

/**
 * 象棋规则类
 */
public class ChessRules {
    private static int[][] chessboard = {
            //白方：1车  2马  3相  4士  5帅  6炮 7兵
            //黑方:14车 13马 12相 11士 10帅 9炮 8兵
            {1, 2, 3, 4, 5, 4, 3, 2, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 6, 0, 0, 0, 0, 0, 6, 0},
            {7, 0, 7, 0, 7, 0, 7, 0, 7},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},//白方棋子分布
            {0, 0, 0, 0, 0, 0, 0, 0, 0},//黑方棋子分布
            {8, 0, 8, 0, 8, 0, 8, 0, 8},
            {0, 9, 0, 0, 0, 0, 0, 9, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {14, 13, 12, 11, 10, 11, 12, 13, 14}
    };
    public static int NOT_WIN = 0;
    public static int WHRITE_WIN = 1;
    public static int BLACK_WIN = 2;

    /**
     * 判断输赢
     * 根据遍历棋盘数列中是否存在5，10
     *
     * @return
     */
    public static int win() {
        int whrite_win = 0, black_win = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                if (chessboard[i][j] == 5) {
                    whrite_win = 1;
                } else if (chessboard[i][j] == 10) {
                    black_win = 1;
                }
            }
        }
        if (whrite_win == black_win) {
            return NOT_WIN;
        } else if (whrite_win == 1 && black_win == 0) {
            return WHRITE_WIN;
        } else if (whrite_win == 0 && black_win == 1) {
            return BLACK_WIN;
        } else {
            return NOT_WIN;
        }
    }

    public static int checkShuaiSee(int X, int Y) {
        int count = 0, whritex = 0, whritey = 0, blackX = 0, blackY = 0;
        for (int i = 0; i < 9; i++) {//求出两个帅的位子
            for (int j = 0; j < 10; j++) {
                if (chessValus(i, j) == 10) {//求出黑帅的位子
                    blackX = i;
                    blackY = j;
                }
                if (chessValus(i, j) == 5) {
                    whritex = i;
                    whritey = j;
                }
            }
        }
        if (whritex == blackX) {
            if (havaWhriteChess(X,Y)) {//移动的为baiqi
                for (int i = whritey + 1; i < blackY; i++) {
                    if (chessValus(blackX, i) != 0) {
                        count++;
                    }
                }
                if (count == 0) {
                    return BLACK_WIN;
                }
            }else if (havaBlackChess(X,Y)){//移动为黑棋时
                for (int i = blackY - 1; i > whritey; i--) {
                    if (chessValus(whritex, i) != 0) {
                        count++;
                    }
                }
                if (count == 0) {
                    return WHRITE_WIN;
                }
            }
        }

        return NOT_WIN;
    }

    /**
     * 根据坐标找到具体的数组值（代表的棋子）X
     *
     * @param positionX x坐标
     * @param positionY y坐标X
     * @return
     */
    public static int chessValus(int positionX, int positionY) {
        return chessboard[positionY][positionX];
    }

    /**
     * 移动棋子
     *
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     */
    public static void moveChess(int fromX, int fromY, int toX, int toY) {
        chessboard[toY][toX] = chessValus(fromX, fromY);
        chessboard[fromY][fromX] = 0;
        Log.d("ssss", "moveChess: " + chessboard[toY][toX] + ":" + fromX + "ss" + fromY + ":" + toX + "ss" + toY);
    }

    /**
     * 判断该点是否有白棋
     *
     * @param x
     * @param y
     * @return
     */
    public static boolean havaWhriteChess(int x, int y) {
        if (chessValus(x, y) > 0 && chessValus(x, y) < 8) {
            return true;
        }
        return false;
    }

    /**
     * 判断该点是否有黑棋
     *
     * @param x
     * @param y
     * @return
     */
    public static boolean havaBlackChess(int x, int y) {
        if (chessValus(x, y) > 7 && chessValus(x, y) < 15) {
            return true;
        }
        return false;
    }

    //白方：1车  2马  3相  4士  5帅  6炮 7兵
    //黑方:14车 13马 12相 11士 10帅 9炮 8兵
//    {1, 2, 3, 4, 5, 4, 3, 2, 1},
//    {0, 0, 0, 0, 0, 0, 0, 0, 0},
//    {0, 6, 0, 0, 0, 0, 0, 6, 0},
//    {7, 0, 7, 0, 7, 0, 7, 0, 7},
//    {0, 0, 0, 0, 0, 0, 0, 0, 0},//白方棋子分布
//    {0, 0, 0, 0, 0, 0, 0, 0, 0},//黑方棋子分布
//    {8, 0, 8, 0, 8, 0, 8, 0, 8},
//    {0, 9, 0, 0, 0, 0, 0, 9, 0},
//    {0, 0, 0, 0, 0, 0, 0, 0, 0},
//    {14, 13, 12, 11, 10, 11, 12, 13, 14}

    /**
     * 象棋规则
     *
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @return
     */
    public static boolean canMove(int fromX, int fromY, int toX, int toY) {
        if (toX < 0 || toX > 8 || toY < 0 || toY > 9) {//移动点超出范围
            return false;
        }
        switch (chessValus(fromX, fromY)) {
            case 1://车
            case 14:
                if (Math.abs(toY - fromY) > 0 && Math.abs(toX - fromX) == 0) {//走竖线
                    if (toY > fromY) {
                        for (int i = fromY + 1; i < toY; i++) {//直线向下途中无棋子
                            if (chessValus(fromX, i) != 0) {
                                return false;
                            }
                        }
                    } else {
                        for (int i = toY + 1; i < fromY; i++) {//向上
                            if (chessValus(fromX, i) != 0) {
                                return false;
                            }
                        }
                    }
                    return true;
                } else if (Math.abs(toX - fromX) > 0 && Math.abs(toY - fromY) == 0) {//走横线
                    if (toX > fromX) {//向右
                        for (int i = fromX + 1; i < toX; i++) {
                            if (chessValus(i, fromY) != 0) {
                                return false;
                            }
                        }
                    } else {//left
                        for (int i = toX + 1; i < fromX; i++) {
                            if (chessValus(i, fromY) != 0) {
                                return false;
                            }
                        }
                    }
                    return true;
                }
                break;
            case 2://马
            case 13:
                if (Math.abs(toY - fromY) == 2 && Math.abs(toX - fromX) == 1) {//竖着跳
                    int centerY = (toY + fromY) / 2;
                    if (chessValus(fromX, centerY) != 0) {//马蹄处有棋子
                        return false;
                    }
                    return true;
                } else if (Math.abs(toX - fromX) == 2 && Math.abs(toY - fromY) == 1) {
                    int centerX = (fromX + toX) / 2;
                    if (chessValus(centerX, fromY) != 0) {
                        return false;
                    }
                    return true;
                }
                break;
            case 3://白象
                if (toY > 4) {//过河了
                    return false;
                } else if (Math.abs(toY - fromY) == 2 && Math.abs(toX - fromX) == 2) {//走“田”字
                    int centerX = (toX + fromX) / 2;
                    int centerY = (toY + fromY) / 2;
                    if (chessValus(centerX, centerY) != 0) {//象眼处无棋子
                        return false;
                    }
                    return true;
                }
                break;
            case 12://黑象
                if (toY < 5) {
                    return false;
                } else if (Math.abs(toX - fromX) == 2 && Math.abs(toY - fromY) == 2) {
                    int centerX = (toX + fromX) / 2;
                    int centerY = (toY + fromY) / 2;
                    if (chessValus(centerX, centerY) != 0) {//象眼处无棋子
                        return false;
                    }
                    return true;
                }
                break;
            case 4://白仕
                if (toY > 2 || toX < 3 || toX > 5) {//走出9宫格
                    return false;
                } else if (Math.abs(toX - fromX) == 1 && Math.abs(toY - fromY) == 1) {//走斜线，只能走一格
                    return true;
                }
                break;
            case 11:
                if (toX < 3 || toX > 5 || toY < 7) {
                    return false;
                } else if (Math.abs(toX - fromX) == 1 && Math.abs(toY - fromY) == 1) {
                    return true;
                }
                break;
            case 5://帅
                if (toY > 2 || toX < 3 || toX > 5) {//走出9宫格
                    return false;
                } else if (Math.abs(toY - fromY) + Math.abs(toX - fromX) == 1) {//只能横着，或竖着走一格
                    return true;
                }
                break;
            case 10:
                if (toX < 3 || toX > 5 || toY < 7) {
                    return false;
                } else if (Math.abs(toY - fromY) + Math.abs(toX - fromX) == 1) {//只能横着，或竖着走一格
                    return true;
                }
                break;
            case 6://炮
            case 9:
                int count = 0;
                if (chessValus(toX, toY) == 0) {//到点无棋子
                    if (Math.abs(toY - fromY) > 0 && Math.abs(toX - fromX) == 0) {//竖线
                        if (toY > fromY) {
                            for (int i = fromY + 1; i < toY; i++) {
                                if (chessValus(fromX, i) != 0) {
                                    return false;
                                }
                            }
                        } else {
                            for (int i = toY + 1; i < fromY; i++) {
                                if (chessValus(fromX, i) != 0) {
                                    return false;
                                }
                            }
                        }
                    } else if (Math.abs(toY - fromY) == 0 && Math.abs(toX - fromX) > 0) {//横线
                        if (toX > fromX) {//right
                            for (int i = fromX + 1; i < toX; i++) {
                                if (chessValus(i, fromY) != 0) {
                                    return false;
                                }
                            }
                        } else {//left
                            for (int i = toX + 1; i < fromX; i++) {
                                if (chessValus(i, fromY) != 0) {
                                    return false;
                                }
                            }
                        }
                    } else {//斜线时
                        return false;
                    }
                    return true;
                } else {//到点有棋子
                    if (Math.abs(toY - fromY) > 0 && Math.abs(toX - fromX) == 0) {//竖
                        if (toY > fromY) {
                            for (int i = fromY + 1; i < toY; i++) {
                                if (chessValus(fromX, i) != 0) {
                                    count++;
                                }
                            }
                        } else {
                            for (int i = toY + 1; i < fromY; i++) {
                                if (chessValus(fromX, i) != 0) {
                                    count++;
                                }
                            }
                        }
                    } else if (Math.abs(toX - fromX) > 0 && Math.abs(toY - fromY) == 0) {//走的横线
                        if (toX > fromX) {
                            for (int i = fromX + 1; i < toX; i++) {
                                if (chessValus(i, fromY) != 0) {
                                    count++;
                                }
                            }
                        } else {
                            for (int i = fromX - 1; i > toX; i--) {
                                if (chessValus(i, fromY) != 0) {
                                    count++;
                                }
                            }
                        }
                    }
                    if (count == 1) {//中间必须有一个棋子
                        return true;
                    }
                }
                break;
            case 7://白兵
                if (toY - fromY < 0) {//后退
                    return false;
                } else if (fromY > 4) {//过河
                    if ((Math.abs(toX - fromX) + Math.abs(toY - fromY)) == 1) {
                        return true;
                    }
                } else {//未过河
                    if (Math.abs(toY - fromY) == 1 && fromX == toX) {
                        return true;
                    }
                }
                break;
            case 8:
                if ((toY - fromY) > 0) {
                    return false;
                } else if (fromY <= 4) {
                    if ((Math.abs(toX - fromX) + Math.abs(toY - fromY)) == 1) {
                        return true;
                    }
                } else {
                    if (Math.abs(toY - fromY) == 1 && fromX == toX) {
                        return true;
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }

}
