package chanjin.flightplan;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.BufferedInputStream;
import java.util.logging.Handler;

public class MainView extends SurfaceView implements SurfaceHolder.Callback {
    private GameActivity mGameActivity;
    MainThread mMainThread;
    Handler mHandler;
    Context mMainContext;
    boolean mDrawCls = false;
    private ScreenConfig mScreenConfig;
    private MyPlane mMyPlain;
    private GameButton mController;
    private float StartPointX = 0;
    private float StartPointY = 0;

    public MainView(Context r, AttributeSet a) {
        super(r, a);
        getHolder().addCallback(this);
        mMainThread = new MainThread(getHolder(), this);
        setFocusable(true);
        mMainContext = r;
    }

    public void surfaceChanged(SurfaceHolder holder,
                               int format, int width, int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        mMainThread.setRunning(true);
        try {
            if (mMainThread.getState() == Thread.State.TERMINATED) {
                mMainThread = new MainThread(getHolder(), this);
                mMainThread.setRunning(true);
                setFocusable(true);
                mMainThread.start();
            } else {
                mMainThread.start();
            }
        } catch (Exception ex) {
            Log.i("MainView", "ex:" + ex.toString());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        mMainThread.setRunning(false);
        while (retry) {
            try {
                mMainThread.join();
                retry = false;
            } catch (Exception e) {
                Log.i("MainView", "surfaceDestoryed ex" + e.toString());
            }
        }
    }

    public void init(int width, int height, GameActivity mGameActivity) {
        this.mGameActivity = mGameActivity;
        mScreenConfig = new ScreenConfig(width, height);
        mScreenConfig.setSize(1000, 2000);

        Bitmap myPlainBitmap = loadBitmap("plane.png");
        Bitmap myPlainLBitmap = loadBitmap("planel.png");
        Bitmap myPlainRBitmap = loadBitmap("planer.png");
        mMyPlain = new MyPlane(mScreenConfig, myPlainBitmap, myPlainLBitmap, myPlainRBitmap);
        mMyPlain.move(500, 1200);

        mDrawCls = true;
    }

    public Bitmap loadBitmap(String filename) {
        Bitmap bm = null;
        try {
            AssetManager am = mMainContext.getAssets();
            BufferedInputStream buf = new BufferedInputStream(am.open(filename));
            bm = BitmapFactory.decodeStream(buf);
        } catch (Exception ex) {
        }
        return bm;
    }

    public void think() {
        mMyPlain.think();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (mDrawCls == false)
            return;
        canvas.drawColor(Color.rgb(255, 255, 255));
        mMyPlain.draw(canvas);
    }

    private int mControlPointerId;
    int mPointerId = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mDrawCls == true) {
            final int action = event.getAction();
            switch (action & MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_DOWN: {

                    mPointerId = event.getPointerId(0);
                    final float x = event.getX();
                    final float y = event.getY();

                    StartPointX = x;
                    StartPointY = y;

                    mControlPointerId = mPointerId;
                    moveControl((int) x, (int) y);

                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    for (int i = 0; i < event.getPointerCount(); i++) {
                        mPointerId = event.getPointerId(i);
                        if (mPointerId == mControlPointerId) {
                            final float x = (int) event.getX(i);
                            final float y = (int) event.getY(i);

                            moveControl((int) x, (int) y);
                        }
                    }
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    mPointerId = event.getPointerId(0);
                    final float x = (int) event.getX();
                    final float y = (int) event.getY();

                    if (mControlPointerId == mPointerId) {
                        stopControl((int) x, (int) y);
                    }
                    break;
                }
                case MotionEvent.ACTION_CANCEL: {
                    mPointerId = event.getPointerId(0);
                    final float x = (int) event.getX();
                    final float y = (int) event.getY();

                    if (mControlPointerId == mPointerId) {
                        stopControl((int) x, (int) y);
                    }
                    break;
                }
            }
        }
        return true;
    }

    public void moveControl(int x, int y) {
        if (mMyPlain.mActive == false)
            return;

        if (mMyPlain.mActive == true)
            mMyPlain.mGoCls = true;

        mMyPlain.mGabX = (x - StartPointX) / 2;
        mMyPlain.mGabY = (y - StartPointY) / 2;
    }

    public void stopControl(int x, int y) {
        mMyPlain.mGoCls = false;
    }
}
