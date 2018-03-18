package com.stunapps.fearlessjumper.system.input;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.system.Systems;

/**
 * Created by sunny.s on 18/03/18.
 */

public class GestureListener extends GestureDetector.SimpleOnGestureListener
{
	private static final String TAG = GestureListener.class.getSimpleName();

	@Inject
	public GestureListener()
	{
		super();
	}

	@Override
	public boolean onDown(MotionEvent e)
	{
		super.onDown(e);
		Log.d(TAG, "On Show Press");
		Systems.processInput(e);
		return true;
	}


}
