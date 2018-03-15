package com.stunapps.fearlessjumper.scene;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.game.loop.TestView;
import com.stunapps.fearlessjumper.system.update.RenderSystem;

/**
 * Created by sunny.s on 15/03/18.
 */

public class ParticleTestScene extends AbstractScene
{
	private static final String TAG = ParticleTestScene.class.getSimpleName();

	private TestView testView;

	@Inject
	public ParticleTestScene(EventSystem eventSystem, Context context, final TestView testView)
	{
		super(new FrameLayout(context), eventSystem);
		this.testView = testView;
	}

	@Override
	void setUpScene()
	{
		modifyScene(new SceneModificationCallback()
		{
			@Override
			public Object call() throws Exception
			{
				((FrameLayout) view).addView(testView);
				return null;
			}
		});
	}

	@Override
	void playScene()
	{
		testView.start();
		Log.d(TAG, "Playing particles test");
	}

	@Override
	void pauseScene()
	{
		testView.pause();
	}

	@Override
	void stopScene()
	{
		testView.stop();
		;
	}

	@Override
	void resumeScene()
	{
		testView.resume();
	}

	@Override
	void terminateScene()
	{
		testView.terminate();
	}
}
