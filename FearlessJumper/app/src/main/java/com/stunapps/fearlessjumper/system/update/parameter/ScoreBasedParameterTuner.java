package com.stunapps.fearlessjumper.system.update.parameter;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.specific.Score;
import com.stunapps.fearlessjumper.system.update.parameter.ParameterTuneRequest
		.ScoreBasedTuneRequest;

/**
 * @author: sunny.s
 * @since 11/05/18.
 */

public class ScoreBasedParameterTuner implements ParameterTuner
{
	@Override
	public void tune(ParameterTuneRequest request)
	{
		ScoreBasedTuneRequest tuneRequest = (ScoreBasedTuneRequest) request;
		Component component = request.getEntity().getComponent(Score.class);
		Score score = (Score) component;
		String threshold = request.getThreshold();

		switch (request.getField())
		{
			case "score":
				if (score.getScore() > Float.valueOf(threshold))
				{
					score.addScore(Float.valueOf(request.getValue()));
				}
		}
	}
}
