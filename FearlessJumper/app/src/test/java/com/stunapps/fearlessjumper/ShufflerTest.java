package com.stunapps.fearlessjumper;

import android.util.Log;

import com.stunapps.fearlessjumper.core.Shuffler;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by sunny.s on 11/02/18.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public class ShufflerTest
{
	Shuffler<Integer> shuffler;

	@BeforeClass
	public static void mock()
	{
		PowerMockito.mockStatic(Log.class);
	}

	@Test
	public void testShufflerWeights()
	{
		final int testCount = 10000;

		int hundred = 100;
		int two_hundred = 200;
		int three_hundred = 300;
		shuffler = new Shuffler.Builder<Integer>().returnItem(hundred).withWeight(1f).returnItem(
				two_hundred).withWeight(7f).returnItem(three_hundred).withWeight(2f).build();

		int hundreds = 0;
		int two_hundreds = 0;
		int three_hundreds = 0;

		for (int i = 0; i < testCount; i++)
		{
			Integer integer = shuffler.shuffle();
			if (integer == hundred) hundreds++;
			else if (integer == two_hundred) two_hundreds++;
			else if (integer == three_hundred) three_hundreds++;
		}

		System.out.println(
				"Final count : Hundreds: " + hundreds + " Two hundreds: " + two_hundreds + " " +
						"Three hundreds: " + three_hundreds);
		System.out.println("More than 0.5: " + shuffler.getTotalGenerated());
	}
}
