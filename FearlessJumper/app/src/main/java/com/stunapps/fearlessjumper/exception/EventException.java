package com.stunapps.fearlessjumper.exception;

/**
 * Created by sunny.s on 13/02/18.
 */

public class EventException extends RuntimeException
{
	public EventException()
	{
	}

	public EventException(String message)
	{
		super(message);
	}

	public EventException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public EventException(Throwable cause)
	{
		super(cause);
	}
}
