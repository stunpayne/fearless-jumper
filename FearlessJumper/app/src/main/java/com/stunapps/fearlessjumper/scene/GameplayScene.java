package com.stunapps.fearlessjumper.scene;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.audio.SoundSystem;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.game.GameContext;
import com.stunapps.fearlessjumper.game.loop.GameView;
import com.stunapps.fearlessjumper.helper.Environment;
import com.stunapps.fearlessjumper.scene.AbstractScene;

import javax.inject.Inject;


/**
 * Created by sunny.s on 12/02/18.
 */

public class GameplayScene extends AbstractScene
{
	@Inject
	public GameplayScene(EventSystem eventSystem)
	{
		super(R.layout.game_play_container, eventSystem);
	}

	@Override
	void setUpScene()
	{
		LayoutInflater inflater = LayoutInflater.from(Environment.CONTEXT);
		LinearLayout hud = (LinearLayout) inflater.inflate(R.layout.hud, null);
		GameView gameView = DI.di().getInstance(GameView.class);
		((FrameLayout)view).addView(gameView);
		((FrameLayout)view).addView(hud);
	}

	@Override
	public void playScene()
	{

	}

	@Override
	public void terminateScene()
	{

	}
}
