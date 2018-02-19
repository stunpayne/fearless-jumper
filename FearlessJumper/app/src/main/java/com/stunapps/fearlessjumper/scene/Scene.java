package com.stunapps.fearlessjumper.scene;

import android.graphics.Canvas;
import android.support.annotation.LayoutRes;
import android.view.MotionEvent;
import android.view.View;

import com.stunapps.fearlessjumper.MainActivity;
import com.stunapps.fearlessjumper.core.State;

/**
 * Created by sunny.s on 21/01/18.
 */

public interface Scene
{
    void setup();
    void play();
    void terminate();


    void receiveTouch(MotionEvent motionEvent);
    void setActive();
    //destroy()

    class ViewLoader
    {
        public static void requestViewLoad(View view)
        {
            MainActivity.getInstance().getLoadViewCallback(view).call();
        }

        public static void requestViewLoad(@LayoutRes int layoutResId)
        {
            MainActivity.getInstance().getLoadViewCallback(layoutResId).call();
        }
    }
}
