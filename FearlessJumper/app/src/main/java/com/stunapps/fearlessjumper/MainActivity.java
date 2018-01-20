package com.stunapps.fearlessjumper;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.stunapps.fearlessjumper.core.OrientationData;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.game.loop.GameView;
import com.stunapps.fearlessjumper.helper.Constants;
import com.stunapps.fearlessjumper.module.GameModule;
import com.stunapps.fearlessjumper.system.Systems;

import static com.stunapps.fearlessjumper.di.DI.di;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        Constants.DISPLAY_DENSITY = getResources().getDisplayMetrics().density;

        Log.d("CONTEXT", "Context hash code: " + this.hashCode());


        DI.install(new GameModule(this));
        di().getInstance(Systems.class).initialise();

        setContentView(DI.di().getInstance(GameView.class));
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        di().getInstance(OrientationData.class).register();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        di().getInstance(OrientationData.class).pause();
    }
}
