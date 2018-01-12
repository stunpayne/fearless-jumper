package com.stunapps.fearlessjumper.module;

import android.content.Context;
import android.util.Log;

import com.google.inject.AbstractModule;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.GameComponentManager;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.entity.EntityManager;

/**
 * Created by sunny.s on 10/01/18.
 */

public class GameModule extends AbstractModule
{
    private Context context;

    public GameModule(Context context)
    {
        this.context = context;
        Log.d("CONTEXT", this.getClass() + " Context hash code: " + context.hashCode());
    }

    @Override
    protected void configure()
    {
        bind(ComponentManager.class).toInstance(new GameComponentManager());
        //bind(EntityManager.class).toInstance(new EntityManager(DI.di().getInstance(GameComponentManager.class)));
        bind(Context.class).toInstance(context);
    }
}
