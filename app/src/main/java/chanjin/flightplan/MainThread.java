package chanjin.flightplan;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private SurfaceHolder mSurfaceHolder;
    private MainView mMainView;
    private boolean mRunning = false;

    public MainThread(SurfaceHolder surfaceHolder, MainView mMainView) {
        mSurfaceHolder = surfaceHolder;
        this.mMainView = mMainView;
    }

    public SurfaceHolder getSurfaceHolder() {
        return mSurfaceHolder;
    }

    public void setRunning(boolean run) {
        mRunning = run;
    }

    @Override
    public void run() {
        Log.i("mainThread", "run called:" + mRunning);
        try {
            Canvas c;
            while (mRunning) {
                c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    synchronized (mSurfaceHolder) {
                        try {
                            mMainView.think();
                            mMainView.onDraw(c);
                            Thread.sleep(2);
                        } catch (Exception exTemp) {
                            Log.e("에러", exTemp.toString());
                        }
                    }
                } finally {
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        } catch (Exception exTot) {
            Log.e("에러", exTot.toString());
        }
    }
}
