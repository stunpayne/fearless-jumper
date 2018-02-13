package com.stunapps.fearlessjumper.scene;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.event.BaseEventInfo;
import com.stunapps.fearlessjumper.event.Event;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.helper.Constants;

import static com.stunapps.fearlessjumper.scene.Scene.ViewLoader.requestViewLoad;

/**
 * Created by sunny.s on 11/02/18.
 */

@Singleton
public class MainMenuScene extends AbstractScene
{
	private final EventSystem eventSystem;

	@Inject
	public MainMenuScene(EventSystem eventSystem)
	{
		super(R.layout.main_menu);
		this.eventSystem = eventSystem;
		setupView();
	}

	@Override
	public void play()
	{
		//Set a Rect for the 200 x 200 px center of a 400 x 400 px area
		Rect rect = new Rect();
		rect.set(100, 100, 300, 300);

		//Allocate a new Bitmap at 400 x 400 px
		Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
		Canvas canvas = Constants.canvas;

		//Make a new view and lay it out at the desired Rect dimensions
		final TextView view = new TextView(Constants.CURRENT_CONTEXT);
		view.setText("This is a custom drawn textview");
		view.setBackgroundColor(Color.RED);
		view.setGravity(Gravity.CENTER);

		view.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				requestViewLoad(R.layout.activity_main);
			}
		});

		//Measure the view at the exact dimensions (otherwise the text won't center correctly)
		int widthSpec = View.MeasureSpec.makeMeasureSpec(rect.width(), View.MeasureSpec.EXACTLY);
		int heightSpec = View.MeasureSpec.makeMeasureSpec(rect.height(), View.MeasureSpec.EXACTLY);
		view.measure(widthSpec, heightSpec);

		//Lay the view out at the rect width and height
		view.layout(0, 0, rect.width(), rect.height());

		//Translate the Canvas into position and draw it
		//		canvas.save();
		//		canvas.translate(rect.left, rect.top);
		//		view.draw(canvas);
		//		canvas.restore();

		//To make sure it works, set the bitmap to an ImageView
		ImageView imageView = new ImageView(Constants.CURRENT_CONTEXT);
		imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
															 ViewGroup.LayoutParams.MATCH_PARENT));
		imageView.setScaleType(ImageView.ScaleType.CENTER);
		imageView.setImageBitmap(bitmap);

		//		ViewLoader.requestViewLoad(view);
		//		ViewLoader.requestViewLoad(R.layout.activity_main);
		Constants.CURRENT_CONTEXT.getResources().getLayout(R.layout.activity_main);
		//		Constants.canvas.drawRGB(255, 167, 203);
	}

	@Override
	public void terminate()
	{

	}

	@Override
	public void receiveTouch(MotionEvent motionEvent)
	{

	}

	@Override
	protected void setupView()
	{
		TextView textView = view.findViewById(R.id.textView);
		Log.d("MAIN_MENU", textView.getText().toString());
		textView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				eventSystem.raiseEvent(Event.START_GAME, new BaseEventInfo());
			}
		});
	}
}
