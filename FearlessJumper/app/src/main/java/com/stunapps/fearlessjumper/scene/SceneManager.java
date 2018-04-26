package com.stunapps.fearlessjumper.scene;

/**
 * Created by anand.verma on 30/12/17.
 */

public interface SceneManager
{
	void initialise();

	void destroy();

	void start();

	void stop();

	void pause();

	void resume();
}
