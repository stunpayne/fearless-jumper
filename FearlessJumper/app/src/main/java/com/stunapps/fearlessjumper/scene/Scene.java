package com.stunapps.fearlessjumper.scene;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by sunny.s on 21/01/18.
 */

public interface Scene
{
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void receiveTouch(MotionEvent motionEvent);
}
