package com.stunapps.fearlessjumper.system;

import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.system.input.InputSystem;
import com.stunapps.fearlessjumper.system.update.UpdateSystem;

import static com.stunapps.fearlessjumper.di.DI.di;

/**
 * Created by sunny.s on 10/01/18.
 */

@Singleton
class SystemFactory
{
    UpdateSystem getUpdateSystem(Class<? extends UpdateSystem> systemType)
    {
        return di().getInstance(systemType);
    }

    InputSystem getInputSystem(Class<? extends InputSystem> systemType)
    {
        return di().getInstance(systemType);
    }
}
