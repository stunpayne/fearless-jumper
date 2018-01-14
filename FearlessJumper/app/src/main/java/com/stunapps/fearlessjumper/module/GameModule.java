package com.stunapps.fearlessjumper.module;

import android.content.Context;
import android.hardware.SensorEventListener;
import android.util.Log;

import com.google.inject.AbstractModule;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.GameComponentManager;
import com.stunapps.fearlessjumper.core.OrientationData;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.game.init.GameInitializer;
import com.stunapps.fearlessjumper.game.init.GameInitializerImpl;
import com.stunapps.fearlessjumper.system.Systems;

import static com.stunapps.fearlessjumper.di.DI.di;

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
        bind(ComponentManager.class).to(GameComponentManager.class);
        bind(Context.class).toInstance(context);
        bind(GameInitializer.class).to(GameInitializerImpl.class);
    }
}
