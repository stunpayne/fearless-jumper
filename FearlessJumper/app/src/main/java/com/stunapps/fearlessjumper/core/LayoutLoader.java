package com.stunapps.fearlessjumper.core;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.stunapps.fearlessjumper.MainActivity;
import com.stunapps.fearlessjumper.helper.Constants;

/**
 * Created by sunny.s on 11/02/18.
 */

public class LayoutLoader
{
	public static void requestViewLoad(View view)
	{
		((MainActivity) Constants.activity).requestViewLoad(view);
	}

	public static void requestViewLoad(@LayoutRes int layoutResId)
	{
		((MainActivity) Constants.activity).requestViewLoad(layoutResId);
	}
}
