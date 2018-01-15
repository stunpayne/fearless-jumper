package com.stunapps.fearlessjumper.component;

/**
 * Created by anand.verma on 14/01/18.
 */


//TODO: This class is used by both Collider and Renderable. So need to think what package to put this class in.
public class Delta
{
    public float x;
    public float y;

    public Delta(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
}
