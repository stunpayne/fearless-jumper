package com.stunapps.fearlessjumper.rules.signal.generation;

import com.stunapps.fearlessjumper.rules.signal.SignalRequest;
import com.stunapps.fearlessjumper.rules.signal.generation.enums.GenerationSignalRequestIntent;

/**
 * Created by sunny.s on 21/04/18.
 */

public class GenerationSignalRequest extends SignalRequest
{
	private GenerationSignalRequestIntent intent;

	public GenerationSignalRequestIntent getIntent()
	{
		return intent;
	}

	public void setIntent(GenerationSignalRequestIntent intent)
	{
		this.intent = intent;
	}
}
