package com.stunapps.fearlessjumper.helper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.ads.AdManager;
import com.stunapps.fearlessjumper.scene.Action;
import com.stunapps.fearlessjumper.view.BaseView;

/**
 * Created by sunny.s on 07/04/18.
 */

public class UIBridge
{
	private final static String TAG = UIBridge.class.getSimpleName();
	private final Handler mHandler;
	private final AdManager mAdManager;

	@Inject
	public UIBridge(AdManager mAdManager)
	{
		this.mAdManager = mAdManager;
		this.mHandler = new Handler(Looper.getMainLooper())
		{
			@Override
			public void handleMessage(Message msg)
			{
				Log.d(TAG, "New message received: " + msg);
				super.handleMessage(msg);

				View view = (View) msg.obj;

				switch (msg.what)
				{
					case Action.SHOW:
						view.setVisibility(View.VISIBLE);
						break;
					case Action.HIDE:
						view.setVisibility(View.GONE);
						break;
					case Action.KILL:
						BaseView baseView = (BaseView) view;
						baseView.stop();
						baseView.terminate();
						break;
					case Action.SHOW_AD:
						UIBridge.this.mAdManager.showAd();
				}
			}
		};
	}

	public final void showView(View v)
	{
		mHandler.sendMessage(mHandler.obtainMessage(Action.SHOW, v));
	}

	public final void hideView(View v)
	{
		mHandler.sendMessage(mHandler.obtainMessage(Action.HIDE, v));
	}

	public final void killScene(BaseView baseView)
	{
		mHandler.sendMessage(mHandler.obtainMessage(Action.KILL, baseView));
	}

	public final void showAd()
	{
		mHandler.sendMessage(mHandler.obtainMessage(Action.SHOW_AD));
	}
}
