package com.stunapps.fearlessjumper.component;

import com.stunapps.fearlessjumper.entity.Entity;

/**
 * Created by sunny.s on 04/01/18.
 */

public interface ComponentManager<E extends Entity>
{
    public <C extends Component> void addComponent(E gameObject, C componentType);

    public void deleteComponent(Entity gameObject, Class<? extends Component> componentType);

    public Component getComponent(E gameObject, Class<? extends Component> componentType);
}
