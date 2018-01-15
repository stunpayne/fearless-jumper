package com.stunapps.fearlessjumper;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.GameComponentManager;
import com.stunapps.fearlessjumper.component.collider.Collider;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.health.Health;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest
{
    @Test
    public void addition_isCorrect() throws Exception
    {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testMap() throws Exception
    {
/*        Map<Class<? extends Component>, List<Entity>> map = new HashMap<>();

        Entity entity = new Entity(1);
        Entity entity2 = new Entity(2);
        ContactDamageComponent contactDamageComponent = new ContactDamageComponent(10);
        AreaDamageComponent areaDamageComponent = new AreaDamageComponent(10);
        map.put(contactDamageComponent.componentType, Lists.newArrayList(Entity));
        map.get(DamageComponent.class).add(entity2);

        List<Entity> gameObjects = map.get(DamageComponent.class);
        System.out.println(Arrays.toString(gameObjects.toArray()));*/
    }

    @Test
    public void testComponentAndEntityManager()
    {
        ComponentManager cm = new GameComponentManager();
        EntityManager em = new EntityManager(cm);

        Component component = new PhysicsComponent(15, new PhysicsComponent.Velocity(10, 10));
        Entity entity = em.createEntity(new Transform(null, null, null));
        entity.addComponent(component);
        Component component1 = entity.getComponent(PhysicsComponent.class);
        List<Component> components = entity.getComponents();
        entity.hasComponent(PhysicsComponent.class);
        entity.removeComponent(PhysicsComponent.class);
        entity.delete();
        cm.getEntities(PhysicsComponent.class);
        em.getEntities();
    }

    @Test
    public void test()
    {

        //Component component = new Health(100);
        //assertFalse(component.componentType == PhysicsComponent.class);
        //assertTrue(component.componentType == Health.class);
    }
}