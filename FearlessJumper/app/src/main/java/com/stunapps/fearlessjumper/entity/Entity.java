package com.stunapps.fearlessjumper.entity;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.transform.Transform;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Created by sunny.s on 02/01/18.
 */

@EqualsAndHashCode(exclude = {"componentManager", "entityManager", "transform"})
public class Entity
{
    private final int id;
    @Getter
    public Transform transform;

    private ComponentManager componentManager;
    private EntityManager entityManager;

    public Entity(ComponentManager componentManager, EntityManager entityManager,
                  Transform transform, int id)
    {
        this.id = id;
        this.componentManager = componentManager;
        this.entityManager = entityManager;
        this.transform = transform;
    }

    public void addComponent(Component component)
    {
        componentManager.addComponent(this, component);
    }

    public <C extends Component> C getComponent(Class<C> componentType)
    {
        return componentManager.getComponent(this, componentType);
    }

    public List<Component> getComponents()
    {
        return componentManager.getComponents(this);
    }

    public void removeComponent(Class<? extends Component> componentType)
    {
        componentManager.removeComponent(this, componentType);
    }

    public boolean hasComponent(Class<? extends Component> componentType)
    {
        return componentManager.hasComponent(this, componentType);
    }

    public void delete()
    {
        entityManager.deleteEntity(this);
    }

    public int getId()
    {
        return id;
    }
}
