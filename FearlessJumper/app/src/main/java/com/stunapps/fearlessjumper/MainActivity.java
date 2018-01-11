package com.stunapps.fearlessjumper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;

import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.gameloop.GameView;
import com.stunapps.fearlessjumper.helper.Constants;
import com.stunapps.fearlessjumper.module.GameModule;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;

        Log.d("CONTEXT", "Context hash code: " + this.hashCode());
        DI.install(new GameModule(this));
        setContentView(di().getInstance(GameView.class));
    }
}
