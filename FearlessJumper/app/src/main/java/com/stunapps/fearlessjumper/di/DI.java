package com.stunapps.fearlessjumper.di;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Created by sunny.s on 10/01/18.
 */

public class DI
{
    private static Injector INJECTOR = Guice.createInjector(new Module[0]);

    public DI()
    {
    }

    public static void install(Module module)
    {
        synchronized (DI.class)
        {
            INJECTOR = Guice.createInjector(module);
        }
    }

    public static Injector di()
    {
        return INJECTOR;
    }
}
