package com.stunapps.fearlessjumper.module;

import com.google.inject.AbstractModule;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.GameComponentManager;

/**
 * Created by sunny.s on 10/01/18.
 */

public class GameModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(ComponentManager.class).toInstance(new GameComponentManager());
    }
}
