package com.stunapps.fearlessjumper;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.stunapps.fearlessjumper.core.OrientationData;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.game.loop.GameView;
import com.stunapps.fearlessjumper.helper.Constants;
import com.stunapps.fearlessjumper.module.GameModule;
import com.stunapps.fearlessjumper.scene.MainMenuScene;
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
        Constants.activity = this;
        Constants.CURRENT_CONTEXT = this;

        Log.d("CONTEXT", "Context hash code: " + this.hashCode());


        DI.install(new GameModule(this));
        di().getInstance(Systems.class).initialise();

//        setContentView(DI.di().getInstance(GameView.class));
//        setContentView(R.layout.activity_main);
        new MainMenuScene().play();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
//        di().getInstance(OrientationData.class).register();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
//        di().getInstance(OrientationData.class).pause();
    }

    public void requestViewLoad(View view)
    {
        setContentView(view);
        Log.d("CONTENT_VIEW", "Content view changed");
    }

    public void requestViewLoad(@LayoutRes int layoutResId)
    {
        setContentView(layoutResId);
        Log.d("CONTENT_VIEW", "Content view changed");
    }
}
