package com.stunapps.fearlessjumper.scene;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.stunapps.fearlessjumper.MainActivity;

/**
 * Created by sunny.s on 21/01/18.
 */

public interface Scene
{
	void setup();

	void play();

	void pause();

	void resume();

	void stop();

	void terminate();

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
