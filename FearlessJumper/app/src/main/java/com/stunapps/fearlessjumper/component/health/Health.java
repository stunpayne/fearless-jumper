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
        super(Health.class);
        this.health = health;
    }

    public float getHealth()
    {
        return health;
    }

    /**
     * @param damage is amount of damage causing to entity heaving health component.
     * @return returns true if entity's health exhausted, false otherwise.
     */
    public void takeDamage(float damage)
    {
        float newHealth = (health - damage);
        health = (newHealth) > 0 ? newHealth : 0;
    }

    public boolean isOver()
    {
        return health <= 0;
    }
}
