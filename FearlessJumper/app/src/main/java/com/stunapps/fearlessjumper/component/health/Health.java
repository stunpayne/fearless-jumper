package com.stunapps.fearlessjumper.component.health;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 03/01/18.
 */

public class Health extends Component
{
    private float health;

    public Health(float health)
    {
        this.health = health;
        this.componentType = Health.class;
    }

    public float getHealth()
    {
        return health;
    }
}
