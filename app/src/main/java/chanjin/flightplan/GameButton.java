package chanjin.flightplan;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class GameButton {
    public int mNum;
    private Bitmap mBitmap;
    public int mX;
    public int mY;
    private int mWidth;
    private int mHeight;
    private boolean mIsDraw = true;
    private boolean mIsAble = true;
    private ScreenConfig mScreenConfig;

    public GameButton(int num, int width, int height, ScreenConfig screenConfig, Bitmap bitmaporg) {
        mScreenConfig = screenConfig;
        mNum = num;
        mWidth = mScreenConfig.getX(width);
        mHeight = mScreenConfig.getY(height);
        mBitmap = Bitmap.createScaledBitmap(
                bitmaporg, mWidth, mHeight, false);
    }

    public void destory() {
        try {
            if (mBitmap != null)
                mBitmap.recycle();
        } catch (Exception ex) {
        }
    }

    public void setIsDraw(boolean is_draw) {
        mIsDraw = is_draw;
    }

    public void Move(int x, int y) {
        mX = mScreenConfig.getX(x);
        mY = mScreenConfig.getY(y);
    }

    public void draw(Canvas canvas) {
        if (mIsDraw == false)
            return;
        canvas.drawBitmap(mBitmap, mX, mY, null);
    }
}
