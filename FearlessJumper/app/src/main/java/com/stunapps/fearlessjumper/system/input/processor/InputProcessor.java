package com.stunapps.fearlessjumper.system.input.processor;

import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.system.model.InputWrapper;

/**
 * Created by sunny.s on 20/01/18.
 */

public interface InputProcessor
{
	void update(long deltaTime, Entity player, InputWrapper inputWrapper);
}
