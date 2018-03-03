package com.stunapps.fearlessjumper;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.core.ObjectPool;

import org.junit.Test;

import java.util.Random;

/**
 * Created by anand.verma on 03/03/18.
 */

public class ObjectPoolTest
{
	@Test
	public void testObjectPool(){
		final Random random = new Random();
		ObjectPool<Integer> integerPool = new ObjectPool<Integer>(5, 9)
		{
			@Override
			protected Integer createObject()
			{
				return random.nextInt(100);
			}
		};

		System.out.println(integerPool);

		Integer intVar1 = integerPool.getObject();

		System.out.println(integerPool);

		try
		{
			Thread.sleep(5000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		System.out.println(integerPool);

		Integer intVar2 = integerPool.getObject();

		System.out.println(integerPool);

		Integer intVar3 = integerPool.getObject();

		try
		{
			Thread.sleep(5000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		System.out.println(integerPool);
	}
}
