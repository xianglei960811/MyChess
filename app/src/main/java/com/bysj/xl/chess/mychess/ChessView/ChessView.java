package com.bysj.xl.chess.mychess.ChessView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.bysj.xl.chess.mychess.R;
import com.bysj.xl.chess.mychess.entity.getScreenSize.ScreenSizeUtils;
import com.bysj.xl.chess.mychess.entity.ReSizeDrawable.ReSizeDrawable;

import java.util.HashMap;

import com.bysj.xl.chess.mychess.Constant.C;


/**
 * 自定义view类，、
 * 用于绘制象棋的棋盘和棋子
 */
public class ChessView extends View {
    private String TAG = "ChessView";
    private Context context;
    private volatile Canvas mcanvas = new Canvas();

    private int ScreenWidth;//屏幕宽高度
    private int ScreenHeight;
    private Paint paint = new Paint();
    private volatile Bitmap[] signBit = new Bitmap[4];//标记图片

    private int startX;
    private int startY;
    private int everyWidth;//每格的宽度
    private int chessWidth;//棋子高度，与宽度一致
    private int BoardWidth;//棋盘的宽度
    private Bitmap BoardBit;//棋盘
    private Bitmap[] piecesBit = new Bitmap[14];//棋子数组
    private int pieceWidth;//棋子的宽度
    private boolean isWhrite = true;//白棋下棋
    private volatile int isWin;//0为都没赢，1为白棋赢，2为黑棋赢

    private volatile Boolean isChoose = false; //选中状态

    private volatile Point movePoint = new Point();//移动的坐标
    private volatile Point choosePoint = new Point();//选中的点的序列


    public ChessView(Context context) {
        super(context);
        init(context);
    }

    public ChessView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChessView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //Todo:初始化一些变量
        this.context = context;
        initDrawble(context);

