package com.stunapps.fearlessjumper.component.spawnable;

/**
 * Created by sunny.s on 10/02/18.
 */

public class Dragon extends Enemy
{
    public Dragon(EnemyType enemyType)
    {
        super(enemyType);
    }

    @Override
    public Dragon clone() throws CloneNotSupportedException
    {
        return new Dragon(enemyType);
    }
}
