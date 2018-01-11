package com.stunapps.fearlessjumper.module;

import android.content.Context;
import android.util.Log;

import com.google.inject.AbstractModule;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.GameComponentManager;

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
        bind(Context.class).toInstance(context);
    }
}
