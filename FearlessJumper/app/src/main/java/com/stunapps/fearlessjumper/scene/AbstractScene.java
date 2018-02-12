package com.stunapps.fearlessjumper.scene;

import android.util.Log;
import android.view.View;

import lombok.Getter;
import lombok.Setter;

import static com.stunapps.fearlessjumper.scene.Scene.ViewLoader.requestViewLoad;

/**
 * Created by sunny.s on 12/02/18.
 */

public abstract class AbstractScene implements Scene
{
	@Getter
	protected int id;

	@Getter
	@Setter
	ViewInfo viewInfo;

	public AbstractScene(int id, View view)
	{
		this.id = id;
		this.viewInfo = new ViewInfo(view);
	}

	public AbstractScene(int id, int layoutResId)
	{
		this.id = id;
		this.viewInfo = new ViewInfo(layoutResId);
	}

	@Override
	public final void setActive()
	{
		try
		{
			switch (viewInfo.getViewInfoType())
			{
				case LAYOUT_RES_ID:
					requestViewLoad(viewInfo.layoutResId);
					break;
				case VIEW_INSTANCE:
					requestViewLoad(viewInfo.view);
					break;
				default:
					throw new Exception("Incorrect View Type");
			}
		}
		catch (Exception e)
		{
			Log.e("VIEW_LOAD", "View could not be loaded!");
		}
	}

	@Getter
	public class ViewInfo
	{
		private ViewInfoType viewInfoType;
		private View view;
		private int layoutResId;

		public ViewInfo(View view)
		{
			this.viewInfoType = ViewInfoType.VIEW_INSTANCE;
			this.view = view;
		}

		public ViewInfo(int layoutResId)
		{
			this.viewInfoType = ViewInfoType.LAYOUT_RES_ID;
			this.layoutResId = layoutResId;
		}
	}

	private enum ViewInfoType
	{
		LAYOUT_RES_ID, VIEW_INSTANCE;
	}
}
