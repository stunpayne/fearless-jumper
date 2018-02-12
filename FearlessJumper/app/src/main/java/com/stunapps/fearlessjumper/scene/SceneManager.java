package com.stunapps.fearlessjumper.scene;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anand.verma on 30/12/17.
 */

public interface SceneManager
{
    void start();
    void receiveTouch(MotionEvent motionEvent);
}
