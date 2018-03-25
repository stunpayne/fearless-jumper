package com.stunapps.fearlessjumper.scene;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.Toast;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.component.Vector2D;
import com.stunapps.fearlessjumper.component.emitter.EternalEmitter.EmitterConfig;
import com.stunapps.fearlessjumper.component.emitter.EternalEmitter.EmitterConfig.Builder;
import com.stunapps.fearlessjumper.component.emitter.Emitter.RenderMode;
import com.stunapps.fearlessjumper.component.emitter.EternalEmitter.EmitterShape;
import com.stunapps.fearlessjumper.core.Bitmaps;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.game.loop.TestView;

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
		try
		{
			int max = textViewIntValue(R.id.maxParticlesValue);
			int life = textViewIntValue(R.id.lifeValue);
			int rate = textViewIntValue(R.id.rateValue);
			float xPosVar = textViewFloatValue(R.id.xPosVarValue);
			float yPosVar = textViewFloatValue(R.id.yPosVarValue);
			float speed = textViewFloatValue(R.id.speedValue);
			float direction = textViewFloatValue(R.id.dirValue);
			float directionVar = textViewFloatValue(R.id.dirVarValue);
			EmitterShape emitterShape = emitterShape();
			Builder configBuilder =
					EmitterConfig.builder().emitterShape(emitterShape).maxParticles(max)
							.particleLife(life).emissionRate(rate)
							.positionVar(new Vector2D(xPosVar, yPosVar)).maxSpeed(speed)
							.direction(direction).directionVar(directionVar);

			RenderMode renderMode = renderMode();
			switch (renderMode)
			{
				case TEXTURE:
					configBuilder.texture(Bitmaps.FIRE_TEXTURE);
					break;
				case SHAPE:
				default:
					//					configBuilder.color(Color.CYAN);
					configBuilder
							.colorLimits(Color.parseColor("#ffe0a307"),
									Color.parseColor("#fff04f0b"));
					break;
			}
			mTestView.restartParticles(configBuilder.build());
		}
		catch (Exception e)
		{
			Log.e(TAG, "Error occurred while rendering particles: " + e.getMessage(), e);
			Toast.makeText(mContext, "Error occurred: " + e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}
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

	private RenderMode renderMode()
	{
		Object dropDownSelected =
				((Spinner) mParticlesMenu.findViewById(R.id.renderMode)).getSelectedItem();
		String emitterTypeString =
				dropDownSelected == null ? RenderMode.SHAPE.name() : dropDownSelected.toString();
		return RenderMode.valueOf(emitterTypeString);
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

			//	Setup emitter type drop down
			Spinner emitterTypeDropDown = particlesMenu.findViewById(R.id.emitterType);
			ArrayAdapter<EmitterShape> emitterShapeArrayAdapter =
					new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item,
							EmitterShape.values());
			emitterShapeArrayAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			emitterTypeDropDown.setAdapter(emitterShapeArrayAdapter);


			//	Setup render mode drop down
			Spinner renderModeDropDown = particlesMenu.findViewById(R.id.renderMode);
			ArrayAdapter<RenderMode> renderModeArrayAdapter =
					new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item,
							RenderMode.values());
			renderModeArrayAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			renderModeDropDown.setAdapter(renderModeArrayAdapter);
		}
	}
}
