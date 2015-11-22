package chanjin.flightplan;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MyPlane {
    public int mNum;
    private Bitmap mBitmap;
    private Bitmap mBitmapL;
    private Bitmap mBitmapR;

    public double mX;
    public double mY;
    public double[] mXOld = new double[20];
    public double[] mYOld = new double[20];

    private int mWidth;
    private int mHeight;


    private boolean mIsDraw = true;
    private boolean mIsAble = true;
    public boolean mGoCls = false;
    public boolean mActive = true;

    public double mGabX = 0;
    public double mGabY = 0;

    public ScreenConfig mScreenConfig;
    public int mDirectionMode = 1;
    public int mTimeLine = 0;
    public int mSensorMode;

    public Paint mP = new Paint();

    public void destory() {
        try {
            if (mBitmap != null) {
                mBitmap.recycle();
            }
        } catch (Exception ex) {
        }
    }

    public MyPlane(ScreenConfig screenConfig, Bitmap bitmaporg, Bitmap bitmaporgL, Bitmap bitmaporgR) {
        mScreenConfig = screenConfig;
        mWidth = screenConfig.getX(200);
        mHeight = screenConfig.getY(156);
        mBitmap = Bitmap.createScaledBitmap(bitmaporg, mWidth, mHeight, false);
        mBitmapL = Bitmap.createScaledBitmap(bitmaporgL, mWidth, mHeight, false);
        mBitmapR = Bitmap.createScaledBitmap(bitmaporgR, mWidth, mHeight, false);
    }

    public void setIsDraw(boolean isDraw) {
        mIsDraw = isDraw;
    }

    public void move(int x, int y) {
        mX = mScreenConfig.getX(x);
        mY = mScreenConfig.getY(y);
    }

    public void moveX(int x) {
        mX = mScreenConfig.getX(x);
    }

    public void moveY(int y) {
        mY = mScreenConfig.getY(y);
    }

    public int m_degree = 0;

    public void think() {
        mTimeLine++;
        for (int i = 0; i < 18; i++) {
            mXOld[i] = mXOld[i + 1];
            mYOld[i] = mYOld[i + 1];
        }
        mXOld[17] = mX;
        mYOld[17] = mY;

        if (mTimeLine > 10000) {
            mTimeLine = 0;
        }
        if (mGoCls == true) {
            mX += (mGabX / 10);

            if (mX - mWidth / 2 < 0)
                mX = mWidth / 2;
            else if (mX + mWidth / 2 > mScreenConfig.screen_width)
                mX = mScreenConfig.screen_width - mWidth / 2;

            mY += (mGabY / 10);
            if (mY - mHeight / 2 < 0)
                mY = mHeight / 2;
            if (mY + mHeight / 2 > mScreenConfig.getY(2000))
                mY = mScreenConfig.getY(2000) - mHeight / 2;

        }
    }

    public void setDirection(int direction) {
        mDirectionMode = direction;
    }

    public boolean isSelected(int x, int y) {
        boolean is_selected = false;
        if (mIsAble == true) {
            if ((x > mX - mWidth / 3 && x < mX + mWidth / 3) &&
                    (y > mY - mHeight / 3 && y < mY + mHeight / 3)) {
                is_selected = true;
            }
        }
        return is_selected;
    }

    public boolean isPlainSelected(int x, int y, int width, int height) {
        boolean is_selected = false;
        if (mIsAble == true) {
            if ((x > mX - mWidth / 3 - width / 4 && x < mX + mWidth / 3 + width / 4) &&
                    (y > mY - mHeight / 3 - height / 4 && y < mY + mHeight / 3 + height / 4)) {
                is_selected = true;
            }
        }
        return is_selected;
    }

    public void draw(Canvas canvas) {
        if (mIsDraw == true && mActive == true) {
            if (mDirectionMode == 1) {
                canvas.drawBitmap(mBitmap, (int) (mX - mWidth / 2), (int) (mY - mHeight / 2), null);
            } else if (mDirectionMode == 2) {
                canvas.drawBitmap(mBitmapR, (int) (mX - mWidth / 2), (int) (mY - mHeight / 2), null);
            } else if (mDirectionMode == 3) {
                canvas.drawBitmap(mBitmapL, (int) (mX - mWidth / 2), (int) (mY - mHeight / 2), null);
            }
        }
    }
}
