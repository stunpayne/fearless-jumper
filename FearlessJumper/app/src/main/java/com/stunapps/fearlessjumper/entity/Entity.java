package com.stunapps.fearlessjumper.entity;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.transform.Transform;

import org.apache.commons.collections4.CollectionUtils;
import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by sunny.s on 02/01/18.
 */

@ToString(exclude = {"componentManager", "entityManager", "transform", "children"})
@EqualsAndHashCode(of = "id")
public class Entity
{
	private static final List<Entity> EMPTY_LIST = Lists.newArrayList();

	private final int id;
	public Transform transform;

	private List<Entity> children;
	private Entity parent;

	private ComponentManager componentManager;
	private EntityManager entityManager;

	public Entity(ComponentManager componentManager, EntityManager entityManager,
			Transform transform, int id)
	{
		this.id = id;
		this.componentManager = componentManager;
		this.entityManager = entityManager;
		this.transform = transform;
		this.parent = null;
		this.children = null;
	}

	public void setParent(Entity parent)
	{
		this.parent = parent;
	}

	public void unsetParent()
	{
		this.parent = null;
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

	public <C extends Component> List<C> getAllComponents(Class<C> componentType)
	{
		return componentManager.getAllComponents(this, componentType);
	}

	public <C extends Component> List<C> getComponentsInChildren(Class<C> componentType)
	{
		return componentManager.getComponentsInChildrenRecursive(this, componentType);
	}

	public void removeComponent(Class<? extends Component> componentType)
	{
		componentManager.removeComponent(this, componentType);
	}

	public boolean hasComponent(Class<? extends Component> componentType)
	{
		return componentManager.hasComponent(this, componentType);
	}

	public void addChild(Entity child)
	{
		if (CollectionUtils.isEmpty(children))
		{
			children = new ArrayList<>();
		}

		children.add(child);
		child.setParent(this);
	}

	public void removeChild(Entity child)
	{
		if (CollectionUtils.isNotEmpty(children))
		{
			children.remove(child);
			if (children.isEmpty())
			{
				children = null;
			}
		}
	}

	public List<Entity> getChildren()
	{
		return hasChildren() ? children: EMPTY_LIST;
	}

	public boolean hasParent()
	{
		return (parent != null);
	}

	public boolean hasChildren()
	{
		return CollectionUtils.isNotEmpty(children);
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
