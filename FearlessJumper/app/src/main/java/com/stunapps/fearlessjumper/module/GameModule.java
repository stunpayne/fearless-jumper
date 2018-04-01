package com.stunapps.fearlessjumper.module;

import android.content.Context;

import com.google.inject.AbstractModule;
import com.stunapps.fearlessjumper.MainActivity;
import com.stunapps.fearlessjumper.ads.AdManager;
import com.stunapps.fearlessjumper.ads.AdManagerImpl;
import com.stunapps.fearlessjumper.audio.SoundEffectPlayer;
import com.stunapps.fearlessjumper.audio.SoundEffectPlayerImpl;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.GameComponentManager;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.impl.GameEventSystem;
import com.stunapps.fearlessjumper.game.init.GameInitializer;
import com.stunapps.fearlessjumper.game.init.GameInitializerImpl;
import com.stunapps.fearlessjumper.helper.EntityTransformCalculator;
import com.stunapps.fearlessjumper.helper.EntityTransformCalculatorImpl;
import com.stunapps.fearlessjumper.scene.LazySceneManager;
import com.stunapps.fearlessjumper.scene.SceneManager;
import com.stunapps.fearlessjumper.scene.SceneManagerImpl;
import com.stunapps.fearlessjumper.system.input.processor.InputProcessor;
import com.stunapps.fearlessjumper.system.input.processor.PlayerInputProcessor;
import com.stunapps.fearlessjumper.system.update.InputUpdateManager;
import com.stunapps.fearlessjumper.system.update.InputUpdateSystem;

/**
 * Created by sunny.s on 10/01/18.
 */

public class GameModule extends AbstractModule
{
	private Context context;
	private MainActivity mainActivity;

	public GameModule(Context context)
	{
		this.context = context;
	}

	@Override
	protected void configure()
	{
		bind(InputProcessor.class).to(PlayerInputProcessor.class);
		bind(ComponentManager.class).to(GameComponentManager.class);
		bind(Context.class).toInstance(context);
		bind(SceneManager.class).to(LazySceneManager.class).asEagerSingleton();
		bind(GameInitializer.class).to(GameInitializerImpl.class);
		bind(AdManager.class).to(AdManagerImpl.class);

		bind(EventSystem.class).to(GameEventSystem.class).asEagerSingleton();
		bind(SoundEffectPlayer.class).to(SoundEffectPlayerImpl.class).asEagerSingleton();

		bind(EntityTransformCalculator.class).to(EntityTransformCalculatorImpl.class)
				.asEagerSingleton();
		bind(InputUpdateManager.class).to(InputUpdateSystem.class).asEagerSingleton();
	}
}