        ScreenWidth = ScreenSizeUtils.getInstance(context).getScreenWidth();
        ScreenHeight = ScreenSizeUtils.getInstance(context).getScreenHeight();

        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        startX = 0;
        startY = 0;
        everyWidth = ScreenWidth / 9;
        isWin = C.ISWIN_NOT_WIN;
    }

    private void initDrawble(Context context) {
        //ToDO:加载图片资源
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.chessbroad);
        drawable = ReSizeDrawable.reSize(context, (BitmapDrawable) drawable);
        BoardBit = ((BitmapDrawable) drawable).getBitmap();

        Drawable signup = ContextCompat.getDrawable(context, R.drawable.sign_up);
        signup = ReSizeDrawable.reSize(context, (BitmapDrawable) signup);
        signBit[0] = ((BitmapDrawable) signup).getBitmap();

        Drawable sigdown = ContextCompat.getDrawable(context, R.drawable.sign_down);
        sigdown = ReSizeDrawable.reSize(context, (BitmapDrawable) sigdown);
        signBit[1] = ((BitmapDrawable) sigdown).getBitmap();

        Drawable signleft = ContextCompat.getDrawable(context, R.drawable.sign_left);
        signleft = ReSizeDrawable.reSize(context, (BitmapDrawable) signleft);
        signBit[2] = ((BitmapDrawable) signleft).getBitmap();

        Drawable signrigt = ContextCompat.getDrawable(context, R.drawable.sign_right);
        signrigt = ReSizeDrawable.reSize(context, (BitmapDrawable) signrigt);
        signBit[3] = ((BitmapDrawable) signrigt).getBitmap();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = ScreenWidth;
        if (chessWidth == 0) {
            chessWidth = width / 13;//将屏幕宽分为9块
        }
        Log.d(TAG, "onMeasure: chessWidth=" + chessWidth);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(width + BoardWidth, MeasureSpec.EXACTLY);
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        Point point = new Point();//零时点

        if (event.getY() > (startY + everyWidth * 9) || event.getY() < 0) {//超出棋盘，有人赢了都被不可点击
            return false;
        }
        if (isWin == C.ISWIN_WHRITE_WIN) {
            return false;
        } else if (isWin == C.ISWIN_BLACK_WIN) {
            return false;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            point.set((int) touchX, (int) touchY);
            downChess(point);
//            if (!isChoose && !piece.isEmpty()) {
//                choosePoint.y = piece.get("y");
//                choosePoint.x = piece.get("x");
//                Log.d(TAG, "onTouchEvent: choose" + choosePoint);
//                isChoose = true;
//            }
//            if (isChoose && !piece.isEmpty()) {
//                movePoint.x = piece.get("x");
//                movePoint.y = piece.get("y");
//                if (movePoint.y == choosePoint.y && movePoint.x == choosePoint.x) {
//                    isChoose = false;
//                    return super.onTouchEvent(event);
//                } else {
//                    ChessRules.moveChess(choosePoint.x, choosePoint.y, movePoint.x, movePoint.y);
//                    isChoose = false;
//                    invalidate();
//                }
//            }
        }

        return super.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mcanvas = canvas;
        canvas.drawColor(Color.WHITE);
        drawBroard(canvas);//画棋盘
        canvasPiece(canvas);
        drawSign(canvas);
    }

    private void drawSign(Canvas canvas) {
        // TODO: 画棋子选中标志
        if (isChoose) {//选中棋子时，显示标记
            HashMap<String,Integer> coor = Account.SignCoord(choosePoint,everyWidth,pieceWidth);
            if (coor!=null) {
                int x =coor.get("x");
                int y = coor.get("y");
                int mode =coor.get(C.SIGN_MODE_NAME);
                if (mode==C.SIGN_UP) {
                    signBit[0] = Bitmap.createScaledBitmap(signBit[0], pieceWidth / 2, pieceWidth, true);
                    canvas.drawBitmap(signBit[0], x, y, null);
                }else if (mode==C.SIGN_DOWN){
                    signBit[1] = Bitmap.createScaledBitmap(signBit[1], pieceWidth / 2, pieceWidth, true);
                    canvas.drawBitmap(signBit[1], x, y, null);
                }else if (mode==C.SIGN_LEFT){
                    signBit[2] = Bitmap.createScaledBitmap(signBit[2], pieceWidth , pieceWidth/2, true);
                    canvas.drawBitmap(signBit[2], x, y, null);
                }else{
                    signBit[3] = Bitmap.createScaledBitmap(signBit[3], pieceWidth, pieceWidth / 2, true);
                    canvas.drawBitmap(signBit[3], x, y, null);
                }
            }
        }
    }


    private void canvasPiece(Canvas canvas) {
        // TODO:画棋子
        loadPieces();
        for (int j = 0; j < 10; j++) {//9行10列
            for (int i = 0; i < 9; i++) {
                if (ChessRules.chessValus(i, j) <= 0 || ChessRules.chessValus(i, j) > 14) {
                    if (ChessRules.chessValus(i, j) != 0) {
                        Log.e(TAG, "canvasPiece: hava a BUG");
                    }
                } else {
                    drawPiece(canvas, i, j);
                }
            }
        }
    }

    private void loadPieces() {
        // TODO: 加载棋子资源
        for (int i = 0; i < piecesBit.length; i++) {
            //whrite方:1车  2马  3相  4士  5帅  6炮 7兵
            //黑方:14车 13马 12相 11士 10帅 9炮 8兵
            switch (i + 1) {
                case 1:
                    Drawable drawable = ContextCompat.getDrawable(context, R.drawable.whrite_che);
                    drawable = ReSizeDrawable.reSizePieces(context, (BitmapDrawable) drawable);
                    piecesBit[i] = ((BitmapDrawable) drawable).getBitmap();
//                    piecesBit[i] = BitmapFactory.decodeResource(getResources(), R.drawable.whrite_che);
                    break;
                case 2:
                    piecesBit[i] = BitmapFactory.decodeResource(getResources(), R.drawable.whrite_ma);
                    break;
                case 3:
                    piecesBit[i] = BitmapFactory.decodeResource(getResources(), R.drawable.whrite_xiang);
                    break;
                case 4:
                    piecesBit[i] = BitmapFactory.decodeResource(getResources(), R.drawable.whrite_shi);
                    break;
                case 5:
                    piecesBit[i] = BitmapFactory.decodeResource(getResources(), R.drawable.whrite_shuai);
                    break;
                case 6:
                    piecesBit[i] = BitmapFactory.decodeResource(getResources(), R.drawable.whrite_pao);
                    break;
                case 7:
                    piecesBit[i] = BitmapFactory.decodeResource(getResources(), R.drawable.whrite_bin);
                    break;


                case 8:
                    piecesBit[i] = BitmapFactory.decodeResource(getResources(), R.drawable.black_bin);
                    break;
                case 9:
                    piecesBit[i] = BitmapFactory.decodeResource(getResources(), R.drawable.black_pao);
                    break;
                case 10:
                    piecesBit[i] = BitmapFactory.decodeResource(getResources(), R.drawable.black_jiang);
                    break;
                case 11:
                    piecesBit[i] = BitmapFactory.decodeResource(getResources(), R.drawable.black_shi);
                    break;
                case 12:
                    piecesBit[i] = BitmapFactory.decodeResource(getResources(), R.drawable.black_xiang);
                    break;
                case 13:
                    piecesBit[i] = BitmapFactory.decodeResource(getResources(), R.drawable.black_ma);
                    break;
                case 14:
                    piecesBit[i] = BitmapFactory.decodeResource(getResources(), R.drawable.black_che);
                    break;
            }
        }
    }

    private void drawBroard(Canvas canvas) {
        // TODO: 画棋盘
        BoardWidth = ScreenWidth;
        BoardBit = Bitmap.createScaledBitmap(BoardBit, BoardWidth, BoardWidth, true);
        canvas.drawBitmap(BoardBit, 0, 0, null);
    }

    private void downChess(Point point) {
        //TODO ：下棋的具体操作，点击事件的操作
        HashMap<String, Integer> piece = getX_Y(point);
        if (piece.isEmpty()) {//点击无效
            return;
        }
        int x = piece.get("x");
        int y = piece.get("y");
        if (isWhrite) {//白棋走
            if (!isChoose) {//第一次选中中棋子
                if (ChessRules.havaWhriteChess(x, y)) {//点击处存在白棋
                    choosePoint.set(x, y);
                    isChoose = true;
                    invalidate();
                    Log.d(TAG, "onTouchEvent: choose" + choosePoint);
                }
            } else {//选中棋子
                if (ChessRules.havaWhriteChess(x, y)) {//点击处存在白棋，则重选
                    choosePoint.set(x, y);
                    invalidate();
                    Log.d(TAG, "onTouchEvent: reChoose " + choosePoint);
                } else {//没有则移动该棋子
                    movePoint.set(x, y);
                    if (ChessRules.canMove(choosePoint.x, choosePoint.y, movePoint.x, movePoint.y)) {//符合规则，则移动
                        ChessRules.moveChess(choosePoint.x, choosePoint.y, movePoint.x, movePoint.y);
                        isWhrite = false;
                        isChoose = false;
                        invalidate();
                        if ((isWin = ChessRules.win()) != C.ISWIN_NOT_WIN) {
                            if (isWin == C.ISWIN_WHRITE_WIN) {
                                Toast.makeText(context, "白方获胜", Toast.LENGTH_SHORT).show();
                            } else if (isWin == C.ISWIN_BLACK_WIN) {
                                Toast.makeText(context, "黑方获胜", Toast.LENGTH_SHORT).show();
                            }
                        } else if ((isWin = ChessRules.checkShuaiSee(movePoint.x, movePoint.y)) != C.ISWIN_NOT_WIN) {
                            if (isWin == C.ISWIN_WHRITE_WIN) {
                                Toast.makeText(context, "白方获胜", Toast.LENGTH_SHORT).show();
                            } else if (isWin == C.ISWIN_BLACK_WIN) {
                                Toast.makeText(context, "黑方获胜", Toast.LENGTH_SHORT).show();
                            }
                            Log.e(TAG, "onTouchEvent:ssssshuai " + isWin);
                        }
                        Log.d(TAG, "onTouchEvent: is Move" + movePoint);
                    } else {
                        Log.d(TAG, "onTouchEvent:not move " + movePoint);
                    }
                }
            }
        }

        if (!isWhrite) {//黑棋走
            if (!isChoose) {//未选中棋子
                if (ChessRules.havaBlackChess(x, y)) {//点击处存在heiqi
                    choosePoint.set(x, y);
                    isChoose = true;
                    invalidate();
                    Log.d(TAG, "onTouchEvent: choose" + choosePoint);
                }
            } else {//选中棋子
                if (ChessRules.havaBlackChess(x, y)) {//点击处存在白棋，则重选
                    choosePoint.set(x, y);
                    invalidate();
                    Log.d(TAG, "onTouchEvent: reChoose " + choosePoint);
                } else {//没有则移动该棋子
                    movePoint.set(x, y);
                    if (ChessRules.canMove(choosePoint.x, choosePoint.y, movePoint.x, movePoint.y)) {//符合规则，则移动
                        ChessRules.moveChess(choosePoint.x, choosePoint.y, movePoint.x, movePoint.y);
                        isWhrite = true;
                        isChoose = false;
                        invalidate();
                        if ((isWin = ChessRules.win()) != C.ISWIN_NOT_WIN) {
                            if (isWin == C.ISWIN_WHRITE_WIN) {
                                Toast.makeText(context, "白方获胜", Toast.LENGTH_SHORT).show();
                            } else if (isWin == C.ISWIN_BLACK_WIN) {
                                Toast.makeText(context, "黑方获胜", Toast.LENGTH_SHORT).show();
                            }
                        } else if ((isWin = ChessRules.checkShuaiSee(movePoint.x, movePoint.y)) != C.ISWIN_NOT_WIN) {
                            if (isWin == C.ISWIN_WHRITE_WIN) {
                                Toast.makeText(context, "白方获胜", Toast.LENGTH_SHORT).show();
                            } else if (isWin == C.ISWIN_BLACK_WIN) {
                                Toast.makeText(context, "黑方获胜", Toast.LENGTH_SHORT).show();
                            }
                            Log.e(TAG, "onTouchEvent:ssssshuai " + isWin);
                        }
                        Log.d(TAG, "onTouchEvent: is Move" + movePoint);
                    } else {
                        Log.d(TAG, "onTouchEvent:not move " + movePoint);
                    }
                }
            }
        }


    }
    /**
     * 具体画棋子
     * @param canvas
     * @param i      x坐标
     * @param j      Y坐标
     */
    private void drawPiece(Canvas canvas, int i, int j) {
        //白方：1车  2马  3相  4士  5帅  6炮 7兵
        //黑方:14车 13马 12相 11士 10帅 9炮 8兵
        int pieceNum = ChessRules.chessValus(i, j);
        if (pieceNum <= 0) {
            return;
        }
        int pieceMode = pieceNum - 1;

        pieceWidth = piecesBit[1].getWidth();//棋子宽度
        int x = -100;//画棋子时的坐标
        int y = -100;
        piecesBit[pieceMode] = Bitmap.createScaledBitmap(piecesBit[pieceMode], chessWidth, chessWidth, true);
        if (pieceNum > 0 && pieceNum < 15) {
            x = startX + i * everyWidth + pieceWidth / 16 * i;
            y = startY + everyWidth * j + pieceWidth / 12 * j - pieceWidth / 6 * j - pieceWidth / 40 * j;
        }
        canvas.drawBitmap(piecesBit[pieceMode], x, y, null);
    }
    /**
     * 根据点击的坐标，求出点击的序列坐标
     *
     * @param choosePoint 选中的点
     * @return 返回值若为empty，则表示点击无效
     */
    private HashMap<String, Integer> getX_Y(Point choosePoint) {
        float x = choosePoint.x;
        float y = choosePoint.y;
        HashMap<String, Integer> point = new HashMap<>();
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 9; i++) {
                if (i == 0 && j == 0) {//左上角
                    if (x > 0 && x <= pieceWidth && y > 0 && y <= pieceWidth) {
                        point.put("x", i);
                        point.put("y", j);
                    }
                } else if (i == 0 && j == 9) {//左下角
                    if (x > 0 && x < pieceWidth && y <= ScreenWidth && y >= (ScreenWidth - pieceWidth)) {
                        point.put("x", i);
                        point.put("y", j);
                    }
                } else if (i == 8 && j == 0) {//右上角
                    if (x > (ScreenWidth - pieceWidth) && x < ScreenWidth && y > 0 && y < pieceWidth) {
                        point.put("x", i);
                        point.put("y", j);
                    }
                } else if (i == 8 && j == 9) {//右下角
                    if (x > (ScreenWidth - pieceWidth) && x < ScreenWidth && y > (ScreenWidth - pieceWidth) && y < ScreenWidth) {
                        point.put("x", i);
                        point.put("y", j);
                    }
                } else if (i == 0 && j != 0 && j != 9) {//最左边，除去定点
                    if (x > 0 && x <= pieceWidth && y >= (j * everyWidth - pieceWidth / 2) && y <= (j * everyWidth + pieceWidth / 2)) {
                        point.put("x", i);
                        point.put("y", j);
                    }
                } else if (i == 8 && j != 0 && j != 9) {
                    if (x > (ScreenWidth - pieceWidth) && x < ScreenWidth && y >= (j * everyWidth - pieceWidth / 2) && y <= (j * everyWidth + pieceWidth / 2)) {
                        point.put("x", i);
                        point.put("y", j);
                    }
                } else if (i != 0 && j == 0 && i != 8) {//最上边，去除顶点
                    if (x > (i * everyWidth + pieceWidth / 14 * i) && x < (i * everyWidth + pieceWidth + pieceWidth / 14 * i) && y > 0 && y < pieceWidth) {
                        point.put("x", i);
                        point.put("y", j);
                    }
                } else if (j == 9 && i != 0 && i != 8) {//最下边，去除定点
                    if (x > (i * everyWidth + pieceWidth / 14 * i) && x < (i * everyWidth + pieceWidth + pieceWidth / 14 * i) && y < ScreenWidth && y > (ScreenWidth - pieceWidth)) {
                        point.put("x", i);
                        point.put("y", j);
                    }
                } else {
                    if (x > (i * everyWidth + pieceWidth / 14 * i) && x < (i * everyWidth + pieceWidth + pieceWidth / 14 * i) && y >= (j * everyWidth - pieceWidth / 2) && y <= (j * everyWidth + pieceWidth / 2)) {
                        point.put("x", i);
                        point.put("y", j);
                    }
                }
            }
        }
        Log.d(TAG, "CheckPiece: 点击的序列坐标为" + point);
        return point;
    }

}
