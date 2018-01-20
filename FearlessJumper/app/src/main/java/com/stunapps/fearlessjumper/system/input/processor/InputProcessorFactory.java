package com.stunapps.fearlessjumper.system.input.processor;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;

/**
 * Created by sunny.s on 20/01/18.
 */

@Singleton
public class InputProcessorFactory
{
    Provider<PlayerInputProcessor> playerProcessorProvider;

    @Inject
    public InputProcessorFactory(
            Provider<PlayerInputProcessor> playerProcessorProvider)
    {
        this.playerProcessorProvider = playerProcessorProvider;
    }

    public InputProcessor get(Class<? extends Component> componentType)
    {
        if (componentType == PlayerComponent.class)
            return playerProcessorProvider.get();
        return null;
    }
}
