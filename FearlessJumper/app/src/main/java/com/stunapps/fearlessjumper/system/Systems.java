package com.stunapps.fearlessjumper.system;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sunny.s on 10/01/18.
 */

@Singleton
public class Systems
{
    private final SystemFactory systemFactory;
    private final List<Class<? extends System>> systemOrder = Arrays.asList(
            CollisionSystem.class,
            RenderSystem.class
    );

    private static ArrayList<System> systemsInOrder = Lists.newArrayList();

    @Inject
    public Systems(SystemFactory systemFactory)
    {
        this.systemFactory = systemFactory;
        for (Class<? extends System> systemType : systemOrder)
        {
            this.systemsInOrder.add(systemFactory.get(systemType));
        }
    }

    public static void process()
    {
        for (System system : systemsInOrder)
        {
            system.process();
        }
    }
}
