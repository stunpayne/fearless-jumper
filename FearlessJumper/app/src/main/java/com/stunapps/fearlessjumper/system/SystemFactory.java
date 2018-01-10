package com.stunapps.fearlessjumper.system;

import com.google.inject.Singleton;

import static com.stunapps.fearlessjumper.di.DI.di;

/**
 * Created by sunny.s on 10/01/18.
 */

@Singleton
public class SystemFactory
{
    public System get(Class<? extends System> systemType)
    {
        return di().getInstance(systemType);
    }
}
