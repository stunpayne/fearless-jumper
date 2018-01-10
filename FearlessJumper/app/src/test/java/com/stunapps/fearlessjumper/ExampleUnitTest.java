package com.stunapps.fearlessjumper;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.damage.AreaDamageComponent;
import com.stunapps.fearlessjumper.component.damage.ContactDamageComponent;
import com.stunapps.fearlessjumper.component.damage.DamageComponent;

import org.junit.Test;
import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<Class<? extends Component>, List<GameObject>> map = new HashMap<>();

        GameObject gameObject = new GameObject(1);
        GameObject gameObject2 = new GameObject(2);
        ContactDamageComponent contactDamageComponent = new ContactDamageComponent(10);
        AreaDamageComponent areaDamageComponent = new AreaDamageComponent(10);
        map.put(contactDamageComponent.componentType, Lists.newArrayList(gameObject));
        map.get(DamageComponent.class).add(gameObject2);

        List<GameObject> gameObjects = map.get(DamageComponent.class);
        System.out.println(Arrays.toString(gameObjects.toArray()));
    }
}