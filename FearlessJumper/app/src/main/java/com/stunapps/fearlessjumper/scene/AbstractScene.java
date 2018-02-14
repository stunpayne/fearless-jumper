package com.stunapps.fearlessjumper.scene;

import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.helper.Constants;

import lombok.Getter;
import lombok.Setter;

import static com.stunapps.fearlessjumper.scene.Scene.ViewLoader.requestViewLoad;

/**
 * Created by sunny.s on 12/02/18.
 */

public abstract class AbstractScene implements Scene
{
	@Getter
	@Setter
	View view;

	public AbstractScene(View view)
	{
		this.view = view;
	}

	public AbstractScene(@LayoutRes int layoutResId)
	{
		this.view = LayoutInflater.from(Constants.CURRENT_CONTEXT).inflate(layoutResId, null);
	}

	@Override
	public final void setActive()
	{
		try
		{
			requestViewLoad(view);
		}
		catch (Exception e)
		{
			Log.e("VIEW_LOAD", "View could not be loaded!");
		}
	}

	protected abstract void setupScene();
}
