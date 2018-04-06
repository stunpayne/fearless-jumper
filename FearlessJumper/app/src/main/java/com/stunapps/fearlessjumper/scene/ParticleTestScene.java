package com.stunapps.fearlessjumper.scene;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.support.annotation.IdRes;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.component.Vector2D;
import com.stunapps.fearlessjumper.component.emitter.Emitter.EmitterConfig;
import com.stunapps.fearlessjumper.component.emitter.Emitter.EmitterConfig.Builder;
import com.stunapps.fearlessjumper.component.emitter.Emitter.EmitterShape;
import com.stunapps.fearlessjumper.component.emitter.Emitter.RenderMode;
import com.stunapps.fearlessjumper.core.Bitmaps;
import com.stunapps.fearlessjumper.event.system.EventSystem;
import com.stunapps.fearlessjumper.view.TestView;

import java.util.Arrays;
import java.util.List;

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
			EmitterShape emitterShape = getSpinnerEnumValue(R.id.emitterType, EmitterShape.class,
					EmitterShape.CONE_DIVERGE);
			Mode blendMode = getSpinnerEnumValue(R.id.blendMode, Mode.class, Mode.DST_OVER);
			int[] startC = parseColor(R.id.startColorValue);
			int[] endC = parseColor(R.id.endColorValue);
			int size = textViewIntValue(R.id.sizeValue);


			Builder configBuilder =
					EmitterConfig.builder().emitterShape(emitterShape).maxParticles(max)
							.particleLife(life).emissionRate(rate)
							.positionVar(new Vector2D(xPosVar, yPosVar)).maxSpeed(speed)
							.direction(direction).directionVar(directionVar)
							.blendingMode(blendMode);

			RenderMode renderMode =
					getSpinnerEnumValue(R.id.renderMode, RenderMode.class, RenderMode.SHAPE);
			switch (renderMode)
			{
				case TEXTURE:
					configBuilder.texture(Bitmaps.FIRE_TEXTURE);
					break;
				case SHAPE:
				default:
					configBuilder
							.colorLimits(Color.argb(startC[0], startC[1], startC[2], startC[3]),
									Color.argb(endC[0], endC[1], endC[2], endC[3])).size(size);
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
		return Integer.parseInt(((EditText) mParticlesMenu.findViewById(id)).getText().toString());
	}

	private float textViewFloatValue(@IdRes int id)
	{
		return Float.parseFloat(((EditText) mParticlesMenu.findViewById(id)).getText().toString());
	}

	private int[] parseColor(@IdRes int id)
	{
		String colorString = ((EditText) mParticlesMenu.findViewById(id)).getText().toString();

		if (null == colorString || colorString.isEmpty())
			throw new IllegalArgumentException("Incorrect value of color String");

		int[] argb = new int[4];
		String[] splits = colorString.split(",");
		if (splits.length < 4)
			throw new IllegalArgumentException("Incorrect number of values in color");

		argb[0] = Integer.parseInt(splits[0]);
		argb[1] = Integer.parseInt(splits[1]);
		argb[2] = Integer.parseInt(splits[2]);
		argb[3] = Integer.parseInt(splits[3]);

		return argb;
	}

	private <C extends Enum<C>> C getSpinnerEnumValue(@IdRes int id, Class<C> enumClass,
			C defaultValue)
	{
		Object selectedItem = ((Spinner) mParticlesMenu.findViewById(id)).getSelectedItem();
		String string = selectedItem == null ? defaultValue.name() : selectedItem.toString();
		try
		{
			return Enum.valueOf(enumClass, string);
		}
		catch (IllegalArgumentException e)
		{
			Log.d(TAG, "Exception Type =  " + enumClass.getName() + " and value = " + string, e);
		}
		catch (NullPointerException e)
		{
			Log.d(TAG, "Exception Type =  " + enumClass + " and value = " + string, e);
		}
		return defaultValue;
	}

	private class ViewSetup
	{
		void setupParticlesMenu(View particlesMenu)
		{
			List<Integer> numberInputIds =
					Arrays.asList(R.id.maxParticlesValue, R.id.lifeValue, R.id.rateValue,
							R.id.xPosVarValue, R.id.yPosVarValue, R.id.speedValue, R.id.dirValue,
							R.id.dirVarValue);
			for (Integer id : numberInputIds)
			{
				((EditText) particlesMenu.findViewById(id)).setRawInputType(
						InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED |
								InputType.TYPE_NUMBER_FLAG_DECIMAL);
			}

			Button renderButton = particlesMenu.findViewById(R.id.render);
			renderButton.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					restartView();
				}
			});

			fillSpinnerEnumValues(particlesMenu, R.id.emitterType, EmitterShape.class);
			fillSpinnerEnumValues(particlesMenu, R.id.renderMode, RenderMode.class);
			fillSpinnerEnumValues(particlesMenu, R.id.blendMode, PorterDuff.Mode.class);
		}

		private <C extends Enum<C>> void fillSpinnerEnumValues(View view, @IdRes int id,
				Class<C> enumClass)
		{
			Spinner spinner = view.findViewById(id);
			ArrayAdapter<C> arrayAdapter =
					new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item,
							enumClass.getEnumConstants());
			arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(arrayAdapter);
		}
	}
}
