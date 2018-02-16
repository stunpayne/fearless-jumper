package com.stunapps.fearlessjumper.module;

import android.content.Context;

import com.google.inject.AbstractModule;
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
import com.stunapps.fearlessjumper.scene.SceneManager;
import com.stunapps.fearlessjumper.scene.SceneManagerImpl;

/**
 * Created by sunny.s on 10/01/18.
 */

public class GameModule extends AbstractModule
{
	private Context context;

	public GameModule(Context context)
	{
		this.context = context;
	}

	@Override
	protected void configure()
	{
		bind(ComponentManager.class).to(GameComponentManager.class);
		bind(Context.class).toInstance(context);
		bind(GameInitializer.class).to(GameInitializerImpl.class);
		bind(EntityTransformCalculator.class).to(EntityTransformCalculatorImpl.class)
				.asEagerSingleton();
		bind(SceneManager.class).to(SceneManagerImpl.class).asEagerSingleton();
		bind(EventSystem.class).to(GameEventSystem.class).asEagerSingleton();
		bind(SoundEffectPlayer.class).to(SoundEffectPlayerImpl.class).asEagerSingleton();
	}
}
