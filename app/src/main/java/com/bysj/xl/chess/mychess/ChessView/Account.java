package com.bysj.xl.chess.mychess.ChessView;

import android.graphics.Point;

import java.util.HashMap;

import com.bysj.xl.chess.mychess.Constant.C;

/**
 * 该类用于计算
 * TODO: 2018/9/13
 */
public class Account {

    /**
     *   用于计算选中标志的方向及坐标
     * @param point
     * @param everyWidth
     * @param pieceWidth
     * @return
     */
    public static HashMap<String, Integer> SignCoord(Point point, int everyWidth, int pieceWidth) {
        HashMap<String, Integer> coord = new HashMap<>();
        int mode;
        int x, y;
        if (point.y == 0 && point.x != 0&& point.x != 8) {//在上边界
            if (ChessRules.chessValus((point.x - 1), point.y) == 0) {//zuo
                mode = C.SIGN_LEFT;
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4 - pieceWidth/6*8;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y+pieceWidth/40*point.y;
            } else if (ChessRules.chessValus((point.x + 1), point.y) == 0) {//→
                mode = C.SIGN_RIGHT;
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4 + pieceWidth/4*3;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y+pieceWidth/40*point.y;
            } else {
                mode = C.SIGN_DOWN;
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y - pieceWidth / 40 * point.y + pieceWidth;
            }
        } else if (point.y == 9 && point.x != 0&& point.x != 8) {//下边界
            if (ChessRules.chessValus((point.x - 1), point.y) == 0) {//zuo
                mode = C.SIGN_LEFT;
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4 - pieceWidth/6*8;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y+pieceWidth/40*point.y;
            } else if (ChessRules.chessValus((point.x + 1), point.y) == 0) {//→
                mode = C.SIGN_RIGHT;
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4 + pieceWidth/4*3;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y+pieceWidth/40*point.y;
            } else {
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y - pieceWidth / 40 * point.y - pieceWidth;
                mode = C.SIGN_UP;
            }
        } else if (point.x == 0 && point.y != 0&& point.y != 9) {//左边界
            if (ChessRules.chessValus(point.x, (point.y - 1)) == 0) {//在上方
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y - pieceWidth / 40 * point.y - pieceWidth;
                mode = C.SIGN_UP;
            } else if (ChessRules.chessValus(point.x, (point.y + 1)) == 0) {//xia
                mode = C.SIGN_DOWN;
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y - pieceWidth / 40 * point.y + pieceWidth;
            } else {
                mode = C.SIGN_RIGHT;
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4 + pieceWidth/4*3;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y+pieceWidth/40*point.y;
            }
        } else if (point.x == 8 && point.y != 0&& point.y != 9) {//右边界
            if (ChessRules.chessValus(point.x, (point.y - 1)) == 0) {//在上方
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y - pieceWidth / 40 * point.y - pieceWidth;
                mode = C.SIGN_UP;
            } else if (ChessRules.chessValus(point.x, (point.y + 1)) == 0) {//xia
                mode = C.SIGN_DOWN;
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y - pieceWidth / 40 * point.y + pieceWidth;
            } else {
                mode = C.SIGN_LEFT;
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4 - pieceWidth/6*8;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y+pieceWidth/40*point.y;
            }
        } else if (point.y == 0 && point.x == 0) {//左上顶点
            if (ChessRules.chessValus((point.x + 1), point.y) == 0) {//→
                mode = C.SIGN_RIGHT;
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4 + pieceWidth/4*3;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y+pieceWidth/40*point.y;
            } else {
                mode = C.SIGN_DOWN;
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y - pieceWidth / 40 * point.y + pieceWidth;
            }
        } else if (point.x == 0 && point.y == 9) {//左下
            if (ChessRules.chessValus((point.x + 1), point.y) == 0) {//→
                mode = C.SIGN_RIGHT;
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4 + pieceWidth/4*3;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y+pieceWidth/40*point.y;
            } else {
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y - pieceWidth / 40 * point.y - pieceWidth;
                mode = C.SIGN_UP;
            }
        } else if (point.x == 8 && point.y == 0) {//右上
            if (ChessRules.chessValus((point.x - 1), point.y) == 0) {//zuo
                mode = C.SIGN_LEFT;
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4 - pieceWidth/6*8;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y+pieceWidth/40*point.y;
            } else {
                mode = C.SIGN_DOWN;
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y - pieceWidth / 40 * point.y + pieceWidth;
            }
        } else if (point.x == 8 && point.y == 9) {//右下
            if (ChessRules.chessValus((point.x - 1), point.y) == 0) {//zuo
                mode = C.SIGN_LEFT;
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4 - pieceWidth/6*8;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y+pieceWidth/40*point.y;
            } else {
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y - pieceWidth / 40 * point.y - pieceWidth;
                mode = C.SIGN_UP;
            }
        } else {//非边界非顶点
            if (ChessRules.chessValus(point.x, (point.y - 1)) == 0) {//在上方
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y - pieceWidth / 40 * point.y - pieceWidth;
                mode = C.SIGN_UP;
            } else if (ChessRules.chessValus(point.x, (point.y + 1)) == 0) {//xia
                mode = C.SIGN_DOWN;
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y - pieceWidth / 40 * point.y + pieceWidth;
            } else if (ChessRules.chessValus((point.x - 1), point.y) == 0) {//zuo
                mode = C.SIGN_LEFT;
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4 - pieceWidth/6*8;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y+pieceWidth/40*point.y;
            } else if (ChessRules.chessValus((point.x + 1), point.y) == 0) {//→
                mode = C.SIGN_RIGHT;
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4 + pieceWidth/4*3;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y+pieceWidth/40*point.y;
            } else {
                x = point.x * everyWidth + pieceWidth / 16 * point.x + pieceWidth / 4;
                y = everyWidth * point.y + pieceWidth / 12 * point.y - pieceWidth / 6 * point.y - pieceWidth / 40 * point.y - pieceWidth;
                mode = C.SIGN_UP;
            }
        }
        coord.put(C.SIGN_MODE_NAME, mode);
        coord.put("x", x);
        coord.put("y", y);
        return coord;
    }
}
