package com.stunapps.fearlessjumper.init;

/**
 * Created by sunny.s on 12/01/18.
 */

public interface GameInitializer
{
    boolean isInitialized();

    void initialize();

    void destroy();
}
