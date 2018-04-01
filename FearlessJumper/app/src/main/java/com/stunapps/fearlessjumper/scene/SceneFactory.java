package com.stunapps.fearlessjumper.scene;

import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.game.loop.TestView;
import com.stunapps.fearlessjumper.system.update.ParticleSystem;
import com.stunapps.fearlessjumper.system.update.RenderSystem;

/**
 * Created by sunny.s on 01/04/18.
 */

@Singleton
public class SceneFactory
{
	private final Context mContext;
	private final EntityManager mEntityManager;

	private final EventSystem mEventSystem;
	private final ParticleSystem mParticleSystem;
	private final RenderSystem mRenderSystem;

	@Inject
	public SceneFactory(Context mContext, EntityManager mEntityManager, EventSystem eventSystem,
			ParticleSystem mParticleSystem, RenderSystem mRenderSystem)
	{
		this.mContext = mContext;
		this.mEntityManager = mEntityManager;
		this.mEventSystem = eventSystem;

		this.mParticleSystem = mParticleSystem;
		this.mRenderSystem = mRenderSystem;
	}

	public Scene get(Class<? extends Scene> sceneType)
	{
		if (sceneType.equals(ParticleTestScene.class))
		{
			return new ParticleTestScene(mEventSystem, mContext,
					new TestView(mContext, mParticleSystem, mRenderSystem, mEntityManager));
		}
		return null;
	}
}
