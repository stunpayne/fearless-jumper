package com.stunapps.fearlessjumper.scene;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.component.emitter.EternalEmitter.EmitterShape;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.game.loop.TestView;
import com.stunapps.fearlessjumper.game.loop.TestView.EmitterConfig;

/**
 * Created by sunny.s on 15/03/18.
 */

public class ParticleTestScene extends AbstractScene
{
	private static final String TAG = ParticleTestScene.class.getSimpleName();

	private Context mContext;

	private View mParticlesMenu;
	private TestView mTestView;
	private ViewSetup mViewSetup = new ViewSetup();

	@Inject
	public ParticleTestScene(EventSystem eventSystem, Context context, final TestView mTestView)
	{
		super(new LinearLayout(context), eventSystem);
		this.mTestView = mTestView;
		this.mContext = context;
	}

	@Override
	void setUpScene()
	{
		mParticlesMenu = LayoutInflater.from(mContext).inflate(R.layout.particles_menu, null);
		modifyScene(new SceneModificationCallback()
		{
			@Override
			public Object call() throws Exception
			{
				((LinearLayout) view).setOrientation(LinearLayout.VERTICAL);
				mViewSetup.setupParticlesMenu(mParticlesMenu);

				mParticlesMenu.setFocusable(true);
				((LinearLayout) view).addView(mParticlesMenu);
				((LinearLayout) view).addView(mTestView);
				return null;
			}
		});
	}

	@Override
	void playScene()
	{
		mTestView.start();
		restartView();

		Log.d(TAG, "Playing particles test");
	}

	@Override
	void pauseScene()
	{
		mTestView.pause();
	}

	@Override
	void stopScene()
	{
		mTestView.stop();
	}

	@Override
	void resumeScene()
	{
		mTestView.resume();
	}

	@Override
	void terminateScene()
	{
		mTestView.terminate();
	}

	private void restartView()
	{

		int max = textViewIntValue(R.id.maxParticlesValue);
		int life = textViewIntValue(R.id.lifeValue);
		int rate = textViewIntValue(R.id.rateValue);
		float positionVar = textViewFloatValue(R.id.posVarValue);
		float speed = textViewFloatValue(R.id.speedValue);
		float direction = textViewFloatValue(R.id.dirValue);
		float directionVar = textViewFloatValue(R.id.dirVarValue);
		mTestView.restartParticles(
				new EmitterConfig(emitterShape(), max, life, rate, positionVar, speed, direction,
						directionVar));
	}

	private int textViewIntValue(@IdRes int id)
	{
		return Integer.parseInt(((TextView) mParticlesMenu.findViewById(id)).getText().toString());
	}

	private float textViewFloatValue(@IdRes int id)
	{
		return Float.parseFloat(((TextView) mParticlesMenu.findViewById(id)).getText().toString());
	}

	private EmitterShape emitterShape()
	{
		Object dropDownSelected =
				((Spinner) mParticlesMenu.findViewById(R.id.emitterType)).getSelectedItem();
		String emitterTypeString =
				dropDownSelected == null ? EmitterShape.CONE_DIVERGE.name() : dropDownSelected
						.toString();
		return EmitterShape.valueOf(emitterTypeString);
	}

	private class ViewSetup
	{
		void setupParticlesMenu(View particlesMenu)
		{
			Button renderButton = particlesMenu.findViewById(R.id.render);
			renderButton.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					restartView();
				}
			});

			Spinner emitterTypeDropDown = particlesMenu.findViewById(R.id.emitterType);
			ArrayAdapter<EmitterShape> adapter =
					new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item,
							EmitterShape.values());
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			emitterTypeDropDown.setAdapter(adapter);
		}
	}
}
