package chanjin.flightplan;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {

    MainView mainView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        int ScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int ScreenHeight = getWindowManager().getDefaultDisplay().getHeight();
        mainView = (MainView) findViewById(R.id.main_view);

        Log.e("Start", "Start...");
        mainView.init(ScreenWidth, ScreenHeight, this);
    }
}
