package com.stunapps.fearlessjumper.component.specific;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 21/01/18.
 */

public class EnemyComponent extends Component
{
    public EnemyComponent()
    {
        super(EnemyComponent.class);
    }

    @Override
    public EnemyComponent clone() throws CloneNotSupportedException
    {
        return new EnemyComponent();
    }
}
