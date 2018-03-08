package com.stunapps.fearlessjumper;

import android.content.Context;

import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.core.Bitmaps;
import com.stunapps.fearlessjumper.helper.Environment;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.prefab.Prefab;
import com.stunapps.fearlessjumper.prefab.Prefabs;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Random;

/**
 * Created by sunny.s on 09/03/18.
 */

public class PrefabSetTest
{
	private final Random random = new Random();
	@Mock private Context context;

	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testOriginTranslate()
	{
		Transform oldT = new Transform(new Position(random.nextInt(), random.nextInt()));

		Transform newOrigin = new Transform(new Position(random.nextInt(), random.nextInt()));

		Transform newT = oldT.translateOrigin(newOrigin);

		Assert.assertTrue("Origin x translation failed",
				oldT.getPosition().getX() + newOrigin.getPosition().getX() ==
						newT.getPosition().getX());

		Assert.assertTrue("Origin y translation failed",
				oldT.getPosition().getY() + newOrigin.getPosition().getY() ==
						newT.getPosition().getY());

		Environment.CONTEXT = context;
		Bitmaps.initialise();

		Prefab prefab = Prefabs.DRAGON.get();
		System.out.println(prefab);
	}
}
